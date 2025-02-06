package me.joepeterson.Timeless.scene;

import me.joepeterson.Timeless.engine.Renderer;
import me.joepeterson.Timeless.engine.Window;
import me.joepeterson.Timeless.engine.world.WorldBuilder;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.block.BreakableBlock;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.hud.FullscreenHUDItem;
import me.joepeterson.Timeless.engine.hud.HUD;
import me.joepeterson.Timeless.engine.hud.HUDItem;
import me.joepeterson.Timeless.inventory.Item;
import me.joepeterson.Timeless.engine.scene.WorldScene;
import me.joepeterson.Timeless.engine.texture.Texture;
import me.joepeterson.Timeless.engine.util.Vector;
import me.joepeterson.Timeless.engine.world.World;
import me.joepeterson.Timeless.entity.Player;
import me.joepeterson.Timeless.hud.Crosshair;
import me.joepeterson.Timeless.inventory.Material;
import me.joepeterson.Timeless.world.SpaceWorld;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3i;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import static org.lwjgl.glfw.GLFW.*;

public class GameScene extends WorldScene {
	WorldBuilder worldBuilder;

	Player player;

	HUD gameHUD;
	HUD loadingHUD;
	HUD inventoryHUD;

	boolean loadingWorld = true;

	MeshEntity debugEntity;

	int itemStart = 0; // Item position in the HUD array
	int slotStart = 0; // Slot position in the HUD array

	int selectedSlot = 0;

	private Vector2d lastMousePosition = new Vector2d();
	public float mouseSensitivity = 5.0f;

	boolean brokenBlock = false;
	boolean placedBlock = false;
	boolean mouseUnlocked = false;

	Window window;

	float dt = 0.0f;

	Texture slotTexture;
	Texture selectedSlotTexture;

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

		// Loading HUD
		loadingHUD = new HUD();

		loadingHUD.addHUDItem(new FullscreenHUDItem(new Texture("assets/textures/ui/loading_screen.png")));

		// Game HUD
		gameHUD = new HUD();

		try {
			// Crosshair
			gameHUD.addHUDItem(new Crosshair());

			// Slot and Item HUDs
			float offset = 0.0f;

			Vector2f scale = new Vector2f(1/24.f, 1/13.5f);
			Vector2f slotScale = new Vector2f(1/24.f, 1/6.75f);
			Vector2f oneOver = new Vector2f(scale.x + offset, scale.y + offset); // idk what else to name this :I
			Vector2f slotOneOver = new Vector2f(slotScale.x + offset, slotScale.y + offset);

			slotTexture = new Texture("assets/textures/ui/slot.png");
			selectedSlotTexture = new Texture("assets/textures/ui/slot_selected.png");

			Texture emptyTexture = new Texture("assets/textures/item/empty.png");

			// Slots
			slotStart = gameHUD.getHUDItems().size();

			for(int i = 0; i < 6; i++) {
				float x = (.5f - (slotOneOver.x * 3)) + (slotOneOver.x * (i)) + offset;
				float y = 1.0f - slotOneOver.y;

				HUDItem slot = new HUDItem(slotTexture, new Vector2f(x, y), slotScale);

				gameHUD.addHUDItem(slot);
			}

			// Items
			itemStart = gameHUD.getHUDItems().size();

			for(int i = 0; i < 6; i++) {
				float x = (.5f - (oneOver.x * 3)) + (oneOver.x * (i)) + offset;
				float y = 1.0f - oneOver.y;

				HUDItem slot = new HUDItem(emptyTexture, new Vector2f(x, y), scale);

				gameHUD.addHUDItem(slot);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Generating World
		this.hud = loadingHUD;

		System.out.println("Generating world");

		this.world = new SpaceWorld();

		this.worldBuilder = new WorldBuilder(this.world, window);
		this.worldBuilder.generateWorld();

        updateSlots();
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
				blocksToRender.clear();
				Vector3f playerPos = player.getPosition();
				
				int viewDistance = 30;
				
				for(int x = (int) playerPos.x - viewDistance; x < (int) playerPos.x + viewDistance; x++) {
					for(int y = (int) playerPos.y - viewDistance; y < (int) playerPos.y + viewDistance; y++) {
						for(int z = (int) playerPos.z - viewDistance; z < (int) playerPos.z + viewDistance; z++) {
							Vector3i blockPosition = new Vector3i(x, y, z);

							if(world.getBlocks().containsKey(blockPosition)) { // Block exists
								Block block = world.getBlocks().get(blockPosition);

								if(block.mesh.visible) {
									blocksToRender.put(blockPosition, block);
								}
							}
						}
					}	
				}

//				Less efficient way of checking for blocks to render
//
//				for(Vector3i position : world.getBlocks().keySet()) {
//					float distance = Vector.distance(Vector.toVector3f(position), player.getPosition());
//					if(distance <= 30.f) {
//						Block block = world.getBlocks().get(position);
//
//						if(block.mesh.visible) {
//							blocksToRender.put(position, block);
//						}
//					}
//				}

				entitiesToRender.clear();
				entitiesToRender.addAll(world.getEntities());

				// Item setup in HUD
				for(int i = 0; i < 6; i++) {
					if(i > player.getInventory().size() - 1 || player.getInventory().get(i).getMaterial() == Material.AIR) {
						gameHUD.getHUDItems().get(itemStart + i).shouldRender = false;

						continue;
					}

					Item itemStack = player.getInventory().get(i);
					Material item = itemStack.getMaterial();
					Texture itemTexture = item.texture;

					HUDItem hudItem = gameHUD.getHUDItems().get(itemStart + i);
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
				world.loadWorld(worldBuilder.blocksGenerated);

				glfwSetInputMode(window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

				System.out.println("Generated World");

//				ModelMesh modelMesh = new ModelMesh("assets/models/testing_entity.dae", new Texture("assets/textures/model/blue_rock.png"));
//				debugEntity = new MeshEntity(modelMesh);
//				world.addEntity(debugEntity);

				this.hud = gameHUD;

				world.fixFaces();

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

		// Slot selection
		boolean slotChanged =
				window.isKeyPressed(GLFW_KEY_1) ||
				window.isKeyPressed(GLFW_KEY_2) ||
				window.isKeyPressed(GLFW_KEY_3) ||
				window.isKeyPressed(GLFW_KEY_4) ||
				window.isKeyPressed(GLFW_KEY_5) ||
				window.isKeyPressed(GLFW_KEY_6);

		if(window.isKeyPressed(GLFW_KEY_1)) selectedSlot = 0;
		if(window.isKeyPressed(GLFW_KEY_2)) selectedSlot = 1;
		if(window.isKeyPressed(GLFW_KEY_3)) selectedSlot = 2;
		if(window.isKeyPressed(GLFW_KEY_4)) selectedSlot = 3;
		if(window.isKeyPressed(GLFW_KEY_5)) selectedSlot = 4;
		if(window.isKeyPressed(GLFW_KEY_6)) selectedSlot = 5;

		if(slotChanged) {
			updateSlots();
		}

		// Opening Inventory
		if(window.isKeyPressed(GLFW_KEY_TAB) && !mouseUnlocked) {
			player.inventoryOpen = !player.inventoryOpen;

			if(player.inventoryOpen) {
				glfwSetInputMode(window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
			} else {
				glfwSetInputMode(window.windowHandle, GLFW_CURSOR, GLFW_CURSOR_DISABLED);
			}

			mouseUnlocked = true;
		}

		if(window.isKeyReleased(GLFW_KEY_TAB)) {
			mouseUnlocked = false;
		}
	}

	public void mouseInput() {
		if(loadingWorld) return;

		// Camera rotation
		Vector2d mousePosNormalized = new Vector2d(window.getMousePos().x / window.width - .5d, window.getMousePos().y / window.height - .5d);
		Vector2d mouseDelta = new Vector2d(mousePosNormalized.x - lastMousePosition.x, mousePosNormalized.y - lastMousePosition.y);

		if(!player.inventoryOpen) {
			lastMousePosition = mousePosNormalized;

			camera.rotate((float) mouseDelta.y * dt * mouseSensitivity, (float) mouseDelta.x * dt * mouseSensitivity, 0.0f);

			float xRotClamp = Math.clamp(camera.getRotation().x, -90.0f, 90.0f);

			camera.setRotation(xRotClamp, camera.getRotation().y, camera.getRotation().z);
		} else {
			lastMousePosition = mousePosNormalized;
		}

		// Mouse Down
		Vector3f lookingPosition = camera.getLookingPosition(world, 5, .01f);
		if(this.debugEntity != null) debugEntity.setPosition(lookingPosition);

		if(window.isMouseButtonPressed(GLFW_MOUSE_BUTTON_LEFT) && !brokenBlock) {
			Vector3i lookingBlockPosition = Vector.toVector3iFloor(lookingPosition);

			if(world.getBlocks().get(lookingBlockPosition) instanceof BreakableBlock) {
				BreakableBlock block = (BreakableBlock) world.getBlocks().get(lookingBlockPosition);

				if (!world.deleteBlock(block.position)) {
					System.out.println("Couldn't delete block");
				} else {
					world.fixFacesAround(block.position, 1);

					Material material = block.getGiveMaterial();

					if(material != null)
						player.giveItem(material, 1);
				}
			}

			brokenBlock = true;
		}

		if(window.isMouseButtonPressed(GLFW_MOUSE_BUTTON_RIGHT) && !placedBlock) {
			if(selectedSlot < player.getInventory().size() && player.getInventory().get(selectedSlot).getMaterial().blockToPlace != null) {
				Item item = player.getInventory().get(selectedSlot);

				Vector3i lookingBlockPosition = Vector.toVector3iFloor(lookingPosition);
				Block lookingBlock = world.getBlocks().get(lookingBlockPosition);
				Vector3f face = Vector.getNormalBlockFace(lookingPosition, lookingBlock);

				if(face == null) {
					System.err.println("Invalid face at looking position");
				} else {
					Vector3i newBlockPosition = Vector.toVector3iRound(
							Vector.addVectors(face.mul(.5f), lookingBlockPosition)
					);

					Material material = item.getMaterial();
					Block block = null;

					try {
						assert material.blockToPlace != null;
						block = material.blockToPlace.getClass().getDeclaredConstructor(Vector3i.class).newInstance(newBlockPosition);
					} catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
						throw new RuntimeException(e);
					}

					if(!block.boundingBox.collidesWith(new Vector3f(), player.getBoundingBox())) {
						world.addBlock(block);
						world.fixFacesAround(newBlockPosition, 1);

						player.getInventory().get(selectedSlot).addAmount(-1);

						if(player.getInventory().get(selectedSlot).getAmount() <= 0) {
							Item air = new Item(Material.AIR, 1);
							player.getInventory().set(selectedSlot, air);
						}
					}
				}

				placedBlock = true;
			}
		}

		// Mouse Up
		if(window.isMouseButtonReleased(GLFW_MOUSE_BUTTON_LEFT)) {
				brokenBlock = false;
		}

		if(window.isMouseButtonReleased(GLFW_MOUSE_BUTTON_RIGHT)) {
			placedBlock = false;
		}
	}

	public void updateSlots() {
		for(int i = 0; i < 6; i++) {
			if(i == selectedSlot) {
				gameHUD.getHUDItems().get(i + slotStart).refreshTexture(selectedSlotTexture);
			} else {
				gameHUD.getHUDItems().get(i + slotStart).refreshTexture(slotTexture);
			}
		}
	}
}
