package uk.endercraft.enderhub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.endercraft.endercore.managers.PlayerManager;
import uk.endercraft.enderhub.EnderHub;

public class SetSpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage("Only Players");
			return true;
		}
		if (!PlayerManager.getData((Player) s).hasPermission("rank")) {
			s.sendMessage("§c§lERROR  §c§l§oNo permission!");
			return true;
		}
		Player p = (Player) s;
		EnderHub.get().setSpawn(p.getLocation());
		s.sendMessage("§d§lENDERCRAFT §5Spawn set!");
		return true;
	}

}
