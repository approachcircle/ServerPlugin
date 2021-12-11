package net.approachcircle.plugin;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class EntityDeathListener implements Listener {
  VillagerDetector villagerDetector = new VillagerDetector();
  
  @EventHandler
  public void onEntityDeath(EntityDeathEvent event) {
    this.villagerDetector.checkEntityType(event);
  }
}
