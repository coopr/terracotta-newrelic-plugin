package com.terracotta.nrplugin.pojo;

import com.terracotta.nrplugin.util.MetricUtil;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/24/14
 * Time: 8:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class Metric implements Serializable {

    private static final long serialVersionUID = -1055398640926238446L;

    String reportedPath;
    String dataPath;
    Source source;
    Unit unit;

    public Metric() {
    }

    public Metric(String dataPath, String reportedPath, Source source, Unit unit) {
        if (reportedPath == null) throw new IllegalArgumentException("reportedPath cannot be null.");
        if (dataPath == null) throw new IllegalArgumentException("dataPath cannot be null.");
        this.dataPath = dataPath;
        this.reportedPath = reportedPath;
        this.source = source;
        this.unit = unit;
    }

    public String getName() {
        String[] split = reportedPath.split(MetricUtil.PATH_SEPARATOR);
        return split.length > 0 ? split[split.length - 1] : null;
    }

    public String getDataPath() {
        return dataPath;
    }

    public void setDataPath(String dataPath) {
        this.dataPath = dataPath;
    }

    public String getReportedPath() {
        return reportedPath;
    }

    public void setReportedPath(String reportedPath) {
        this.reportedPath = reportedPath;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public static enum Source {server, client, cache}

    public static enum Unit
    {
        Count( "count" ),
        CountSecond( "count/sec" ),
        Bytes( "bytes" ),
        QueriesSecond( "queries/sec" ),
        Rate( "value/sec" ),
        BytesSecond( "bytes/sec" ),
        Percent( "percent" );

        final private String m_name;

        private Unit(final String name)
        {
            m_name = name;
        }

        public String getName()
        {
            return m_name;
        }
    }
}
