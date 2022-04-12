package net.approachcircle.plugin.command;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Maintenance {
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("ServerPlugin");
	private static Logger logger = plugin.getLogger();
	private static Boolean maintenanceMode = false;
	
	public static void toggleMaintenance(CommandSender sender) {
		sender.sendMessage(ChatColor.GREEN + "maintenance mode: " + maintenanceMode + " -> " + !maintenanceMode);
		maintenanceMode = !maintenanceMode;
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!player.isOp()) {
				turnAwayPlayer(player, false);
			}
		}
	}
	
	public static void turnAwayPlayer(Player player, Boolean triedToJoin) {
		player.kickPlayer(
				ChatColor.MAGIC + "a" + ChatColor.RESET + 
				ChatColor.RED + "the server is currently in maintenance mode" +
				ChatColor.RESET + ChatColor.MAGIC + "a" +
				ChatColor.RESET + "\n" + ChatColor.DARK_AQUA +
				"this means that something that requires " +
				"the server to be restarted many times is taking place, the " +
				"server is currently unstable, or it's not safe to play right now for "
				+ "any reason\n" + ChatColor.BLUE + "either join back later, "
				+ "or message me on discord"
		);
		
		if (triedToJoin) {
			for (Player curPlayer : Bukkit.getOnlinePlayers()) {
				if (curPlayer.isOp()) {
					curPlayer.sendMessage(ChatColor.RED + player.getName() + " just tried to join");
				}
			}
			logger.info(player.getName() + " just tried to join");
		}
	}
	
	public static Boolean getState() {
		return maintenanceMode;
	}
}
