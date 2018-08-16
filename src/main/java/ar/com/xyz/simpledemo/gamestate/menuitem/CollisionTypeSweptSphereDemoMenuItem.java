package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.CollisionTypeSweptSphereDemoGameState;

public class CollisionTypeSweptSphereDemoMenuItem extends MenuItem {

	public CollisionTypeSweptSphereDemoMenuItem() {
		setText("SWEPT SPHERE COLLISION TYPE ENTITIES DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CollisionTypeSweptSphereDemoGameState();
	}

}
