package org.example.jmx;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class TotalMissStats extends NotificationBroadcasterSupport implements TotalMissStatsMXBean{
    private final Map<String, UserStats> statsMap = new ConcurrentHashMap<>();
    private final AtomicLong sequenceNumber = new AtomicLong(1);

    @Override
    public List<UserStats> getStats() {
        return new ArrayList<>(statsMap.values());
    }

    @Override
    public void registerPoint(String username, boolean isHit) {
        UserStats stats = statsMap.computeIfAbsent(
                username,
                user -> new UserStats(user, 0, 0, 0)
        );

        if (isHit) {
            stats.registerHit();
        } else {
            long consecutiveMisses = stats.registerMiss();

            if (consecutiveMisses >= 3) {
                sendMissNotification(username, consecutiveMisses);
            }
        }
    }

    private void sendMissNotification(String username, long consecutiveMisses) {
        String message = "User " + username + " missed " + consecutiveMisses +" times in a row!";

        Notification notification = new Notification(
                "point.miss.streak",
                this,
                sequenceNumber.getAndIncrement(),
                System.currentTimeMillis(),
                message
        );

        sendNotification(notification);
    }
}
