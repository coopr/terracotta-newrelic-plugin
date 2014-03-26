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
                JSONObject jsonObject = (JSONObject) o;
                putValue(metric, JsonPath.read(jsonObject, metric.getDataPath()));
            }
        }
        log.info("Done caching stats.");
    }

    private void putValue(Metric metric, Object value) {
        MetricDataset metricDataset = getMetricDataset(metric.getReportedPath());
        if (metricDataset == null) metricDataset = new MetricDataset(metric, windowSize);

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
        statsCache.put(new Element(metricDataset.getKey(), metricDataset));
    }

}
