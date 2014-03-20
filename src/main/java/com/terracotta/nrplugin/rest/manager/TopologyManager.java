package com.terracotta.nrplugin.rest.manager;

import com.terracotta.nrplugin.pojo.tmc.Topologies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/20/14
 * Time: 8:55 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TopologyManager {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    List<Topologies> topologies;

    @Value("${com.saggs.terracotta.nrplugin.tmc.url}")
    String tmcUrl;

//    @PostConstruct
//    private void init() {
//        // Connect to TMC and load topology
//    }

    @Scheduled(fixedDelay=600000, initialDelay = 500)
    private void getTopology() {
        log.info("Fetching Topologies from TMC");
        topologies = getTopologies();
    }

    public List<Topologies> getTopologies() {
        return restTemplate.getForObject(tmcUrl + "/agents/topologies/", List.class);
    }
}
