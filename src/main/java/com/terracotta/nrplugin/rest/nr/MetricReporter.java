package com.terracotta.nrplugin.rest.nr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.terracotta.nrplugin.cache.MetricProvider;
import com.terracotta.nrplugin.pojo.nr.Agent;
import com.terracotta.nrplugin.pojo.nr.Component;
import com.terracotta.nrplugin.pojo.nr.NewRelicPayload;
import com.terracotta.nrplugin.rest.RestClientBase;
import com.terracotta.nrplugin.util.MetricUtil;
import org.apache.http.entity.ContentType;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/21/14
 * Time: 3:02 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MetricReporter extends RestClientBase {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String X_LICENSE_KEY = "X-License-Key";

    long pid;

    @Autowired
    ExecutorService executorService;

    @Autowired
    MetricUtil metricUtil;

    @Autowired
    MetricProvider metricProvider;

    @Value("${com.saggs.terracotta.nrplugin.nr.name}")
    String name;

    @Value("${com.saggs.terracotta.nrplugin.nr.guid}")
    String guid;

    @Value("${com.saggs.terracotta.nrplugin.nr.hostname}")
    String hostname;

    @Value("${com.saggs.terracotta.nrplugin.version}")
    String version;

    @Value("${com.saggs.terracotta.nrplugin.nr.licenseKey}")
    String licenseKey;

    @PostConstruct
    private void init() {
        Sigar sigar = new Sigar();

        try {
            pid = sigar.getPid();
        } catch (Error e) {
            log.error("Could not infer PID.");
            pid = -1;
        }

        try {
            hostname = sigar.getNetInfo().getHostName();
        } catch (Error e) {
            log.error("Could not infer hostname.");
        } catch (Exception ex) {
            log.error("Could not infer hostname.");
        }
    }

    @Scheduled(fixedDelay=30000, initialDelay = 5000)
    public void reportMetrics() {
        NewRelicPayload newRelicPayload = new NewRelicPayload(
                new Agent(hostname, pid, version),
                Collections.singletonList(
                        new Component(name, guid, 30, metricProvider.getAllMetrics())));
        log.info("Attempting to report stats to NewRelic...");
        if (log.isDebugEnabled()) {
            try {
                log.debug("Payload: " + new ObjectMapper().writeValueAsString(newRelicPayload));
            } catch (JsonProcessingException e) {
                log.error("Error serializing payload.", e);
            }
        }

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.set(X_LICENSE_KEY, licenseKey);
        requestHeaders.set(org.apache.http.HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        requestHeaders.set(org.apache.http.HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());
        HttpEntity<NewRelicPayload> requestEntity = new HttpEntity<NewRelicPayload>(newRelicPayload, requestHeaders);
        HttpEntity<String> response = restTemplate.exchange(nrUrl, HttpMethod.POST, requestEntity, String.class);
        log.debug("Received response: " + response);
        log.info("Done reporting to NewRelic.");
    }

}
