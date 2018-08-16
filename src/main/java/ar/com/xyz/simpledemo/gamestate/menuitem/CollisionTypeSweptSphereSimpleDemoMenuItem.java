package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.CollisionTypeSweptSphereAnimationDemoGameState;

public class CollisionTypeSweptSphereSimpleDemoMenuItem extends MenuItem {

	public CollisionTypeSweptSphereSimpleDemoMenuItem() {
		setText("SWEPT SPHERE COLLISION TYPE ENTITIES DEMO (3D ANIMATIONS)");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CollisionTypeSweptSphereAnimationDemoGameState();
	}

}
