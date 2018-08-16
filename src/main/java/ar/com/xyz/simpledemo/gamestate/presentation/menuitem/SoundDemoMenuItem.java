package ar.com.xyz.simpledemo.gamestate.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.sound.SoundDemoGameState;

public class SoundDemoMenuItem extends MenuItem {

	public SoundDemoMenuItem() {
		setText("SOUND");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new SoundDemoGameState();
	}

}
