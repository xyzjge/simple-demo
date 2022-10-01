package ar.com.xyz.simpledemo.control2d.menu;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.control2d.Control2D007GameState;

public class Control2DSieteMenuItem extends MenuItem {

	public Control2DSieteMenuItem() {
		setText("SIETE (paneles internos)");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Control2D007GameState();
	}

}
