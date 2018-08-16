package ar.com.xyz.simpledemo.lightandshadow.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.lightandshadow.LightAndShadowGameState;

public class LightAndShadowMenuItem extends MenuItem {

	public LightAndShadowMenuItem() {
		setText("SIMPLE");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new LightAndShadowGameState();
	}

}
