package me.freakjoost.Dabois.command.commands;

import me.freakjoost.Dabois.Dabois;
import me.freakjoost.Dabois.command.Command;
import me.freakjoost.Dabois.module.Module;
import me.freakjoost.Dabois.utils.PlayerUtils;

public class ToggleCommand implements Command {

    @Override
    public boolean run(String[] args) {

        if (args.length == 2) {

            Module module = Dabois.INSTANCE.MODULE_MANAGER.getModule(args[1]);

            if (module == null) {
                PlayerUtils.tellPlayer("The module with the name " + args[1] + " does not exist.");
                return true;
            }

            module.toggle();

            return true;
        }


        return false;
    }

    @Override
    public String usage() {
        return "USAGE: -toggle [module]";
    }
}
