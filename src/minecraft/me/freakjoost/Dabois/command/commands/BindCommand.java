package me.freakjoost.Dabois.command.commands;

import me.freakjoost.Dabois.utils.PlayerUtils;
import org.lwjgl.input.Keyboard;

import me.freakjoost.Dabois.Dabois;
import me.freakjoost.Dabois.command.Command;
import me.freakjoost.Dabois.module.Module;

public class BindCommand implements Command {

    @Override
    public boolean run(String[] args) {
        if (args.length == 3) {

            Module m = Dabois.INSTANCE.MODULE_MANAGER.getModule(args[1]);

            if (m == null)
                return false;

            m.setKeyCode(Keyboard.getKeyIndex(args[2].toUpperCase()));
            PlayerUtils.tellPlayer(m.getName() + " has been bound to " + args[2] + ".");
            return true;
        }
        return false;
    }

    @Override
    public String usage() {
        return "USAGE: -bind [module] [key]";
    }

}
