package me.joepeterson.Timeless.engine.block;

import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector3i;

public class BreakableBlock extends Block {

	float health = 1.0f;
	float breakMul = 0.1f;

	public BreakableBlock(Vector3i position, Texture texture, float breakMul) {
		super(position, texture);

		this.breakMul = breakMul;
	}

	public BreakableBlock(Vector3i position, Texture texture) {
		super(position, texture);
	}

	public void destroy() {}
}
