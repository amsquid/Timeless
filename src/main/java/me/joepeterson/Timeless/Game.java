package me.joepeterson.Timeless;

import me.joepeterson.Timeless.blocks.RockBlock;
import me.joepeterson.Timeless.engine.*;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.entity.Entity;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.hud.FullscreenHUDItem;
import me.joepeterson.Timeless.engine.hud.HUD;
import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.engine.mesh.ModelMesh;
import me.joepeterson.Timeless.engine.scene.Scene;
import me.joepeterson.Timeless.engine.scene.WorldScene;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.util.Vector;
import me.joepeterson.Timeless.engine.world.World;
import me.joepeterson.Timeless.entity.Player;
import me.joepeterson.Timeless.hud.Crosshair;
import me.joepeterson.Timeless.scene.GameScene;
import me.joepeterson.Timeless.world.SpaceWorld;
import org.joml.Vector2d;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;
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
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		// Alpha Blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Setup
		this.window = window;

		window.update();

		this.activeScene = new GameScene(renderer, new SpaceWorld());
		activeScene.init(window);

		renderer.init();
	}

	@Override
	public void input(Window window) {
		activeScene.input();
	}

	@Override
	public void update(float dt) {
		this.dt = dt;

		// Debug
		System.out.println(dt);

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
