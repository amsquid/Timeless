
package me.joepeterson.Timeless.block;

import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.inventory.Material;
import org.joml.Vector3i;

public class GrassBlock extends BreakableBlock {
	private static final Texture texture = new Texture("assets/textures/block/grass.png");

	public GrassBlock(Vector3i position) {
		super(position, texture, Material.GRASS);
	}


}
