package me.joepeterson.Timeless.engine;

public interface IGameLogic {
	void init(Window window) throws Exception;

	void input(Window window);

	void update(float dt);

	void render();

	void cleanup();
}
