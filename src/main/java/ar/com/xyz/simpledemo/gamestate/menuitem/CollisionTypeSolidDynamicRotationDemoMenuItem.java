package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.CollisionTypeSolidDynamicRotationDemoGameState;

public class CollisionTypeSolidDynamicRotationDemoMenuItem extends MenuItem {

	public CollisionTypeSolidDynamicRotationDemoMenuItem() {
		setText("SOLID DYNAMIC COLLISION TYPE ENTITIES DEMO (ROTATION)");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CollisionTypeSolidDynamicRotationDemoGameState();
	}

}
