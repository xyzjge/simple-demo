package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.CollisionTypeSolidDynamicDemoGameState;

public class CollisionTypeSolidDynamicDemoMenuItem extends MenuItem {

	public CollisionTypeSolidDynamicDemoMenuItem() {
		setText("SOLID DYNAMIC COLLISION TYPE ENTITIES DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CollisionTypeSolidDynamicDemoGameState();
	}

}
