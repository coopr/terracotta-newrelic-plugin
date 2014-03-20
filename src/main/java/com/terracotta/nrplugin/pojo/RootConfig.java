package com.terracotta.nrplugin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/11/14
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RootConfig implements Serializable {

    private static final long serialVersionUID = -516019410327286659L;

    @JsonProperty("servers")
    Collection<TerracottaServer> servers;

    public RootConfig() {
    }

    public RootConfig(Collection<TerracottaServer> servers) {
        this.servers = servers;
    }

    public Collection<TerracottaServer> getServers() {
        return servers;
    }

    public void setServers(Collection<TerracottaServer> servers) {
        this.servers = servers;
    }
}
