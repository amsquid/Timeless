package me.joepeterson.Timeless.engine;

import org.lwjgl.opengl.GL;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class GameEngine implements Runnable {

	public float targetFPS = 60.0f;
	public float targetUPS = 60.0f;

	private final Thread loopThread;

	public Window window;

	String title;
	int width, height, vSync;

	IGameLogic gameLogic;

	public GameEngine(String title, int width, int height, int vSync, IGameLogic gameLogic) {
		this.title = title;
		this.width = width;
		this.height = height;
		this.vSync = vSync;

		loopThread = new Thread(this, "GAME_LOOP_THREAD");
		this.gameLogic = gameLogic;
	}

	public void start() {
		String osName = System.getProperty("os.name");

		if(osName.contains("Mac")) {
			loopThread.run();
		} else {
			loopThread.start();
		}
	}

	protected void input() {
		gameLogic.input(window);
	}

	protected void update(float dt) {
		gameLogic.update(dt);
	}

	protected void render() {
		gameLogic.render();
		window.update();
	}

	@Override
	public void run() {
		try {
			// Making window
			window = new Window(title, width, height);

			// Make game context current
			glfwMakeContextCurrent(window.windowHandle);
			GL.createCapabilities();

			// V-Sync
			glfwSwapInterval(vSync);

			// Enabling stuff
			glEnable(GL_DEPTH_TEST);
			glEnable(GL_TEXTURE_2D);



			// Initializing game logic
			gameLogic.init(window);

			long initialTime = System.currentTimeMillis();
			float timeU = 1000.0f / targetUPS;
			float timeR = targetFPS > 0 ? 1000.0f / targetFPS : 0;
			float deltaUpdate = 0;
			float deltaFps = 0;

			long updateTime = initialTime;

			// Game loop
			while(!glfwWindowShouldClose(window.windowHandle)) {
				glfwPollEvents();

				long now = System.currentTimeMillis();
				deltaUpdate += (now - initialTime) / timeU;
				deltaFps += (now - initialTime) / timeR;

				if (targetFPS <= 0 || deltaFps >= 1) {
					input();
				}

				if (deltaUpdate >= 1) {
					long diffTimeMillis = now - updateTime;
					update(diffTimeMillis);
					updateTime = now;
					deltaUpdate--;
				}

				if (targetFPS <= 0 || deltaFps >= 1) {
					render();
					deltaFps--;
				}

				initialTime = now;
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			quit();
		}
	}

	public void quit() {
		gameLogic.cleanup();

		glfwFreeCallbacks(window.windowHandle);
		glfwDestroyWindow(window.windowHandle);

		glfwTerminate();
		Objects.requireNonNull(glfwSetErrorCallback(null)).free();
	}

}
