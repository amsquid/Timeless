package me.joepeterson.Timeless.material;

import me.joepeterson.Timeless.block.RockBlock;
import me.joepeterson.Timeless.engine.inventory.BlockMaterial;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector3i;

import java.io.IOException;

public class Rock extends BlockMaterial {
	private static RockBlock rockBlock = new RockBlock(new Vector3i());
	private static Texture texture;

	static {
		try {
			texture = new Texture("assets/textures/item/rock_block.png");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Rock() {
		super(50, "Rock", texture, rockBlock);
	}
}
