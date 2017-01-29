package uk.endercraft.enderhub.particles;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public abstract class Particle implements Listener{
	
	public abstract int getID();
	
	public abstract boolean canUse(Player p);
	
	public abstract void spawnParticles(Player p);
	
	public abstract ItemStack getShowcaseItem(Player p);
	
	public abstract String getName();
	
	public abstract int getPrice();

}
