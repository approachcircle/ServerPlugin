package net.approachcircle.plugin.dimensions;

import org.bukkit.World;

public abstract class Dimension {
	public abstract World get();

	public abstract void prepare();

	public abstract String getName();
}
