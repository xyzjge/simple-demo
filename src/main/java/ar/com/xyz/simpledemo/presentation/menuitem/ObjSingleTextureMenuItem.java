package ar.com.xyz.simpledemo.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.ObjSingleTextureGameState;

public class ObjSingleTextureMenuItem extends MenuItem {

	public ObjSingleTextureMenuItem() {
		setText("OBJ (SINGLE TEXTURE)");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ObjSingleTextureGameState();
	}

}
