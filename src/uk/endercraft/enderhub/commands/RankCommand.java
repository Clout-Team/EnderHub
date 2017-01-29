package uk.endercraft.enderhub.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.endercraft.endercore.EnderCore;
import uk.endercraft.endercore.Rank;
import uk.endercraft.endercore.managers.PlayerManager;

public class RankCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (s instanceof Player && !PlayerManager.getData((Player) s).hasPermission("rank")) {
			s.sendMessage("§c§lERROR  §c§l§oNo permission!");
			return true;
		}
		if (args.length > 0)
			if (args[0].equalsIgnoreCase("set")) {
				if (args.length < 3) {
					s.sendMessage("/" + label + " set <player> <new rank name>");
					return true;
				}
				Rank r = EnderCore.get().getRank(args[2]);
				if (r.getId() == 0) {
					s.sendMessage("§c§lERROR  §c§l§oRank not found");
					return true;
				}
				Player p = Bukkit.getPlayer(args[1]);
				if (p != null) {
					PlayerManager.getData(p).setRank(r.getId());
					s.sendMessage("§d§lENDERCRAFT §fSuccessfuly added " + p.getName() + " to the rank "
							+ r.getColoredDisplayName());
					return true;
				}
				s.sendMessage("§d§lENDERCRAFT §fPlayer " + args[1] + " is not online!");
				return true;
			} else if (args[0].equalsIgnoreCase("view")) {
				if (args.length < 2) {
					s.sendMessage("/" + label + " view <player>");
					return true;
				}
				Player p = Bukkit.getPlayer(args[1]);
				if (p != null) {
					s.sendMessage("§d§lENDERCRAFT §fPlayer " + p.getName() + " is on the rank "
							+ PlayerManager.getData(p).getRank().getColoredDisplayName());
					return true;
				}
				try {
					Connection conn = EnderCore.get().getSql().getConnection();
					PreparedStatement stmt = conn.prepareStatement("SELECT name, rank FROM players WHERE name=?");
					stmt.setString(1, args[1]);
					ResultSet rs = stmt.executeQuery();
					while (rs.next()) {
						s.sendMessage("§d§lENDERCRAFT §fPlayer " + rs.getString("name") + " is on the rank "
								+ EnderCore.get().getRank(rs.getInt("rank")).getColoredDisplayName());
						return true;
					}
					s.sendMessage("§c§lERROR  §c§l§oCan't find player " + args[1] + " on the database.");
				} catch (Exception e) {
					s.sendMessage("§c§lERROR  §c§l§oCan't connect to the database.");
				}
				return true;
			}
		s.sendMessage("/" + label + " set");
		s.sendMessage("/" + label + " view");
		return true;
	}

}
