package me.joepeterson.Timeless.engine;

import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.engine.util.Files;

import java.util.Objects;

import static org.lwjgl.opengl.GL11.*;

public class Renderer {

	Shader d3Shader;
	Shader d2Shader;
	Shader activeShader;

	public void init() throws Exception {
		// 3D Shader
		d3Shader = new Shader();

		d3Shader.renderingType = RenderingType.D3;

		d3Shader.createVertexShader(Files.loadResource("shaders/shader.vert"));
		d3Shader.createFragmentShader(Files.loadResource("shaders/shader.frag"));
		d3Shader.link();

		d3Shader.createUniform("modelViewMatrix");
		d3Shader.createUniform("projectionMatrix");
		d3Shader.createUniform("tex");

		d3Shader.setUniform("tex", 0);

		// 2D Shader
		d2Shader = new Shader();

		d2Shader.renderingType = RenderingType.D2;

		d2Shader.createVertexShader(Files.loadResource("shaders/gui.vert"));
		d2Shader.createFragmentShader(Files.loadResource("shaders/gui.frag"));
		d2Shader.link();

		d2Shader.createUniform("projectionMatrix");
		d2Shader.createUniform("tex");

		d2Shader.setUniform("tex", 0);

		// Setting active shader
		this.setRenderingType(RenderingType.D3);
	}

	public void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	public void setRenderingType(RenderingType type) {
		if(activeShader != null) activeShader.unbind();

		if (type == RenderingType.D2) {
			activeShader = d2Shader;
		} else {
			activeShader = d3Shader;
		}

		activeShader.bind();
	}

	public RenderingType getRenderingType() {
		return activeShader.renderingType;
	}

	public void render(MeshEntity entity, Camera camera) {
		activeShader.setUniform("modelViewMatrix", entity.getMatrix(camera.getViewMatrix()));
		activeShader.setUniform("projectionMatrix", camera.getProjectionMatrix());

		entity.render();
	}

	public void render(Block block, Camera camera) {
		activeShader.setUniform("modelViewMatrix", block.getMatrix(camera.getViewMatrix()));
		activeShader.setUniform("projectionMatrix", camera.getProjectionMatrix());

		block.render();
	}

	public void render(HUDItem item, Camera camera) {
		activeShader.setUniform("projectionMatrix", camera.getOrthographicMatrix());

		item.render();
	}

	public void cleanup() {
		activeShader.unbind();

		d2Shader.cleanup();
		d3Shader.cleanup();
	}


}
