package net.approachcircle.plugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

public class SeizeIllegal {
	
	Logger logger = Bukkit.getLogger();
	
	Collection<Material> illegalItems = new
			ArrayList<Material>(Arrays.asList(
			Material.BEDROCK,
			Material.BARRIER,
			Material.STRUCTURE_VOID,
			Material.STRUCTURE_BLOCK,
			Material.END_PORTAL_FRAME
	));
	
	public void CheckItem(EntityPickupItemEvent event) {
		Item entityItem = event.getItem();
		ItemStack entityItemStack = entityItem.getItemStack();
		for (Material item : illegalItems) {
			ItemStack currentItem = new ItemStack(item);
			if (currentItem.isSimilar(entityItemStack)) {
				logger.info("entity has bad item");
				EntityType entityType = event.getEntityType();
				if (entityType == EntityType.PLAYER) {
					logger.info("entity is player");
					SeizePlayer(event.getEntity(), entityItemStack);
				} else {
					logger.info("entity is not player");
					SeizeEntity(event.getEntity(), entityItemStack);
				}
			}
		}
	}
	
	private void SeizePlayer(LivingEntity player, ItemStack illegalItem) {
		logger.info("called SeizePlayer()");
		Player playerEntity = (Player) player;
		logger.warning("player " + playerEntity.getName() + " has item " + illegalItem.toString());
		if (playerEntity.getInventory().contains(illegalItem)) {
			playerEntity.getInventory().removeItem(illegalItem);
			Bukkit.broadcastMessage(ChatColor.RED + "[ServerPlugin] '" + illegalItem.toString() + "' was found in " + playerEntity.getName() + "'s inventory");
		}
	}
	
	private void SeizeEntity(LivingEntity entity, ItemStack illegalItem) {
		logger.info("called SeizeEntity()");
		logger.warning("entity " + entity.getName() + " has item " + illegalItem.toString());
		entity.remove();
		Bukkit.broadcastMessage(ChatColor.RED + "[ServerPlugin] '" + illegalItem.toString() + "' has just been removed from a " + entity.toString() + "'s inventory, who the fuck gave that to it?");
	}
}
