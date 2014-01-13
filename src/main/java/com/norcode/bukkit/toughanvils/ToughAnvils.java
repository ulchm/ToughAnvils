package com.norcode.bukkit.toughanvils;

import com.norcode.bukkit.toughanvils.command.ToughAnvilsCommand;
import com.norcode.bukkit.toughanvils.util.ConfigAccessor;
import com.norcode.bukkit.toughanvils.util.ParseUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ToughAnvils extends JavaPlugin {

    private ConfigAccessor toughAnvilsConfig;

    @Override
    public void onEnable() {
        toughAnvilsConfig = new ConfigAccessor(this, "ToughAnvils.yml");
        getServer().getPluginManager().registerEvents(new ToughAnvilsListener(this), this);
        new ToughAnvilsCommand(this);
    }

    public void setToughAnvil(Location location, Player p) {
        if (location.getBlock().getType().equals(Material.ANVIL)){
            List<String> locationList = toughAnvilsConfig.getConfig().getStringList("anvils");
            for (String s: locationList) {
                if (ParseUtil.parseLocation(s).equals(ParseUtil.BlockLocationToString(location, false))) {
                    p.sendMessage("This anvil is already in the configuration file.");
                    return;
                }
            }
            locationList.add(ParseUtil.BlockLocationToString(location, false));
            toughAnvilsConfig.getConfig().set("anvils", locationList);
            toughAnvilsConfig.saveConfig();
            p.sendMessage("Setting anvil at " + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ() + " to be a tough anvil.");
        } else {
            p.sendMessage("You must be looking at an anvil to set it as tough.");
        }

    }

    public void unSetToughAnvil(Location location, Player p) {
        if (location.getBlock().getType().equals(Material.ANVIL)) {
            List<String> locationList = toughAnvilsConfig.getConfig().getStringList("anvils");
            for (String s: locationList) {
                if (ParseUtil.parseLocation(s).equals(ParseUtil.BlockLocationToString(location, false))) {
                    locationList.remove(s);
                    toughAnvilsConfig.getConfig().set("anvils", locationList);
                    toughAnvilsConfig.saveConfig();
                    p.sendMessage("Removed tough from anvil at " + location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ());
                    return;
                }
            }
            p.sendMessage("No tough anvil found where you are looking.");
        } else {
            p.sendMessage("You must be looking at an anvil to unset it.");
        }
    }

    public boolean isToughAnvil(Location location) {
        if (location.getBlock().getType().equals(Material.ANVIL)) {
            List<String> locationList = toughAnvilsConfig.getConfig().getStringList("anvils");
            for (String s: locationList) {
                if (ParseUtil.parseLocation(s).equals(ParseUtil.BlockLocationToString(location, false))) {
                    return true;
                }
            }
        }
        return false;
    }
}
