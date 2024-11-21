package me.joepeterson.Timeless.engine.hud;

import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector2f;

public class FullscreenHUDItem extends HUDItem {
	public FullscreenHUDItem(Texture texture) {
		super(texture, new Vector2f(), new Vector2f(1.0f, 1.0f));
	}
}
