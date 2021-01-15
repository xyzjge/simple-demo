package ar.com.xyz.simpledemo.control2d.menu;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.control2d.Control2DUnoGameState;

public class Control2DUnoMenuItem extends MenuItem {

	public Control2DUnoMenuItem() {
		setText("UNO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Control2DUnoGameState();
	}

}
