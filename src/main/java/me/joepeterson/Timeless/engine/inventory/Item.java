package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.texture.Texture;

public class Item {
	private final int itemStackSize;
	private final String name;
	private final Texture texture;

	public Item(int itemStackSize, String name, Texture texture) {
		this.itemStackSize = itemStackSize;
		this.name = name;
		this.texture = texture;
	}

	public int getItemStackSize() {
		return itemStackSize;
	}

	public String getName() {
		return name;
	}

	public Texture getTexture() {
		return texture;
	}
}
