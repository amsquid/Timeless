package me.joepeterson.Timeless.engine.scene;

import me.joepeterson.Timeless.engine.Renderer;
import me.joepeterson.Timeless.engine.block.Block;
import me.joepeterson.Timeless.engine.entity.Camera;
import me.joepeterson.Timeless.engine.entity.Entity;
import me.joepeterson.Timeless.engine.entity.MeshEntity;
import me.joepeterson.Timeless.engine.hud.HUD;
import me.joepeterson.Timeless.engine.world.World;
import org.joml.Vector3i;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldScene extends Scene {

	protected World world;

	protected Map<Vector3i, Block> blocksToRender = new HashMap<>();
	protected List<MeshEntity> entitiesToRender = new ArrayList<>();

	public WorldScene(Renderer renderer) {
		super(renderer);

		this.world = new World();
	}

	public WorldScene(Renderer renderer, HUD hud) {
		super(renderer, hud);

		this.world = new World();
	}

	public WorldScene(Renderer renderer, World world) {
		super(renderer);

		this.world = world;
	}

	public WorldScene(Renderer renderer, HUD hud, World world) {
		super(renderer, hud);

		this.world = world;
	}

	public World getWorld() {
		return this.world;
	}

	@Override
	public void prerender() {
		this.blocksToRender = world.getBlocks();
		this.entitiesToRender = world.getEntities();
	}

	@Override
	public void render() {
		for(Block block : blocksToRender.values()) {
			renderer.render(block, camera);
		}

		for(MeshEntity entity : entitiesToRender) {
			renderer.render(entity, camera);
		}

		super.render();
	}
}
