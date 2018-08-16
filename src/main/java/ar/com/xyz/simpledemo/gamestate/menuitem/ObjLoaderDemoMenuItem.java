package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.ObjLoaderDemoGameState;

public class ObjLoaderDemoMenuItem extends MenuItem {

	public ObjLoaderDemoMenuItem() {
		setText("OBJ LOADER");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ObjLoaderDemoGameState();
	}

}
