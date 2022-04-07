package net.approachcircle.plugin;

import org.bukkit.entity.Player;

public class DieCommand {
	public static void suicide(Player player) {
		player.setHealth(0);
	}
}
