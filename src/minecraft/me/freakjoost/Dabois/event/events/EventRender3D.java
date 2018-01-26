package me.freakjoost.Dabois.event.events;

import me.freakjoost.Dabois.event.Event;

public class EventRender3D extends Event {

	public float particlTicks;

	public EventRender3D(float particlTicks) {
		super(Type.PRE);
		this.particlTicks = particlTicks;
	}

}
