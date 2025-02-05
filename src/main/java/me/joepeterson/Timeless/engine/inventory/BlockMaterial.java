package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.texture.Texture;

public class BlockMaterial extends Material {

	private final Block block;

	public BlockMaterial(int stackSize, String name, Texture texture, Block block) {
		super(stackSize, name, texture);

		this.block = block;
	}

	public Block getBlock() {
		return block;
	}
}
