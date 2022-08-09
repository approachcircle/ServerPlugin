package net.approachcircle.plugin.misc;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.approachcircle.plugin.anticheat.AntiSuffocate;
import net.approachcircle.plugin.command.Die;
import net.approachcircle.plugin.command.Firework;
import net.approachcircle.plugin.command.Maintenance;
import net.approachcircle.plugin.command.Mock;
import net.approachcircle.plugin.command.Reports;
import net.approachcircle.plugin.command.SpawnOn;
import net.approachcircle.plugin.command.TeleportAsk;
import net.approachcircle.plugin.dimensions.DimensionPreparer;
import net.approachcircle.plugin.dimensions.StaggeredDimensionPreparer;
import net.approachcircle.plugin.dimensions.WorldSwitcher;

public class CommandDispatcher {
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
	private static Logger logger = PluginLogger.getPluginLogger();

	public static boolean dispatch(String label, CommandSender sender, String[] args) {
		String noPerms = ChatColor.RED + "SP> sorry, you do not have the permission to run this command";
		if (label.equalsIgnoreCase("die")) {
			Player player = null;
			if (sender instanceof Player) {
				player = (Player) sender;
			} else {
				return true;
			}
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
			Player player = null;
			if (sender instanceof Player) {
				player = (Player) sender;
			} else {
				return true;
			}
			TeleportAsk.requestTeleport(player, args[0]);
			return true;
		} else if (label.equalsIgnoreCase("firework")) {
			Player player = null;
			if (sender instanceof Player) {
				player = (Player) sender;
			} else {
				return true;
			}
			Firework.checkEligibility(player);
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
			Player player = null;
			if (sender instanceof Player) {
				player = (Player) sender;
			} else {
				return true;
			}
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
				sender.sendMessage("SP> " + ChatColor.RED + "this player does not exist");
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
				sender.sendMessage("SP> " + ChatColor.RED + "this player does not exist");
				return true;
			}
			Mock.mockPlayer(target, args);
		} else if (label.equalsIgnoreCase("antisuffocate")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
				return true;
			} else {
				sender.sendMessage("SP> " + ChatColor.GREEN + String.format("disabled: %s -> %s",
						AntiSuffocate.disabled, !AntiSuffocate.disabled));
				AntiSuffocate.disabled = !AntiSuffocate.disabled;
				return true;
			}
		} else if (label.equalsIgnoreCase("busy-on")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
				return true;
			} else {
				String joinedMessage = "";
				for (String word : args) {
					joinedMessage += word;
					joinedMessage += " ";
				}
				BusyManager.setBusy(joinedMessage);
				return true;
			}
		} else if (label.equalsIgnoreCase("busy-off")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
				return true;
			} else {
				BusyManager.notBusy();
			}
		} else if (label.equalsIgnoreCase("forcegenerate")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
				return true;
			} else {
				StaggeredDimensionPreparer.stopTimer();
				StaggeredDimensionPreparer.setFinished();
				logger.warning("force generating dimensions...");
				Bukkit.broadcastMessage("SP> " + ChatColor.YELLOW + String.format("%s has performed a force generation of dimensions", sender.getName()));
				Bukkit.broadcastMessage("SP> " + ChatColor.YELLOW + String.format("you might time out, just wait for a sec after and you should be able to join back"));
				DimensionPreparer.prepareAllDimensions();
			}
		}
		return true;
	}

	private static void insufficientArguments(String[] missingArgs, CommandSender sender) {
		String message = "";
		message += "not enough arguments: <command> ";
		for (String arg : missingArgs) {
			message += "{";
			message += arg;
			message += "} ";
		}
		sender.sendMessage("SP> " + ChatColor.RED + message);
	}
}
