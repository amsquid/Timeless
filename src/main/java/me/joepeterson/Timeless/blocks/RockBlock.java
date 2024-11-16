package me.joepeterson.Timeless.blocks;

import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector3i;

import java.io.IOException;

public class RockBlock extends Block {
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
