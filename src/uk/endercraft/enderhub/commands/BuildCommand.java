package uk.endercraft.enderhub.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.endercraft.endercore.EnderPlayer;
import uk.endercraft.endercore.language.LanguageMain;
import uk.endercraft.endercore.managers.PlayerManager;
import uk.endercraft.enderhub.EnderHub;
import uk.endercraft.enderhub.utils.ItemUtils;

public class BuildCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String name, String[] args) {

		if (args.length == 0) {
			EnderPlayer p;

			if ((p = PlayerManager.getData((Player) sender)).hasPermission("endercraft.build")) {
				if (EnderHub.get().isBuildEnabled(p.toBukkit())) {
					sender.sendMessage(LanguageMain.get(p, "command.build.off"));
					EnderHub.get().denyBuild((Player) sender);
					((Player) sender).getInventory().clear();
					ItemUtils.giveStartItems((Player) sender);
					((Player) sender).updateInventory();
				} else {
					sender.sendMessage(LanguageMain.get(p, "command.build.on"));
					EnderHub.get().allowBuild((Player) sender);
					((Player) sender).getInventory().clear();
					((Player) sender).updateInventory();
				}
			} else {
				sender.sendMessage(LanguageMain.get(p, "command.nopermission"));
			}
		} else if (args.length == 1) {
			Player t;

			if ((t = Bukkit.getPlayer(args[0])) != null) {

			} else {
				sender.sendMessage("§c§lERROR  §cUnknown player: §4" + args[0]);
				return false;
			}

			if (PlayerManager.getData((Player) sender).hasPermission("endercraft.build.others")) {
				// Allows toggling others
				if (EnderHub.get().isBuildEnabled(t)) {
					// Target can no longer build
					EnderHub.get().denyBuild(t);
					sender.sendMessage(
							LanguageMain.get((Player) sender, "command.build.other.off", t.getDisplayName()));
					t.sendMessage(LanguageMain.get((Player) sender, "command.build.off"));
					t.getInventory().clear();
					ItemUtils.giveStartItems(t);
					t.updateInventory();
				} else {
					// Target can now build
					EnderHub.get().allowBuild(t);
					sender.sendMessage(LanguageMain.get((Player) sender, "command.build.other.on", t.getDisplayName()));
					t.sendMessage(LanguageMain.get((Player) sender, "command.build.on"));
					t.getInventory().clear();
					t.updateInventory();
				}
			} else {
				sender.sendMessage(LanguageMain.get((Player) sender, "command.nopermission"));
			}
		} else {

		}

		return false;
	}

}
