package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.ObjLoaderDemoGameState;
import ar.com.xyz.simpledemo.gamestate.PentagonAnimationGameState;

public class PentagonAnimationMenuItem extends MenuItem {

	public PentagonAnimationMenuItem() {
		setText("PENTAGON ANIMATION");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new PentagonAnimationGameState();
	}

}
