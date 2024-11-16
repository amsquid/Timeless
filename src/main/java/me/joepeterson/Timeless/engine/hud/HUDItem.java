package me.joepeterson.Timeless.engine.hud;

import me.joepeterson.Timeless.engine.mesh.HUDMesh;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class HUDItem {

	Vector2f position;
	Vector2f size;
	Vector4f color;
	HUDMesh mesh;

	public HUDItem(Texture texture) {
		mesh = new HUDMesh(texture);
	}

	public void render() {
		mesh.render();
	}
}
