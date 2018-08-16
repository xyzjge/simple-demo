package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.CollisionTypeSweptSphereSimpleDemoGameState;

public class CollisionTypeSweptSphereAnimationDemoMenuItem extends MenuItem {

	public CollisionTypeSweptSphereAnimationDemoMenuItem() {
		setText("SWEPT SPHERE COLLISION TYPE ENTITIES SIMPLE DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CollisionTypeSweptSphereSimpleDemoGameState();
	}

}
