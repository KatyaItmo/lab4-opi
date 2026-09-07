package org.example.jmx;

import javax.management.openmbean.TabularData;

public interface StatisticMBean {
    TabularData getStatistic() throws Exception;

    void calculateStatistic(String username, boolean isHit);
}
