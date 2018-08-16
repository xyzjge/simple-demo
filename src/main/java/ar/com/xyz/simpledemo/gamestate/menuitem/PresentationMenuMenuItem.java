package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.WaterDemoGameState;

public class PresentationMenuMenuItem extends MenuItem {

	public PresentationMenuMenuItem() {
		setText("PRESENTATION");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		// return new PresentationMenuGameState(getMainGameLoop(), "ZIPCLOSE.wav", "stone.png", this));
		return null ;
	}

}
