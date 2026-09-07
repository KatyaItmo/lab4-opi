package org.example.jmx;

import javax.management.openmbean.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Statistic implements StatisticMBean {
    private final Map<String, int[]> statisticMap = new ConcurrentHashMap<>();

    private static CompositeType rowType;
    private static TabularType tabularType;

    static {
        try {
            String[] itemNames = {"username", "missPercentage"};
            String[] itemDescriptions = {"User Login", "Ratio of misses to all clicks"};
            OpenType<?>[] itemTypes = {SimpleType.STRING, SimpleType.INTEGER};

            rowType = new CompositeType(
                    "UserStatistic",
                    "Statistic of user",
                    itemNames,
                    itemDescriptions,
                    itemTypes
            );

            String[] keys = {"username"};

            tabularType = new TabularType(
                    "StatisticTable",
                    "Table of statistic",
                    rowType,
                    keys
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TabularData getStatistic() throws OpenDataException {
        TabularData table = new TabularDataSupport(tabularType);

        for (Map.Entry<String, int[]> entry : statisticMap.entrySet()) {
            String username = entry.getKey();
            int[] stats = entry.getValue();
            int allCount = stats[0];
            int missCount = stats[1];

            int percent = (allCount > 0) ? (int) Math.round(((double) missCount / allCount) * 100) : 0;
            Object[] itemValues = {username, percent};
            String[] fields = {"username", "missPercentage"};

            CompositeData row = new CompositeDataSupport(
                    rowType,
                    fields,
                    itemValues
            );

            table.put(row);
        }

        return table;
    }

    public void calculateStatistic(String username, boolean isHit) {
        int[] stats = statisticMap.computeIfAbsent(username, k -> new int[]{0, 0});

        if (!isHit) {
            stats[1]++;
        }
        stats[0]++;
    }
}
