package me.freakjoost.Dabois.module.render.hud;

import me.freakjoost.Dabois.Dabois;
import me.freakjoost.Dabois.module.Category;
import me.freakjoost.Dabois.module.Module;
import me.freakjoost.Dabois.module.render.hud.components.TabGUI;
import me.freakjoost.Dabois.module.render.hud.components.ToggledModules;
import me.freakjoost.Dabois.setting.Setting;
import org.lwjgl.input.Keyboard;

public class HUD extends Module {

    private ToggledModules ar;
    private TabGUI tab;

    public static Setting arrayList;
    public static Setting tabGui;

    public HUD() {
        super("HUD", Keyboard.KEY_L, Category.RENDER);

        Dabois.INSTANCE.SETTING_MANAGER.addSetting(arrayList = (new Setting(this, "Actives", true)));
        Dabois.INSTANCE.SETTING_MANAGER.addSetting(tabGui = (new Setting(this, "TabGUI", true)));

        ar = new ToggledModules();
        tab = new TabGUI();
    }

    public void onEnable() {
        super.onEnable();
        Dabois.INSTANCE.EVENT_MANAGER.register(ar);
        Dabois.INSTANCE.EVENT_MANAGER.register(tab);
    }

    public void onDisable() {
        super.onDisable();
        Dabois.INSTANCE.EVENT_MANAGER.unregister(ar);
        Dabois.INSTANCE.EVENT_MANAGER.unregister(tab);
    }


}
