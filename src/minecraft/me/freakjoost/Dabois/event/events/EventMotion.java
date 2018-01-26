package me.freakjoost.Dabois.event.events;

import me.freakjoost.Dabois.event.Event;

public class EventMotion extends Event {

    public float yaw, pitch;
    public boolean ground;
    public double y;

    public EventMotion(Event.Type type, float yaw, float pitch, boolean ground, double y) {
        super(type);
        this.yaw = yaw;
        this.pitch = pitch;
        this.ground = ground;
        this.y = y;
    }
}
