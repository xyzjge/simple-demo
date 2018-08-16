package ar.com.xyz.simpledemo.gamestate.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.PresentationGameState;

public class PresentationMenuItem extends MenuItem {

	public PresentationMenuItem() {
		setText("PRESENTATION");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new PresentationGameState();
	}

}
