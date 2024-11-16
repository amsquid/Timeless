package me.joepeterson.Timeless.engine.mesh;

import me.joepeterson.Timeless.engine.texture.Texture;

public class HUDMesh extends BaseMesh {

	static float[] vertices = new float[]{
			0.0f, 0.0f, 0.0f,
			0.0f, 1.0f, 0.0f,
			1.0f, 1.0f, 0.0f,
			1.0f, 0.0f, 0.0f,
	};

	static int[] indices = new int[]{
			0, 1, 2,
			0, 2, 3,
	};

	static float[] texCoords = new float[]{
			-1.0f, -1.0f,
			-1.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, -1.0f,
	};

	public HUDMesh(Texture texture) {
		super(vertices, texCoords, indices, texture);
	}
}