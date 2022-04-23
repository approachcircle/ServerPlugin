package net.approachcircle.plugin.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Firework {
	
	/**
	 * the Player instance is re-instantiated when the player logs out and back in, meaning
	 * that the algorithm will not recognise the new player as having already gotten fireworks,
	 * enabling them to get as many fireworks as they'd like by just logging in and out. this is
	 * why we are going to compare their UUID instead, as that never changes.
	 */
	
	private static List<UUID> gotFireworks = new ArrayList<UUID>();
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("ServerPlugin");
	private static Logger logger = plugin.getLogger();
	
	public static void checkEligibility(Player target) {
		for (UUID ID : gotFireworks) {
			if (!ID.equals(target.getUniqueId())) {
				giveFireworks(target);
				return;
			} else {
				target.sendMessage("SP> " + ChatColor.RED + "your firework cooldown has not expired yet");
				return;
			}
		}
		giveFireworks(target);
	}
	
	private static BukkitTask timer(Player target) {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ServerPlugin");
		BukkitTask task = Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				gotFireworks.remove(target.getUniqueId());
				target.sendMessage("SP> " + ChatColor.GREEN + "your firework cooldown has expired!");
				logger.info(target.getName() + "'s firework cooldown just expired");
			}
		}, 20*300L);
		return task;
	}
	
	private static void giveFireworks(Player target) {
		logger.info(target.getName() + " just got a stack of fireworks, they now have a 5 minute cooldown");
		target.getInventory().addItem(new ItemStack(Material.FIREWORK_ROCKET, 64));
		gotFireworks.add(target.getUniqueId());
		target.sendMessage("SP> " + ChatColor.GREEN + "you have been given a stack of fireworks");
		target.sendMessage("SP> " + ChatColor.YELLOW + "you now have a 5 minute cooldown before you can get any more");
		timer(target);
		return;
	}
}
