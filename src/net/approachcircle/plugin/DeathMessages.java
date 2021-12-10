package net.approachcircle.plugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class DeathMessages {
	
	Logger logger = Bukkit.getLogger();
	
	public void displayMessage(PlayerDeathEvent event) {
		//EntityType entityType = event.getEntityType();
		Player victim = event.getEntity();
		Player killer = victim.getKiller();
		EntityType killerEntityType = EntityType.UNKNOWN;
		
		if (killer != null) {
			killerEntityType = killer.getType();
		}
		
		String victimName = victim.getName();
		
		String lava = victimName + " fell into lava and fucking died";
		String burned = victimName + " caught on fire and couldn't put themselves out";
		String fell = victimName + " broke their fucking legs and died apparently";
		String _void = victimName + " sacrificed themself to the void";
		String drowned = victimName + " couldn't escape the fucking water quick enough and drowned";
		String suffocated = victimName + " got absolutely fucking smothered by a block and died";
		String unknownDeath = victimName + " died from an unknown cause";
		String suicide = victimName + " killed themself";
		
		if (killerEntityType == EntityType.PLAYER) {
			if (killer.getInventory().getItemInMainHand() != null) {
				logger.info("killer.getInventory().getItemInMainHand() was not null");
				ItemStack currentItem = killer.getInventory().getItemInMainHand();
				if (currentItem.getItemMeta().hasDisplayName()) {
					logger.info("currentItem.getItemMeta().hasDisplayName() was true");
					String itemName = currentItem.getItemMeta().getDisplayName();
					event.setDeathMessage(ChatColor.RED + killer.getName() + " murdered " + victim.getName() + " using " + itemName);
				} else {
					logger.info("currentItem.getItemMeta().hasDisplayName() was false");
					if (killer == victim) {
						logger.info("killer == victim is true");
						event.setDeathMessage(suicide);
					} else {
						logger.info("killer == victim is false");
						event.setDeathMessage(ChatColor.RED + killer.getName() + " murdered " + victim.getName());
					}
				}
			} else {
				logger.info("killer.getItemInUse() was null");
				event.setDeathMessage(ChatColor.RED + killer.getName() + " murdered " + victim.getName());
			}
		} else if (event.getDeathMessage().contains("tried to swim in lava")) {
			event.setDeathMessage(lava);
		} else if (event.getDeathMessage().contains("burned to death")) {
			event.setDeathMessage(burned);
		} else if (event.getDeathMessage().contains("fell from a high place")) {
			event.setDeathMessage(fell);
		} else if (event.getDeathMessage().contains("fell out of the world")) {
			event.setDeathMessage(_void);
		} else if (event.getDeathMessage().endsWith("died")) {
			event.setDeathMessage(unknownDeath);
		} else if (event.getDeathMessage().endsWith("drowned")) {
			event.setDeathMessage(drowned);
		} else if (event.getDeathMessage().contains("suffocated in a wall")) {
			event.setDeathMessage(suffocated);
		}
	}
}