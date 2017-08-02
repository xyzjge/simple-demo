package ar.com.xyz.simpledemo.handler;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.simpledemo.gamestate.SimpleDemoMenuGameState;

/**
 * 
 * @author alfredo
 *
 */
public class PlayerDeathHandler implements CrushHandler {

	private AbstractGameState gameState ;
	
	private AbstractMainGameLoop mainGameLoop ;
	
	public PlayerDeathHandler(AbstractMainGameLoop mainGameLoop, AbstractGameState gameState) {
		this.gameState = gameState ;
		this.mainGameLoop = mainGameLoop ;
	}

	@Override
	public void crush() {
		handlePlayerDeath();
	}

	private void handlePlayerDeath() {
		// Asi cada vez que muere vuelve a arrancar, estaría ok ...
//		mainGameLoop.setNextGameState(new XYZDemoLevelGameState(mainGameLoop));
		
		mainGameLoop.setNextGameState(new SimpleDemoMenuGameState(mainGameLoop, "arial", "ZIPCLOSE.wav", "stone.png")) ;
	}

	public void tick() {
		if (gameState.getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
	}

}