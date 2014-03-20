package com.terracotta.nrplugin.rest.manager;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.terracotta.nrplugin.cache.manager.CacheStatsManager;
import com.terracotta.nrplugin.pojo.RootConfig;
import com.terracotta.nrplugin.pojo.TerracottaServer;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/12/14
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ServerConfigManager {

    final Logger log = LoggerFactory.getLogger(this.getClass());

//    @Autowired
//    CacheStatsManager cacheStatsManager;
//
//    Map<String, TerracottaServer> serverConfig = new ConcurrentHashMap<String, TerracottaServer>();
//
//    @PostConstruct
//    public void readConfig() throws IOException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        String json = IOUtils.toString(getClass().getClassLoader().getResourceAsStream("tc-servers.json"));
//        log.debug("Read the following TerracottaServer configs: " + json);
//
//        RootConfig rootConfig = objectMapper.readValue(json, RootConfig.class);
//        for (TerracottaServer server : rootConfig.getServers()) {
//            serverConfig.put(server.getName(), server);
//        }
//        log.info("Configured " + rootConfig.getServers().size() + " server(s).");
//    }
//
//    public TerracottaServer getConfig(String serverName) {
//        return serverConfig.get(serverName);
//    }
//
//    public Collection<TerracottaServer> getAllServers() {
//        return serverConfig.values();
//    }

}
