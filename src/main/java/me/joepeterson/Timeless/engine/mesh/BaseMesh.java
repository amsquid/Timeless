package me.joepeterson.Timeless.engine.mesh;

import me.joepeterson.Timeless.engine.texture.Texture;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class BaseMesh {

	public int vao, vbo, ebo, texVbo;
	public int vertexLength;

	Texture texture;

	public BaseMesh(float[] vertices, float[] texCoords, int[] indices, Texture texture) {
		updateBuffers(false, vertices, texCoords, indices, texture);
	}

	public BaseMesh() { }

	public void updateBuffers(boolean cleanup, float[] vertices, float[] texCoords, int[] indices, Texture texture) {
		if(cleanup) this.cleanup();

		FloatBuffer vertexBuffer = null;
		FloatBuffer texBuffer = null;
		IntBuffer indicesBuffer = null;

		try {
			vertexLength = indices.length;
			this.texture = texture;

			vertexBuffer = MemoryUtil.memAllocFloat(vertices.length);
			vertexBuffer.put(vertices).flip();

			indicesBuffer = MemoryUtil.memAllocInt(indices.length);
			indicesBuffer.put(indices).flip();

			texBuffer = MemoryUtil.memAllocFloat(texCoords.length);
			texBuffer.put(texCoords).flip();

			// VAO
			vao = glGenVertexArrays();
			glBindVertexArray(vao);

			// VBO
			vbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);

			glVertexAttribPointer(0, 3, GL_FLOAT, false, 3 * Float.BYTES, 0);

			// Texture VBO
			texVbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, texVbo);
			glBufferData(GL_ARRAY_BUFFER, texBuffer, GL_STATIC_DRAW);

			glVertexAttribPointer(1, 2, GL_FLOAT, false, 2 * Float.BYTES, 0);

			// EBO
			ebo = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);

			// Unbinding
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			glBindVertexArray(0);
		} finally {
			if(vertexBuffer != null)
				MemoryUtil.memFree(vertexBuffer);

			if(texBuffer != null)
				MemoryUtil.memFree(texBuffer);

			if(indicesBuffer != null)
				MemoryUtil.memFree(indicesBuffer);
		}
	}

	public Texture getTexture() {
		return texture;
	}

	public void render() {
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);

		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, getTexture().getId());

		glDrawElements(GL_TRIANGLES, vertexLength, GL_UNSIGNED_INT, 0);

		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	}

	public void cleanup() {
		glDisableVertexAttribArray(0);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vbo);
		glDeleteBuffers(texVbo);
		glDeleteBuffers(ebo);

		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
	}
}
