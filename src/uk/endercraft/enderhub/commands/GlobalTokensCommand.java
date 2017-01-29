package uk.endercraft.enderhub.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import uk.endercraft.endercore.managers.PlayerManager;

public class GlobalTokensCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if (!(s instanceof Player)) {
			s.sendMessage("Only players!");
			return true;
		}
		
		if(!(PlayerManager.getData((Player) s).getRankID() == 11)){
			s.sendMessage("§c§lERROR  §4No permission");
			return true;
		}
		
		if(args.length == 2){
			if(args[0] == "give"){
				try {
					int amount = Integer.parseInt(args[1]);
					for(Player p : Bukkit.getOnlinePlayers()){
						PlayerManager.getData(p).setTokens(amount);
					}
					s.sendMessage("§d§lTOKENS  " + "§rGiven server-wide token bonus of §d§l" + amount);
				}catch(Exception ex){
					
				}
			}else{
				s.sendMessage("§d§lTOKENS  " + "§rUsage: §d/globaltokens give <amount>");
			}
		}else{
			s.sendMessage("§d§lTOKENS  " + "§rUsage: §d/globaltokens give <amount>");
		}
		
		return true;
		
	}

}