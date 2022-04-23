package net.approachcircle.plugin.command;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Die {
	public static void suicide(Player player) {
		player.sendMessage("SP> " + ChatColor.YELLOW + "good riddance");
		player.setHealth(0);
	}
}
