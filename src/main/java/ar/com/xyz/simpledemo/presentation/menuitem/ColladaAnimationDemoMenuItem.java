package ar.com.xyz.simpledemo.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.ColladaAnimationDemoGameState;

public class ColladaAnimationDemoMenuItem extends MenuItem {

	public ColladaAnimationDemoMenuItem() {
		setText("DAE (SINGLE TEXTURE)");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ColladaAnimationDemoGameState();
	}

}
