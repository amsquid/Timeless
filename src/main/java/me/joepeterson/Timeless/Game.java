package me.joepeterson.Timeless;

import me.joepeterson.Timeless.engine.*;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.scene.Scene;
import me.joepeterson.Timeless.engine.scene.WorldScene;
import me.joepeterson.Timeless.scene.GameScene;
import me.joepeterson.Timeless.world.SpaceWorld;

import static org.lwjgl.opengl.GL11.*;

public class Game implements IGameLogic {

	private final Renderer renderer;

	Window window;

	public Scene activeScene;

	float dt = 0.0f;

	public Game() {
		renderer = new Renderer();
	}

	@Override
	public void init(Window window) throws Exception {
		// Culling
		System.out.println("Enabling culling");
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		// Alpha Blending
		System.out.println("Enabling alpha blending");
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Stencil Test
		System.out.println("Enabling stencil");
		glEnable(GL_STENCIL_TEST);

		// Setup
		System.out.println("Setting up window");
		this.window = window;
		window.update();

		System.out.println("Setting up renderer");
		renderer.init();

		System.out.println("Setting up active scene");
		this.activeScene = new GameScene(renderer, new SpaceWorld());
		activeScene.init(window);
	}

	@Override
	public void input(Window window) {
		activeScene.input();
	}

	@Override
	public void update(float dt) {
		this.dt = dt;

		// Debug
		//System.out.println(dt);

		activeScene.update(dt);
	}

	@Override
	public void render() {
		window.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		renderer.clear();

		if (window.resized) {
			glViewport(0, 0, window.width, window.height);
			window.resized = false;
		}

		activeScene.prerender();
		activeScene.render();
	}

	@Override
	public void cleanup() {
		renderer.cleanup();

		if(activeScene instanceof WorldScene) {
			for(Block block : ((WorldScene) activeScene).getWorld().getBlocks().values()) {
				block.cleanup();
			}
		}
	}
}
