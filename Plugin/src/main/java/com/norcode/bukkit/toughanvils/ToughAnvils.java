package com.norcode.bukkit.toughanvils;

import com.norcode.bukkit.toughanvils.command.ToughAnvilsCommand;
import com.norcode.bukkit.toughanvils.util.ConfigAccessor;
import com.norcode.bukkit.toughanvils.util.ParseUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class ToughAnvils extends JavaPlugin {

    private ConfigAccessor toughAnvilsConfig;
	private List<Location> anvilLocations;
	private IAnvilLocator anvilLocator;

	@Override
    public void onEnable() {
        toughAnvilsConfig = new ConfigAccessor(this, "ToughAnvils.yml");
        getServer().getPluginManager().registerEvents(new ToughAnvilsListener(this), this);
        new ToughAnvilsCommand(this);
		loadLocations();
		initializeAnvilLocator();
    }

	private void initializeAnvilLocator() {
		String packageName = this.getServer().getClass().getPackage().getName();
		// Get full package string of CraftServer.
		// org.bukkit.craftbukkit.versionstring (or for pre-refactor, just org.bukkit.craftbukkit
		String version = packageName.substring(packageName.lastIndexOf('.') + 1);
		// Get the last element of the package
		if (version.equals("craftbukkit")) { // If the last element of the package was "craftbukkit" we are now pre-refactor
			version = "pre";
		}
		try {
			final Class<?> clazz = Class.forName("com.norcode.bukkit.toughanvils." + version + ".AnvilLocator");
			// Check if we have a NMSHandler class at that location.
			if (IAnvilLocator.class.isAssignableFrom(clazz)) { // Make sure it actually implements NMS
				this.anvilLocator = (IAnvilLocator) clazz.getConstructor().newInstance(); // Set our handler
			}
		} catch (final Exception e) {
			getLogger().log(Level.SEVERE, "Exception loading implementation: ", e);

			this.getLogger().severe("Could not find support for this craftbukkit version " + version + ".");
			this.getLogger().info("Check for updates at http://dev.bukktit.org/bukkit-plugins/portable-horses/");
			this.setEnabled(false);
			return;
		}
	}

	private void loadLocations() {
		anvilLocations = new ArrayList<Location>();
		for (String s: toughAnvilsConfig.getConfig().getKeys(false)) {
			anvilLocations.add(ParseUtil.parseLocation(s));
		}
	}

	public void setToughAnvil(Location location) {
		if (!anvilLocations.contains(location)) {
			anvilLocations.add(location);
			toughAnvilsConfig.getConfig().set(ParseUtil.blockLocationToString(location, false), true);
			toughAnvilsConfig.saveConfig();
		}
	}

	private List<String> serializeLocationList(List<Location> anvilLocations) {
		List<String> locs = new ArrayList<String>(anvilLocations.size());
		for (Location l: anvilLocations) {
			locs.add(ParseUtil.blockLocationToString(l, false));
		}
		return locs;
	}


    public void unSetToughAnvil(Location location) {
        if (anvilLocations.contains(location)) {
			anvilLocations.remove(location);
			toughAnvilsConfig.getConfig().set(ParseUtil.blockLocationToString(location, false), null);
			toughAnvilsConfig.saveConfig();
		}
    }

    public boolean isToughAnvil(Location location) {
        if (location.getBlock().getType().equals(Material.ANVIL)) {
        	return anvilLocations.contains(location);
        }
        return false;
    }

	public Location getAnvilLocation(AnvilInventory inventory) {
		return anvilLocator.getAnvilLocation(inventory);
	}
}
