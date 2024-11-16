package me.joepeterson.Timeless.engine.hud;

import me.joepeterson.Timeless.engine.entity.Camera;

import java.util.ArrayList;

public class HUD {

	private ArrayList<HUDItem> hudItems = new ArrayList<>();

	public HUD() { }

	public void addHUDItem(HUDItem item) {
		hudItems.add(item);
	}

	public void deleteHUDItem(HUDItem item) {
		hudItems.remove(item);
	}

	public ArrayList<HUDItem> getHUDItems() {
		return hudItems;
	}

	public void resetHUD() {
		hudItems = new ArrayList<>();
	}
}
