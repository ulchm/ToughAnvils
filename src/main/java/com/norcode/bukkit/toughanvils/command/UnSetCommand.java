package com.norcode.bukkit.toughanvils.command;

import com.norcode.bukkit.toughanvils.ToughAnvils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class UnSetCommand extends BaseCommand {
    public UnSetCommand(ToughAnvils plugin) {
        super(plugin, "unset", new String[] {"destroy","delete","remove"}, "toughanvils.command.unset", null);
    }

    @Override
    protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
        if (!(commandSender instanceof Player)) {
            throw new CommandError("This command is only available to players.");
        }

        Player p = (Player) commandSender;
        plugin.unSetToughAnvil(p.getEyeLocation(), p);
    }
}
