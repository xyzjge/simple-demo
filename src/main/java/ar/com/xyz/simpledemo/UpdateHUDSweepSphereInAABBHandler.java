package ar.com.xyz.simpledemo;

import ar.com.xyz.gameengine.entity.Entity;
import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.entity.SweepSphereInAABBHandler;

public class UpdateHUDSweepSphereInAABBHandler implements SweepSphereInAABBHandler {

	private Entity entity ;

	private XYZDemoLevelGameState xyzDemoLevelGameState ;
	
	public UpdateHUDSweepSphereInAABBHandler(XYZDemoLevelGameState xyzBloodLevelGameState) {
		this.xyzDemoLevelGameState = xyzBloodLevelGameState ;
	}

	@Override
	public void handleSweepSphereInAABB(SweepSphereCollisionEntity sweepSphere) {
		System.out.println("XYZDemoSweepSphereInAABBHandler: AABB solapado con AABB de " + sweepSphere);
		if (sweepSphere.equals(xyzDemoLevelGameState.getPlayer())) {
			System.out.println("XYZDemoSweepSphereInAABBHandler: colision con el player");
//			xyzBloodLevelGameState.scheduleEntityForRemoval(entity);
			xyzDemoLevelGameState.setSweepSphereInAABB(true);
//			xyzDemoLevelGameState.addNotification("PICKED UP BODY ARMOR");
		} else {
			System.out.println("XYZDemoSweepSphereInAABBHandler: colision con " + sweepSphere);
		}
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity ;
	}

}
