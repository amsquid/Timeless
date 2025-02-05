package me.joepeterson.Timeless.engine.inventory;

import me.joepeterson.Timeless.engine.texture.Texture;

public class Material {
	private final int stackSize;
	private final String name;
	private final Texture texture;

	public Material(int stackSize, String name, Texture texture) {
		this.stackSize = stackSize;
		this.name = name;
		this.texture = texture;
	}

	public int getStackSize() {
		return stackSize;
	}

	public String getName() {
		return name;
	}

	public Texture getTexture() {
		return texture;
	}
}
