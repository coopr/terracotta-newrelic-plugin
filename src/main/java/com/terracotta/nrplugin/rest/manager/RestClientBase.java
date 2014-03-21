package com.terracotta.nrplugin.rest.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/20/14
 * Time: 11:35 AM
 * To change this template use File | Settings | File Templates.
 */
public abstract class RestClientBase {

    final Logger log = LoggerFactory.getLogger(this.getClass());

    RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    @Value("${com.saggs.terracotta.nrplugin.tmc.url}")
    String tmcUrl;

}
