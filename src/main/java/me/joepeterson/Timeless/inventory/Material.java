package me.joepeterson.Timeless.inventory;

import me.joepeterson.Timeless.block.DirtBlock;
import me.joepeterson.Timeless.block.RockBlock;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector3i;

public enum Material {
	AIR("air", new Texture("assets/textures/item/empty.png")),
	DIRT("Dirt", new Texture("assets/textures/item/dirt.png"), 999, new DirtBlock(new Vector3i())),
	ROCK("Rock", new Texture("assets/textures/item/rock.png"), 999, new RockBlock(new Vector3i()));

	public final String displayName;
	public final Texture texture;
	public final int stackSize;
	public final Block blockToPlace;

	Material(String displayName, Texture texture) {
		this.displayName = displayName;
		this.texture = texture;
		this.stackSize = -1;
		this.blockToPlace = null;
	}
	Material(String displayName, Texture texture, int stackSize) {
		this.displayName = displayName;
		this.texture = texture;
		this.stackSize = stackSize;
		this.blockToPlace = null;
	}

	Material(String displayName, Texture texture, int stackSize, Block blockToPlace) {
		this.displayName = displayName;
		this.texture = texture;
		this.stackSize = stackSize;
		this.blockToPlace = blockToPlace;
	}
}
