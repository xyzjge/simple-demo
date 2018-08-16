package ar.com.xyz.simpledemo.lightandshadow.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.menu.LightsAndShadowsMenuGameState;

public class LightsAndShadowsMenuMenuItem extends MenuItem {

	public static LightsAndShadowsMenuMenuItem getInstance() { return lightsAndShadowsMenuMenuItem ; }
	
	private static final LightsAndShadowsMenuMenuItem lightsAndShadowsMenuMenuItem = new LightsAndShadowsMenuMenuItem() ;
	
	private static final LightsAndShadowsMenuGameState lightsAndShadowsMenuGameState = new LightsAndShadowsMenuGameState("ZIPCLOSE.wav", "stone.png") ;
	
	public LightsAndShadowsMenuMenuItem() {
		setText("LIGHTS AND SHADOWS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return lightsAndShadowsMenuGameState ;
	}

}
