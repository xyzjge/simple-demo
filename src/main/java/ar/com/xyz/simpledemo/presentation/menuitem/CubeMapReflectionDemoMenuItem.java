package ar.com.xyz.simpledemo.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.cubemapreflection.CubeMapReflectionDemoGameState;

public class CubeMapReflectionDemoMenuItem extends MenuItem {

	public CubeMapReflectionDemoMenuItem() {
		setText("CUBE MAP REFLECTIONS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CubeMapReflectionDemoGameState();
	}

}
