package net.approachcircle.plugin;

import java.util.logging.Logger;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.approachcircle.plugin.dimensions.StaggeredDimensionPreparer;
import net.approachcircle.plugin.misc.BusyManager;
import net.approachcircle.plugin.misc.CommandDispatcher;
import net.approachcircle.plugin.misc.MainListener;

public class ServerPlugin extends JavaPlugin {
	MainListener mainListener = new MainListener();
	Logger logger = getLogger();

	@Override
	public void onEnable() {
		BusyManager.setBusy("still starting up");
		logger.info("plugin made by approachcircle. contact: <osc-mcd@protonmail.com>");
		PluginManager pluginManager = getServer().getPluginManager();
		pluginManager.registerEvents(mainListener, this);
		logger.info("all events have been registered");
		logger.info("started monitoring reports");
		// StaggeredDimensionPreparer.startTimer();
		logger.info("all done!");
		BusyManager.notBusy();
	}

	@Override
	public void onDisable() {
		BusyManager.setBusy("server shutting down");
		HandlerList.unregisterAll(mainListener);
		StaggeredDimensionPreparer.stopTimer();
		logger.info("finished up, goodbye!");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		logger.info(String.format("DISPATCH INTERNAL: /%s", label));
		return CommandDispatcher.dispatch(label, sender, args);
	}
}
