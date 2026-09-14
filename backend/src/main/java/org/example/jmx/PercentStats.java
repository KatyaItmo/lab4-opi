package org.example.jmx;

public class PercentStats {
    private final String username;
    private long totalPoint;
    private long missPoint;
    private int percent;

    public PercentStats(String username, long totalPoint, long missPoint, int percent) {
        this.username = username;
        this.totalPoint = totalPoint;
        this.missPoint = missPoint;
        this.percent = percent;
    }

    public String getUsername() {
        return username;
    }

    public int getPercent() {
        return percent;
    }

    public void registerHit() {
        this.totalPoint++;
    }

    public void registerMiss() {
        this.totalPoint++;
        this.missPoint++;
    }

    public void calculatePercent() {
        this.percent = (int) Math.round((double) this.missPoint / this.totalPoint * 100);
    }
}
