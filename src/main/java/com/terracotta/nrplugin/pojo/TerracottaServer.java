package com.terracotta.nrplugin.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/11/14
 * Time: 11:32 AM
 * To change this template use File | Settings | File Templates.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TerracottaServer implements Serializable {

    private static final long serialVersionUID = 6580505433849849210L;

    String name;
    String jmxHost;
    int jmxPort;
    String jmxUser;
    String jmxPassword;
    boolean nameDiscovery = false;
    boolean trackUniqueClients = false;
    long intervalInMillis = 5000;

    public TerracottaServer() {
    }

    public TerracottaServer(String name, String jmxHost, int jmxPort, String jmxUser, String jmxPassword,
                            boolean nameDiscovery, boolean trackUniqueClients, long intervalInMillis) {
        this.name = name;
        this.jmxHost = jmxHost;
        this.jmxPort = jmxPort;
        this.jmxUser = jmxUser;
        this.jmxPassword = jmxPassword;
        this.nameDiscovery = nameDiscovery;
        this.trackUniqueClients = trackUniqueClients;
        this.intervalInMillis = intervalInMillis;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJmxHost() {
        return jmxHost;
    }

    public void setJmxHost(String jmxHost) {
        this.jmxHost = jmxHost;
    }

    public int getJmxPort() {
        return jmxPort;
    }

    public void setJmxPort(int jmxPort) {
        this.jmxPort = jmxPort;
    }

    public String getJmxUser() {
        return jmxUser;
    }

    public void setJmxUser(String jmxUser) {
        this.jmxUser = jmxUser;
    }

    public String getJmxPassword() {
        return jmxPassword;
    }

    public void setJmxPassword(String jmxPassword) {
        this.jmxPassword = jmxPassword;
    }

    public boolean isNameDiscovery() {
        return nameDiscovery;
    }

    public void setNameDiscovery(boolean nameDiscovery) {
        this.nameDiscovery = nameDiscovery;
    }

    public boolean isTrackUniqueClients() {
        return trackUniqueClients;
    }

    public void setTrackUniqueClients(boolean trackUniqueClients) {
        this.trackUniqueClients = trackUniqueClients;
    }

    public long getIntervalInMillis() {
        return intervalInMillis;
    }

    public void setIntervalInMillis(long intervalInMillis) {
        this.intervalInMillis = intervalInMillis;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("name", name)
                .append("jmxHost", jmxHost)
                .append("jmxPort", jmxPort)
                .append("jmxUser", jmxUser)
                .append("jmxPassword", jmxPassword)
                .append("nameDiscovery", nameDiscovery)
                .append("trackUniqueClients", trackUniqueClients)
                .append("intervalInMillis", intervalInMillis)
                .toString();
    }
}
