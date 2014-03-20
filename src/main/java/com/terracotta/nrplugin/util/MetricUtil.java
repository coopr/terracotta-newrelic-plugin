package com.terracotta.nrplugin.util;

import com.terracotta.nrplugin.pojo.ReportedMetric;
import com.terracotta.nrplugin.pojo.StatsBundle;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/12/14
 * Time: 3:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MetricUtil {

    public Collection<ReportedMetric> toMetrics(StatsBundle statsBundle) {
        List<ReportedMetric> reportedMetrics = new ArrayList<ReportedMetric>();
        return reportedMetrics;
    }

    public String toMetricPath(String... values) {
        String path = "";
        Iterator<String> i = Arrays.asList(values).iterator();
        while (i.hasNext()) {
            path += i.next();
            if (i.hasNext()) path += "\\/";
        }
        return path;
    }

}
