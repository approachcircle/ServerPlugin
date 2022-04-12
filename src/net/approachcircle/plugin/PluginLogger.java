package net.approachcircle.plugin;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class PluginLogger {
	public static Logger getPluginLogger() {
		Plugin plugin = Bukkit.getPluginManager().getPlugin("ServerPlugin");
		return plugin.getLogger();
	}
}
