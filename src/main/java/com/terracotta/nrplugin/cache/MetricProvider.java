package com.terracotta.nrplugin.cache;

import com.terracotta.nrplugin.pojo.MetricDataset;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/25/14
 * Time: 7:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface MetricProvider {

    public Collection<MetricDataset> getAllMetrics();

}