package com.terracotta.nrplugin.cache.manager;

import com.jayway.jsonpath.JsonPath;
import com.terracotta.nrplugin.pojo.Metric;
import com.terracotta.nrplugin.pojo.MetricDataset;
import com.terracotta.nrplugin.pojo.tmc.ClientStatistics;
import com.terracotta.nrplugin.rest.manager.StatsFetcher;
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

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/21/14
 * Time: 11:31 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class StatsCacher {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ExecutorService executorService;

    @Autowired
    StatsFetcher statsFetcher;

    @Autowired
    MetricUtil metricUtil;

    @Value("#{cacheManager.getCache('statsCache')}")
    Cache statsCache;

    @Value("${com.saggs.terracotta.nrplugin.data.windowSize}")
    int windowSize;

    @Scheduled(fixedDelay=30000, initialDelay = 500)
    public void cacheStats() {
        log.info("Starting to cache all stats...");
        String serverJson = statsFetcher.getServerStatisticsAsString();
        String clientJson = statsFetcher.getClientStatisticsAsString();
        String cacheJson = statsFetcher.getCacheStatisticsAsString();

        for (Metric metric : metricUtil.getMetrics()) {
            if (Metric.Source.server.equals(metric.getSource())) {
                JSONArray servers  = JsonPath.read(serverJson, "$[*]");
                for (Object server : servers) {
                    JSONObject jsonObject = (JSONObject) server;
                    putValue(metric, JsonPath.read(jsonObject, metric.getDataPath()));
                }
            }
            else if (Metric.Source.client.equals(metric.getSource())) {
                JSONArray clients  = JsonPath.read(clientJson, "$[*]");
                for (Object client : clients) {
                    JSONObject jsonObject = (JSONObject) client;
                    putValue(metric, JsonPath.read(jsonObject, metric.getDataPath()));
                }
            }
            else if (Metric.Source.cache.equals(metric.getSource())) {
                JSONArray caches  = JsonPath.read(cacheJson, "$[*]");
                for (Object cache : caches) {
                    JSONObject jsonObject = (JSONObject) cache;
                    putValue(metric, JsonPath.read(jsonObject, metric.getDataPath()));
                }
            }
        }
        log.info("Done caching stats.");
    }

//    public void cacheServerStats() {
//        List<ServerStatistics> serverStatisticsList = statsFetcher.getServerStatistics();
//        String baseKey = metricUtil.toMetricPath("Component", "Terracotta", "Servers");
//        for (ServerStatistics serverStatistics : serverStatisticsList) {
//            Map storageStats = (Map) serverStatistics.getStatistics().get("StorageStats");
//            Map data = (Map) storageStats.get("DATA");
//            Map offheap = (Map) storageStats.get("OFFHEAP");
//            putValue(metricUtil.toMetricPath(baseKey, "Data", "Objects", MetricUtil.METRIC_LIVE_OBJECT_COUNT), Metric.Unit.Count,
//                    (Integer) serverStatistics.getStatistics().get(MetricUtil.METRIC_LIVE_OBJECT_COUNT));
//            putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "Heap", MetricUtil.METRIC_USED), Metric.Unit.Bytes,
//                    (Integer) data.get(MetricUtil.METRIC_USED));
//            putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "Heap", MetricUtil.METRIC_MAX), Metric.Unit.Bytes,
//                    (Integer) data.get(MetricUtil.METRIC_MAX));
//            putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "OffHeap", MetricUtil.METRIC_USED), Metric.Unit.Bytes,
//                    (Integer) offheap.get(MetricUtil.METRIC_USED));
//            putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "OffHeap", MetricUtil.METRIC_MAX), Metric.Unit.Bytes,
//                    (Integer) offheap.get(MetricUtil.METRIC_MAX));
//        }
//
//    }

//    public void cacheClientStats() {
//        String baseKey = metricUtil.toMetricPath("Component", "Terracotta", "Ehcache", "Clients");
//        List<ClientStatistics> clientStatisticsList = statsFetcher.getClientStatistics();
//        for (ClientStatistics clientStatistics : clientStatisticsList) {
//
//        }
//    }

//    public void cacheCacheStats() {
//        String baseKey = metricUtil.toMetricPath("Component", "Terracotta", "Ehcache", "Clients");
//        List<CacheStatistics> cacheStatisticsListList = statsFetcher.getCacheStatistics();
//        for (CacheStatistics cacheStatistics : cacheStatisticsListList) {
//            String name = cacheStatistics.getName();
//            for (String statName : metricUtil.getCacheStatsNames()) {
//                putValue(metricUtil.toMetricPath(baseKey, name, statName), Metric.Unit.Count,
//                        (Integer) cacheStatistics.getAttributes().get(statName));
//            }
//        }
//    }

    private void putValue(Metric metric, Object value) {
        MetricDataset metricDataset = getMetricDataset(metric.getReportedPath());
        if (metricDataset == null) metricDataset = new MetricDataset(metric, windowSize);

        if (value instanceof Integer) metricDataset.addValue((Integer) value);
        else if (value instanceof Double) metricDataset.addValue((Double) value);
        else if (value instanceof Long) metricDataset.addValue((Long) value);
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
