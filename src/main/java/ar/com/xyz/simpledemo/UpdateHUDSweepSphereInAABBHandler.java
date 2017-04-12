package ar.com.xyz.simpledemo;

import ar.com.xyz.gameengine.entity.Entity;
import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.entity.SweepSphereInAABBHandler;

public class UpdateHUDSweepSphereInAABBHandler implements SweepSphereInAABBHandler {

	private Entity entity ;

	private XYZDemoLevelGameState xyzDemoLevelGameState ;
	
	public UpdateHUDSweepSphereInAABBHandler(XYZDemoLevelGameState xyzDemoLevelGameState) {
		this.xyzDemoLevelGameState = xyzDemoLevelGameState ;
	}

	@Override
	public void handleSweepSphereInAABB(SweepSphereCollisionEntity sweepSphere) {
		System.out.println("UpdateHUDSweepSphereInAABBHandler: AABB solapado con AABB de " + sweepSphere);
		if (sweepSphere.equals(xyzDemoLevelGameState.getPlayer())) {
			System.out.println("UpdateHUDSweepSphereInAABBHandler: colision con el player");
			xyzDemoLevelGameState.setSweepSphereInAABB(true);
		} else {
			System.out.println("UpdateHUDSweepSphereInAABBHandler: colision con " + sweepSphere);
		}
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity ;
	}

}
