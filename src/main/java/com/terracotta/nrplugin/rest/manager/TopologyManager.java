package com.terracotta.nrplugin.rest.manager;

import com.terracotta.nrplugin.pojo.tmc.Topologies;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/20/14
 * Time: 8:55 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class TopologyManager extends RestClientBase {

    List<Topologies> topologies;

    @Scheduled(fixedDelay=600000, initialDelay = 500)
    private void getTopology() {
        log.info("Fetching Topologies from TMC");
        topologies = getTopologies();
    }

    public List<Topologies> getTopologies() {
        return restTemplate.getForObject(tmcUrl + "/agents/topologies/", List.class);
    }
}
