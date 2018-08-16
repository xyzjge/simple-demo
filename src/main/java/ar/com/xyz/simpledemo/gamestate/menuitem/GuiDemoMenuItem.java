package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.guitexture.GuiDemoGameState;

public class GuiDemoMenuItem extends MenuItem {

	public GuiDemoMenuItem() {
		setText("GUI");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new GuiDemoGameState();
	}

}
