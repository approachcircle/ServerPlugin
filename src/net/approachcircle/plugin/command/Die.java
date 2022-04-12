package net.approachcircle.plugin.command;

import org.bukkit.entity.Player;

public class Die {
	public static void suicide(Player player) {
		player.setHealth(0);
	}
}
