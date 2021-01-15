package ar.com.xyz.simpledemo.control2d.menu;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.control2d.Control2DTresGameState;

public class Control2DTresMenuItem extends MenuItem {

	public Control2DTresMenuItem() {
		setText("TRES");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Control2DTresGameState();
	}

}
