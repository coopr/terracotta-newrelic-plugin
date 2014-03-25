package com.terracotta.nrplugin.rest.tmc;

import com.terracotta.nrplugin.pojo.tmc.Topologies;
import com.terracotta.nrplugin.rest.RestClientBase;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/20/14
 * Time: 8:55 AM
 * To change this template use File | Settings | File Templates.
 */
//@Service
public class TopologyFetcher extends RestClientBase {

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
