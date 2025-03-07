package me.joepeterson.Timeless.world;

import me.joepeterson.Timeless.block.DirtBlock;
import me.joepeterson.Timeless.block.RockBlock;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.world.World;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpaceWorld extends World {

	public Map<Vector3i, Class<?>> generateWorld(long seed) {
		Random random = new Random(seed);

		Map<Vector3i, Class<?>> returnBlocks = new HashMap<>();

		for(int i = 0; i < 5; i++) {
			returnBlocks.putAll(createAsteroid(new Vector3i(random.nextInt(-10, 10), random.nextInt(-10, 10), random.nextInt(-10, 10)), random.nextInt(1, 5)));
		}

		return returnBlocks;
	}

	private Map<Vector3i, Class<?>> createAsteroid(Vector3i position, int size) {
		Map<Vector3i, Class<?>> blocks = new HashMap<>();

		Class<?> blockToPlace = RockBlock.class;

		int random = (int) (Math.random() * 2);

		if(random == 1) {
			blockToPlace = DirtBlock.class;
		}

		for(int x = position.x - size; x <= position.x + size; x++) {
			for(int y = position.y - size; y <= position.y + size; y++) {
				for(int z = position.z - size; z <= position.z + size; z++) {
					Vector3i pos = new Vector3i(x, y, z);

					blocks.put(pos, blockToPlace);
				}
			}
		}

		return blocks;
	}

}
