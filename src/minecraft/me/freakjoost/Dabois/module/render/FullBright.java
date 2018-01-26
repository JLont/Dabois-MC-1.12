package me.freakjoost.Dabois.module.render;

import me.freakjoost.Dabois.event.EventTarget;
import me.freakjoost.Dabois.event.events.EventUpdate;
import me.freakjoost.Dabois.module.Category;
import me.freakjoost.Dabois.module.Module;
import org.lwjgl.input.Keyboard;

public class FullBright extends Module {

	private float gamma;
	private float targetGamma;

	public FullBright() {
		super("FullBright", Keyboard.KEY_B, Category.RENDER);
		this.targetGamma = 100F;
	}

	public void onEnable() {
		super.onEnable();
		this.gamma = mc.gameSettings.gammaSetting;
	}

	public void onDisable() {
		super.onDisable();
		mc.gameSettings.gammaSetting = this.gamma;
	}

	@EventTarget
	public void onRender(EventUpdate e) {
		if (mc.gameSettings.gammaSetting < this.targetGamma)
			mc.gameSettings.gammaSetting += 0.2F;
	}

}
