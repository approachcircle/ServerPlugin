package net.approachcircle.plugin.anticheat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class PlayerStorage {
	private static List<UUID> playerIDs = new ArrayList<UUID>();
	
	public static void playerViolated(Player player) {
		playerIDs.add(player.getUniqueId());
	}
	
	public static Boolean playerShouldBeKicked(Player player) {
		Integer violationCount = 0;
		for (UUID ID : playerIDs) {
			if (player.getUniqueId().equals(ID)) {
				violationCount++;
			}
		}
		
		if (violationCount >= 3) {
			return true;
		} else {
			return false;
		}
	}
	
	public static BukkitTask setupRepeatingReset() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ServerPlugin");
		BukkitTask task = plugin.getServer().getScheduler().runTaskTimer(plugin, new Runnable() {
			public void run() {
				playerIDs.clear();
			}
		}, 0, 120*20); // every 2 minutes
		return task;
	}
}
