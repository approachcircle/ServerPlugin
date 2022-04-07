package net.approachcircle.plugin;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class MainListener implements Listener {
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		VillagerDetector.checkEntityType(event);
	}
	  
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent event) {
		Player player = event.getPlayer();
		LocalTime time = LocalTime.now();
		ReportsCommand.playerJoined(player, dtf.format(time));
	}
	  
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		LocalTime time = LocalTime.now();
		ReportsCommand.playerQuit(player, dtf.format(time));
	}
}
