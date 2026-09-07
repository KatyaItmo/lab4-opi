package org.example.jmx;

import javax.management.MBeanNotificationInfo;
import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import javax.management.openmbean.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CountMisses extends NotificationBroadcasterSupport implements CountMissesMBean {
    private final Map<String, int[]> missStatsMap = new ConcurrentHashMap<>();
    private int totalMisses = 0;

    private static CompositeType rowType;
    private static TabularType tabularType;

    private long sequenceNumber = 1;

    static {
        try {
            String[] itemNames = {"username", "totalMisses", "lastMisses"};
            String[] itemDescriptions = {"User Login", "Total number of user misses", "The latest series of user misses"};
            OpenType<?>[] itemTypes = {SimpleType.STRING, SimpleType.INTEGER, SimpleType.INTEGER};

            rowType = new CompositeType(
                    "UserMissStats",
                    "Misses for user",
                    itemNames,
                    itemDescriptions,
                    itemTypes
            );

            String[] keys = {"username"};

            tabularType = new TabularType(
                    "MissStatsTable",
                    "Table of miss stats",
                    rowType,
                    keys
            );

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public TabularData getMissStats() throws OpenDataException {
        TabularData table = new TabularDataSupport(tabularType);

        for (Map.Entry<String, int[]> entry: missStatsMap.entrySet()) {
            String username = entry.getKey();
            int[] stats = entry.getValue();
            int totalMisses = stats[0];
            int lastMisses = stats[1];

            Object[] itemValues = {username, totalMisses, lastMisses};
            String[] fields = {"username", "totalMisses", "lastMisses"};

            CompositeData row = new CompositeDataSupport(
                    rowType,
                    fields,
                    itemValues
            );

            table.put(row);
        }

        return table;
    }

    @Override
    public synchronized void registerPoint(String username, boolean isHit) {
        int[] stats = missStatsMap.computeIfAbsent(username, k -> new int[]{0, 0});

        if(!isHit) {
            totalMisses++;
            stats[0]++;
            stats[1]++;

            if (stats[1] % 3 == 0) {
                String message = "User " + username + " missed " + stats[1] +" times in a row!";

                Notification notification = new Notification(
                        "point.miss.streak",
                        this,
                        sequenceNumber++,
                        System.currentTimeMillis(),
                        message
                );

                sendNotification(notification);
            }

        } else {
            totalMisses = 0;
            stats[1] = 0;
        }
    }

    public int getTotalMisses() {
        return totalMisses;
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = {"point.miss.streak"};
        String name = Notification.class.getName();
        String description = "Notification sent when a user reaches a miss streak threshold";

        MBeanNotificationInfo info = new MBeanNotificationInfo(types, name, description);
        return new MBeanNotificationInfo[] { info };
    }
}
