package me.joepeterson.Timeless.hud;

import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.util.Vector;
import org.joml.Vector2f;

import java.io.IOException;

public class Crosshair extends HUDItem {

	public Crosshair() throws IOException {
		Texture texture = new Texture("textures/ui/crosshair.png");

		Vector2f scale = Vector.multiplyVector(new Vector2f(1 / 16.f, 1 / 9.f), .4f);
		Vector2f position = new Vector2f(0.5f - (scale.x / 2.0f), 0.5f - (scale.y / 2.0f));

		super(texture, position, scale);
	}

	public void updatePosition() {}
}
