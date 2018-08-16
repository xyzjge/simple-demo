package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.menu.SimpleDemoMenuGameState;

public class SimpleDemoMenuMenuItem extends MenuItem {

	public static SimpleDemoMenuMenuItem getInstance() { return simpleDemoMenuMenuItem ; }
	
	private static final SimpleDemoMenuMenuItem simpleDemoMenuMenuItem = new SimpleDemoMenuMenuItem() ;
	
	private static final SimpleDemoMenuGameState simpleDemoMenuGameState = new SimpleDemoMenuGameState("ZIPCLOSE.wav", "stone.png") ;
	
	public SimpleDemoMenuMenuItem() {
		setText("MENU PRINCIPAL");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return simpleDemoMenuGameState ;
	}

}
