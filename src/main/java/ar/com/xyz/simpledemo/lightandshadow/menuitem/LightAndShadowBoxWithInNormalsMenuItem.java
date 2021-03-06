package ar.com.xyz.simpledemo.lightandshadow.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.lightandshadow.LightAndShadowBoxWithInNormalsGameState;

public class LightAndShadowBoxWithInNormalsMenuItem extends MenuItem {

	public LightAndShadowBoxWithInNormalsMenuItem() {
		setText("INSIDE NORMALS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new LightAndShadowBoxWithInNormalsGameState();
	}

}
