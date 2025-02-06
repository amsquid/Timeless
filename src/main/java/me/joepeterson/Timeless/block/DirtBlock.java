package me.joepeterson.Timeless.block;

import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.inventory.Material;
import org.joml.Vector3i;

import java.io.IOException;

public class DirtBlock extends BreakableBlock {
	private static final Texture texture = new Texture("assets/textures/block/dirt.png");

	public DirtBlock(Vector3i position) {
		super(position, texture, Material.DIRT);
	}


}
