package me.joepeterson.Timeless;

import me.joepeterson.Timeless.engine.*;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.hud.HUD;
import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.engine.mesh.ModelMesh;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.util.Vector;
import me.joepeterson.Timeless.engine.world.World;
import me.joepeterson.Timeless.entity.Player;
import me.joepeterson.Timeless.hud.Crosshair;
import me.joepeterson.Timeless.world.SpaceWorld;
import org.joml.Vector2d;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.IOException;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game implements IGameLogic {

	private final Renderer renderer;

	Camera camera;
	Player player;
	HUD hud;

	Window window;

	World world;

	MeshEntity debugEntity;

	private Vector2d lastMousePosition = new Vector2d();
	public float mouseSensitivity = 5.0f;

	public boolean brokenBlock = false;

	float x = 0.0f;

	public Game() {
		renderer = new Renderer();
		player = new Player(.1f);
		hud = new HUD();
	}

	@Override
	public void init(Window window) throws Exception {
		// Cursor
		glfwSetInputMode(window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

		// Culling
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		// Alpha Blending
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		// Setup
		this.window = window;

		window.update();

		System.out.println("Generating world");
		this.world = new SpaceWorld();
		this.world.generateWorld(0L);
		System.out.println("Generated world");

		camera = new Camera(window);

		ModelMesh modelMesh = new ModelMesh("models/testing_entity.dae", new Texture("textures/model/blue_rock.png"));
		debugEntity = new MeshEntity(modelMesh);

		world.addEntity(debugEntity);

		// HUD
		try {
			hud.addHUDItem(new Crosshair());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		renderer.init();
	}

	@Override
	public void input(Window window) {
		keyInput();
		mouseInput();
	}

	@Override
	public void update(float dt) {
		// Player movement
		Vector3f playerPos = player.getPosition();

		camera.setPosition(playerPos.x, playerPos.y + 1.0f, playerPos.z);
		player.setRotation(0.0f, camera.getRotation().y, camera.getRotation().z);

		player.move();

		// Camera rotation
		Vector2d mousePosNormalized = new Vector2d(window.getMousePos().x / window.width - .5d, window.getMousePos().y / window.height - .5d);
		Vector2d mouseDelta = new Vector2d(mousePosNormalized.x - lastMousePosition.x, mousePosNormalized.y - lastMousePosition.y);
		lastMousePosition = mousePosNormalized;

		camera.rotate((float) mouseDelta.y * dt * mouseSensitivity, (float) mouseDelta.x * dt * mouseSensitivity, 0.0f);

		float xRotClamp = Math.clamp(camera.getRotation().x, -90.0f, 90.0f);

		camera.setRotation(xRotClamp, camera.getRotation().y, camera.getRotation().z);

		x += 0.05f;
	}

	@Override
	public void render() {
		window.setClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		renderer.clear();

		if (window.resized) {
			glViewport(0, 0, window.width, window.height);
			window.resized = false;
		}

		//renderer.render(entity, camera);

		try {
			for(Vector3i position : world.getBlocks().keySet()) {
				Block block = world.getBlocks().get(position);

				if(block.mesh.visible) renderer.render(block, camera);
			}

			for(MeshEntity entity : world.getEntities()) {
				renderer.render(entity, camera);
			}

			for(HUDItem item : hud.getHUDItems()) {
				renderer.render(item, camera);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void cleanup() {
		renderer.cleanup();

		for(Block block : world.getBlocks().values()) {
			block.cleanup();
		}
	}

	@Override
	public void quit() {
		glfwSetWindowShouldClose(window.windowHandle, true);
	}

	// Methods for each of the input methods
	void keyInput() {
		// Quit
		if(window.isKeyPressed(GLFW_KEY_ESCAPE)) {
			quit();
		}

		// Movement
		float horizontal = 0.0f;
		float up = 0.0f;
		float vertical = 0.0f;

		float speedMultiplier = 0.05f;
		float fov = (float) Math.toDegrees(camera.startFOV);

		if(window.isKeyPressed(GLFW_KEY_S)) {
			horizontal -= 1f;
		}

		if(window.isKeyPressed(GLFW_KEY_W)) {
			horizontal += 1f;
		}

		if(window.isKeyPressed(GLFW_KEY_A)) {
			vertical -= 1f;
		}

		if(window.isKeyPressed(GLFW_KEY_D)) {
			vertical += 1f;
		}

		if(window.isKeyPressed(GLFW_KEY_SPACE)) {
			up += 1f;
		}

		if(window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
			up -= 1f;
		}

		player.sprinting = window.isKeyPressed(GLFW_KEY_LEFT_CONTROL);

		if(player.sprinting) {
			speedMultiplier *= 2;
			fov += 15.0f;
		}

		camera.updateMatrices(fov);

		Vector3f velocity = player.getVelocityForward(horizontal, up, vertical);
		velocity = Vector.multiplyVector(velocity, speedMultiplier);

		player.moveAndCollide(velocity, world);
	}

	void mouseInput() {
		// Mouse Related inputs
		if(window.isKeyPressed(GLFW_KEY_GRAVE_ACCENT)) { // Unlocking cursor
			glfwSetInputMode(window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		}

		Vector3f lookingPosition = camera.getLookingPosition(world, 5, .1f);
		debugEntity.setPosition(lookingPosition);

		if(window.isMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT) && !brokenBlock) {
			Vector3i lookingBlockPosition = Vector.toVector3iFloor(lookingPosition);
			Block lookingBlock = world.getBlocks().get(lookingBlockPosition);

			if(lookingBlock != null) {
				if (!world.deleteBlock(lookingBlock.position)) System.out.println("Couldn't delete block");

				world.fixFaces();
			}

			brokenBlock = true;
		}

		if(window.isMouseButtonReleased(GLFW_MOUSE_BUTTON_LEFT)) {
			brokenBlock = false;
		}
	}
}
