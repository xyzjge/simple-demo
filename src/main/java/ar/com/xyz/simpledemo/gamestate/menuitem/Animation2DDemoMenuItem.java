package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.Animation2DDemoGameState;

public class Animation2DDemoMenuItem extends MenuItem {

	public Animation2DDemoMenuItem() {
		setText("2D ANIMATIONS DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new Animation2DDemoGameState();
	}

}
