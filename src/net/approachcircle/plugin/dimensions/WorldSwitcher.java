package net.approachcircle.plugin.dimensions;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WorldSwitcher {
	public static Boolean isDisabled = false;
	public static List<Dimension> faultyDimensions = new ArrayList<Dimension>();
	
	public static void switchTo(String worldName, Player target, CommandSender sender, Boolean usingSend) {
		if (isDisabled) {
			warnFaultyDimensions((Player) sender);
			return;
		}
		if (
				worldName.equalsIgnoreCase("the_nether") || 
				worldName.equalsIgnoreCase("the_end")
			) {
			if (usingSend) {
				sender.sendMessage(ChatColor.RED + "you cannot send " + target.getName() + " to this dimension");
			} else {
				sender.sendMessage(ChatColor.RED + "you cannot warp to this dimension");
			}
			return;
		}
		World world = Bukkit.getWorld(worldName);
		if (world == null) {
			sender.sendMessage(ChatColor.RED + worldName + " is not a valid dimension");
			return;
		}
		World oldWorld = target.getWorld();
		if (oldWorld.getName().equalsIgnoreCase(world.getName())) {
			if (usingSend) {
				sender.sendMessage(ChatColor.RED + target.getName() + " is already in this dimension");
			} else {
				sender.sendMessage(ChatColor.RED + "you are already in this dimension");
			}
			return;
		}
		if (usingSend) {
			sender.sendMessage(ChatColor.GREEN + "sending " + target.getName() + " to " + worldName + "...");
		} else {
			target.sendMessage(ChatColor.GREEN + "sending you to " + worldName + "...");
		}
		target.teleport(world.getSpawnLocation());
		if (usingSend) {
			sender.sendMessage(ChatColor.GREEN + "sent " + target.getName() + " to " + worldName);
		}
		Bukkit.broadcastMessage(ChatColor.GREEN + target.getName() + ": " + oldWorld.getName() + " -> " + worldName);
	}
	
	public static void warnFaultyDimensions(Player target) {
		if (isDisabled) {
			target.sendMessage(ChatColor.RED + "dimension warping is completely disabled right now due to "
					+ "the following faulty dimensions: ");
			Integer listNumber = 1;
			for (Dimension dim : faultyDimensions) {
				target.sendMessage(ChatColor.RED + listNumber.toString() + ". " + dim.getName());
				listNumber++;
			}
		}
	}
}
