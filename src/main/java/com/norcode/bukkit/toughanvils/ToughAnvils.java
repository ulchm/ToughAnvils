package com.norcode.bukkit.toughanvils;

import com.norcode.bukkit.toughanvils.command.ToughAnvilsCommand;
import com.norcode.bukkit.toughanvils.util.ConfigAccessor;
import com.norcode.bukkit.toughanvils.util.ParseUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ToughAnvils extends JavaPlugin {

    private ConfigAccessor toughAnvilsConfig;
	private List<Location> anvilLocations;

    @Override
    public void onEnable() {
        toughAnvilsConfig = new ConfigAccessor(this, "ToughAnvils.yml");
        getServer().getPluginManager().registerEvents(new ToughAnvilsListener(this), this);
        new ToughAnvilsCommand(this);
		loadLocations();
    }

	private void loadLocations() {
		anvilLocations = new ArrayList<Location>();
		for (String s: toughAnvilsConfig.getConfig().getStringList("anvils")) {
			anvilLocations.add(ParseUtil.parseLocation(s));
		}
	}

	public void setToughAnvil(Location location) {
		if (!anvilLocations.contains(location)) {
			anvilLocations.add(location);
			toughAnvilsConfig.getConfig().set("anvils", serializeLocationList(anvilLocations));
			toughAnvilsConfig.saveConfig();
		}
	}

	private List<String> serializeLocationList(List<Location> anvilLocations) {
		List<String> locs = new ArrayList<String>(anvilLocations.size());
		for (Location l: anvilLocations) {
			locs.add(ParseUtil.BlockLocationToString(l, false));
		}
		return locs;
	}


    public void unSetToughAnvil(Location location) {
        if (anvilLocations.contains(location)) {
			anvilLocations.remove(location);
			toughAnvilsConfig.getConfig().set("anvils", serializeLocationList(anvilLocations));
			toughAnvilsConfig.saveConfig();
		}
    }

    public boolean isToughAnvil(Location location) {
        if (location.getBlock().getType().equals(Material.ANVIL)) {
        	return anvilLocations.contains(location);
        }
        return false;
    }
}
