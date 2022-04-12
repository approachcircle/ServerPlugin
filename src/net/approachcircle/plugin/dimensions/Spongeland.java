package net.approachcircle.plugin.dimensions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Spongeland extends Dimension {
	private static World spongeland = null;
	private static Spongeland instance = new Spongeland();
	
	private static String name = "spongeland";
	
	private Spongeland() {}
	
	public static Spongeland getInstance() {
		return instance;
	}
	
	public World get() {
		return spongeland;
	}
	
	public void prepare() {
		WorldCreator creator = new WorldCreator(name);
		creator.generator(new SpongelandChunkGenerator());
		creator.environment(World.Environment.NORMAL);
		spongeland = Bukkit.getServer().createWorld(creator);
		spongeland.setSpawnLocation(new Location(spongeland, 0, 81, 0));
	}
	
	public String getName() {
		return name;
	}
}
