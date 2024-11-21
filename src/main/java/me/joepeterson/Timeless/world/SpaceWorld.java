package me.joepeterson.Timeless.world;

import me.joepeterson.Timeless.blocks.RockBlock;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.mesh.ModelMesh;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.world.World;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SpaceWorld extends World {

	public Map<Vector3i, Integer> generateWorld(long seed) {
		Random random = new Random(seed);

		Map<Vector3i, Integer> returnBlocks = new HashMap<>();

		for(int i = 0; i < 500; i++) {
			returnBlocks.putAll(createAsteroid(new Vector3i(random.nextInt(-100, 100), random.nextInt(-100, 100), random.nextInt(-100, 100)), random.nextInt(1, 5)));
		}

		return returnBlocks;
	}

	private Map<Vector3i, Integer> createAsteroid(Vector3i position, int size) {
		Map<Vector3i, Integer> blocks = new HashMap<>();

		for(int x = position.x - size; x < position.x + size; x++) {
			for(int y = position.y - size; y < position.y + size; y++) {
				for(int z = position.z - size; z < position.z + size; z++) {
					Vector3i pos = new Vector3i(x, y, z);
					int id = RockBlock.id;

					blocks.put(pos, id);
				}
			}
		}

		return blocks;
	}

}
