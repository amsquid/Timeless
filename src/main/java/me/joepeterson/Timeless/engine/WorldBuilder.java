package me.joepeterson.Timeless.engine;

import me.joepeterson.Timeless.engine.world.World;
import org.joml.Vector3i;

import java.util.Map;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;

/*
	The point of this class is to allow for asynchronous world generation to allow for constant event polling during the world creation.
 */
public class WorldBuilder implements Runnable {

	private World world;
	private long seed;

	public boolean generatedWorld = false;
	public Map<Vector3i, Integer> blocksGenerated;

	private Thread generationThread;

	private final Window window;

	public WorldBuilder(World world, Window window) {
		this.world = world;
		seed = (long) new Random().nextInt() * 10000;

		this.window = window;

	}

	public WorldBuilder(World world, Window window, long seed) {
		this.world = world;
		this.seed = seed;

		this.window = window;
	}

	public void generateWorld() {
		generationThread = new Thread(this, "WORLD_GENERATION_THREAD");

		String osName = System.getProperty("os.name");

		if(osName.contains("Mac")) {
			generationThread.run();
		} else {
			generationThread.start();
		}
	}

	@Override
	public void run() {
		blocksGenerated = world.generateWorld(seed);
		this.generatedWorld = true;
	}
}
