package net.approachcircle.plugin.dimensions;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.approachcircle.plugin.PluginLogger;

public class DimensionPreparer {
	private static Dimension[] dimensionArray = {
			Wasteland.getInstance(),
			Spongeland.getInstance(),
			Blackstoneland.getInstance()
	};
	
	private static Logger logger = PluginLogger.getPluginLogger();
	
	public static void prepareAllDimensions() {
		for (Dimension dimension : dimensionArray) {
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
	}
}
