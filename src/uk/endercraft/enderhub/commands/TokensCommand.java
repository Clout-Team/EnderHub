package uk.endercraft.enderhub.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.endercraft.endercore.EnderPlayer;
import uk.endercraft.endercore.managers.PlayerManager;

public class TokensCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage("Only players!");
			return true;
		}

		switch (args.length) {
		case 3:
			if (!(PlayerManager.getData((Player) s).hasPermission("tokens.give"))) {
				s.sendMessage("§c§lERROR  §4No permission");
				return true;
			}
			Player p;
			int amount;

			try {
				p = Bukkit.getPlayer(args[1]);
			} catch (Exception ex) {
				s.sendMessage("§c§lERROR  §rInvalid player.");
				s.sendMessage("§c§lERROR  §rUsage: §c/tokens <add/remove/set> <player> <amount>");
				return true;
			}

			try {
				amount = Integer.parseInt(args[2]);

				if (!(amount > 0)) {
					s.sendMessage("§c§lERROR  §rInvalid amount.");
					s.sendMessage("§c§lERROR  §rUsage: §c/tokens <add/remove/set> <player> <amount>");
					return true;
				}
			} catch (Exception ex) {
				s.sendMessage("§c§lERROR  §rInvalid amount.");
				s.sendMessage("§c§lERROR  §rUsage: §c/tokens <add/remove/set> <player> <amount>");
				return true;
			}
			switch (args[0]) {
			case "add":
				PlayerManager.getData(p).addTokens(amount);
			case "remove":
				PlayerManager.getData(p).removeTokens(amount);
			case "set":
				PlayerManager.getData(p).setTokens(amount);
			default:
				s.sendMessage("§c§lERROR  §rUsage: §c/tokens <add/remove/set> <player> <amount>");
			}
			return true;

		case 0:
			EnderPlayer ep = PlayerManager.getData((Player) s);
			ep.sendMessage("command.tokens", ep.getTokens());
			return true;
		default:
			if (!(PlayerManager.getData((Player) s).hasPermission("tokens.give"))) {
				s.sendMessage("§c§lERROR  §4No permission");
				return true;
			}
			s.sendMessage("§c§lERROR  §rUsage: §c/tokens <add/remove/set> <player> <amount>");
			return true;
		}

	}

}
