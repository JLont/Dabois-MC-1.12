package me.freakjoost.Dabois.event.events;

import me.freakjoost.Dabois.event.Event;

public class EventMove extends Event {

    public double x;
    public double y;
    public double z;

    public EventMove(double x, double y, double z) {
        super(Type.PRE);
        this.x = x;
        this.y = y;
        this.z = z;
    }

}
