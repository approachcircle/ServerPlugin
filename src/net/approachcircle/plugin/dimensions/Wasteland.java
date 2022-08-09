package net.approachcircle.plugin.dimensions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Wasteland extends Dimension {
	private static World wasteland = null;
	private static Wasteland instance = new Wasteland();

	private static String name = "wasteland";

	private Wasteland() {}

	public static Wasteland getInstance() {
		return instance;
	}

	@Override
	public World get() {
		return wasteland;
	}

	@Override
	public void prepare() {
		WorldCreator creator = new WorldCreator(name);
		creator.generator(new WastelandChunkGenerator());
		creator.environment(World.Environment.NORMAL);
		wasteland = Bukkit.getServer().createWorld(creator);
		wasteland.setSpawnLocation(new Location(wasteland, 0, 61, 0));
	}

	@Override
	public String getName() {
		return name;
	}
}
