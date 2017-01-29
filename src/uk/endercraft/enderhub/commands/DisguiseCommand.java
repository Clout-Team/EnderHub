package uk.endercraft.enderhub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.endercraft.endercore.managers.PlayerManager;

public class DisguiseCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage("Only players!");
			return true;
		}

		if (!(PlayerManager.getData((Player) s).hasPermission("disguise"))) {
			s.sendMessage("§c§lERROR  §4No permission");
			return true;
		}
		
		s.sendMessage("§d§lCOMING SOON");

		/*if (args.length == 2) {
			if (args[0].equalsIgnoreCase("player")) {
				//String name = args[1];
				-*if (Bukkit.getPlayer(name) == null) {
					s.sendMessage("§c§lERROR  §rUnknown player!");
					s.sendMessage("§c§lERROR  §rUsage: §c/disguise <player/mob> <name>");
					return true;
				}*-
				PlayerDisplayModifier pdm = new PlayerDisplayModifier(EnderHub.get());
				pdm.changeDisplay((Player) s, args[1], args[1]);
			} else if (args[0].equalsIgnoreCase("mob")) {

			} else {
				s.sendMessage("§c§lERROR  §rUsage: §c/disguise <player/mob> <name>");
			}
		} else {
			s.sendMessage("§c§lERROR  §rUsage: §c/disguise <player/mob> <name>");
		}*/

		return true;

	}

}