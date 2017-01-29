package uk.endercraft.enderhub.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.endercraft.endercore.language.LanguageMain;
import uk.endercraft.enderhub.EnderHub;

public class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage("Only Players");
			return true;
		}
		Player p = (Player) s;
		p.teleport(EnderHub.get().getSpawn());
		p.sendMessage(LanguageMain.get(p, "command.spawn"));
		return true;
	}

}
