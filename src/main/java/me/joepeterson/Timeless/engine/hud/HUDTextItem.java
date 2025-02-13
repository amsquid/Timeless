package me.joepeterson.Timeless.engine.hud;

import me.joepeterson.Timeless.engine.texture.Texture;
import org.joml.Vector2f;

import java.util.ArrayList;

public class HUDTextItem {

	ArrayList<HUDItem> letterHudItems = new ArrayList<>();

	private final Vector2f position;
	private String text;

	private final int size = 8;

	public HUDTextItem(Vector2f position, String text) {
		this.position = position;

		setText(text);
	}

	public void setText(String text) {
		this.text = text;

		for(char c : text.toCharArray()) {
			Texture texture = new Texture("assets/textures/ui/text/" + c + ".png");

		}
	}
}
