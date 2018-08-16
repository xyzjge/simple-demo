package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.ViewStaticSceneDemoGameState;

public class ViewStaticSceneDemoMenuItem extends MenuItem {

	public ViewStaticSceneDemoMenuItem() {
		setText("VIEW");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ViewStaticSceneDemoGameState();
	}

}
