package ar.com.xyz.simpledemo.controller;

import ar.com.xyz.gameengine.entity.Entity;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.singleton.SingletonManager;

/**
 * 
 * @author alfredo
 *
 */
public class LookAtEntityController extends EntityController {

	private Entity target ;
		
	public LookAtEntityController(Entity target) {
		this.target = target ;
	}

	@Override
	public void postConstruct() {
		getGameState().enableDebug(getGameState().getPlayer());
		getGameState().getPlayer().setRunSpeed(2);
		getGameState().getPlayer().setStrafeSpeed(2);
	}

	@Override
	public void update(float tpf) {
		SingletonManager.getInstance().getEntityUtil().stackOverflowLookAt3d(getEntity(), target.getPosition());
	}

}
