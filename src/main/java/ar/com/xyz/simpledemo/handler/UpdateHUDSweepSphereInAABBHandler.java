package ar.com.xyz.simpledemo.handler;

import ar.com.xyz.gameengine.AbstractMainCharacterGameState;
import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.entity.SweepSphereInAABBHandler;
import ar.com.xyz.simpledemo.gamestate.CollisionTypeNoneDemoGameState;

public class UpdateHUDSweepSphereInAABBHandler extends SweepSphereInAABBHandler<AbstractMainCharacterGameState> {
	
	@Override
	public void handleSweepSphereInAABB(SweepSphereCollisionEntity sweepSphere) {
		System.out.println("UpdateHUDSweepSphereInAABBHandler: AABB solapado con AABB de " + sweepSphere);
		if (sweepSphere.equals(getGameState().getPlayer())) {
			System.out.println("UpdateHUDSweepSphereInAABBHandler: colision con el player");
			((CollisionTypeNoneDemoGameState)getGameState()).setSweepSphereInAABB(true);
		} else {
			System.out.println("UpdateHUDSweepSphereInAABBHandler: colision con " + sweepSphere);
		}
	}

}
