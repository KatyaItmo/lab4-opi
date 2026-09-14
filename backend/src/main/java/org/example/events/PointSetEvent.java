package org.example.events;

import jdk.jfr.*;

@Category({"eventLog", "points"})
@Name("org.example.events.PointSetEvent")
@Label("Point")
@Threshold("0 ms")
public class PointSetEvent extends Event {

    @Label("User Login")
    public String username;

    @Label("X coordinate")
    public double x;

    @Label("Y coordinate")
    public double y;

    @Label("Radius")
    public double r;

    @Label("Hit or miss")
    public boolean isHit;
}
