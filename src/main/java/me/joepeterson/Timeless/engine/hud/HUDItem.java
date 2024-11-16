package me.joepeterson.Timeless.engine.hud;

import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.mesh.HUDMesh;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class HUDItem {

	float aspectRatio;
	Vector2f position;
	Vector2f scale;
	HUDMesh mesh;

	public HUDItem(Texture texture, Vector2f position, Vector2f scale) {
		mesh = new HUDMesh(texture, position, scale);

		this.position = position;
		this.scale = scale;
	}

	// Updating positions based on window resizing
	public void updatePosition(Camera camera) { }

	public void render() {
		mesh.render();
	}
}
