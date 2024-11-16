package me.joepeterson.Timeless.engine.entity;

import me.joepeterson.Timeless.engine.Window;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.util.Vector;
import me.joepeterson.Timeless.engine.world.World;
import org.joml.*;

import java.lang.Math;

public class Camera extends Entity {

	public float fov;
	public float startFOV;
	public float aspect;

	public final float Z_NEAR = 0.001f;
	public final float Z_FAR = 1000.0f;

	private Matrix4f projectionMatrix;
	private Matrix4f orthographicMatrix;

	private final Matrix4f viewMatrix;

	private int windowWidth;
	private int windowHeight;

	public Camera(Window window) {
		super();

		viewMatrix = new Matrix4f();

		updateMatrices(window.width, window.height, 90.0f);
		this.startFOV = (float) Math.toRadians(90.0f);
	}

	public void updateMatrices(int windowWidth, int windowHeight, float fovAngle) {
		aspect = (float) windowWidth / windowHeight;
		fov = (float) Math.toRadians(fovAngle);

		this.windowWidth = windowWidth;
		this.windowHeight = windowHeight;

		projectionMatrix = new Matrix4f().perspective(fov, aspect, Z_NEAR, Z_FAR)
				.translate(getPosition())
				.rotateX((float) Math.toRadians(getRotation().x))
				.rotateY((float) Math.toRadians(getRotation().y))
				.rotateZ((float) Math.toRadians(getRotation().z));

		orthographicMatrix = new Matrix4f().setOrtho2D(0.0f, 1.0f, 1.0f, 0.0f);
	}

	public void updateMatrices(float fovAngle) {
		updateMatrices(windowWidth, windowHeight, fovAngle);
	}

	public void updateMatrices() {
		projectionMatrix = new Matrix4f().perspective(fov, aspect, Z_NEAR, Z_FAR);
	}

	// Offset matrix
	public Matrix4f getViewMatrix() {
		Vector3f pos = getPosition();
		Vector3f rot = getRotation();

		pos = Vector.multiplyVector(pos, -1); // Negative 1 to use as an offset

		viewMatrix.identity();

		viewMatrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1.0f, 0.0f, 0.0f))
				.rotate((float) Math.toRadians(rot.y), new Vector3f(0.0f, 1.0f, 0.0f));

		viewMatrix.translate(pos);

		return viewMatrix;
	}

	// Matrices
	public Matrix4f getProjectionMatrix() {
		updateMatrices();

		return projectionMatrix;
	}

	public Matrix4f getOrthographicMatrix() {
		updateMatrices();

		return orthographicMatrix;
	}

	// Raymarching
	private float intbound(float s, float ds) {
		if (ds == 0) return Float.POSITIVE_INFINITY;  // No movement in this direction
		float s0 = (float) Math.floor(s);  // Get the position of the current block boundary
		return (ds > 0 ? (s0 + 1 - s) : (s - s0)) / Math.abs(ds);  // Distance to next boundary
	}

	public Block rayMarchBlock(World world, int maxDistance) {
		Vector3f dir = getViewMatrix().positiveZ(
				new Vector3f(
						getRotation().x,
						getRotation().y,
						getRotation().z
				)
		).negate();

		Vector3f pos = getPosition();

		Vector3i currentBlock = Vector.toVector3iFloor(pos);

		int stepX = dir.x > 0 ? 1 : -1;
		int stepY = dir.y > 0 ? 1 : -1;
		int stepZ = dir.z > 0 ? 1 : -1;

		float tMaxX = intbound(pos.x, dir.x);
		float tMaxY = intbound(pos.y, dir.y);
		float tMaxZ = intbound(pos.z, dir.z);

		float tDeltaX = Math.abs(1 / dir.x);
		float tDeltaY = Math.abs(1 / dir.y);
		float tDeltaZ = Math.abs(1 / dir.z);

		for (int i = 0; i < maxDistance; i++) {
			Block block = world.getBlocks().get(currentBlock);

			if (block != null) {
				block.position = new Vector3i(currentBlock);

				return block;
			}

			if (tMaxX < tMaxY && tMaxX < tMaxZ) {
				currentBlock.x += stepX;
				tMaxX += tDeltaX;
			} else if (tMaxY < tMaxZ) {
				currentBlock.y += stepY;
				tMaxY += tDeltaY;
			} else {
				currentBlock.z += stepZ;
				tMaxZ += tDeltaZ;
			}
		}

		return null;
	}
}
