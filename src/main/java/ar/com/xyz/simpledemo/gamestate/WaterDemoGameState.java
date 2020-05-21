package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.light.DirectionalLight;
import ar.com.xyz.gameengine.postprocessing.fisheye.FishEyePostProcessingFilter;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.water.WaterTile;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

/**
 * Water demo
 * @author alfredo
 *
 */
public class WaterDemoGameState extends AbstractGameState implements CrushHandler {

	private static final boolean CAST_SHADOWS = true;

	private static final String LEVEL = "water-demo-level" ;
	
	private static final Vector3f ambientLight = new Vector3f(.5f,.5f,.5f) ;
	
	private static final Vector3f underWaterAmbientLight = new Vector3f(.1f,.3f,.3f) ;
	
	private FishEyePostProcessingFilter fishEyePostProcessingFilter = new FishEyePostProcessingFilter() ;
	
	public WaterDemoGameState() {
		setupPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("grass");
			entitySpec.setScale(new Vector3f(1,1,1));
			entitySpec.setPosition(new Vector3f(0,-10,0));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setScale(new Vector3f(2,2,2));
			entitySpec.setPosition(new Vector3f(6,-10 + 1,-4));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setScale(new Vector3f(2,2,2));
			entitySpec.setPosition(new Vector3f(-7,-10 + 1,-4));
			createEntity(entitySpec);
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).setAbstractGameState(this);
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).setAbstractGameState(this);

		getWaterTileList().add(new WaterTile(mid(-12, -2) -2 + 1, mid(-4, 10) + 2, -10 - .4f /*+ 1*/, 7)) ;
		getWaterTileList().add(new WaterTile(mid(-2, 11) + 2, mid(-4, 10) + 1, -10 - .2f, 8)) ;
		
		// DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.5f,.5f,.5f), new Vector3f(-10000,-10000,-10000), 1) ;
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1,.5f,.5f), new Vector3f(-10000,-10000,-10000), 1) ;
		// DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1,0,0), new Vector3f(-10000,-10000,-10000), 1) ;
		directionalLight.setCastShadows(CAST_SHADOWS);
		setDirectionalLight(directionalLight);
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
	}
	
	private float mid(float inicio, float fin) {
		return (fin - inicio) / 2 + inicio ;
	}

	@Override
	public void tick(float tpf) {

		// TODO: Asociar un AABB a cada WaterTile ...
		boolean underWater = false ;
		for (WaterTile waterTile : getWaterTileList()) {
			if (getCamera().getPosition().y < waterTile.getHeight()) {
				underWater = true;
				break ;
			}
		}
		if (underWater) {
			getAmbientLight().x = underWaterAmbientLight.x ;
			getAmbientLight().y = underWaterAmbientLight.y ;
			getAmbientLight().z = underWaterAmbientLight.z ;
			
			if (!containsPostProcessingFilter(fishEyePostProcessingFilter)) {
				addPostProcessingFilter(fishEyePostProcessingFilter);
			}
			
			getPlayer().setGravity(-1);
			getPlayer().setJumpPower(1);
			getPlayer().setRunSpeed(1);
			getPlayer().setTurnSpeed(1); // ...
			if (getPlayer().getStrafeSpeed() != 1) {
				getPlayer().resetUpwardsSpeed();
			}
			getPlayer().setStrafeSpeed(1);

		} else {
			getAmbientLight().x = ambientLight.x ;
			getAmbientLight().y = ambientLight.y ;
			getAmbientLight().z = ambientLight.z ;
			
			if (containsPostProcessingFilter(fishEyePostProcessingFilter)) {
				if (!removePostProcessingFilter(fishEyePostProcessingFilter)) {
					System.out.println("WARN: no fishEyePostProcessingFilter ...");
				}
			}
			
			getPlayer().setGravity(SweepSphereCollisionEntity.GRAVITY);
			getPlayer().setJumpPower(SweepSphereCollisionEntity.JUMP_POWER);
			getPlayer().setRunSpeed(SweepSphereCollisionEntity.RUN_SPEED);
			getPlayer().setTurnSpeed(SweepSphereCollisionEntity.TURN_SPEED /* TODO ...*/);
			getPlayer().setStrafeSpeed(SweepSphereCollisionEntity.STRAFE_SPEED);
			
		}
		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(-1 /*-5*/, 0, 6 /*94*/), // new Vector3f(-5, 10, 90), // new Vector3f(5, 10, 5), //new Vector3f(10, 10, 10),
			new Vector3f(0, -30, 0), // new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setCrushHandler(this);
		
//		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));

		this.enableDebug(getPlayer());
	}

	@Override
	public void crush() {
//		handlePlayerDeath();
		getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		if (containsPostProcessingFilter(fishEyePostProcessingFilter)) {
			if (!removePostProcessingFilter(fishEyePostProcessingFilter)) {
				System.out.println("WARN: no fishEyePostProcessingFilter ...");
			}
		}
		getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

}
