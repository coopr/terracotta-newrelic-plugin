package com.terracotta.nrplugin.pojo;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/12/14
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReportedMetric implements Serializable {

    private static final long serialVersionUID = -5040545136571234201L;

    String key;
    Double[] values = new Double[5];

    public ReportedMetric() {
    }

    public ReportedMetric(String key, Double[] values) {
        this.key = key;
        this.values = values;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double[] getValues() {
        return values;
    }

    public void setValues(Double[] values) {
        this.values = values;
    }
}
