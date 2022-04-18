package net.approachcircle.plugin.command;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

public class Firework {
	private static List<Player> gotFireworks = new ArrayList<Player>();
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("ServerPlugin");
	private static Logger logger = plugin.getLogger();
	
	public static void giveFireworks(Player target) {
		if (!gotFireworks.contains(target)) {
			logger.info(target.getName() + " just got a stack of fireworks, they now have a 5 minute cooldown");
			target.getInventory().addItem(new ItemStack(Material.FIREWORK_ROCKET, 64));
			gotFireworks.add(target);
			target.sendMessage(ChatColor.GREEN + "you have been given a stack of fireworks");
			target.sendMessage(ChatColor.YELLOW + "you now have a 5 minute cooldown before you can get any more");
			timer(target);
		} else {
			target.sendMessage(ChatColor.RED + "your firework cooldown has not expired yet");
		}
	}
	
	private static BukkitTask timer(Player target) {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ServerPlugin");
		BukkitTask task = Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(plugin, new Runnable() {
			@Override
			public void run() {
				gotFireworks.remove(target);
				target.sendMessage(ChatColor.GREEN + "your firework cooldown has expired!");
				logger.info(target.getName() + "'s firework cooldown just expired");
			}
		}, 20*300L);
		return task;
	}
}
