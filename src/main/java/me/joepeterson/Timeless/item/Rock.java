package me.joepeterson.Timeless.item;

import me.joepeterson.Timeless.engine.inventory.Item;
import me.joepeterson.Timeless.engine.inventory.ItemType;
import me.joepeterson.Timeless.engine.texture.Texture;

import java.io.IOException;

public class Rock extends Item {
	public Rock() throws IOException {
		super(ItemType.ITEM, 50, "Rock", new Texture("textures/item/test_block.png"));
	}
}
