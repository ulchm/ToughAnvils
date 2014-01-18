package com.norcode.bukkit.toughanvils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.metadata.FixedMetadataValue;

public class ToughAnvilsListener implements Listener {
    private final ToughAnvils plugin;
	public ToughAnvilsListener(ToughAnvils plugin) {
        this.plugin = plugin;
	}

	@EventHandler
	public void onPlayerPunchAnvil(PlayerInteractEvent event) {
		if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			if (event.getClickedBlock() != null && event.getClickedBlock().getType().equals(Material.ANVIL)) {
				if (event.getPlayer().hasMetadata("SETTING_TOUGH_ANVIL")) {
					plugin.setToughAnvil(event.getClickedBlock().getLocation());
	                event.getPlayer().removeMetadata("SETTING_TOUGH_ANVIL", plugin);
					event.getPlayer().sendMessage("This anvil is now indestructible");
					event.setCancelled(true);
				} else if (event.getPlayer().hasMetadata("UNSETTING_TOUGH_ANVIL")) {
					plugin.unSetToughAnvil(event.getClickedBlock().getLocation());
					event.getPlayer().removeMetadata("UNSETTING_TOUGH_ANVIL", plugin);
					event.getPlayer().sendMessage("This anvil is no longer indestructible");
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) {
		if (event.getBlock().getType().equals(Material.ANVIL)) {
			if (plugin.isToughAnvil(event.getBlock().getLocation())) {
				plugin.unSetToughAnvil(event.getBlock().getLocation());
			}
		}
	}

	@EventHandler
	public void onBlockChange(EntityChangeBlockEvent event) {
		if (event.getBlock().getType().equals(Material.ANVIL) && event.getEntity().getType().equals(EntityType.FALLING_BLOCK)) {
			if (plugin.isToughAnvil(event.getBlock().getLocation())) {
				plugin.unSetToughAnvil(event.getBlock().getLocation());
				event.getEntity().setMetadata("IS_TOUGH_ANVIL", new FixedMetadataValue(plugin, true));
			}
		} else if (event.getBlock().getType().equals(Material.AIR) && (event.getTo().equals(Material.ANVIL))) {
			if (event.getEntity().hasMetadata("IS_TOUGH_ANVIL")) {
				plugin.setToughAnvil(event.getBlock().getLocation());
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() instanceof AnvilInventory) {
			if (event.getSlotType().equals(InventoryType.SlotType.RESULT)) {
				final Location loc = plugin.getAnvilLocation((AnvilInventory) event.getInventory());
				if (loc !=  null) {
					BlockState bs = (BlockState) loc.getBlock().getState();
					byte b = bs.getRawData();
					b &= ~(1 << 2);
					b &= ~(1 << 3);
					bs.setRawData((byte)b);
					bs.update(true, true);
				}
			}
        }
    }
}
