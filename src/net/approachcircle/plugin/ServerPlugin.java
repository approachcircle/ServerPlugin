package net.approachcircle.plugin;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
	}
	
	public void onDisable() {
		logger.info("all events have been deregistered, goodbye");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String noPerms = "sorry, you do not have the permission to run this command";
		if (label.equalsIgnoreCase("die")) {
			Player player = (Player) sender;
			DieCommand.suicide(player);
			return true;
		} else if (label.equalsIgnoreCase("spawnon")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
			} else {
				SpawnOnCommand.spawnOn(sender, args[1], args[0]);
			}
			return true;
		} else if (label.equalsIgnoreCase("tpa")) {
			Player player = (Player) sender;
			TeleportAskCommand.requestTeleport(player, args[0]);
			return true;
		} else if (label.equalsIgnoreCase("firework")) {
			Player player = (Player) sender;
			FireworkCommand.giveFireworks(player);
			return true;
		} else if (label.equalsIgnoreCase("reports")) {
			if (!sender.isOp()) {
				sender.sendMessage(noPerms);
			} else {
				if (args.length > 0) {
					String targetName = args[0];
					if (targetName.equalsIgnoreCase("reset")) {
						LocalTime time = LocalTime.now();
						ReportsCommand.resetReports(time.format(dtf), sender);
						return true;
					}
					Player target = Bukkit.getPlayer(targetName);
					ReportsCommand.sendReports(sender, target);
					return true;
				} else {
					Player player = (Player) sender;
					ReportsCommand.showReports(player);
					return true;
				}
			}
		}
		return false;
	}
}
