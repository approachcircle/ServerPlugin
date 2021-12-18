package net.approachcircle.plugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.comphenix.protocol.ProtocolLibrary;

public class PlayerJoinListener implements Listener {
	Logger logger = Bukkit.getLogger();
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		Integer playerVersion = ProtocolLibrary.getProtocolManager().getProtocolVersion(player);
		if (playerVersion < 757) {
			logger.info(player.getName() + " is on a client that is older 1.18.1");
			player.kickPlayer(ChatColor.RED + "for security reasons, you must use 1.18.1 or later.\nthis is because versions using 1.18.1 or over have patches for the log4j2 vulnerability.\nprotocol version: " + playerVersion + ". should be at least: " + 757);
		} else {
			logger.info(player.getName() + " is on 1.18.1 or newer");
		}
	}
}
