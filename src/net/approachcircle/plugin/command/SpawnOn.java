package net.approachcircle.plugin.command;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import net.approachcircle.plugin.misc.PluginLogger;

public class SpawnOn {
	private static Logger logger = PluginLogger.getPluginLogger();

	public static void spawnOn(CommandSender sender, String mobName, String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		EntityType entity = EntityType.UNKNOWN;
		try {
			entity = Enum.valueOf(EntityType.class, mobName.toUpperCase());
		} catch (IllegalArgumentException e) {
			sender.sendMessage("SP> " + ChatColor.RED + "entity of name '" + mobName + "' does not exist");
			return;
		}
		World world = player.getWorld();
		world.spawnEntity(player.getLocation(), entity);
		sender.sendMessage("SP> spawned " + entity.toString() + " on " + player.getName());
		logger.info("a " + entity.toString() + " was just spawned on " + player.getName());
	}
}
