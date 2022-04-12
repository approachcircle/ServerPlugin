package net.approachcircle.plugin.dimensions;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;

public class WastelandChunkGenerator extends ChunkGenerator {	
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
		return false;
	}
	
	@Override
	public boolean shouldGenerateCaves() {
		return false;
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
		return false;
	}
	
	@Override
	public void generateSurface(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, ChunkData chunkData) {
		for (Integer x = 0; x < 16; x++) {
			for (Integer z = 0; z < 16; z++) {
				chunkData.setBlock(x, 60, z, Material.BEDROCK);
			}
		}
	}
}
