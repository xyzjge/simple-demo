package ar.com.xyz.simpledemo.control2d.menu;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.control2d.Control2D005GameState;

public class Control2DCincoMenuItem extends MenuItem {

	public Control2DCincoMenuItem() {
		setText("CINCO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Control2D005GameState();
	}

}
