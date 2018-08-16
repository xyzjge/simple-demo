package ar.com.xyz.simpledemo.gamestate.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.skybox.SkyboxDemoGameState;

public class SkyboxDemoMenuItem extends MenuItem {

	public SkyboxDemoMenuItem() {
		setText("SKYBOX");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new SkyboxDemoGameState();
	}

}
