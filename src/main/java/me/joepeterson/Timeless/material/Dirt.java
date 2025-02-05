package me.joepeterson.Timeless.material;

import me.joepeterson.Timeless.block.DirtBlock;
import me.joepeterson.Timeless.block.RockBlock;
import me.joepeterson.Timeless.engine.inventory.BlockMaterial;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector3i;

import java.io.IOException;

public class Dirt extends BlockMaterial {
	private static DirtBlock dirtBlock = new DirtBlock(new Vector3i());
	private static Texture texture;

	static {
		try {
			texture = new Texture("assets/textures/item/dirt_block.png");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Dirt() {
		super(50, "Dirt", texture, dirtBlock);
	}
}
