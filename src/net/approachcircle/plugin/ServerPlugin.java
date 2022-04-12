package net.approachcircle.plugin;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.approachcircle.plugin.command.Die;
import net.approachcircle.plugin.command.Firework;
import net.approachcircle.plugin.command.Maintenance;
import net.approachcircle.plugin.command.Mock;
import net.approachcircle.plugin.command.Reports;
import net.approachcircle.plugin.command.SpawnOn;
import net.approachcircle.plugin.command.TeleportAsk;
import net.approachcircle.plugin.dimensions.DimensionPreparer;
import net.approachcircle.plugin.dimensions.WorldSwitcher;

public class ServerPlugin extends JavaPlugin {
	MainListener mainListener = new MainListener();
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	public Logger logger = getLogger();
	
	public void onEnable() {
		logger.info("plugin made by approachcircle. contact: <osc-mcd@protonmail.com>");
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(mainListener, this);
		logger.info("all events have been registered");
		logger.info("started monitoring reports");
		logger.info("preparing all dimensions...");
		DimensionPreparer.prepareAllDimensions();
		logger.info("done!");
	}
	
	public void onDisable() {
		logger.info("all events have been deregistered, goodbye");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String noPerms = ChatColor.RED + "sorry, you do not have the permission to run this command";
		if (label.equalsIgnoreCase("die")) {
			Player player = (Player) sender;
			Die.suicide(player);
			return true;
		} else if (label.equalsIgnoreCase("spawnon")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
			} else {
				SpawnOn.spawnOn(sender, args[1], args[0]);
			}
			return true;
		} else if (label.equalsIgnoreCase("tpa")) {
			Player player = (Player) sender;
			TeleportAsk.requestTeleport(player, args[0]);
			return true;
		} else if (label.equalsIgnoreCase("firework")) {
			Player player = (Player) sender;
			Firework.giveFireworks(player);
			return true;
		} else if (label.equalsIgnoreCase("reports")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
				return true;
			} else {
				if (args.length > 0) {
					String targetName = args[0];
					if (targetName.equalsIgnoreCase("reset")) {
						LocalTime time = LocalTime.now();
						Reports.resetReports(time.format(dtf), sender);
						return true;
					}
					Player target = Bukkit.getPlayer(targetName);
					Reports.sendReports(sender, target);
					return true;
				} else {
					Reports.showReports(sender);
					return true;
				}
			}
		} else if (label.equalsIgnoreCase("warpto")) {
			if (args.length <= 0) {
				String[] missing = {"world"};
				insufficientArguments(missing, sender);
				return true;
			}
			Player player = (Player) sender;
			WorldSwitcher.switchTo(args[0].toLowerCase(), player, sender, false);
			return true;
		} else if (label.equalsIgnoreCase("send")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
				return true;
			}
			if (args.length <= 0) {
				String[] missing = {"user", "worlds"};
				insufficientArguments(missing, sender);
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "this player does not exist");
				return true;
			}
			WorldSwitcher.switchTo(args[1].toLowerCase(), target, sender, true);
			return true;
		} else if (label.equalsIgnoreCase("maintenance")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
				return true;
			}
			Maintenance.toggleMaintenance(sender);
			return true;
		} else if (label.equalsIgnoreCase("mock")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
				return true;
			}
			if (args.length <= 0) {
				String[] missing = {"player", "message"};
				insufficientArguments(missing, sender);
				return true;
			}
			Player target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.RED + "this player does not exist");
				return true;
			}
			Mock.mockPlayer(target, args);
		}
		return false;
	}
	
	private void insufficientArguments(String[] missingArgs, CommandSender sender) {
		String message = "";
		message += "not enough arguments: <command> ";
		for (String arg : missingArgs) {
			message += "{";
			message += arg;
			message += "} ";
		}
		sender.sendMessage(ChatColor.RED + message);
	}
}
