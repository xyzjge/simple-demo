package ar.com.xyz.simpledemo.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameState;

public class ParticleSystemDemoMenuItem extends MenuItem {

	public ParticleSystemDemoMenuItem() {
		setText("ANOTHER PARTICLE SYSTEM");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new AnotherParticleSystemDemoGameState();
	}

}
