package me.joepeterson.Timeless.block;

import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.inventory.Material;
import org.joml.Vector3i;

public class RockBlock extends BreakableBlock {
	private static Texture texture = new Texture("assets/textures/block/rock.png");

	public RockBlock(Vector3i position) {
		super(position, texture, Material.ROCK);
	}


}
