package ar.com.xyz.simpledemo.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.particles.ParticleSystemDemoGameState;

public class ParticleSystemDemoMenuItem extends MenuItem {

	public ParticleSystemDemoMenuItem() {
		setText("PARTICLE SYSTEM");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ParticleSystemDemoGameState();
	}

}
