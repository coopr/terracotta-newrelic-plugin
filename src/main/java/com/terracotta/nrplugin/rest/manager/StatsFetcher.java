package com.terracotta.nrplugin.rest.manager;

import com.terracotta.nrplugin.pojo.tmc.CacheStatistics;
import com.terracotta.nrplugin.pojo.tmc.ClientStatistics;
import com.terracotta.nrplugin.pojo.tmc.ServerStatistics;
import com.terracotta.nrplugin.util.MetricUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/20/14
 * Time: 11:24 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class StatsFetcher extends RestClientBase {

    MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<String, String>();

    @Autowired
    MetricUtil metricUtil;

    @PostConstruct
    private void init() {
        requestParams.put(MetricUtil.PARAMETER_SHOW, metricUtil.getCacheStatsNames());
    }

    public String getServerStatisticsAsString() {
        return restTemplate.getForObject(tmcUrl + "/agents/statistics/servers/", String.class);
    }

    public List<ServerStatistics> getServerStatistics() {
        return restTemplate.getForObject(tmcUrl + "/agents/statistics/servers/", List.class);
    }

    public String getClientStatisticsAsString() {
        return restTemplate.getForObject(tmcUrl + "/agents/statistics/clients/", String.class);
    }

    public List<ClientStatistics> getClientStatistics() {
        return restTemplate.getForObject(tmcUrl + "/agents/statistics/clients/", List.class);
    }

    public String getCacheStatisticsAsString() {
        String url = buildUrl("/agents/cacheManagers/caches", requestParams);
        return restTemplate.getForObject(tmcUrl + url, String.class);
    }

    public List<CacheStatistics> getCacheStatistics() {
        String url = buildUrl("/agents/cacheManagers/caches", requestParams);
        return restTemplate.getForObject(tmcUrl + url, List.class);
    }

    public String buildUrl(String baseUrl, Map params) {
        String url = baseUrl;
        for (Object o : params.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            String key = (String) entry.getKey();
            Object val = entry.getValue();
            if (val instanceof Collection) {
                Collection<String> values = (Collection<String>) val;
                for (String multiVal : values) {
                    url = addParam(url, key, multiVal);
                }
            }
            else {
                url = addParam(url, key, (String) val);
            }
        }
        return url;
    }

    private String addParam(String url, String key, String val) {
        String ret = url;
        ret += ret.contains("?") ? "&" : "?";
        ret += key + "=" + val;
        return ret;
    }
}
