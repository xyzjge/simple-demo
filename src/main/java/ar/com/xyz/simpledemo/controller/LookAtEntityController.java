package ar.com.xyz.simpledemo.controller;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.entity.Entity;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.singleton.SingletonManager;

/**
 * 
 * @author alfredo
 *
 */
public class LookAtEntityController extends EntityController {

	private AbstractGameState gameState ;
	private Entity target ;
		
	public LookAtEntityController(AbstractGameState gameState, Entity target) {
		this.gameState = gameState ;
		this.gameState.enableDebug(this.gameState.getPlayer());
		this.gameState.getPlayer().setRunSpeed(2);
		this.gameState.getPlayer().setStrafeSpeed(2);
		this.target = target ;
	}

	@Override
	public void update(float tpf) {
		SingletonManager.getInstance().getEntityUtil().lookAt3d(getEntity(), target.getPosition());
	}

}
