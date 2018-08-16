package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.NewLookAtVersionDemoGameState;

public class NewLookAtVersionDemoMenuItem extends MenuItem {

	public NewLookAtVersionDemoMenuItem() {
		setText("NEW LOOK AT 3D VERSION DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new NewLookAtVersionDemoGameState();
	}

}
