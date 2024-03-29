package net.approachcircle.plugin.misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;

public class VillagerDetector {
  public static void checkEntityType(EntityDeathEvent event) {
    if (event.getEntityType() == EntityType.VILLAGER)
      villagerHasDied(event);
  }

  private static void villagerHasDied(EntityDeathEvent event) {
    LivingEntity villagerEntity = event.getEntity();
    Location villagerLocation = villagerEntity.getLocation();
    Player villagerKiller = villagerEntity.getKiller();
    if (villagerKiller != null) {
      EntityType villagerKillerEntity = villagerKiller.getType();
      if (villagerKillerEntity == EntityType.PLAYER) {
        announceDeath(villagerKiller, villagerLocation);
      }
    } else {
    	announceDeath(villagerLocation);
    }
  }

  private static void announceDeath(Player villagerKiller, Location villagerLocation) {
    String villagerx = Integer.valueOf((int)villagerLocation.getX()).toString();
    String villagery = Integer.valueOf((int)villagerLocation.getY()).toString();
    String villagerz = Integer.valueOf((int)villagerLocation.getZ()).toString();
    String worldName = villagerLocation.getWorld().getName();
    String message = "SP> " + ChatColor.RED + String.format("a villager at %d %d %d was just killed by %s "
    		+ "in world '%s'", villagerx, villagery, villagerz, villagerKiller.getName(), worldName);
    Bukkit.broadcastMessage(message);
  }

  private static void announceDeath(Location villagerLocation) {
    String villagerx = Integer.valueOf((int)villagerLocation.getX()).toString();
    String villagery = Integer.valueOf((int)villagerLocation.getY()).toString();
    String villagerz = Integer.valueOf((int)villagerLocation.getZ()).toString();
    String worldName = villagerLocation.getWorld().getName();
    String message = "SP> " + ChatColor.RED + String.format("a villager at %d %d %d in world '%s' "
    		+ "has just died", villagerx, villagery, villagerz, worldName);
    Bukkit.broadcastMessage(message);
  }
}
