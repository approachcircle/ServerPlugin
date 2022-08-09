package net.approachcircle.plugin.dimensions;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.approachcircle.plugin.misc.BusyManager;
import net.approachcircle.plugin.misc.PluginLogger;

public class DimensionPreparer {
	public final static Dimension[] dimensions = {
			Wasteland.getInstance(),
			Spongeland.getInstance(),
			Blackstoneland.getInstance()
	};

	private static Logger logger = PluginLogger.getPluginLogger();

	public static void prepareAllDimensions() {
		BusyManager.setBusy("preparing dimensions");
		logger.warning("---===server may become unresponsive, don't panic!===---");
		for (Dimension dimension : dimensions) {
			try {
				logger.info("preparing dimension: " + dimension.getName());
				dimension.prepare();
			} catch (Exception e) {
				logger.log(Level.SEVERE, "failed to prepare dimension: " + dimension.getName(), e);
				WorldSwitcher.isDisabled = true;
				WorldSwitcher.faultyDimensions.add(dimension);
				logger.severe("dimension switching has been disabled");
			}
		}
		logger.info("finished preparing dimensions");
		BusyManager.notBusy();
	}
}
