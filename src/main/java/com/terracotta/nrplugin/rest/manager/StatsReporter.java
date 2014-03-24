package com.terracotta.nrplugin.rest.manager;

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
 * Date: 3/21/14
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class StatsReporter {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ExecutorService executorService;

    @Value("#{cacheManager.getCache('statsCache')}")
    Cache statsCache;

    @Scheduled(fixedDelay=30000, initialDelay = 500)
    public void reportMetrics() {
        log.info("Reporting current stats...");
    }

}
