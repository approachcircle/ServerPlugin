package net.approachcircle.plugin.dimensions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.approachcircle.plugin.misc.PluginLogger;

public class WorldSwitcher {
	public static Boolean isDisabled = false;
	public static List<Dimension> faultyDimensions = new ArrayList<>();
	private static Logger logger = PluginLogger.getPluginLogger();

	public static void switchTo(String worldName, Player target, CommandSender sender, Boolean usingSend) {
		if (isDisabled) {
			warnFaultyDimensions((Player) sender);
			return;
		}
		if (!StaggeredDimensionPreparer.isFinished()) {
			warnUngeneratedDimensions((Player) sender);
			return;
		}
		if (
				worldName.equalsIgnoreCase("the_nether") ||
				worldName.equalsIgnoreCase("the_end")
		) {
			if (usingSend) {
				sender.sendMessage("SP> " + ChatColor.RED + "you cannot send " + target.getName() + " to this dimension");
			} else {
				sender.sendMessage("SP> " + ChatColor.RED + "you cannot warp to this dimension");
			}
			return;
		}
		World world = Bukkit.getWorld(worldName);
		if (world == null) {
			sender.sendMessage("SP> " + ChatColor.RED + worldName + " is not a valid dimension");
			return;
		}
		World oldWorld = target.getWorld();
		if (oldWorld.getName().equalsIgnoreCase(world.getName())) {
			if (usingSend) {
				sender.sendMessage("SP> " + ChatColor.RED + target.getName() + " is already in this dimension");
			} else {
				sender.sendMessage("SP> " + ChatColor.RED + "you are already in this dimension");
			}
			return;
		}
		if (usingSend) {
			sender.sendMessage("SP> " + ChatColor.GREEN + "sending " + target.getName() + " to " + worldName + "...");
		} else {
			target.sendMessage("SP> " + ChatColor.GREEN + "sending you to " + worldName + "...");
		}
		try {
			target.teleport(world.getSpawnLocation());
		} catch (Exception e) {
			logger.log(Level.SEVERE, "warp to " + worldName + " failed", e);
			if (usingSend) {
				sender.sendMessage("SP> " + ChatColor.RED + "failed to send " + target.getName() + " to " + worldName);
			} else {
				sender.sendMessage("SP> " + ChatColor.RED + "failed to warp you to " + worldName);
			}
			if (sender.isOp()) {
				sender.sendMessage("SP> " + ChatColor.YELLOW + "if possible, check the console for exception details");
			}
		}
		if (usingSend) {
			sender.sendMessage("SP> " + ChatColor.GREEN + "sent " + target.getName() + " to " + worldName);
		}
		Bukkit.broadcastMessage("SP> " + ChatColor.GREEN + target.getName() + ": " + oldWorld.getName() + " -> " + worldName);
	}

	private static void warnUngeneratedDimensions(Player sender) {
		sender.sendMessage("SP> " + ChatColor.RED + "dimensions have not been generated yet");
		sender.sendMessage(String.format("SP> " + ChatColor.RED + "the server must be empty for %s minute(s) in order for the dimensions to generate", StaggeredDimensionPreparer.timerMinutes));
		
	}

	public static void warnFaultyDimensions(Player target) {
		if (isDisabled) {
			if (!target.isOp()) {
				target.sendMessage("SP> " + ChatColor.RED + "dimension warping is completely disabled right now due to "
						+ "the following faulty dimensions: ");
				int listNumber = 1;
				for (Dimension dim : faultyDimensions) {
					target.sendMessage(ChatColor.RED + Integer.toString(listNumber) + ". " + dim.getName());
					listNumber++;
				}
			} else {
				target.sendMessage("SP> " + ChatColor.YELLOW + "dimension(s) generated with exceptions, proceed "
						+ "with caution. faulty dimensions: ");
				for (Dimension dim : faultyDimensions) {
					target.sendMessage("SP> " + ChatColor.YELLOW + "- " + dim.getName());
				}
			}
		}
	}
}
