package ar.com.xyz.simpledemo.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.menu.EntitiesMenuGameState;

public class EntitiesMenuMenuItem extends MenuItem {

	public static EntitiesMenuMenuItem getInstance() { return entitiesMenuMenuItem ; }
	
	private static final EntitiesMenuMenuItem entitiesMenuMenuItem = new EntitiesMenuMenuItem() ;
	
	private static final EntitiesMenuGameState entitiesMenuGameState = new EntitiesMenuGameState("ZIPCLOSE.wav", "stone.png") ;
	
	public EntitiesMenuMenuItem() {
		setText("ENTITIES");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return entitiesMenuGameState ;
	}

}
