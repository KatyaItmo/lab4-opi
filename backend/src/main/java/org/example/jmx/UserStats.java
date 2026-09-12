package org.example.jmx;

public class UserStats {
    private final String username;
    private long totalPoints;
    private long missedPoints;
    private long consecutiveMisses;

    public UserStats(String username, long totalPoints, long missedPoints, long consecutiveMisses) {
        this.username = username;
        this.totalPoints = totalPoints;
        this.missedPoints = missedPoints;
        this.consecutiveMisses = consecutiveMisses;
    }

    public String getUsername() {
        return username;
    }

    public long getTotalPoints() {
        return totalPoints;
    }

    public long getMissedPoints() {
        return missedPoints;
    }

    public long getConsecutiveMisses() {
        return consecutiveMisses;
    }

    public synchronized void registerHit() {
        this.totalPoints++;
        this.consecutiveMisses = 0;
    }

    public synchronized long registerMiss() {
        this.totalPoints++;
        this.missedPoints++;
        this.consecutiveMisses++;

        return this.consecutiveMisses;
    }
}
