package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.ParentChildDemoGameState;

public class ParentChildDemoMenuItem extends MenuItem {

	public ParentChildDemoMenuItem() {
		setText("PARENT CHILD DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ParentChildDemoGameState();
	}

}
