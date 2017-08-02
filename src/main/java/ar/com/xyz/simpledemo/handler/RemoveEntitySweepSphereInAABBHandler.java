package ar.com.xyz.simpledemo.handler;

import ar.com.xyz.gameengine.entity.Entity;
import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.entity.SweepSphereInAABBHandler;
import ar.com.xyz.simpledemo.gamestate.CollisionTypeNoneDemoGameState;

public class RemoveEntitySweepSphereInAABBHandler implements SweepSphereInAABBHandler {

	private Entity entity ;

	private CollisionTypeNoneDemoGameState xyzDemoLevelGameState ;
	
	public RemoveEntitySweepSphereInAABBHandler(CollisionTypeNoneDemoGameState xyzBloodLevelGameState) {
		this.xyzDemoLevelGameState = xyzBloodLevelGameState ;
	}

	@Override
	public void handleSweepSphereInAABB(SweepSphereCollisionEntity sweepSphere) {
		System.out.println("RemoveEntitySweepSphereInAABBHandler: AABB solapado con AABB de " + sweepSphere);
		if (sweepSphere.equals(xyzDemoLevelGameState.getPlayer())) {
			System.out.println("RemoveEntitySweepSphereInAABBHandler: colision con el player");
			xyzDemoLevelGameState.scheduleEntityForRemoval(entity);
			xyzDemoLevelGameState.addNotification("PICKED UP BOX");
		} else {
			System.out.println("RemoveEntitySweepSphereInAABBHandler: colision con " + sweepSphere);
		}
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity ;
	}

}
