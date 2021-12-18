package net.approachcircle.plugin;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerPlugin extends JavaPlugin {
	
	PlayerDeathListener playerDeathListener = new PlayerDeathListener();
	EntityPickupItemListener EPIL = new EntityPickupItemListener();
	EntityDeathListener entityDeathListener = new EntityDeathListener();
	PlayerJoinListener playerJoinListener = new PlayerJoinListener();
	DoxCommand dox = new DoxCommand();
	
	public Logger logger = getLogger();
	
	public void onEnable() {
		logger.info("plugin made by approachcircle. contact: <osc-mcd@protonmail.com>");
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(playerDeathListener, this);
		pluginManager.registerEvents(entityDeathListener, this);
		pluginManager.registerEvents(playerJoinListener, this);
		// pluginManager.registerEvents(EPIL, this);
		// ^^^^ has been deregistered because it is too unstable
	}
	
	public void onDisable() {
		logger.info("plugin disabled");
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (label.equalsIgnoreCase("dox")) {
			if (sender.isOp()) {
				if (args.length > 0) {
					dox.Dox(sender, args);
					return true;
				} else {
					sender.sendMessage(ChatColor.RED + "you need to supply a player to dox");
					return true;
				}
			} else {
				dox.DoxFailed(sender, args);
				return true;
			}
		} else {
			return true;
		}
	}
}
