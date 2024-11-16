package me.joepeterson.Timeless.hud;

import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.engine.texture.Texture;

import java.io.IOException;

public class Crosshair extends HUDItem {

	public Crosshair() throws IOException {
		Texture texture = new Texture("textures/ui/crosshair.png");

		super(texture);
	}
}
