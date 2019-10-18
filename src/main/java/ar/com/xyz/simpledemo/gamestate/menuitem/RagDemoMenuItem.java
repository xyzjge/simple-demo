package ar.com.xyz.simpledemo.gamestate.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.RagDemoGameState;

public class RagDemoMenuItem extends MenuItem {

	public RagDemoMenuItem() {
		setText("RAG");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new RagDemoGameState();
	}

}
