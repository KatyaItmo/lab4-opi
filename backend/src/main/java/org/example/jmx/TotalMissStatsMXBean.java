package org.example.jmx;

import java.util.List;

public interface TotalMissStatsMXBean {
    int getStreakMiss();

    List<UserStats> getStats();

    void registerPoint(String username, boolean isHit);
}
