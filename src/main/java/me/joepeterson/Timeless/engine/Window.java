package me.joepeterson.Timeless.engine;

import org.joml.Vector2d;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.DoubleBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
	public long windowHandle;

	public int width, height;
	public boolean resized = false;

	public Window(String title, int width, int height) {
		GLFWErrorCallback.createPrint(System.err).set();

		if(!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

		windowHandle = glfwCreateWindow(width, height, title, NULL, NULL);
		if(windowHandle == NULL)
			throw new RuntimeException("Failed to create the GLFW window");

		glfwSetFramebufferSizeCallback(windowHandle, (window, fbWidth, fbHeight) -> {
			Window.this.width = fbWidth;
			Window.this.height = fbHeight;
			Window.this.resized = true;
		});

		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			glfwGetWindowSize(windowHandle, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			assert vidmode != null;
			/*glfwSetWindowPos(
					windowHandle,
					(vidmode.width() - pWidth.get(0)) / 2,
					(vidmode.height() - pHeight.get(0)) / 2
			);*/
		}


		this.width = width;
		this.height = height;

		glfwShowWindow(windowHandle);
	}

	public boolean isKeyPressed(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_PRESS;
	}

	public boolean isKeyReleased(int keyCode) {
		return glfwGetKey(windowHandle, keyCode) == GLFW_RELEASE;
	}

	public boolean isMouseButtonPressed(int button) {
		return glfwGetMouseButton(windowHandle, button) == GLFW_PRESS;
	}

	public boolean isMouseButtonReleased(int button) {
		return glfwGetMouseButton(windowHandle, button) == GLFW_RELEASE;
	}

	public void update() {
		glfwSwapBuffers(windowHandle);
	}

	public void setClearColor(float r, float g, float b, float a) {
		glClearColor(r, g, b, a);
	}

	public Vector2d getMousePos() {
		DoubleBuffer posX = BufferUtils.createDoubleBuffer(1);
		DoubleBuffer posY = BufferUtils.createDoubleBuffer(1);

		glfwGetCursorPos(windowHandle, posX, posY);

		return new Vector2d(posX.get(0), posY.get(0));
	}

}
