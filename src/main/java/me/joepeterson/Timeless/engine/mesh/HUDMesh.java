package me.joepeterson.Timeless.engine.mesh;

import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector2f;

public class HUDMesh extends BaseMesh {

	static float[] initVertices = new float[]{
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
			0.0f, 0.0f,
			0.0f, 1.0f,
			1.0f, 1.0f,
			1.0f, 0.0f,
	};

	public HUDMesh(Texture texture, Vector2f position, Vector2f scale) {
		float[] vertices = new float[initVertices.length];

		for(int i = 0; i < initVertices.length / 3; i++) {
			float initX = initVertices[i * 3 + 0];
			float initY = initVertices[i * 3 + 1];

			// X
			if(initX == 0.0f) vertices[i * 3 + 0] = position.x;
			if(initX == 1.0f) vertices[i * 3 + 0] = position.x + scale.x;

			// Y
			if(initY == 0.0f) vertices[i * 3 + 1] = position.y;
			if(initY == 1.0f) vertices[i * 3 + 1] = position.y + scale.y;

			// Z
			vertices[i * 3 + 2] = 0.0f;
		}

		super(vertices, texCoords, indices, texture);
	}
}