package me.joepeterson.Timeless;

import me.joepeterson.Timeless.engine.GameEngine;
import me.joepeterson.Timeless.engine.IGameLogic;
import me.joepeterson.Timeless.engine.Window;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Main {
	public void run() {
		System.out.println("Working Directory = " + System.getProperty("user.dir"));
		System.out.println("LWJGL " + Version.getVersion());

		try {
			IGameLogic iGameLogic = new Game();
			GameEngine gameEngine = new GameEngine("Timeless", 1280, 720, 1, iGameLogic);

			gameEngine.start();
		} catch (Exception e) {
			e.printStackTrace();

			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		new Main().run();
	}
}
