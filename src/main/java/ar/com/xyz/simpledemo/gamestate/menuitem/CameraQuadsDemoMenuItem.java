package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.CameraQuadsDemoGameState;

public class CameraQuadsDemoMenuItem extends MenuItem {

	public static CameraQuadsDemoMenuItem getInstance() { return cameraQuadsDemoMenuItem ; }
	
	private static final CameraQuadsDemoMenuItem cameraQuadsDemoMenuItem = new CameraQuadsDemoMenuItem() ;

	public CameraQuadsDemoMenuItem() {
		setText("CAMERA QUADS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CameraQuadsDemoGameState();
	}

}
