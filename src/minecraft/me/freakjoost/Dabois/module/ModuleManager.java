package me.freakjoost.Dabois.module;

import me.freakjoost.Dabois.Dabois;
import me.freakjoost.Dabois.event.EventTarget;
import me.freakjoost.Dabois.event.events.EventKeyboard;
import me.freakjoost.Dabois.module.combat.Killaura;
import me.freakjoost.Dabois.module.render.ChestEsp;
import me.freakjoost.Dabois.module.render.FullBright;
import me.freakjoost.Dabois.module.render.XRay;
import me.freakjoost.Dabois.module.render.hud.HUD;

import java.util.ArrayList;

public class ModuleManager {

    /**
     * The ArrayList that holds all the modules.
     **/
    private ArrayList<Module> modules;

    public ModuleManager() {
        modules = new ArrayList<Module>();
        Dabois.INSTANCE.EVENT_MANAGER.register(this);
    }

    /**
     * Loads all the modules.
     **/
    public void loadMods() {
        addModule(new HUD());
        addModule(new FullBright());
        addModule(new XRay());
        addModule(new ChestEsp());
        addModule(new Killaura());
    }

    /**
     * A methods that allows you to add a module to the array of all the modules.
     **/
    private void addModule(Module m) {
        modules.add(m);
    }

    /**
     * Returns all the modules.
     **/
    public ArrayList<Module> getModules() {
        return modules; 
    }

    @EventTarget
    public void onKey(EventKeyboard eventKeyBoard) {
        for (Module mod : modules) {
            if (mod.getKeyCode() == eventKeyBoard.getKey()){
            	mod.toggle();
            }
        }
    }

    /**
     * Goes through all the modules and returns an array of all the toggled modules.
     **/
    public ArrayList<Module> getToggledModules() {
        ArrayList<Module> mods = new ArrayList<Module>();
        for (Module m : modules) {
            if (m.isToggled()){
                mods.add(m);
            }
        }
        return mods;
    }

    /**
     * Tries to get the module by name, if none found it returns null.
     **/
    public Module getModule(String name) {
        for (Module m : modules) {
            if (m.getName().equalsIgnoreCase(name))
                return m;
        }
        return null;
    }

    /**
     * Tries to get the module by class, in none found it returns null.
     **/
    public Module getModule(Class<? extends Module> clazz) {
        for (Module m : modules) {
            if (m.getClass() == clazz)
                return m;
        }
        return null;
    }

}
