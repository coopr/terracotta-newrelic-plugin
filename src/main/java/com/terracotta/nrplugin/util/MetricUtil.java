package com.terracotta.nrplugin.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terracotta.nrplugin.pojo.Metric;
import com.terracotta.nrplugin.pojo.MetricDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/12/14
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MetricUtil {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String PARAMETER_SHOW = "show";

    // Server metrics
    public static final String METRIC_LIVE_OBJECT_COUNT = "LiveObjectCount";
    public static final String METRIC_MAX = "max";
    public static final String METRIC_USED = "used";

    // Client metrics
    public static final String METRIC_READ_RATE = "ReadRate";
    public static final String METRIC_WRITE_RATE = "WriteRate";

    // Cache metrics
    public static final String METRIC_EVICTED_COUNT = "EvictedCount";
    public static final String METRIC_EXPIRED_COUNT = "ExpiredCount";
    public static final String METRIC_CACHE_ON_DISK_HIT_RATE = "CacheOnDiskHitRate";
    public static final String METRIC_CACHE_IN_MEMORY_HIT_RATE = "CacheInMemoryHitRate";
    public static final String METRIC_CACHE_OFF_HEAP_HIT_RATE = "CacheOffHeapHitRate";
    public static final String METRIC_CACHE_HIT_RATE = "CacheHitRate";
    public static final String METRIC_ON_DISK_HIT_COUNT = "OnDiskHitCount";
    public static final String METRIC_IN_MEMORY_HIT_COUNT = "InMemoryHitCount";
    public static final String METRIC_OFF_HEAP_HIT_COUNT = "OffHeapHitCount";
    public static final String METRIC_CACHE_HIT_COUNT = "CacheHitCount";
    public static final String METRIC_ON_DISK_MISS_COUNT = "OnDiskMissCount";
    public static final String METRIC_IN_MEMORY_MISS_COUNT = "InMemoryMissCount";
    public static final String METRIC_OFF_HEAP_MISS_COUNT = "OffHeapMissCount";
    public static final String METRIC_CACHE_MISS_COUNT = "CacheMissCount";
    public static final String METRIC_PUT_COUNT = "PutCount";
    public static final String METRIC_REMOVED_COUNT = "RemovedCount";
    public static final String METRIC_ON_DISK_SIZE = "OnDiskSize";
    public static final String METRIC_IN_MEMORY_SIZE = "InMemorySize";
    public static final String METRIC_OFF_HEAP_SIZE = "OffHeapSize";
    public static final String METRIC_SIZE = "Size";
    public static final String METRIC_ENABLED = "Enabled";

    // NewRelic constants

    public static final String NEW_RELIC_PATH_SEPARATOR = "\\/";
    public static final String NEW_RELIC_MIN = "min";
    public static final String NEW_RELIC_MAX = "max";
    public static final String NEW_RELIC_TOTAL = "total";
    public static final String NEW_RELIC_COUNT = "count";
    public static final String NEW_RELIC_SUM_OF_SQUARES = "sum_of_squares";

    List<String> cacheStatsNames = new ArrayList<String>();
    List<Metric> metrics = new ArrayList<Metric>();
    ObjectMapper mapper = new ObjectMapper();

    @PostConstruct
    private void init() {
        String tc = toMetricPath("Component", "Terracotta");
        String servers = toMetricPath(tc, "Servers");
        String clients = toMetricPath(tc, "Clients");
        String ehcacheClients = toMetricPath(tc, "Ehcache", "Clients");

        // Server metrics
        metrics.add(new Metric("$.statistics." + METRIC_LIVE_OBJECT_COUNT,
                toMetricPath(servers, "Data", "Objects", METRIC_LIVE_OBJECT_COUNT), Metric.Source.server, Metric.Unit.Count));
        metrics.add(new Metric("$.statistics.StorageStats.DATA." + METRIC_USED,
                toMetricPath(servers, "Data", "Objects", METRIC_USED), Metric.Source.server, Metric.Unit.Bytes));
        metrics.add(new Metric("$.statistics.StorageStats.DATA." + METRIC_MAX,
                toMetricPath(servers, "Data", "Objects", METRIC_MAX), Metric.Source.server, Metric.Unit.Bytes));
        metrics.add(new Metric("$.statistics.StorageStats.OFFHEAP." + METRIC_USED,
                toMetricPath(servers, "Data", "Objects", METRIC_USED), Metric.Source.server, Metric.Unit.Bytes));
        metrics.add(new Metric("$.statistics.StorageStats.OFFHEAP." + METRIC_MAX,
                toMetricPath(servers, "Data", "Objects", METRIC_MAX), Metric.Source.server, Metric.Unit.Bytes));

        // Client metrics
        metrics.add(new Metric("$.statistics." + METRIC_READ_RATE,
                toMetricPath(clients, METRIC_READ_RATE), Metric.Source.client, Metric.Unit.Rate));
        metrics.add(new Metric("$.statistics." + METRIC_WRITE_RATE,
                toMetricPath(clients, METRIC_WRITE_RATE), Metric.Source.client, Metric.Unit.Rate));

        // Cache metrics
        metrics.add(new Metric("$.attributes." + METRIC_EVICTED_COUNT,
                toMetricPath(ehcacheClients, METRIC_EVICTED_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_EXPIRED_COUNT,
                toMetricPath(ehcacheClients, METRIC_EXPIRED_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_CACHE_ON_DISK_HIT_RATE,
                toMetricPath(ehcacheClients, METRIC_CACHE_ON_DISK_HIT_RATE), Metric.Source.cache, Metric.Unit.Rate));
        metrics.add(new Metric("$.attributes." + METRIC_CACHE_IN_MEMORY_HIT_RATE,
                toMetricPath(ehcacheClients, METRIC_CACHE_IN_MEMORY_HIT_RATE), Metric.Source.cache, Metric.Unit.Rate));
        metrics.add(new Metric("$.attributes." + METRIC_CACHE_OFF_HEAP_HIT_RATE,
                toMetricPath(ehcacheClients, METRIC_CACHE_OFF_HEAP_HIT_RATE), Metric.Source.cache, Metric.Unit.Rate));
        metrics.add(new Metric("$.attributes." + METRIC_CACHE_HIT_RATE,
                toMetricPath(ehcacheClients, METRIC_CACHE_HIT_RATE), Metric.Source.cache, Metric.Unit.Rate));
        metrics.add(new Metric("$.attributes." + METRIC_ON_DISK_HIT_COUNT,
                toMetricPath(ehcacheClients, METRIC_ON_DISK_HIT_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_IN_MEMORY_HIT_COUNT,
                toMetricPath(ehcacheClients, METRIC_IN_MEMORY_HIT_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_OFF_HEAP_HIT_COUNT,
                toMetricPath(ehcacheClients, METRIC_OFF_HEAP_HIT_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_CACHE_HIT_COUNT,
                toMetricPath(ehcacheClients, METRIC_CACHE_HIT_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_ON_DISK_MISS_COUNT,
                toMetricPath(ehcacheClients, METRIC_ON_DISK_MISS_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_IN_MEMORY_MISS_COUNT,
                toMetricPath(ehcacheClients, METRIC_IN_MEMORY_MISS_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_OFF_HEAP_MISS_COUNT,
                toMetricPath(ehcacheClients, METRIC_OFF_HEAP_MISS_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_CACHE_MISS_COUNT,
                toMetricPath(ehcacheClients, METRIC_CACHE_MISS_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_PUT_COUNT,
                toMetricPath(ehcacheClients, METRIC_PUT_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_REMOVED_COUNT,
                toMetricPath(ehcacheClients, METRIC_REMOVED_COUNT), Metric.Source.cache, Metric.Unit.Count));
        metrics.add(new Metric("$.attributes." + METRIC_ON_DISK_SIZE,
                toMetricPath(ehcacheClients, METRIC_ON_DISK_SIZE), Metric.Source.cache, Metric.Unit.Bytes));
        metrics.add(new Metric("$.attributes." + METRIC_IN_MEMORY_SIZE,
                toMetricPath(ehcacheClients, METRIC_IN_MEMORY_SIZE), Metric.Source.cache, Metric.Unit.Bytes));
        metrics.add(new Metric("$.attributes." + METRIC_OFF_HEAP_SIZE,
                toMetricPath(ehcacheClients, METRIC_OFF_HEAP_SIZE), Metric.Source.cache, Metric.Unit.Bytes));
        metrics.add(new Metric("$.attributes." + METRIC_SIZE,
                toMetricPath(ehcacheClients, METRIC_SIZE), Metric.Source.cache, Metric.Unit.Bytes));
//        metrics.add(new Metric("$.attributes." + METRIC_ENABLED,
//                toMetricPath(ehcacheClients, METRIC_ENABLED), Metric.Source.cache, Metric.Unit.));

        // Populate cache stat names list
        for (Metric metric : metrics) {
            if (Metric.Source.cache.equals(metric.getSource())) {
                cacheStatsNames.add(metric.getName());
            }
        }
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<Metric> metrics) {
        this.metrics = metrics;
    }

    public List<String> getCacheStatsNames() {
        return cacheStatsNames;
    }

    public Map<String, Object> metricsAsJson(Collection<MetricDataset> metrics) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (MetricDataset metricDataset : metrics) {
            try {
                Map.Entry<String, Map<String, Number>> entry = metricAsJson(metricDataset);
                map.put(entry.getKey(), entry.getValue());
            } catch (Exception e) {
                log.error("Error marshalling metric to JSON.", e);
            }
        }
        return map;
    }

    public Map.Entry<String, Map<String, Number>> metricAsJson(MetricDataset metricDataset)
            throws JsonProcessingException {
//        Map<String, Map<String, Number>> metric = new HashMap<String, Map<String, Number>>();
        Map<String, Number> values = new HashMap<String, Number>();
        values.put(NEW_RELIC_MIN, metricDataset.getStatistics().getMin());
        values.put(NEW_RELIC_MAX, metricDataset.getStatistics().getMax());
        values.put(NEW_RELIC_TOTAL, metricDataset.getStatistics().getSum());
        values.put(NEW_RELIC_COUNT, metricDataset.getStatistics().getN());
        values.put(NEW_RELIC_SUM_OF_SQUARES, metricDataset.getStatistics().getSumsq());
//        metric.put(metricDataset.getKey(), values);
        return new AbstractMap.SimpleEntry<String, Map<String, Number>>(metricDataset.getKey(), values);
//        return mapper.writeValueAsString(metric);
    }

    public String toMetricPath(String... values) {
        String path = "";
        Iterator<String> i = Arrays.asList(values).iterator();
        while (i.hasNext()) {
            path += i.next();
            if (i.hasNext()) path += NEW_RELIC_PATH_SEPARATOR;
        }
        return path;
    }

}
