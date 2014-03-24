package com.terracotta.nrplugin.pojo;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.springframework.beans.factory.annotation.Value;

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
    DescriptiveStatistics statistics;

    public MetricDataset() {
        statistics = new DescriptiveStatistics(WINDOW_SIZE_DEFAULT);
    }

    public MetricDataset(Metric metric, int windowSize) {
        this.metric = metric;
        statistics = new DescriptiveStatistics(windowSize);
    }

//    public MetricDataset addPath(String pathComponent) {
//        pathComponents.add(pathComponent);
//        return this;
//    }

    public void addValue(double value) {
        statistics.addValue(value);
    }

    public DescriptiveStatistics getStatistics() {
        return statistics;
    }

    public String getKey() {
        return metric.getReportedPath() + "[" + metric.getUnit() + "]";
    }

//    public String getPath() {
//        String path = "";
//        Iterator<String> i = pathComponents.iterator();
//        while (i.hasNext()) {
//            path += i.next();
//            if (i.hasNext()) path += "\\/";
//        }
//        return path;
//    }


    public Metric getMetric() {
        return metric;
    }

    public void setMetric(Metric metric) {
        this.metric = metric;
    }

    public void setStatistics(DescriptiveStatistics statistics) {
        this.statistics = statistics;
    }
}
