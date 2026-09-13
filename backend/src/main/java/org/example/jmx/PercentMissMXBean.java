package org.example.jmx;

import java.util.List;

public interface PercentMissMXBean {
    List<PercentStats> getPercent();

    void registerPoint(String username, boolean isHit);
}
