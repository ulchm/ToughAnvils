package com.norcode.bukkit.toughanvils;

import net.minecraft.server.v1_7_R1.ContainerAnvil;
import net.minecraft.server.v1_7_R1.ContainerAnvilInventory;
import net.minecraft.server.v1_7_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_7_R1.inventory.CraftInventoryAnvil;
import org.bukkit.inventory.AnvilInventory;

import java.lang.reflect.Field;

public class NMS {
	private static Field containerAnvilField;
	private static Field anvilWorldField;
	private static Field anvilXField;
	private static Field anvilYField;
	private static Field anvilZField;

	static {
		try {
			containerAnvilField = ContainerAnvilInventory.class.getDeclaredField("a");
			anvilWorldField = ContainerAnvil.class.getDeclaredField("i");
			anvilXField = ContainerAnvil.class.getDeclaredField("j");
			anvilYField = ContainerAnvil.class.getDeclaredField("k");
			anvilZField = ContainerAnvil.class.getDeclaredField("l");
			containerAnvilField.setAccessible(true);
			anvilWorldField.setAccessible(true);
			anvilXField.setAccessible(true);
			anvilYField.setAccessible(true);
			anvilZField.setAccessible(true);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	public static Location getAnvilLocation(AnvilInventory inv) {
		CraftInventoryAnvil craftInv = (CraftInventoryAnvil) inv;
		ContainerAnvilInventory nmsInv = (ContainerAnvilInventory) craftInv.getInventory();
		try {
			ContainerAnvil anvil = (ContainerAnvil) containerAnvilField.get(nmsInv);
			World w = (World) anvilWorldField.get(anvil);
			int x = (Integer) anvilXField.get(anvil);
			int y = (Integer) anvilYField.get(anvil);
			int z = (Integer) anvilZField.get(anvil);
			return new Location(w.getWorld(), x, y, z);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
