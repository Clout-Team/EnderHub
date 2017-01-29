package uk.endercraft.enderhub.utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_10_R1.IChatBaseComponent;
import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle.EnumTitleAction;
import uk.endercraft.endercore.language.LanguageMain;
import uk.endercraft.endercore.managers.PlayerManager;

public class JoinLeaveUtils {

	public static void sendJoinTitle(Player p) {
		sendTitle(p, LanguageMain.get(p, "join.title", p.getName()), LanguageMain.get(p, "join.subtitle", p.getName()),
				0.5, 2, 0.5);
	}

	public static void sendTitle(Player p, String title, String subtitle, double fadeInSec, double staySec,
			double fadeOutSec) {
		PacketPlayOutTitle titleP = new PacketPlayOutTitle(EnumTitleAction.TITLE, toJSON(title));
		PacketPlayOutTitle subtitleP = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, toJSON(subtitle));
		PacketPlayOutTitle timesP = new PacketPlayOutTitle((int) (fadeInSec * 20), (int) (staySec * 20),
				(int) (fadeOutSec * 20));
		for (PacketPlayOutTitle packet : new PacketPlayOutTitle[] { titleP, subtitleP, timesP }) {
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}

	private static IChatBaseComponent toJSON(String text) {
		return ChatSerializer.a("{\"text\": \"" + text + "\"}");
	}

	public static void sendJoinMessages(Player p) {
		Bukkit.getServer().broadcastMessage(ChatColor.translateAlternateColorCodes('&',
				"&8[&a+&8] &f" + PlayerManager.getData(p).getRank().getDisplayName() + " " + p.getName() + " joined."));
	}

	public static void sendLeaveMessages(Player p) {
		Bukkit.getServer()
				.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&8[&c-&8] &f" + p.getName() + " left."));
	}

}
