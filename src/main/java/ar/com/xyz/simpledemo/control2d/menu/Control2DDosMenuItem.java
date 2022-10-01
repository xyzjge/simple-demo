package ar.com.xyz.simpledemo.control2d.menu;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.control2d.Control2D002GameState;

public class Control2DDosMenuItem extends MenuItem {

	public Control2DDosMenuItem() {
		setText("DOS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Control2D002GameState();
	}

}
