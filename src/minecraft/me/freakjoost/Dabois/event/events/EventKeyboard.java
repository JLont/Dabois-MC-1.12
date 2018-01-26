package me.freakjoost.Dabois.event.events;

import me.freakjoost.Dabois.event.Event;

public class EventKeyboard extends Event {

    private int k;

    public EventKeyboard(int k) {
        super(Type.PRE);
        this.k = k;
    }

    public int getKey() {
        return this.k;
    }

}
