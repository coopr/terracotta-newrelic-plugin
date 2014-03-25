package com.terracotta.nrplugin.cache;

import com.terracotta.nrplugin.pojo.MetricDataset;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/25/14
 * Time: 8:03 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class DefaultMetricProvider implements MetricProvider {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("#{cacheManager.getCache('statsCache')}")
    Cache statsCache;

    @Override
    public Collection<MetricDataset> getAllMetrics() {
        log.debug("Gathering stats from cache...");
        Set<MetricDataset> metrics = new HashSet<MetricDataset>();
        for (Object key : statsCache.getKeys()) {
            Element element = statsCache.get((key));
            if (element != null && element.getObjectValue() instanceof MetricDataset) {
                metrics.add((MetricDataset) element.getObjectValue());
            }
        }
        log.info("Returning " + metrics.size() + " metric(s) from cache.");
        return metrics;
    }
}
