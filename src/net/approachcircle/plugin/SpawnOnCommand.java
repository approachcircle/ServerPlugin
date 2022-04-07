package net.approachcircle.plugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.EntityType;

public class SpawnOnCommand {
	private static Plugin plugin = Bukkit.getPluginManager().getPlugin("ServerPlugin");
	private static Logger logger = plugin.getLogger();
	
	public static void spawnOn(CommandSender sender, String mobName, String playerName) {
		Player player = Bukkit.getPlayer(playerName);
		EntityType entity = EntityType.UNKNOWN;
		try {
			entity = (EntityType) Enum.valueOf(EntityType.class, mobName.toUpperCase());
		} catch (IllegalArgumentException e) {
			sender.sendMessage(ChatColor.RED + "entity of name '" + mobName + "' does not exist");
			return;
		}
		World world = player.getWorld();
		world.spawnEntity(player.getLocation(), entity);
		sender.sendMessage("spawned " + entity.toString() + " on " + player.getName());
		logger.info("a " + entity.toString() + " was just spawned on " + player.getName());
	}
}
