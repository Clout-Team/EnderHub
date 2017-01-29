package uk.endercraft.enderhub;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

import uk.endercraft.endercore.EnderCore;
import uk.endercraft.endercore.utils.StringUtils;
import uk.endercraft.enderhub.commands.BuildCommand;
import uk.endercraft.enderhub.commands.CoinsCommand;
import uk.endercraft.enderhub.commands.GlobalCoinsCommand;
import uk.endercraft.enderhub.commands.GlobalTokensCommand;
import uk.endercraft.enderhub.commands.RankCommand;
import uk.endercraft.enderhub.commands.SetSpawnCommand;
import uk.endercraft.enderhub.commands.SpawnCommand;
import uk.endercraft.enderhub.commands.TokensCommand;
import uk.endercraft.enderhub.config.ServersConfig;
import uk.endercraft.enderhub.listeners.PlayerListener;
import uk.endercraft.enderhub.listeners.Weatherman;
import uk.endercraft.enderhub.scoreboard.ScoreboardMan;
import uk.endercraft.enderhub.tasks.PingTask;
import uk.endercraft.enderhub.utils.InventoryUtils;

public class EnderHub extends EnderCore {

	private final InventoryUtils inventoryUtils = new InventoryUtils();
	private static ProtocolManager protocolManager;
	private static List<String> canBuild = new ArrayList<>();
	private Location spawn;

	@Override
	public void onEnable() {
		super.onEnable();
		registerEvent(new PlayerListener());
		registerEvent(new Weatherman());
		ServersConfig.get();
		new PingTask().runTaskTimer(this, 20L, 20L);
		new BukkitRunnable() {
			@Override
			public void run() {
				for (Player p : getServer().getOnlinePlayers()) {
					ScoreboardMan.updateScoreboard(p);
				}
			}
		}.runTaskTimer(this, 20L, 20L);

		protocolManager = ProtocolLibrary.getProtocolManager();

		getCommand("tokens").setExecutor(new TokensCommand());
		getCommand("coins").setExecutor(new CoinsCommand());
		getCommand("globaltokens").setExecutor(new GlobalTokensCommand());
		getCommand("globalcoins").setExecutor(new GlobalCoinsCommand());
		// getCommand("disguise").setExecutor(new DisguiseCommand());
		getCommand("forcebuild").setExecutor(new BuildCommand());
		getCommand("rank").setExecutor(new RankCommand());
		getCommand("spawn").setExecutor(new SpawnCommand());
		getCommand("setspawn").setExecutor(new SetSpawnCommand());
		spawn = StringUtils.getLocFromString(getConfig().getString("spawn", "world;0;0;0;0;0"));
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}

	public InventoryUtils getInventoryUtils() {
		return inventoryUtils;
	}

	public static EnderHub get() {
		return (EnderHub) EnderCore.get();
	}

	public ProtocolManager getProtocolManager() {
		return protocolManager;
	}

	/**
	 * Allows a player to build
	 * 
	 * @param p
	 *            The player to allow to build
	 * @return Whether the player's build status was toggled.
	 */
	public boolean allowBuild(Player p) {
		if (canBuild.contains(p.getName())) {
			return false;
		} else {
			canBuild.add(p.getName());
			return true;
		}
	}

	/**
	 * Prevents a player build access
	 * 
	 * @param p
	 *            The player to deny building
	 * @return Whether the player's build status was toggled.
	 */
	public boolean denyBuild(Player p) {
		if (canBuild.contains(p.getName())) {
			canBuild.remove(p.getName());
			return true;
		} else {
			return false;
		}
	}

	public boolean isBuildEnabled(Player p) {
		return canBuild.contains(p.getName());
	}

	public void setSpawn(Location loc) {
		this.spawn = loc;
		getConfig().set("spawn", StringUtils.getStringFromLoc(spawn));
		saveConfig();
	}

	public Location getSpawn() {
		return spawn;
	}

}
