package me.joepeterson.Timeless.world;

import me.joepeterson.Timeless.blocks.RockBlock;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.mesh.ModelMesh;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.world.World;
import org.joml.Vector3i;

import java.util.Random;

public class SpaceWorld extends World {

	public void generateWorld(long seed) {
		Random random = new Random(seed);

		try {
			Texture texture = new Texture("textures/model/blue_rock.png");
			ModelMesh modelMesh = new ModelMesh("models/test.dae", texture);

			MeshEntity entity = new MeshEntity(modelMesh);

			//addEntity(entity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		for(int i = 0; i < 500; i++) {
			createAsteroid(new Vector3i(random.nextInt(-100, 100), random.nextInt(-100, 100), random.nextInt(-100, 100)), random.nextInt(1, 5));
		}

		fixFaces();
	}

	private void createAsteroid(Vector3i position, int size) {
		for(int x = position.x - size; x < position.x + size; x++) {
			for(int y = position.y - size; y < position.y + size; y++) {
				for(int z = position.z - size; z < position.z + size; z++) {
					Vector3i pos = new Vector3i(x, y, z);

					RockBlock block = new RockBlock(pos);

					addBlock(block);
				}
			}
		}
	}

}
