package net.approachcircle.plugin.misc;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.approachcircle.plugin.anticheat.AntiSuffocate;
import net.approachcircle.plugin.command.Maintenance;
import net.approachcircle.plugin.command.Reports;
import net.approachcircle.plugin.dimensions.WorldSwitcher;

public class MainListener implements Listener {
	private DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");

	@EventHandler
	public void onEntityDeath(EntityDeathEvent event) {
		VillagerDetector.checkEntityType(event);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (Maintenance.getState()) {
			if (player.isOp()) {
				player.sendMessage("SP> " + ChatColor.YELLOW + "warning: the server is currently in maintenance mode");
			} else {
				Maintenance.turnAwayPlayer(player, true);
				return;
			}
		}
		if (BusyManager.getState()) {
			BusyManager.turnAwayPlayer(player);
			return;
		}
		LocalTime time = LocalTime.now();
		Reports.playerJoined(player, dtf.format(time));
		player.sendMessage("SP> " + ChatColor.LIGHT_PURPLE + "welcome back " + player.getName());
		if (player.isOp()) {
			player.sendMessage("SP> " + ChatColor.LIGHT_PURPLE + "to view server reports, type /reports");
		}
		time = LocalTime.now();
		player.sendMessage("SP> " + ChatColor.LIGHT_PURPLE + "the server time is " + dtf.format(time));
		WorldSwitcher.warnFaultyDimensions(player);
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		LocalTime time = LocalTime.now();
		Reports.playerQuit(player, dtf.format(time));
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		// just keep in case
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		AntiSuffocate.checkSuffocationState(event.getPlayer());
	}
}
