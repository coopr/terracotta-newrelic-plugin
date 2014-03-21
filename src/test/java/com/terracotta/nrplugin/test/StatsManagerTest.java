package com.terracotta.nrplugin.test;

import com.terracotta.nrplugin.app.RestConfig;
import com.terracotta.nrplugin.pojo.tmc.BaseStatistics;
import com.terracotta.nrplugin.pojo.tmc.ClientStatistics;
import com.terracotta.nrplugin.pojo.tmc.ServerStatistics;
import com.terracotta.nrplugin.rest.manager.StatsManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/20/14
 * Time: 9:15 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {RestConfig.class})
public class StatsManagerTest {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StatsManager statsManager;

    @Test
    public void test() {
        log.info("Getting server stats...");
        List<ServerStatistics> serverStatisticsList = statsManager.getServerStatistics();
        log.info("Getting client stats...");
        List<ClientStatistics> clientStatisticsList = statsManager.getClientStatistics();
        log.info("Done.");
    }

}
