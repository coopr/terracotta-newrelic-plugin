package com.terracotta.nrplugin.rest;

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

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    protected RestTemplate restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

    @Value("${com.saggs.terracotta.nrplugin.tmc.url}")
    protected String tmcUrl;

    @Value("${com.saggs.terracotta.nrplugin.nr.url}")
    protected String nrUrl;

}