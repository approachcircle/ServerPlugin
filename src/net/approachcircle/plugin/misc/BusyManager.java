package net.approachcircle.plugin.misc;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class BusyManager {
	private static boolean busy = false;
	public static String defaultReason = "no reason provided";
	public static String reason = defaultReason;
	private static Logger logger = PluginLogger.getPluginLogger();

	public static void turnAwayPlayer(Player player) {
		player.kickPlayer(String.format("server busy\nreason: %s", reason));
	}

	public static boolean getState() {
		return busy;
	}

	public static void setBusy(String reasonToSet) {
		busy = true;
		if (!reasonToSet.isBlank()) {
			reason = reasonToSet;
		} else {
			reason = defaultReason;
		}
		logger.warning("server marked as busy");
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(ChatColor.YELLOW + String.format("server busy, reason: %s", reason));
		}
	}

	public static void notBusy() {
		busy = false;
		reason = defaultReason;
		logger.warning("server marked as not busy");
		for (Player player : Bukkit.getOnlinePlayers()) {
			player.sendMessage(ChatColor.YELLOW + "server not busy");
		}
	}
}