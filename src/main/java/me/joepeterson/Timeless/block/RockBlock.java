package me.joepeterson.Timeless.block;

import me.joepeterson.Timeless.engine.block.BreakableBlock;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector3i;

import java.io.IOException;

public class RockBlock extends BreakableBlock {
	public RockBlock(Vector3i position) {
		Texture texture = null;
		try {
			texture = new Texture("textures/block/rock.png");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		super(position, texture);
	}


}
