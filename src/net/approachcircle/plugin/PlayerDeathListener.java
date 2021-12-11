package net.approachcircle.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
	
	DeathMessages deathMessages = new DeathMessages();
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		deathMessages.displayMessage(event);
	}
}
