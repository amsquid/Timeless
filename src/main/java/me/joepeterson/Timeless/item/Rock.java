package me.joepeterson.Timeless.item;

import me.joepeterson.Timeless.engine.inventory.Item;
import me.joepeterson.Timeless.engine.texture.Texture;

import java.io.IOException;

public class Rock extends Item {
	public Rock() throws IOException {
		super(50, "Rock", new Texture("assets/textures/item/test_block.png"));
	}
}
