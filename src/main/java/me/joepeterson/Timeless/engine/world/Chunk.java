package me.joepeterson.Timeless.engine.world;

import me.joepeterson.Timeless.engine.block.Block;
import org.joml.Vector3i;

import java.util.HashMap;
import java.util.Map;

public class Chunk {

	private Map<Vector3i, Block> blocks = new HashMap<>();

	public void renderChunk() {
		for(Block block : blocks.values()) {
			block.render();
		}
	}

}
