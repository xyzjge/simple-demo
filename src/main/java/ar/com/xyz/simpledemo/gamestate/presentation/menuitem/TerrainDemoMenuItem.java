package ar.com.xyz.simpledemo.gamestate.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.lights.TerrainDemoGameState;

public class TerrainDemoMenuItem extends MenuItem {

	public TerrainDemoMenuItem() {
		setText("LIGHTS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new TerrainDemoGameState();
	}

}
