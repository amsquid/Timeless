package me.joepeterson.Timeless.block;

import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.inventory.Material;
import org.joml.Vector3i;

public class BreakableBlock extends Block {

	float health = 1.0f;
	float breakMul = 0.1f;

	private Material giveMaterial;

	public BreakableBlock(Vector3i position, Texture texture, float breakMul) {
		super(position, texture);

		this.breakMul = breakMul;
	}

	public BreakableBlock(Vector3i position, Texture texture) {
		super(position, texture);
		this.giveMaterial = null;
	}

	public BreakableBlock(Vector3i position, Texture texture, Material giveMaterial) {
		super(position, texture);
		this.giveMaterial = giveMaterial;
	}

	public Material getGiveMaterial() {
		return giveMaterial;
	}

	public void destroy() {}
}
