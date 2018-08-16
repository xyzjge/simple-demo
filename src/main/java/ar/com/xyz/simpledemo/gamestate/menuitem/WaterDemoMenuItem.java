package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.WaterDemoGameState;

public class WaterDemoMenuItem extends MenuItem {

	public WaterDemoMenuItem() {
		setText("WATER");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new WaterDemoGameState();
	}

}
