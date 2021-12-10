package net.approachcircle.plugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class EntityPickupItemListener implements Listener {
	
	SeizeIllegal seizeIllegal = new SeizeIllegal();
	Logger logger = Bukkit.getLogger();
	
	@EventHandler
	public void onEntityPickupItem(EntityPickupItemEvent event) {
		seizeIllegal.CheckItem(event);
	}
}