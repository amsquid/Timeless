package me.joepeterson.Timeless.engine.world;

import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.block.BlockFace;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.mesh.CubeMesh;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class World {

	private Map<Vector3i, Block> blocks = new HashMap<>();
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

	public void generateWorld(long seed) { }

	public void fixFaces() {
		for(Vector3i position : blocks.keySet()) {
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
}
