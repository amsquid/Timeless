package me.joepeterson.Timeless.scene;

import me.joepeterson.Timeless.block.RockBlock;
import me.joepeterson.Timeless.engine.Renderer;
import me.joepeterson.Timeless.engine.Window;
import me.joepeterson.Timeless.engine.WorldBuilder;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.hud.FullscreenHUDItem;
import me.joepeterson.Timeless.engine.hud.HUD;
import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.engine.inventory.Item;
import me.joepeterson.Timeless.engine.inventory.ItemStack;
import me.joepeterson.Timeless.engine.mesh.ModelMesh;
import me.joepeterson.Timeless.engine.scene.WorldScene;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.util.Vector;
import me.joepeterson.Timeless.engine.world.World;
import me.joepeterson.Timeless.entity.Player;
import me.joepeterson.Timeless.hud.Crosshair;
import me.joepeterson.Timeless.item.Rock;
import me.joepeterson.Timeless.world.SpaceWorld;
import org.joml.Vector2d;
import org.joml.Vector2f;
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

	int slotStart = 0; // Item position in the HUD array

	private Vector2d lastMousePosition = new Vector2d();
	public float mouseSensitivity = 5.0f;

	public boolean brokenBlock = false;

	Window window;

	float dt = 0.0f;

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
		player = new Player(0.005f);

		try {
			player.getInventory().addItem(new Rock(), 1);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

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
			// Crosshair
			gameHUD.addHUDItem(new Crosshair());

			// Slot and Item HUDs
			float offset = 0.0f;

			Vector2f scale = new Vector2f(1/24.f, 1/13.5f);
			Vector2f oneOver = new Vector2f(scale.x + offset, scale.y + offset); // idk what else to name this :I

			Texture slotTexture = new Texture("textures/ui/slot.png");
			Texture emptyTexture = new Texture("textures/item/empty.png");

			// Item HUD
			slotStart = gameHUD.getHUDItems().size();

			HUDItem lItem1 = new HUDItem(emptyTexture, new Vector2f(.5f - (oneOver.x * 1.5f) + offset, 1.0f - oneOver.y), scale);
			HUDItem lItem2 = new HUDItem(emptyTexture, new Vector2f(.5f - (oneOver.x * 2.5f) + offset, 1.0f - oneOver.y), scale);
			HUDItem lItem3 = new HUDItem(emptyTexture, new Vector2f(.5f - (oneOver.x * 3.5f) + offset, 1.0f - oneOver.y), scale);

			HUDItem rItem1 = new HUDItem(emptyTexture, new Vector2f(.5f + (oneOver.x * 0.5f) + offset, 1.0f - oneOver.y), scale);
			HUDItem rItem2 = new HUDItem(emptyTexture, new Vector2f(.5f + (oneOver.x * 1.5f) + offset, 1.0f - oneOver.y), scale);
			HUDItem rItem3 = new HUDItem(emptyTexture, new Vector2f(.5f + (oneOver.x * 2.5f) + offset, 1.0f - oneOver.y), scale);

			gameHUD.addHUDItem(lItem1);
			gameHUD.addHUDItem(lItem2);
			gameHUD.addHUDItem(lItem3);

			gameHUD.addHUDItem(rItem1);
			gameHUD.addHUDItem(rItem2);
			gameHUD.addHUDItem(rItem3);

			// Slot HUD
			HUDItem lSlot1 = new HUDItem(slotTexture, new Vector2f(.5f - (oneOver.x * 1.5f) + offset, 1.0f - oneOver.y), scale);
			HUDItem lSlot2 = new HUDItem(slotTexture, new Vector2f(.5f - (oneOver.x * 2.5f) + offset, 1.0f - oneOver.y), scale);
			HUDItem lSlot3 = new HUDItem(slotTexture, new Vector2f(.5f - (oneOver.x * 3.5f) + offset, 1.0f - oneOver.y), scale);

			HUDItem rSlot1 = new HUDItem(slotTexture, new Vector2f(.5f + (oneOver.x * 0.5f) + offset, 1.0f - oneOver.y), scale);
			HUDItem rSlot2 = new HUDItem(slotTexture, new Vector2f(.5f + (oneOver.x * 1.5f) + offset, 1.0f - oneOver.y), scale);
			HUDItem rSlot3 = new HUDItem(slotTexture, new Vector2f(.5f + (oneOver.x * 2.5f) + offset, 1.0f - oneOver.y), scale);

			gameHUD.addHUDItem(lSlot1);
			gameHUD.addHUDItem(lSlot2);
			gameHUD.addHUDItem(lSlot3);

			gameHUD.addHUDItem(rSlot1);
			gameHUD.addHUDItem(rSlot2);
			gameHUD.addHUDItem(rSlot3);
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
				// HUD Setup
				this.hud = gameHUD;

				// Seeing which blocks to render
				// TODO: Make more efficient
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

				// Item setup in HUD
				for(int i = 0; i < 6; i++) {
					if(i > player.getInventory().getItems().size() - 1) {
						gameHUD.getHUDItems().get(slotStart + i).shouldRender = false;

						continue;
					}
					ItemStack itemStack = player.getInventory().getItems().get(i);
					Item item = itemStack.getItem();
					Texture itemTexture = item.getTexture();

					HUDItem hudItem = gameHUD.getHUDItems().get(slotStart + i);
					hudItem.refreshTexture(itemTexture);
					hudItem.shouldRender = true;
				}
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void update(float dt) {
		this.dt = dt;

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

			if(loadingWorld) worldBuilder.stopThread();
		}

		if(loadingWorld) return;

		if(window.isKeyPressed(GLFW_KEY_GRAVE_ACCENT)) { // Unlocking cursor
			glfwSetInputMode(window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
		}

		// Movement
		float horizontal = 0.0f;
		float up = 0.0f;
		float vertical = 0.0f;

		float speedMultiplier = 1.f;
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

		velocity = Vector.multiplyVector(velocity, player.getSpeed());
		velocity = Vector.multiplyVector(velocity, speedMultiplier);
		velocity = Vector.multiplyVector(velocity, dt);

		player.moveAndCollide(velocity, world);
	}

	public void mouseInput() {
		if(loadingWorld) return;

		// Camera rotation
		Vector2d mousePosNormalized = new Vector2d(window.getMousePos().x / window.width - .5d, window.getMousePos().y / window.height - .5d);
		Vector2d mouseDelta = new Vector2d(mousePosNormalized.x - lastMousePosition.x, mousePosNormalized.y - lastMousePosition.y);
		lastMousePosition = mousePosNormalized;

		camera.rotate((float) mouseDelta.y * dt * mouseSensitivity, (float) mouseDelta.x * dt * mouseSensitivity, 0.0f);

		float xRotClamp = Math.clamp(camera.getRotation().x, -90.0f, 90.0f);

		camera.setRotation(xRotClamp, camera.getRotation().y, camera.getRotation().z);

		// Clicking
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
