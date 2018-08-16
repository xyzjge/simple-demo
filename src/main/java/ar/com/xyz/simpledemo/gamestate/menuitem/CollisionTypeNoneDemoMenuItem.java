package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.CollisionTypeNoneDemoGameState;

public class CollisionTypeNoneDemoMenuItem extends MenuItem {

	public CollisionTypeNoneDemoMenuItem() {
		setText("NONE COLLISION TYPE ENTITIES DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CollisionTypeNoneDemoGameState();
	}

}
