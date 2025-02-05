package me.joepeterson.Timeless.material;

import me.joepeterson.Timeless.engine.inventory.Material;
import me.joepeterson.Timeless.engine.texture.Texture;

import java.io.IOException;

public class Air extends Material {

	private static Texture texture;

	static {
		try {
			texture = new Texture("assets/textures/item/empty.png");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Air() {
		super(0, "air", texture);
	}

}
