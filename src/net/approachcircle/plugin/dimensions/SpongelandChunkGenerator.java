package net.approachcircle.plugin.dimensions;


import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class SpongelandChunkGenerator extends ChunkGenerator {
	@Override
	public boolean shouldGenerateNoise() {
		return false;
	}

	@Override
	public boolean shouldGenerateSurface() {
		return true;
	}

	@Override
	public boolean shouldGenerateBedrock() {
		return true;
	}

	@Override
	public boolean shouldGenerateCaves() {
		return true;
	}

	@Override
	public boolean shouldGenerateDecorations() {
		return false;
	}

	@Override
	public boolean shouldGenerateMobs() {
		return false;
	}

	@Override
	public boolean shouldGenerateStructures() {
		return true;
	}

	@Override
	public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
		SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(worldInfo.getSeed()), 6);
		generator.setScale(0.005D);

		int worldX = chunkX * 16;
		int worldZ = chunkZ * 16;

		for (Integer x = 0; x < 16; x++) {
			for (Integer z = 0; z < 16; z ++) {
				Double noise = generator.noise(worldX + x, worldZ + z, 1, 1, true);
				Integer height = (int) (noise * 40);
				height += 84;
				if (height > chunkData.getMaxHeight()) {
					height = chunkData.getMaxHeight();
				}
				for (Integer y = chunkData.getMinHeight(); y < height; y++) {
					chunkData.setBlock(x, y, z, Material.SPONGE);
				}
			}
		}
	}

	@Override
    public void generateBedrock(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
        if (chunkData.getMinHeight() == worldInfo.getMinHeight()) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    chunkData.setBlock(x, chunkData.getMinHeight(), z, Material.BEDROCK);
                    chunkData.setBlock(x, chunkData.getMinHeight() + 1, z, Material.BEDROCK);
                }
            }
        }
    }
}
