package ar.com.xyz.simpledemo.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.menu.PresentationMenuGameState;

public class PresentationMenuMenuItem extends MenuItem {

	public static PresentationMenuMenuItem getInstance() { return presentationMenuMenuItem ; }
	
	private static final PresentationMenuMenuItem presentationMenuMenuItem = new PresentationMenuMenuItem() ;
	
	private static final PresentationMenuGameState presentationMenuGameState = new PresentationMenuGameState("ZIPCLOSE.wav", "stone.png") ;
	
	public PresentationMenuMenuItem() {
		setText("PRESENTATION");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return presentationMenuGameState ;
	}

}
