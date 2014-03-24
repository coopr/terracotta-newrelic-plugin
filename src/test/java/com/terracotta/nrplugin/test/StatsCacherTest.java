package com.terracotta.nrplugin.test;

import com.terracotta.nrplugin.app.AppConfig;
import com.terracotta.nrplugin.cache.manager.StatsCacher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/24/14
 * Time: 9:25 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
public class StatsCacherTest {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    StatsCacher statsCacher;

    @Test
    public void test() {
        log.info("Testing statsCacher...");
        statsCacher.cacheStats();
    }

}
