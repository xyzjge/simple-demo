package ar.com.xyz.simpledemo.handler;

import ar.com.xyz.gameengine.AbstractMainCharacterGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.simpledemo.presentation.menuitem.EntitiesMenuMenuItem;

/**
 * 
 * @author alfredo
 *
 */
public class PlayerDeathHandler implements CrushHandler {

	private AbstractMainCharacterGameState gameState ;
	
	private AbstractMainGameLoop mainGameLoop ;
	
	public PlayerDeathHandler(AbstractMainGameLoop mainGameLoop, AbstractMainCharacterGameState gameState) {
		this.gameState = gameState ;
		this.mainGameLoop = mainGameLoop ;
	}

	@Override
	public void crush() {
		handlePlayerDeath();
	}

	private void handlePlayerDeath() {
		mainGameLoop.setNextGameState(EntitiesMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

	public void tick() {
		if (gameState.getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
	}

}
