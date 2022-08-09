package net.approachcircle.plugin.dimensions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class Blackstoneland extends Dimension {
	private static World blackstoneland = null;
	private static Blackstoneland instance = new Blackstoneland();

	private static String name = "blackstoneland";

	private Blackstoneland() {}

	public static Blackstoneland getInstance() {
		return instance;
	}

	@Override
	public World get() {
		return blackstoneland;
	}

	@Override
	public void prepare() {
		WorldCreator creator = new WorldCreator(name);
		creator.generator(new BlackstonelandChunkGenerator());
		creator.environment(World.Environment.NORMAL);
		blackstoneland = Bukkit.getServer().createWorld(creator);
		/** use a for loop to loop over blocks in the Y axis that are not air, then when
		 * a block that is not air is found, add one and use that as the Y value for the
		 * spawn location to prevent fall damage when spawning in.
		 */
		blackstoneland.setSpawnLocation(new Location(blackstoneland, 0, 81, 0));
	}

	@Override
	public String getName() {
		return name;
	}
}
