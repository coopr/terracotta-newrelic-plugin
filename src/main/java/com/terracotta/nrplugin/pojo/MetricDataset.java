package com.terracotta.nrplugin.pojo;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/12/14
 * Time: 4:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class MetricDataset implements Serializable {

    private static final long serialVersionUID = 483809302495395084L;

//    List<String> pathComponents = new LinkedList<String>();
    String path;
    MetricUnit unit;
    DescriptiveStatistics statistics;

    public MetricDataset() {
    }

    public MetricDataset(String path, MetricUnit unit) {
        this.path = path;
        this.unit = unit;
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
        return getPath() + "[" + unit + "]";
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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public MetricUnit getUnit() {
        return unit;
    }

    public void setUnit(MetricUnit unit) {
        this.unit = unit;
    }

}
