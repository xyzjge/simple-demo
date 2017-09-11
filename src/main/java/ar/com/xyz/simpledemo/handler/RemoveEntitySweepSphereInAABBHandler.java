package ar.com.xyz.simpledemo.handler;

import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.entity.SweepSphereInAABBHandler;

public class RemoveEntitySweepSphereInAABBHandler extends SweepSphereInAABBHandler {

	@Override
	public void handleSweepSphereInAABB(SweepSphereCollisionEntity sweepSphere) {
		System.out.println("RemoveEntitySweepSphereInAABBHandler: AABB solapado con AABB de " + sweepSphere);
		if (sweepSphere.equals(getGameState().getPlayer())) {
			System.out.println("RemoveEntitySweepSphereInAABBHandler: colision con el player");
			getGameState().scheduleEntityForRemoval(getEntity());
			getGameState().addNotification("PICKED UP BOX");
		} else {
			System.out.println("RemoveEntitySweepSphereInAABBHandler: colision con " + sweepSphere);
		}
	}

}
