package net.approachcircle.plugin.dimensions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import net.approachcircle.plugin.PluginLogger;

public class DimensionPreparer {
	private static Dimension[] dimensionArray = {Wasteland.getInstance(), Spongeland.getInstance()};
	public static List<Dimension> dimensionList = new ArrayList<Dimension>(Arrays.asList(dimensionArray));
	private static Logger logger = PluginLogger.getPluginLogger();
	
	public static void prepareAllDimensions() {
		for (Dimension dimension : dimensionList) {
			try {
				logger.info("preparing dimension: " + dimension.getName());
				dimension.prepare();
			} catch (Exception e) {
				logger.severe("failed to prepare dimension: " + dimension.getName());
				WorldSwitcher.isDisabled = true;
				WorldSwitcher.faultyDimensions.add(dimension);
				logger.severe("dimension switching has been disabled");
			}
		}
	}
}
