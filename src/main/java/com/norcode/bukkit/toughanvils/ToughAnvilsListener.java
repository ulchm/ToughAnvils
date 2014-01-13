package com.norcode.bukkit.toughanvils;

import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.InventoryHolder;

public class ToughAnvilsListener implements Listener {
    private final ToughAnvils plugin;

    public ToughAnvilsListener(ToughAnvils plugin) {
        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getInventory() instanceof AnvilInventory) {
            BlockState bs = (BlockState) event.getInventory().getHolder();
            if (plugin.isToughAnvil(bs.getLocation())) {
                bs.setRawData((byte) 0);
            }
        }
    }
}
