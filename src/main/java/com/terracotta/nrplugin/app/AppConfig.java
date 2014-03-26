package com.terracotta.nrplugin.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
@ComponentScan(basePackages = {"com.terracotta"},
        excludeFilters = @ComponentScan.Filter(pattern = {".*Mock.*"}, type = FilterType.REGEX))
@PropertySource("classpath:application.properties")
public class AppConfig {

    static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    @Value("${com.saggs.terracotta.nrplugin.executor.threadpool.coreSize}")
    int threadPoolCoreSize;

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

    @Autowired
    ResourceLoader resourceLoader;

    @Bean
    public FactoryBean<net.sf.ehcache.CacheManager> cacheManager() {
        EhCacheManagerFactoryBean bean = new EhCacheManagerFactoryBean();
        bean.setConfigLocation(resourceLoader.getResource("classpath:ehcache.xml"));
        return bean;
    }

}