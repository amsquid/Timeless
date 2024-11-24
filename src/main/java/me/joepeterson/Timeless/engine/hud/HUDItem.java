package me.joepeterson.Timeless.engine.hud;

import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.mesh.BaseMesh;
import me.joepeterson.Timeless.engine.mesh.HUDMesh;
import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class HUDItem {

	private Vector2f position;
	private Vector2f scale;
	protected HUDMesh mesh;

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getScale() {
		return scale;
	}

	public HUDMesh getMesh() {
		return mesh;
	}

	public void setPosition(Vector2f position) {
		this.position = position;
	}

	public void setScale(Vector2f scale) {
		this.scale = scale;
	}

	public HUDItem(Texture texture, Vector2f position, Vector2f scale) {
		mesh = new HUDMesh(texture, position, scale);

		this.position = position;
		this.scale = scale;
	}

	public void render() {
		mesh.render();
	}
}
