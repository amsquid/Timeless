package me.joepeterson.Timeless.engine.world;

import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.block.BlockFace;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.mesh.CubeMesh;
import me.joepeterson.Timeless.engine.util.Vector;
import org.joml.Vector3i;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class World {

	private Map<Vector3i, Block> blocks = new HashMap<>();
	private Map<Vector3i, Chunk> chunks = new HashMap<>();
	private ArrayList<MeshEntity> entities = new ArrayList<>();

	public float gravity = 0.0f;

	public void addBlock(Block block) {
		blocks.put(block.position, block);
	}
	public void addEntity(MeshEntity entity) {
		entities.add(entity);
	}

	public boolean deleteBlock(Vector3i position) {
		if(!blocks.containsKey(position)) return false;

		Block block = blocks.get(position);

		block.cleanup();

		blocks.remove(position);

		return true;
	}

	public Map<Vector3i, Block> getBlocks() {
		return blocks;
	}
	public ArrayList<MeshEntity> getEntities() {
		return entities;
	}

	public Map<Vector3i, Class<?>> generateWorld(long seed) {
		return new HashMap<>();
	}

	public void loadWorld(Map<Vector3i, Class<?>> blocks) {
		for(Vector3i position : blocks.keySet()) {
			Class<?> blockClass = blocks.get(position);
			Block block = null;
			try {
				block = (Block) blockClass.getDeclaredConstructor(Vector3i.class).newInstance(position);
			} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				throw new RuntimeException(e);
			}

			addBlock(block);
		}
	}

	public void fixFacesAround(Vector3i position, int checkDiameter) {
		for(int x = -checkDiameter; x <= checkDiameter; x++) {
			for(int y = -checkDiameter; y <= checkDiameter; y++) {
				for(int z = -checkDiameter; z <= checkDiameter; z++) {
					Vector3i blockPos = Vector.toVector3i(Vector.addVectors(position, new Vector3i(x, y, z)));

					if(!blocks.containsKey(blockPos)) continue;

					fixFace(blockPos);
				}
			}
		}
	}

	public void fixFaces() {
		for(Vector3i position : blocks.keySet()) {
			fixFace(position);
		}
	}

	public void fixFaces(Set<Vector3i> positions) {
		for(Vector3i position : positions) {
			if(!blocks.containsKey(position)) continue;

			fixFace(position);
		}
	}

	public void fixFace(Vector3i position) {
		Block block = blocks.get(position);
		CubeMesh mesh = block.mesh;

		mesh.resetIndicesNoUpdate();

		// TOP
		if(blocks.containsKey(new Vector3i(block.position.x, block.position.y + 1, block.position.z))) mesh.deleteCubeIndices(BlockFace.TOP);
		// BOTTOM
		if(blocks.containsKey(new Vector3i(block.position.x, block.position.y - 1, block.position.z))) mesh.deleteCubeIndices(BlockFace.BOTTOM);

		// RIGHT
		if(blocks.containsKey(new Vector3i(block.position.x + 1, block.position.y, block.position.z))) mesh.deleteCubeIndices(BlockFace.RIGHT);
		// LEFT
		if(blocks.containsKey(new Vector3i(block.position.x - 1, block.position.y, block.position.z))) mesh.deleteCubeIndices(BlockFace.LEFT);

		// FRONT
		if(blocks.containsKey(new Vector3i(block.position.x, block.position.y, block.position.z + 1))) mesh.deleteCubeIndices(BlockFace.FRONT);
		// BACK
		if(blocks.containsKey(new Vector3i(block.position.x, block.position.y, block.position.z - 1))) mesh.deleteCubeIndices(BlockFace.BACK);

		block.mesh.updateIndices();
	}
}
