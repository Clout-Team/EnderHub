package uk.endercraft.enderhub.particles.particles;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_10_R1.EnumParticle;
import uk.endercraft.endercore.language.LanguageMain;
import uk.endercraft.endercore.managers.PlayerManager;
import uk.endercraft.endercore.utils.CustomStack;
import uk.endercraft.endercore.utils.PacketUtils;
import uk.endercraft.enderhub.particles.Particle;

public class FireworkSparksParticle extends Particle {

	@Override
	public int getID() {
		return 5;
	}

	@Override
	public boolean canUse(Player p) {
		return PlayerManager.getData(p).hasPermission(getName());
	}

	@Override
	public void spawnParticles(Player p) {
		PacketUtils.spawnParticle(EnumParticle.FIREWORKS_SPARK, p.getLocation().add(0, 2, 0), 0.2F, 0F, 0.2F, 0.1F, 25);
	}

	@Override
	public ItemStack getShowcaseItem(Player p) {
		return (new CustomStack()).setMaterial(Material.NETHER_STAR)
				.setDisplayName((canUse(p) ? ChatColor.GREEN : ChatColor.RED) + LanguageMain.get(p, getName()))
				.setLore(new String[] { "" }).build();
	}

	@Override
	public String getName() {
		return "particles.fireworksparks";
	}

	@Override
	public int getPrice() {
		return 600;
	}

}
