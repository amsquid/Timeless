package me.joepeterson.Timeless.engine.entity;

import me.joepeterson.Timeless.engine.mesh.BaseMesh;
import org.joml.Matrix4f;

public class MeshEntity extends Entity {
	private final BaseMesh mesh;
	private float scale;

	public MeshEntity(BaseMesh mesh) {
		super();

		this.mesh = mesh;
		this.scale = 1.0f;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public BaseMesh getMesh() {
		return mesh;
	}

	public Matrix4f getMatrix(Matrix4f viewMatrix) {
		Matrix4f worldMatrix = new Matrix4f();

		worldMatrix.identity().translate(getPosition())
				.rotateX((float) Math.toRadians(-getRotation().x))
				.rotateY((float) Math.toRadians(-getRotation().y))
				.rotateZ((float) Math.toRadians(-getRotation().z))
				.scale(scale);

		Matrix4f viewCurr = new Matrix4f(viewMatrix);

		return viewCurr.mul(worldMatrix);
	}

	public void render() {
		mesh.render();
	}

}
