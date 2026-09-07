package org.example.events;

import jdk.jfr.*;

@Label("Point")
@Category({"event_log", "points"})
@Description("Event: user clicked point")
@StackTrace(false)
@Threshold("0 ms")
public class PointSetEvent extends Event {

    @Label("User Name")
    public String username;

    @Label("Coordinate X")
    public double x;

    @Label("Coordinate Y")
    public double y;

    @Label("Radius")
    public double r;

    @Label("Hit or miss")
    public boolean isHit;
}
