package uk.endercraft.enderhub.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import uk.endercraft.endercore.language.LanguageMain;
import uk.endercraft.endercore.utils.CustomStack;

public class ItemUtils {

	public static void giveStartItems(Player p) {
		PlayerInventory inv = p.getInventory();
		inv.clear();
		inv.setItem(0, (new CustomStack()).setMaterial(Material.ENDER_PEARL).setDisplayName(LanguageMain.get(p, "item.gameselector")).build());
		inv.setItem(4,
				(new CustomStack()).setMaterial(Material.ENDER_CHEST).setDisplayName(LanguageMain.get(p, "item.particleselector")).build());
		
		ItemStack playerHead = new ItemStack(Material.SKULL_ITEM);
		playerHead.setDurability((short) 3);
		SkullMeta headMeta = (SkullMeta) playerHead.getItemMeta();
		headMeta.setOwner(p.getName());
		headMeta.setDisplayName("§d§lPlayer Stats");
		playerHead.setItemMeta(headMeta);
		inv.setItem(8, playerHead);
		
		p.updateInventory();
	}

}
