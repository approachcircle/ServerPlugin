package net.approachcircle.plugin.command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Reports {
	private static List<String> playerActionList = new ArrayList<String>();
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("ServerPlugin");
	private static Logger logger = plugin.getLogger();
	
	public static void showReports(CommandSender sender) {
		logger.info(sender.getName() + " just viewed reports");
		sender.sendMessage(ChatColor.YELLOW + "here are the activity reports for every"
				+ " player: ");
		printReports(sender);
	}
	
	public static void resetReports(String time, CommandSender sender) {
		playerActionList.clear();
		playerActionList.add(ChatColor.AQUA + "reports reset at " + time + "\n");
		sender.sendMessage(ChatColor.GREEN + "okay, reports have been reset");
	}
	
	public static void playerJoined(Player player, String time) {
			playerActionList.add(ChatColor.GREEN + player.getName() + " joined the game at " + time + "\n");
	}
	
	public static void playerQuit(Player player, String time) {
			playerActionList.add(ChatColor.RED + player.getName() + " left the game at " + time + "\n");
	}
	
	public static void sendReports(CommandSender sender, Player target) {
		logger.info(sender.getName() + " sent reports to " + target.getName());
		target.sendMessage(ChatColor.YELLOW + sender.getName() + " has requested that you view "
				+ "the activity reports for this server");
		target.sendMessage(ChatColor.YELLOW + "the reports are as follows:");
		printReports(target);
		sender.sendMessage(ChatColor.GREEN + "activity reports have been sent to " + target.getName());
		playerActionList.add(ChatColor.AQUA + sender.getName() + " sent reports to " + target.getName());
	}
	
	private static void printReports(CommandSender target) {
		for (String action : playerActionList) {
			target.sendMessage(action);
		}
	}
}
