package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.cameracontroller.CameraController;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

public class PentagonAnimationGameState extends AbstractGameState implements CrushHandler, CameraController {

	private static final float VELOCITY = 10;
	
	private static final String LEVEL = "s-box" ;
	
	public PentagonAnimationGameState() {
		
		SingletonManager.getInstance().getObjLoader().addObjPath("/models") ;
		SingletonManager.getInstance().getObjLoader().addMtlPath("/models") ;
		
		setupPlayerAndCamera() ;
		/*
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}*/
		/*
		{	// Create SOLID_STATIC for X+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("x-box-texture");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
//			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(20,1,0));
			createEntity(entitySpec);
		}

		{	// Create SOLID_STATIC for X-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-x-box-texture");
			entitySpec.setPosition(new Vector3f(-20,1,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for Y+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("y-box-texture");
			entitySpec.setPosition(new Vector3f(0,20,0));
			createEntity(entitySpec);
		}
		{	// Create SOLID_STATIC for Y-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-y-box-texture");
			entitySpec.setPosition(new Vector3f(0,-20,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for Z+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("z-box-texture");
			entitySpec.setPosition(new Vector3f(0,1,20));
			createEntity(entitySpec);
		}
		{	// Create SOLID_STATIC for Z-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-z-box-texture");
			entitySpec.setPosition(new Vector3f(0,1,-20));
			createEntity(entitySpec);
		}
		*/
		{ // Load obj ...
/*			{
//				LapidaDravenEntityController lapidaDravenEntityController = new LapidaDravenEntityController() ;
				EntitySpec entitySpec = new EntitySpec("lapida-draven") ;
				entitySpec.setTexture("lapida-draven.png");
//				entitySpec.setTexture("tile1159-2.png");
//				entitySpec.setTexture("tile1159-s.png");
				
				entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
//				entitySpec.setEntityController(lapidaDravenEntityController);
				entitySpec.setModelRotation(new Vector3f(0,0,0));
//				entitySpec.setPosition(new Vector3f(-14.79f, 1.5f, 73));
				entitySpec.setPosition(new Vector3f(0, 1.5f, 0));
//				entitySpec.setRayHitHandler(lapidaDravenEntityController);
				createEntity(entitySpec);
			}*/
			colocarPentagonos();
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(false);
		setShowPlayerPosition(false);
		
		setCameraController(this);
		
	}

	private void colocarPentagonos() {
		for (int i = 0; i < 10; i++) {
			colocarPentagono(new Vector3f(- (i*10),0,0), new Vector3f((i%2)*180,0,0));
		}
	}

	private void colocarPentagono(Vector3f position, Vector3f rotation) {
//		LapidaDravenEntityController lapidaDravenEntityController = new LapidaDravenEntityController() ;
		EntitySpec entitySpec = new EntitySpec("pentagon1") ;
		entitySpec.setTexture("pentagon1.png");
		entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//		entitySpec.setEntityController(lapidaDravenEntityController);
		entitySpec.setModelRotation(rotation);
		entitySpec.setPosition(position);
		createEntity(entitySpec);
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			// setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			// getCamera().setCameraController(this);
			//addInputEventListener(this) ;
			//addInputEventListener((InputEventListener)getCamera().getCameraController());

		}
	}
	
	@Override
	public void tick(float tpf) {
/*		if (getPlayer().getPosition().y < -10) {
			handlePlayerDeath() ;
		}*/
	}

	private void setupPlayerAndCamera() {
/*
		setupPlayerAndCamera(
			new Vector3f(15, 5, 5),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;
		
		getPlayer().setCrushHandler(this);
		
		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));

		this.enableDebug(getPlayer());
	*/	
	}

	@Override
	public void crush() {
//		handlePlayerDeath();
		//getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

	// Camera Controller
	private Vector3f cameraPosition = new Vector3f(200,0,0) ;
	private Vector3f cameraRotation = new Vector3f(0,-90,0) ;
	
	@Override
	public Vector3f getPosition() {
		return cameraPosition;
	}
	
	@Override
	public Vector3f getRotation() {
		return cameraRotation;
	}

	@Override
	public float getPitch() {
		return cameraRotation.x;
	}

	@Override
	public void setPitch(float pitch) {
		cameraRotation.x = pitch ;
	}
	
	@Override
	public float getYaw() {
		return cameraRotation.y;
	}

	@Override
	public float getRoll() {
		return cameraRotation.z;
	}
		
	@Override
	public void update(float fts) {
		cameraPosition.x -= (fts * VELOCITY) ;
		cameraRotation.z -= (fts * VELOCITY) ;
		
	}

	@Override
	public void resetDefaults() {
		cameraPosition.x = 0;
		cameraPosition.y = 0;
		cameraPosition.z = 0;
		cameraRotation.x = 0;
		cameraRotation.y = 0;
		cameraRotation.z = 0;
	}

}
