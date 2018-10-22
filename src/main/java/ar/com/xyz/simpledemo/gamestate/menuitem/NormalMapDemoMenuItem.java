package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.NormalMapDemoGameState;

public class NormalMapDemoMenuItem extends MenuItem {

	public static NormalMapDemoMenuItem getInstance() { return normalMapDemoMenuItem ; }
	
	private static final NormalMapDemoMenuItem normalMapDemoMenuItem = new NormalMapDemoMenuItem() ;

	public NormalMapDemoMenuItem() {
		setText("NORMAL MAP");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new NormalMapDemoGameState();
	}

}
