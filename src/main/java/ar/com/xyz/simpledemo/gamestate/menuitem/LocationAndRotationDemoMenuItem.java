package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.LocationAndRotationDemoGameState;

public class LocationAndRotationDemoMenuItem extends MenuItem {

	public LocationAndRotationDemoMenuItem() {
		setText("LOCATION AND ROTATION DEMO");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new LocationAndRotationDemoGameState();
	}

}
