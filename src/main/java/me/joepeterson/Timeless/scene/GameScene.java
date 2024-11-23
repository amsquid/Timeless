package me.joepeterson.Timeless.scene;

import me.joepeterson.Timeless.blocks.RockBlock;
import me.joepeterson.Timeless.engine.Renderer;
import me.joepeterson.Timeless.engine.Window;
import me.joepeterson.Timeless.engine.WorldBuilder;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.hud.FullscreenHUDItem;
import me.joepeterson.Timeless.engine.hud.HUD;
import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.engine.mesh.ModelMesh;
import me.joepeterson.Timeless.engine.scene.WorldScene;
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

public class GameScene extends WorldScene {
	WorldBuilder worldBuilder;

	Player player;
	HUD gameHUD;
	HUD loadingHUD;

	boolean loadingWorld = true;

	MeshEntity debugEntity;

	Class<?>[] blocksDictionary = new Class<?>[]{
			RockBlock.class
	};

	private Vector2d lastMousePosition = new Vector2d();
	public float mouseSensitivity = 5.0f;

	public boolean brokenBlock = false;

	Window window;

	public GameScene(Renderer renderer) {
		super(renderer);
	}

	public GameScene(Renderer renderer, HUD hud) {
		super(renderer, hud);
	}

	public GameScene(Renderer renderer, World world) {
		super(renderer, world);
	}

	public GameScene(Renderer renderer, HUD hud, World world) {
		super(renderer, hud, world);
	}

	@Override
	public void init(Window window) {
		super.init(window);

		this.window = window;

		camera = new Camera(window);
		player = new Player(0.1f);

		// Loading HUD
		loadingHUD = new HUD();

		try {
			loadingHUD.addHUDItem(new FullscreenHUDItem(new Texture("textures/ui/loading_screen.png")));
		} catch(IOException e) {
			throw new RuntimeException(e);
		}

		// Game HUD
		gameHUD = new HUD();

		try {
			gameHUD.addHUDItem(new Crosshair());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Generating World
		this.hud = loadingHUD;

		System.out.println("Generating world");

		this.world = new SpaceWorld();

		this.worldBuilder = new WorldBuilder(this.world, window);
		this.worldBuilder.generateWorld();
	}

	@Override
	public void prerender() {
		try {
			if(loadingWorld) {
				this.hud = loadingHUD;
			} else {
				this.hud = gameHUD;

				blocksToRender.clear();

				for(Vector3i position : world.getBlocks().keySet()) {
					float distance = Vector.distance(Vector.toVector3f(position), player.getPosition());
					if(distance <= 30.f) {
						Block block = world.getBlocks().get(position);

						if(block.mesh.visible) {
							blocksToRender.put(position, block);
						}
					}
				}

				entitiesToRender.clear();
				entitiesToRender.addAll(world.getEntities());
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void update(float dt) {
		if(worldBuilder.generatedWorld && loadingWorld) {
			try {
				world.loadWorld(worldBuilder.blocksGenerated, blocksDictionary);

				glfwSetInputMode(window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

				System.out.println("Generated World");

				ModelMesh modelMesh = new ModelMesh("models/testing_entity.dae", new Texture("textures/model/blue_rock.png"));
				debugEntity = new MeshEntity(modelMesh);

				world.addEntity(debugEntity);

				this.hud = gameHUD;

				loadingWorld = false;
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		if(loadingWorld) return;

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
	}

	@Override
	public void input() {
		keyInput();
		mouseInput();
	}

	public void keyInput() {
		// Quit
		if(window.isKeyPressed(GLFW_KEY_ESCAPE)) {
			glfwSetWindowShouldClose(window.windowHandle, true);
		}

		if(loadingWorld) return;

		if(window.isKeyPressed(GLFW_KEY_GRAVE_ACCENT)) { // Unlocking cursor
			glfwSetInputMode(window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
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

	public void mouseInput() {
		if(loadingWorld) return;

		// Mouse Related inputs
		Vector3f lookingPosition = camera.getLookingPosition(world, 5, .1f);
		if(this.debugEntity != null) debugEntity.setPosition(lookingPosition);

		if(window.isMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT) && !brokenBlock) {
			Vector3i lookingBlockPosition = Vector.toVector3iFloor(lookingPosition);
			Block lookingBlock = world.getBlocks().get(lookingBlockPosition);

			if(lookingBlock != null) {
				if (!world.deleteBlock(lookingBlock.position)) System.out.println("Couldn't delete block");

				world.fixFaces(blocksToRender.keySet());
			}

			brokenBlock = true;
		}

		if(window.isMouseButtonReleased(GLFW_MOUSE_BUTTON_LEFT)) {
			brokenBlock = false;
		}
	}
}
