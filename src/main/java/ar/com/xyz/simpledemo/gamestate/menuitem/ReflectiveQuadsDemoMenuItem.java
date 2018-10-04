package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.ReflectiveQuadsGameState;

public class ReflectiveQuadsDemoMenuItem extends MenuItem {

	public static ReflectiveQuadsDemoMenuItem getInstance() { return reflectiveQuadsDemoMenuItem ; }
	
	private static final ReflectiveQuadsDemoMenuItem reflectiveQuadsDemoMenuItem = new ReflectiveQuadsDemoMenuItem() ;

	public ReflectiveQuadsDemoMenuItem() {
		setText("REFLECTIVE QUADS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ReflectiveQuadsGameState();
	}

}
