package com.norcode.bukkit.toughanvils.command;

import com.norcode.bukkit.toughanvils.ToughAnvils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.LinkedList;

public class ToughAnvilsCommand extends BaseCommand {

    public ToughAnvilsCommand(ToughAnvils plugin) {
        super(plugin, "toughanvils", new String[] {}, "toughanvils.command", null);
        registerSubcommand(new SetCommand(plugin));
        registerSubcommand(new UnSetCommand(plugin));
    }

    @Override
    protected void onExecute(CommandSender commandSender, String label, LinkedList<String> args) throws CommandError {
        if (!(commandSender instanceof Player)) {
            throw new CommandError("This command is only available to players.");
        }
        Player p = (Player) commandSender;
        p.sendMessage ("Type /toughanvils set to create an indestructible.");
    }
}
