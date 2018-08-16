package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.CameraControllerDemoGameState;

public class CameraControllerDemoMenuItem extends MenuItem {

	public CameraControllerDemoMenuItem() {
		setText("CAMERA CONTROLLER DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new CameraControllerDemoGameState();
	}

}
