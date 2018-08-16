package ar.com.xyz.simpledemo.gamestate.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.ObjMultipleTextureGameState;

public class ObjMultipleTextureMenuItem extends MenuItem {

	public ObjMultipleTextureMenuItem() {
		setText("OBJ (MULTIPLE TEXTURE)");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ObjMultipleTextureGameState();
	}

}
