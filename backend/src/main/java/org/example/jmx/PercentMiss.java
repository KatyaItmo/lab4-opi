package org.example.jmx;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PercentMiss implements PercentMissMXBean {
    private final Map<String, PercentStats> statsMap = new ConcurrentHashMap<>();

    @Override
    public List<PercentStats> getPercent() {
        return new ArrayList<>(statsMap.values());
    }

    @Override
    public void registerPoint(String username, boolean isHit) {
        PercentStats stats = statsMap.computeIfAbsent(
                username,
                user -> new PercentStats(user, 0, 0, 0)
        );

        if (isHit) {
            stats.registerHit();
        } else {
            stats.registerMiss();
        }

        stats.calculatePercent();
    }
}
