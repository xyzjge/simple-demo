package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.TerrainDemoGameState;

public class TerrainDemoMenuItem extends MenuItem {

	public TerrainDemoMenuItem() {
		setText("TERRAIN");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new TerrainDemoGameState();
	}

}
