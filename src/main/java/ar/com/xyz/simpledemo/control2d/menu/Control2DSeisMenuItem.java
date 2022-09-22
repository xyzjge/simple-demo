package ar.com.xyz.simpledemo.control2d.menu;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.control2d.Control2DSeisGameState;

public class Control2DSeisMenuItem extends MenuItem {

	public Control2DSeisMenuItem() {
		setText("SEIS (checks)");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Control2DSeisGameState();
	}

}
