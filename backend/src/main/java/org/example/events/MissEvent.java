package org.example.events;

import jdk.jfr.*;

@Category({"eventLog", "misses"})
@Name("org.example.events.MissEvent")
@Label("Miss Point")
@Threshold("0 ms")
public class MissEvent extends Event {

    @Label("User Login")
    public String username;

    @Label("X coordinate")
    public double x;

    @Label("Y coordinate")
    public double y;

    @Label("Radius")
    public double r;

}
