package ar.com.xyz.simpledemo.control2d.menu;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.control2d.Control2DCuatroGameState;

public class Control2DCuatroMenuItem extends MenuItem {

	public Control2DCuatroMenuItem() {
		setText("CUATRO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Control2DCuatroGameState();
	}

}
