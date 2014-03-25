package com.terracotta.nrplugin.cache;

import com.terracotta.nrplugin.pojo.Metric;
import com.terracotta.nrplugin.pojo.MetricDataset;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: Jeff
 * Date: 3/25/14
 * Time: 8:26 AM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class MockMetricProvider implements MetricProvider {

    @Override
    public Collection<MetricDataset> getAllMetrics() {
        MetricDataset m1= new MetricDataset(new Metric("fakeDataPath", "Component/MockTerracotta",
                Metric.Source.cache, Metric.Unit.Bytes), 1000 * 60);
        m1.addValue(100);
        m1.addValue(50);
        m1.addValue(300);
        return Collections.singletonList(m1);
    }
}
