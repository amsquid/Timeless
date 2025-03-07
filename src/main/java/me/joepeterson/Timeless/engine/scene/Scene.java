package me.joepeterson.Timeless.engine.scene;

import me.joepeterson.Timeless.engine.Renderer;
import me.joepeterson.Timeless.engine.RenderingType;
import me.joepeterson.Timeless.engine.Window;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.hud.HUD;
import me.joepeterson.Timeless.engine.hud.HUDItem;

public class Scene {

	public HUD hud;
	protected Camera camera;

	protected final Renderer renderer;

	public boolean paused = false;

	public Scene(Renderer renderer) {
		this.renderer = renderer;

		this.hud = new HUD();
	}

	public Scene(Renderer renderer, HUD hud) {
		this.renderer = renderer;

		this.hud = hud;
	}

	public void init(Window window) {
		this.camera = new Camera(window);
	}

	public void update(float dt) { }

	public void pausedUpdate(float dt) { }

	public void input() { }

	public void pausedInput() { }

	public void prerender() { }

	public void render() {
		if(renderer.getRenderingType() != RenderingType.D2) renderer.setRenderingType(RenderingType.D2);

		for(HUDItem hudItem : hud.getHUDItems()) {
			renderer.render(hudItem, camera);
		}
	}
}
