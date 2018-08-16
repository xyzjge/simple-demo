package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.LightsDemoGameState;

public class LightsDemoMenuItem extends MenuItem {

	public LightsDemoMenuItem() {
		setText("LIGHTS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new LightsDemoGameState();
	}

}
