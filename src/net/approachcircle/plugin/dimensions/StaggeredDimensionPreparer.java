package net.approachcircle.plugin.dimensions;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import net.approachcircle.plugin.misc.PluginLogger;

public class StaggeredDimensionPreparer {
	private static BukkitTask timer;
	private static Logger logger = PluginLogger.getPluginLogger();
	private static boolean finished = false;
	public static Double timerMinutes = 5.0;
	private static long timerTicks = (long) (20*(timerMinutes * 60));

	public static void startTimer() {
		if (finished || timer != null) {
			return;
		}
		logger.info("STARTED staggered dimension preparation timer");
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ServerPlugin");
		timer = plugin.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
			@Override
			public void run() {
				DimensionPreparer.prepareAllDimensions();
				finished = true;
			}
		}, timerTicks);
	}

	public static boolean isFinished() {
		return finished;
	}
	
	public static void setFinished() {
		finished = true;
		return;
	}

	public static void stopTimer() {
		if (timer != null && !timer.isCancelled() && !finished) {
			timer.cancel();
			logger.info("CANCELLED staggered dimension preparation timer");
			timer = null;
		}
	}
}
