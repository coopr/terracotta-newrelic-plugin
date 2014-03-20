package com.terracotta.nrplugin.cache.manager;

import com.terracotta.nrplugin.pojo.MetricDataset;
import com.terracotta.nrplugin.pojo.MetricUnit;
import com.terracotta.nrplugin.util.MetricUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/12/14
 * Time: 4:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class DatasetManager {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("#{cacheManager.getCache('datasetCache')}")
    Cache datasetCache;

    @Autowired
    MetricUtil metricUtil;

//    @Cacheable("datasetCache")
    public MetricDataset getMetricDataset(String key) {
        Element element = datasetCache.get(key);
        if (element != null) return (MetricDataset) element.getObjectValue();
        else return null;
    }
//
//    public void putMetricDataset(MetricDataset metricDataset) {
//        datasetCache.put(new Element(metricDataset.getKey(), metricDataset));
//    }
//
//    public void addToDataset(L2ClientRuntimeInfo l2ClientRuntimeInfo) {
//        String baseKey = metricUtil.toMetricPath("Component", "Terracotta", "Clients", "All", "Transactions");
//        putValue(metricUtil.toMetricPath(baseKey, "Faults"), MetricUnit.Rate, l2ClientRuntimeInfo.getObjectFaultRate());
//        putValue(metricUtil.toMetricPath(baseKey, "Flushes"), MetricUnit.Rate, l2ClientRuntimeInfo.getObjectFlushRate());
//        putValue(metricUtil.toMetricPath(baseKey, "Pending"), MetricUnit.Rate, l2ClientRuntimeInfo.getPendingTransactionsCount());
//        putValue(metricUtil.toMetricPath(baseKey, "Total"), MetricUnit.Rate, l2ClientRuntimeInfo.getTransactionRate());
//    }
//
//    public void addToDataset(L2RuntimeStatus l2RuntimeStatus) {
//        String baseKey = metricUtil.toMetricPath("Component", "Terracotta", "Servers");
//        putValue(metricUtil.toMetricPath(baseKey, "State"), MetricUnit.Count, l2RuntimeStatus.getState().getStateIntValue());
//    }
//
//    public void addToDataset(L2DataStats l2DataStats) {
//        String baseKey = metricUtil.toMetricPath("Component", "Terracotta", "Servers");
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Objects", "Total"), MetricUnit.Count, l2DataStats.getLiveObjectCount());
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Objects", "OffHeap"), MetricUnit.Count, l2DataStats.getOffheapObjectCachedCount());
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Objects", "Heap"), MetricUnit.Count, l2DataStats.getCachedObjectCount());
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "Heap", "UsedSize"), MetricUnit.Bytes, l2DataStats.getUsedHeap());
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "Heap", "MaxSize"), MetricUnit.Bytes, l2DataStats.getMaxHeap());
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "OffHeap", "KeyMapSize"), MetricUnit.Bytes, l2DataStats.getOffheapMapAllocatedMemory());
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "OffHeap", "MaxSize"), MetricUnit.Bytes, l2DataStats.getOffheapMaxDataSize());
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "OffHeap", "ObjectSize"), MetricUnit.Bytes, l2DataStats.getOffheapObjectAllocatedMemory());
//        putValue(metricUtil.toMetricPath(baseKey, "Data", "Tiers", "OffHeap", "TotalUsedSize"), MetricUnit.Bytes, l2DataStats.getOffheapTotalAllocatedSize());
//    }
//
//    public void addToDataset(L2TransactionsStats l2TransactionsStats) {
//        String baseKey = metricUtil.toMetricPath("Component", "Terracotta", "Servers");
//        putValue(metricUtil.toMetricPath(baseKey, "Transactions", "Tiers", "Heap", "Faults"), MetricUnit.Rate, l2TransactionsStats.getOnHeapFaultRate());
//        putValue(metricUtil.toMetricPath(baseKey, "Transactions", "Tiers", "Heap", "Flushes"), MetricUnit.Rate, l2TransactionsStats.getOnHeapFlushRate());
//        putValue(metricUtil.toMetricPath(baseKey, "Transactions", "Tiers", "OffHeap", "Faults"), MetricUnit.Rate, l2TransactionsStats.getOffHeapFaultRate());
//        putValue(metricUtil.toMetricPath(baseKey, "Transactions", "Tiers", "OffHeap", "Flushes"), MetricUnit.Rate, l2TransactionsStats.getOffHeapFlushRate());
//        putValue(metricUtil.toMetricPath(baseKey, "Transactions", "Tiers", "Disk", "Faults"), MetricUnit.Rate, l2TransactionsStats.getL2DiskFaultRate());
//        putValue(metricUtil.toMetricPath(baseKey, "Transactions", "Total"), MetricUnit.Rate, l2TransactionsStats.getTransactionRate());
//        putValue(metricUtil.toMetricPath(baseKey, "LockRecalls"), MetricUnit.Rate, l2TransactionsStats.getGlobalLockRecallRate());
//    }
//
//    private void putValue(String key, MetricUnit unit, double value) {
//        MetricDataset metricDataset = getMetricDataset(key);
//        if (metricDataset == null) metricDataset = new MetricDataset(key, unit);
//        metricDataset.addValue(value);
//        putMetricDataset(metricDataset);
//    }
}