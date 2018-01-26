package me.freakjoost.Dabois.command.commands;

import me.freakjoost.Dabois.Dabois;
import me.freakjoost.Dabois.command.Command;
import me.freakjoost.Dabois.utils.PlayerUtils;

public class HelpCommand implements Command {

    @Override
    public boolean run(String[] args) {
        PlayerUtils.tellPlayer("Here are the list of commands:");
        for (Command c : Dabois.INSTANCE.COMMAND_MANAGER.getCommands().values()) {
            PlayerUtils.tellPlayer(c.usage());
        }
        return true;
    }

    @Override
    public String usage() {
        return "USAGE: -help";
    }

}
