package net.approachcircle.plugin.command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class TeleportAsk {
	private static List<Player> requesters = new ArrayList<Player>();
	private static List<Player> targets = new ArrayList<Player>();
	private static BukkitTask currentTask;
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("ServerPlugin");
	private static Logger logger = plugin.getLogger();
	
	public static void requestTeleport(Player commandSender, String targetName) {
		if (targetName.equalsIgnoreCase("yes")) {
			acceptTeleport(commandSender);
			return;
		} else if (targetName.equalsIgnoreCase("no")) {
			denyTeleport(commandSender);
			return;
		}
		Player target = Bukkit.getPlayer(targetName);
		if (target == null) {
			commandSender.sendMessage(ChatColor.RED + targetName + " is not a valid player to teleport to");
			return;
		}
		if (requesters.contains(commandSender) && targets.contains(target)) {
			commandSender.sendMessage(ChatColor.RED + "you've already sent a teleport request to " + targetName);
			return;
		}
		sendTeleportRequest(commandSender, target);
	}
	
	private static void acceptTeleport(Player commandSender) {
		String senderUUID = commandSender.getUniqueId().toString();
		Integer index = 0;
		Boolean success = false;
		for (Player target : targets) {
			String targetUUID = target.getUniqueId().toString();
			if (senderUUID.equalsIgnoreCase(targetUUID)) {
				Location senderLocation = commandSender.getLocation();
				Player requester = requesters.get(index);
				requester.teleport(senderLocation);
				requester.sendMessage(ChatColor.GREEN + "your teleport to " + target.getName() + " has been accepted");
				logger.info(commandSender.getName() + " accepted a teleport request from " + requesters.get(index).getName());
				success = true;
				break;
			}
			index++;
		}
		if (success) {
			commandSender.sendMessage(ChatColor.GREEN + "okay, teleported accepted");
			currentTask.cancel();
			requesters.remove(0);
			targets.remove(0);
		} else {
			commandSender.sendMessage(ChatColor.RED + "you don't have a teleport request to accept");
		}
	}
	
	private static void denyTeleport(Player commandSender) {
		String senderUUID = commandSender.getUniqueId().toString();
		Integer index = 0;
		Boolean success = false;
		for (Player target : targets) {
			String targetUUID = target.getUniqueId().toString();
			if (senderUUID.equalsIgnoreCase(targetUUID)) {
				requesters.get(index).sendMessage(ChatColor.RED + "your teleport request to " + target.getName() + " was denied");
				logger.info(commandSender.getName() + " denied a teleport request from " + requesters.get(index).getName());
				success = true;
				break;
			}
			index++;
		}
		if (success) {
			commandSender.sendMessage(ChatColor.YELLOW + "okay, teleport denied");
			currentTask.cancel();
			requesters.remove(0);
			targets.remove(0);
		} else {
			commandSender.sendMessage(ChatColor.RED + "you don't have a teleport request to deny");
		}
		
		
	}
	
	private static void sendTeleportRequest(Player requester, Player target) {
		requester.sendMessage(ChatColor.YELLOW + "sending teleport request to " + target.getName());
		requester.sendMessage(ChatColor.YELLOW + "(your request will expire in about 15 seconds)");
		requesters.add(requester);
		targets.add(target);
		currentTask = beginTimer(requester, target);
		target.sendMessage(ChatColor.YELLOW + requester.getName() + " would like to teleport to you");
		target.sendMessage(ChatColor.DARK_GREEN + "'/tpa yes' to allow this teleport");
		target.sendMessage(ChatColor.RED + "'/tpa no' to deny this teleport");
		target.sendMessage(ChatColor.YELLOW + "(this will expire in about 15 seconds)");
		requester.sendMessage(ChatColor.YELLOW + "request sent");
		logger.info(requester.getName() + " just sent a teleport request to " + target.getName());
	}
	
	private static BukkitTask beginTimer(Player requester, Player target) {
		BukkitTask task = Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				requester.sendMessage(ChatColor.RED + "your teleport request to " + target.getName() + " has expired");
				target.sendMessage(ChatColor.RED + "the teleport request from " + requester.getName() + " has expired");
				requesters.remove(0);
				targets.remove(0);
				logger.info(requester.getName() + "'s teleport request to " + target.getName() + " has expired");
			}
		}, 20*15L);
		return task;
	}
}