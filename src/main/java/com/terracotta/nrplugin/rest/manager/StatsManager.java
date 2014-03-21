package com.terracotta.nrplugin.rest.manager;

import com.terracotta.nrplugin.pojo.tmc.ClientStatistics;
import com.terracotta.nrplugin.pojo.tmc.ServerStatistics;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/20/14
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class StatsManager extends RestClientBase {

    public List<ServerStatistics> getServerStatistics() {
        return restTemplate.getForObject(tmcUrl + "/agents/statistics/servers/", List.class);
    }

    public List<ClientStatistics> getClientStatistics() {
        return restTemplate.getForObject(tmcUrl + "/agents/statistics/clients/", List.class);
    }

}
