package ar.com.xyz.simpledemo.gamestate.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.PositionRotationScaleGameState;

public class PositionRotationScaleMenuItem extends MenuItem {

	public PositionRotationScaleMenuItem() {
		setText("POSITION ROTATION SCALE");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new PositionRotationScaleGameState();
	}

}
