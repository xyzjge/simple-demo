package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.LightsAndShadowsMenuGameState;

public class LightsAndShadowsMenuMenuItem extends MenuItem {

	private AbstractGameState abstractGameState = null ;
	
	public LightsAndShadowsMenuMenuItem() {
		setText("LIGHTS AND SHADOWS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return abstractGameState;
	}

	public void init() {
//		abstractGameState = new LightsAndShadowsMenuGameState( "ZIPCLOSE.wav", "stone.png", this));
	}

}
