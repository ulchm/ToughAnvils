package com.norcode.bukkit.toughanvils.command;

import com.norcode.bukkit.toughanvils.ToughAnvils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class SetCommand extends BaseCommand {
    public SetCommand(ToughAnvils plugin) {
        super(plugin, "set", new String[] {"create","new"}, "toughanvils.command.set", null);
    }

    @Override
    protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
        if (!(commandSender instanceof Player)) {
            throw new CommandError("This command is only available to players.");
        }

        Player p = (Player) commandSender;
        p.sendMessage("Setting the anvil to be tough.");
        plugin.setToughAnvil(p.getEyeLocation(), p);

    }
}
