package com.terracotta.nrplugin.pojo;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SynchronizedDescriptiveStatistics;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/12/14
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetricDataset implements Serializable {

    private static final long serialVersionUID = 483809302495395084L;
    public static final int WINDOW_SIZE_DEFAULT = 100;

    Metric metric;
    SynchronizedDescriptiveStatistics statistics;
    long lastUpdate;

    public MetricDataset() {
        statistics = new SynchronizedDescriptiveStatistics(WINDOW_SIZE_DEFAULT);
    }

    public MetricDataset(Metric metric, int windowSize) {
        this.metric = metric;
        statistics = new SynchronizedDescriptiveStatistics(windowSize);
    }

    public void addValue(double value) {
        statistics.addValue(value);
        lastUpdate = System.currentTimeMillis();
    }

    public String getKey() {
        return metric.getReportedPath() + "[" + metric.getUnit() + "]";
    }

    public Double getLastValue() {
        return statistics.getElement((int) statistics.getN() - 1);
    }

    public Metric getMetric() {
        return metric;
    }

    public DescriptiveStatistics getStatistics() {
        return statistics;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }
}
