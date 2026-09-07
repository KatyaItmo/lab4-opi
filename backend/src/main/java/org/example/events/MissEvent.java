package org.example.events;

import jdk.jfr.*;

@Label("Miss")
@Category({"event_log", "missed_points"})
@Description("Event: user clicked and missed")
@StackTrace(false)
@Threshold("0 ms")
public class MissEvent extends Event {

    @Label("User Name")
    public String username;

    @Label("Coordinate X")
    public double x;

    @Label("Coordinate Y")
    public double y;

    @Label("Radius")
    public double r;
}
