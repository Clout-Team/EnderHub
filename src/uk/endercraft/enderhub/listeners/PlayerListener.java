package uk.endercraft.enderhub.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import uk.endercraft.endercore.managers.PlayerManager;
import uk.endercraft.enderhub.EnderHub;
import uk.endercraft.enderhub.particles.ParticlesManager;
import uk.endercraft.enderhub.scoreboard.ScoreboardMan;
import uk.endercraft.enderhub.utils.ItemUtils;
import uk.endercraft.enderhub.utils.JoinLeaveUtils;

public class PlayerListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		e.setJoinMessage(null);
		Player p = e.getPlayer();
		p.setFoodLevel(20);
		p.setHealthScale(20);
		p.setHealth(20);
		ItemUtils.giveStartItems(e.getPlayer());
		ScoreboardMan.sendScoreboard(p);
		JoinLeaveUtils.sendJoinMessages(p);
		JoinLeaveUtils.sendJoinTitle(p);
		if (PlayerManager.getData(p).hasPermission("flyonjoin")) {
			p.setAllowFlight(true);
			p.setFlying(true);
		}
		p.teleport(EnderHub.get().getSpawn());
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLeave(PlayerQuitEvent e) {
		e.setQuitMessage(null);
		ParticlesManager.get().removeParticle(e.getPlayer());
		JoinLeaveUtils.sendLeaveMessages(e.getPlayer());
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		if (!EnderHub.get().isBuildEnabled((Player) e.getWhoClicked())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (!EnderHub.get().isBuildEnabled(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (!EnderHub.get().isBuildEnabled(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		if (!EnderHub.get().isBuildEnabled(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		e.setCancelled(playerInteract(e.getPlayer(), e.getAction(), e.getItem()));
	}

	@EventHandler
	public void onInteractEntity(PlayerInteractEntityEvent e) {
		boolean allowCont = true;

		if (e.getRightClicked().getType() == EntityType.ENDERMAN) {
			if (e.getRightClicked().getName().contains("Cosmetics Store")) {
				e.setCancelled(true);
				EnderHub.get().getInventoryUtils().openCosmeticsStore(e.getPlayer());
				allowCont = false;
			}
		}

		if (allowCont) {
			e.setCancelled(playerInteract(e.getPlayer(), Action.RIGHT_CLICK_AIR,
					e.getPlayer().getInventory().getItemInMainHand()));
		}
	}

	private boolean playerInteract(Player p, Action action, ItemStack item) {
		if (item != null) {
			switch (item.getType()) {
			case ENDER_PEARL:
				EnderHub.get().getInventoryUtils().openGameSelector(p);
				return true;
			case ENDER_CHEST:
				// Open cosmetics menu
				EnderHub.get().getInventoryUtils().openCosmeticsGUI(p);
				return true;
			case SKULL_ITEM:
				EnderHub.get().getInventoryUtils().openProfileGUI(p);
			default:
			}
			return false;
		} else {
			return false;
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (e instanceof Player)
			e.setCancelled(true);
	}

	@EventHandler
	public void onFeedLevelChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

}
