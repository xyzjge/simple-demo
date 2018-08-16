package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.LookAtDemoGameState;

public class LookAtDemoMenuItem extends MenuItem {

	public LookAtDemoMenuItem() {
		setText("LOOK AT 3D DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new LookAtDemoGameState();
	}

}
