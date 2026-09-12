package org.example.jmx;

import java.util.List;

public interface TotalMissStatsMXBean {
    List<UserStats> getStats();

    void registerPoint(String username, boolean isHit);
}
