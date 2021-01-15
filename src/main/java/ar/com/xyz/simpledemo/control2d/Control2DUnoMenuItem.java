package ar.com.xyz.simpledemo.control2d;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;

public class Control2DUnoMenuItem extends MenuItem {

	public Control2DUnoMenuItem() {
		setText("UNO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Control2DUnoGameState();
	}

}
