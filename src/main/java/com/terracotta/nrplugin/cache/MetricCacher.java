package com.terracotta.nrplugin.cache;

import com.jayway.jsonpath.JsonPath;
import com.terracotta.nrplugin.pojo.Metric;
import com.terracotta.nrplugin.pojo.MetricDataset;
import com.terracotta.nrplugin.rest.tmc.MetricFetcher;
import com.terracotta.nrplugin.util.MetricUtil;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/21/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MetricCacher {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ExecutorService executorService;

    @Autowired
    MetricFetcher metricFetcher;

    @Autowired
    MetricUtil metricUtil;

    @Value("#{cacheManager.getCache('statsCache')}")
    Cache statsCache;

    @Value("#{cacheManager.getCache('diffsCache')}")
    Cache diffsCache;

    Map<String, MetricDataset> lastDataSet = new HashMap<String, MetricDataset>();

    @Value("${com.saggs.terracotta.nrplugin.data.windowSize}")
    int windowSize;

    @Scheduled(fixedDelay=30000, initialDelay = 500)
    public void cacheStats() {
        log.info("Starting to cache all stats...");
        Map<Metric.Source, String> metricData = metricFetcher.getAllMetricData();

        for (Metric metric : metricUtil.getMetrics()) {
            String json = metricData.get(metric.getSource());
            JSONArray objects = JsonPath.read(json, "$[*]");
            for (Object o : objects) {
                MetricDataset metricDataset = getMetricDataset(metric.getReportedPath());
                if (metricDataset == null) metricDataset = new MetricDataset(metric, windowSize);
                expandPathVariables(metricDataset, (JSONObject) o);
                putValue(metricDataset, (JSONObject) o);
                putDiff(lastDataSet.get(metricDataset.getKey()), metricDataset);
            }

        }
        log.info("Done caching stats.");
    }

    private void putValue(MetricDataset metricDataset, JSONObject jsonObject) {
        Object value = JsonPath.read(jsonObject, metricDataset.getMetric().getDataPath());
        if (value instanceof Integer) metricDataset.addValue((Integer) value);
        else if (value instanceof Double) metricDataset.addValue((Double) value);
        else if (value instanceof Long) metricDataset.addValue((Long) value);
        else if (value instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) value;
            metricDataset.addValue(jsonArray.size());
        }
        else {
            log.warn("Class " + value.getClass() + " not numeric.");
        }
        putMetricDataset(metricDataset);
    }

    public MetricDataset getMetricDataset(String key) {
        Element element = statsCache.get(key);
        if (element != null) return (MetricDataset) element.getObjectValue();
        else return null;
    }

    public void putMetricDataset(MetricDataset metricDataset) {
        log.debug("Putting " + metricDataset.getKey() + " to statsCache.");
        statsCache.put(new Element(metricDataset.getKey(), metricDataset));
    }

    private void expandPathVariables(MetricDataset metricDataset, JSONObject jsonObject) {
        log.trace("Attempting to expand key " + metricDataset.getKey());
        for (Map.Entry<String, String> entry : metricDataset.getMetric().getDataPathVariables().entrySet()) {
            if (metricDataset.getActualVarReplaceMap().get(entry.getKey()) == null) {
                metricDataset.putVarReplace(entry.getKey(), (String) JsonPath.read(jsonObject, entry.getValue()));
            }
        }
    }

    public Map<String, Number> getDiff(String key) {
        Element element = diffsCache.get(key);
        if (element != null) return (Map<String, Number>) element.getObjectValue();
        else return null;
    }

    private void putDiff(MetricDataset previous, MetricDataset latest) {
        if (previous == null) {
            log.debug("No previously cached data for metric " + latest.getKey());
        }
        else {
            Map<String, Number> diffs = new HashMap<String, Number>();
            diffs.put(MetricUtil.NEW_RELIC_MIN, latest.getStatistics().getMin() - previous.getStatistics().getMin());
            diffs.put(MetricUtil.NEW_RELIC_MAX, latest.getStatistics().getMax() - previous.getStatistics().getMax());
            diffs.put(MetricUtil.NEW_RELIC_TOTAL, latest.getStatistics().getSum() - previous.getStatistics().getSum());
            diffs.put(MetricUtil.NEW_RELIC_COUNT, latest.getStatistics().getN() - previous.getStatistics().getN());
            diffs.put(MetricUtil.NEW_RELIC_SUM_OF_SQUARES, latest.getStatistics().getSumsq() - previous.getStatistics().getSumsq());

            // Generate new key for diff rather than absolute
            String newKey = new MetricDataset(latest.getMetric(), MetricDataset.Type.diff,
                    latest.getActualVarReplaceMap()).getKey();
            log.debug("Putting " + newKey);
            diffsCache.put(new Element(newKey, diffs));
        }

        // Update lastDataSet after done
        lastDataSet.put(latest.getKey(), latest);
    }

}
