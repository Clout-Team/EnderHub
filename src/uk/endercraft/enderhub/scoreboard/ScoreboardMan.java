package uk.endercraft.enderhub.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import net.md_5.bungee.api.ChatColor;
import uk.endercraft.endercore.managers.PlayerManager;

public class ScoreboardMan {

	public static void sendScoreboard(Player p) {
		ScoreboardManager sb = Bukkit.getScoreboardManager();
		Scoreboard playerBoard = sb.getNewScoreboard();

		Objective sbb = playerBoard.registerNewObjective("clt_1", "dummy");
		sbb.setDisplayName(CFIX("&dEnderCraft &5Network"));
		sbb.setDisplaySlot(DisplaySlot.SIDEBAR);

		/* Add scores */
		Score spaceScore5 = sbb.getScore("      ");
		spaceScore5.setScore(15);

		// Online
		Score o1 = sbb.getScore(CFIX("&cOnline:"));
		o1.setScore(14);

		Score o2 = sbb.getScore(CFIX("&4") + Bukkit.getServer().getOnlinePlayers().size());
		o2.setScore(13);

		Score spaceScore4 = sbb.getScore("     ");
		spaceScore4.setScore(12);

		// Rank
		Score r1 = sbb.getScore(CFIX("&eRank:"));
		r1.setScore(11);

		Score r2 = sbb.getScore(CFIX("&6" + PlayerManager.getData(p).getRank().getColoredDisplayName().toUpperCase()));
		r2.setScore(10);

		Score spaceScore3 = sbb.getScore("    ");
		spaceScore3.setScore(9);

		// Tokens
		Score t1 = sbb.getScore(CFIX("&aTokens:"));
		t1.setScore(8);

		Score t2 = sbb.getScore(CFIX("&2" + PlayerManager.getData(p).getTokens()));
		t2.setScore(7);

		Score spaceScore2 = sbb.getScore("   ");
		spaceScore2.setScore(6);

		// Coins
		Score c1 = sbb.getScore(CFIX("&5Coins:"));
		c1.setScore(5);

		Score c2 = sbb.getScore(CFIX("&d" + PlayerManager.getData(p).getCoins()));
		c2.setScore(4);

		Score spaceScore1 = sbb.getScore("  ");
		spaceScore1.setScore(3);

		// Website
		Score web1 = sbb.getScore(CFIX("&3Website:"));
		web1.setScore(2);

		Score web2 = sbb.getScore(CFIX("&9endercraft.uk"));
		web2.setScore(1);

		Score spaceScore = sbb.getScore(" ");
		spaceScore.setScore(0);
		
		/* Push scoreboard */
		p.setScoreboard(playerBoard);
	}
	
	public static void updateScoreboard(Player p){
		//Register new objective and once all data is collected, push to player
		int online = Bukkit.getServer().getOnlinePlayers().size();
		int tokens = PlayerManager.getData(p).getTokens();
		int coins = PlayerManager.getData(p).getCoins();
		String objName;
		String objIName;
		
		if(p.getScoreboard().getObjective(DisplaySlot.SIDEBAR).getName() == "clt_1"){
			//push clt_2
			objName = "clt_2";
			objIName = "clt_1";
		}else{
			//push clt_1
			objName = "clt_1";
			objIName = "clt_2";
		}
		
		Scoreboard playerBoard = p.getScoreboard();

		Objective sbb = playerBoard.registerNewObjective(objName, "dummy");
		sbb.setDisplayName(CFIX("&dEnderCraft &5Network"));

		/* Add scores */
		Score spaceScore5 = sbb.getScore("      ");
		spaceScore5.setScore(15);

		// Online
		Score o1 = sbb.getScore(CFIX("&cOnline:"));
		o1.setScore(14);

		Score o2 = sbb.getScore(CFIX("&4") + online);
		o2.setScore(13);

		Score spaceScore4 = sbb.getScore("     ");
		spaceScore4.setScore(12);

		// Rank
		Score r1 = sbb.getScore(CFIX("&eRank:"));
		r1.setScore(11);

		Score r2 = sbb.getScore(CFIX("&6" + PlayerManager.getData(p).getRank().getDisplayName().toUpperCase()));
		r2.setScore(10);

		Score spaceScore3 = sbb.getScore("    ");
		spaceScore3.setScore(9);

		// Tokens
		Score t1 = sbb.getScore(CFIX("&aTokens:"));
		t1.setScore(8);

		Score t2 = sbb.getScore(CFIX("&2" + tokens));
		t2.setScore(7);

		Score spaceScore2 = sbb.getScore("   ");
		spaceScore2.setScore(6);

		// Coins
		Score c1 = sbb.getScore(CFIX("&5Coins:"));
		c1.setScore(5);

		Score c2 = sbb.getScore(CFIX("&d" + coins));
		c2.setScore(4);

		Score spaceScore1 = sbb.getScore("  ");
		spaceScore1.setScore(3);

		// Website
		Score web1 = sbb.getScore(CFIX("&3Website:"));
		web1.setScore(2);

		Score web2 = sbb.getScore(CFIX("&9endercraft.uk"));
		web2.setScore(1);

		Score spaceScore = sbb.getScore(" ");
		spaceScore.setScore(0);
		
		sbb.setDisplaySlot(DisplaySlot.SIDEBAR);
		playerBoard.getObjective(objIName).unregister();
	}

	public static String CFIX(String str) {
		return ChatColor.translateAlternateColorCodes('&', str);
	}

}
