package me.joepeterson.Timeless.engine;

import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.engine.util.Files;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	Shader d3Shader;
	Shader d2Shader;

	public void init() throws Exception {
		// 3D Shader
		d3Shader = new Shader();

		d3Shader.createVertexShader(Files.loadResource("shaders/shader.vert"));
		d3Shader.createFragmentShader(Files.loadResource("shaders/shader.frag"));
		d3Shader.link();

		d3Shader.createUniform("modelViewMatrix");
		d3Shader.createUniform("projectionMatrix");
		d3Shader.createUniform("tex");

		d3Shader.setUniform("tex", 0);

		// 2D Shader
		d2Shader = new Shader();

		d2Shader.createVertexShader(Files.loadResource("shaders/gui.vert"));
		d2Shader.createFragmentShader(Files.loadResource("shaders/gui.frag"));
		d2Shader.link();

		d2Shader.createUniform("projectionMatrix");
		d2Shader.createUniform("tex");

		d2Shader.setUniform("tex", 0);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void render(MeshEntity entity, Camera camera) {
		d3Shader.bind();

		d3Shader.setUniform("modelViewMatrix", entity.getMatrix(camera.getViewMatrix()));
		d3Shader.setUniform("projectionMatrix", camera.getProjectionMatrix());

		entity.render();

		d3Shader.unbind();
	}

	public void render(Block block, Camera camera) {
		d3Shader.bind();

		d3Shader.setUniform("modelViewMatrix", block.getMatrix(camera.getViewMatrix()));
		d3Shader.setUniform("projectionMatrix", camera.getProjectionMatrix());

		block.render();

		d3Shader.unbind();
	}

	public void render(HUDItem item, Camera camera) {
		d2Shader.bind();

		d2Shader.setUniform("projectionMatrix", camera.getOrthographicMatrix());

		item.render();

		d2Shader.unbind();
	}

	public void cleanup() {
		d3Shader.cleanup();
	}


}
