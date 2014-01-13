package com.norcode.bukkit.toughanvils;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.AnvilInventory;

public class ToughAnvilsListener implements Listener {
    private final ToughAnvils plugin;
	public ToughAnvilsListener(ToughAnvils plugin) {
        this.plugin = plugin;

	}

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {

        if (event.getInventory() instanceof AnvilInventory) {
			Location loc = NMS.getAnvilLocation((AnvilInventory) event.getInventory());
			if (loc !=  null) {
				BlockState bs = (BlockState) loc.getBlock().getState();
				bs.setRawData((byte)0);
				bs.update(true, true);
			}
        }
    }
}
