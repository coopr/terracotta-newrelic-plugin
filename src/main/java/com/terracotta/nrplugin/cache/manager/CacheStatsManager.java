package com.terracotta.nrplugin.cache.manager;

import com.terracotta.nrplugin.util.MetricUtil;
import net.sf.ehcache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/6/14
 * Time: 6:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class CacheStatsManager {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ExecutorService executorService;

    @Autowired
    MetricUtil metricUtil;

    @Value("#{cacheManager.getCache('statsCache')}")
    Cache statsCache;


    @Scheduled(fixedDelay=30000, initialDelay = 500)
    public void fetch() {
        fetchServerStats();
    }

    public void fetchServerStats() {

    }

    public void fetchClientStats() {

    }

    @Scheduled(fixedDelay=30000, initialDelay = 500)
    public void reportMetrics() {

    }

}
