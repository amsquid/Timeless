package me.joepeterson.Timeless.block;

import me.joepeterson.Timeless.engine.block.BreakableBlock;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector3i;

import java.io.IOException;

public class DirtBlock extends BreakableBlock {
	private static Texture texture = null;
	static {
		try {
			texture = new Texture("assets/textures/block/dirt.png");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public DirtBlock(Vector3i position) {
		super(position, texture);
	}


}
