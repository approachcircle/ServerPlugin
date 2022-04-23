package net.approachcircle.plugin.anticheat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import net.approachcircle.plugin.command.Reports;
import net.approachcircle.plugin.misc.PluginLogger;

public class AntiSuffocate {
	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
	public static Boolean disabled = false;
	private static Logger logger = PluginLogger.getPluginLogger();
	
	public static void checkSuffocationState(Player target) {
		if (disabled) {
			return;
		}
		Boolean isOperator = false;
		if (target.isOp()) {
			isOperator = true;
		}
		Location playerEyeLocation = target.getEyeLocation();
		Block blockPlayerIn = playerEyeLocation.getBlock();
		Boolean noEmptySpace = false;
		String[] allowedBlockKeywords = {"STAIR",
				"DOOR",
				"LADDER",
				"PANE", 
				"PISTON", 
				"SLAB", 
				"CARPET", 
				"FENCE", 
				"GATE",
				"LANTERN"
		};
		
		if (blockPlayerIn.isLiquid()) {
			return;
		} else if (blockPlayerIn.getType() == Material.AIR) {
			return;
		} else if (
				blockPlayerIn.getType() == Material.GRAVEL ||
				blockPlayerIn.getType() == Material.SAND
		) {
			return;
		} else if (target.getGameMode() == GameMode.SPECTATOR) {
			return;
		} else if (blockPlayerIn.isPassable()) {
			return;
		}
		for (String blockKeyword : allowedBlockKeywords) {
			if (blockPlayerIn.getType().toString().contains(blockKeyword)) {
				return;
			}
		}
		if (isOperator) {
			target.sendMessage("SP> " + ChatColor.RED + "you are in an illegal block!");
			return;
		}
		Location newLocation = checkEmptySpace(target.getLocation(), target.getWorld());
		if (newLocation == null) {
			noEmptySpace = true;
		}
		PlayerStorage.playerViolated(target);
		if (!noEmptySpace) {
			target.teleport(newLocation);
		} else {
			Integer airX = newLocation.getBlockX();
			Integer airY = newLocation.getBlockY() - 1;
			Integer airZ = newLocation.getBlockZ();
			if (blockPlayerIn.getType() != Material.BEDROCK) {
				(new Location(target.getWorld(), airX, airY, airZ)).getBlock().breakNaturally();
				blockPlayerIn.breakNaturally();
			}
		}
		if (PlayerStorage.playerShouldBeKicked(target)) {
			target.kickPlayer(ChatColor.DARK_GREEN + "==--SP--==\n" + ChatColor.RED + "you were kicked because you kept "
					+ "suffocating in blocks that you "
				+ "should not be able to able to suffocate in naturally. please refrain from "
				+ "doing this");
		}
		target.sendMessage("SP> " + ChatColor.RED + "refrain from suffocating in blocks that you "
				+ "shouldn't be able to suffocate in");
		LocalTime time = LocalTime.now();
		Reports.addOtherEvent(ChatColor.DARK_RED + target.getName() + " was flagged for illegal suffocation "
				+ "at " + dtf.format(time));
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.isOp()) {
				player.sendMessage("SP> " + ChatColor.DARK_RED + target.getName() + " was flagged "
						+ "for illegal suffocation at "
						+ playerEyeLocation.getBlockX() + " " + playerEyeLocation.getBlockY() + 
						" " + playerEyeLocation.getBlockZ() + " in " + target.getWorld().getName());
			}
		}
		logger.warning(ChatColor.DARK_RED + target.getName() + " was flagged "
						+ "for illegal suffocation at "
						+ playerEyeLocation.getBlockX() + " " + playerEyeLocation.getBlockY() + 
						" " + playerEyeLocation.getBlockZ() + " in " + target.getWorld().getName());
	}
	
	private static Location checkEmptySpace(Location eyeLocation, World world) {
		Integer initialY = eyeLocation.getBlockY();
		Integer initialX = eyeLocation.getBlockX();
		Integer initialZ = eyeLocation.getBlockZ();
		for (Integer blockRadius = initialY; blockRadius <= initialY + 50; blockRadius++) {
			Location currentLocation = new Location(world, initialX, blockRadius, initialZ);
			Block currentBlock = currentLocation.getBlock();
			if (currentBlock.getType() == Material.AIR) {
				return currentLocation;
			}
		}
		for (Integer blockRadius = initialX; blockRadius <= initialX + 50; blockRadius++) {
			Location currentLocation = new Location(world, blockRadius, initialY, initialZ);
			Block currentBlock = currentLocation.getBlock();
			if (currentBlock.getType() == Material.AIR) {
				return currentLocation;
			}
		}
		for (Integer blockRadius = initialX; blockRadius >= initialX - 50; blockRadius--) {
			Location currentLocation = new Location(world, blockRadius, initialY, initialZ);
			Block currentBlock = currentLocation.getBlock();
			if (currentBlock.getType() == Material.AIR) {
				return currentLocation;
			}
		}
		for (Integer blockRadius = initialZ; blockRadius <= initialZ + 50; blockRadius++) {
			Location currentLocation = new Location(world, initialX, initialY, blockRadius);
			Block currentBlock = currentLocation.getBlock();
			if (currentBlock.getType() == Material.AIR) {
				return currentLocation;
			}
		}
		for (Integer blockRadius = initialZ; blockRadius >= initialZ - 50; blockRadius--) {
			Location currentLocation = new Location(world, initialX, initialY, blockRadius);
			Block currentBlock = currentLocation.getBlock();
			if (currentBlock.getType() == Material.AIR) {
				return currentLocation;
			}
		}
		return null;
	}
}
