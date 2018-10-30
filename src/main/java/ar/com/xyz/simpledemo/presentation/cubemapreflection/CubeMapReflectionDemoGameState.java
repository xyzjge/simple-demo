package ar.com.xyz.simpledemo.presentation.cubemapreflection;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.client.entitycontroller.DummyEntityController;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.skybox.SkyboxTexture;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

/**
 * Terrain
 * @author alfredo
 *
 */
public class CubeMapReflectionDemoGameState extends AbstractGameState implements InputEventListener {
	
//	private static String[] DAY_TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"} ;
//	private static String[] NIGHT_TEXTURE_FILES = {"nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront"} ;
//	private static String[] RED_TEXTURE_FILES = {"red_rt", "red_lf", "red_up", "red_dn", "red_bk", "red_ft"} ;
	
	private static final String[] ENVIRO_MAP_INSIDE = {"lposx", "lnegx", "lposy", "lnegy", "lposz", "lnegz"};
	
	private static final String[] ENVIRO_MAP_INSIDE_2 = {"/textures/enviro/lposx", "/textures/enviro/lnegx", "/textures/enviro/lposy", "/textures/enviro/lnegy", "/textures/enviro/lposz", "/textures/enviro/lnegz"};

//	private SkyboxTexture daySkyboxTexture = new SkyboxTexture("/texture/skybox/", DAY_TEXTURE_FILES) ;
//	private SkyboxTexture nightSkyboxTexture = new SkyboxTexture("/texture/skybox/", NIGHT_TEXTURE_FILES) ;
//	private SkyboxTexture redSkyboxTexture = new SkyboxTexture("/texture/skybox/", RED_TEXTURE_FILES) ;
	
	// /textures/enviro
	private SkyboxTexture environmentMap = new SkyboxTexture("/textures/enviro/", ENVIRO_MAP_INSIDE) ;
	
	private EntityController entityController = new DummyEntityController() ;
	
	private static final String LEVEL = "s-box" ;
	
	private static final boolean BOX = true ;
	
	public CubeMapReflectionDemoGameState() {
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}
		
		if (BOX) {	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setCubeMapReflectionFactor(0.4f);
//			entitySpec.setCubeMapRefractionFactor(.6f);
//			entitySpec.setCubeMapRefractionRatio(.2f);
			entitySpec.setRotation(new Vector3f(0, 90, 0)) ;
			entitySpec.setScale(new Vector3f(5,5,5));
			entitySpec.setPosition(new Vector3f(0,5,0));
			entitySpec.setEntityController(entityController);
			createEntity(entitySpec);
		} else {
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture(ColorEnum.MAROON.getName());
			entitySpec.setPosition(new Vector3f(0, 3, 0));
			entitySpec.setCubeMapReflectionFactor(0.4f);
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
			entitySpec.setEntityController(entityController);
			createEntity(entitySpec);
		}
		
		setupPlayerAndCamera() ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(false);
		
		getAmbientLight().x = .5f ;
		getAmbientLight().y = .5f ;
		getAmbientLight().z = .5f ;
		
		setSkyboxTextureA(environmentMap);
		
		addNotification("Environment cube map reflections and refractions demo (low quality or/and with some visual issues but cheap effect)");
		
	}
	
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			addInputEventListener(this) ;
		}
	}
	
	@Override
	public void tick(float tpf) {

		if (rotateEntity) {
			entityController.getEntity().increaseRotation(0, tpf * 10, 0);
			
		}
		// Por ahora funciona solo para z < -5.5f (es una demo ...)
		if (moveCamera) {
			// -5.5 + (X - -5.5)
			float z = -5.5f - (getCamera().getPosition().z + 5.5f) ; 
			configureDynamic(new Vector3f(0,getCamera().getPosition().y /* 0 */,z/*0*/));
		}
		
		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(0, 1, 0),
			new Vector3f(0, 0, 0), 
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null
		) ;

		getPlayer().setRunSpeed(3);
		getPlayer().setStrafeSpeed(3);
		this.enableDebug(getPlayer());
	}

	private void handlePlayerDeath() {
		// getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
		getMainGameLoop().setNextGameState(PresentationMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

	float skyboxRotationSpeed = 0;

	@Override
	public float getSkyboxRotationSpeed() {
		return skyboxRotationSpeed ;
	}
	
	private boolean moveCamera = false ;
	private boolean rotateEntity = false ;
	
	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		switch (keyOrButton) {
		case Keyboard.KEY_F1:
			addNotification("Environment map data loaded from pre generated images");
			moveCamera = false ;
			activateEnvironmentCubeMap();
			configureStatic(ENVIRO_MAP_INSIDE_2) ;
			break;
		case Keyboard.KEY_F2:
			addNotification("Environment map data dynamically generated (rendering to six FBOs)");
			moveCamera = false ;
			activateEnvironmentCubeMap();
			configureDynamic(new Vector3f(0,1,0));
			break;
		case Keyboard.KEY_F3:
			addNotification("Environment map data dynamically generated adjusting the camera location before rendering to the FBOs (only for Z < -5.5f)");
			activateEnvironmentCubeMap();
			moveCamera = true ;
			break;
		case Keyboard.KEY_F4:
			addNotification("Refraction activated");
			entityController.getEntity().setCubeMapRefractionFactor(0.2f);
			entityController.getEntity().setCubeMapRefractionRatio(0.9f);
			break;
		case Keyboard.KEY_F5:
			addNotification("Refraction deactivated");
			entityController.getEntity().setCubeMapRefractionFactor(0.0f);
			break;
		case Keyboard.KEY_F6:
			rotateEntity = !rotateEntity ;
			break;
//		case Keyboard.KEY_F7:
//			handleF7();
//			break;
//		case Keyboard.KEY_F8:
//			handleF8();
//			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		if (origin == EventOriginEnum.KEYBOARD && type == EventTypeEnum.RELEASED) {
			return true ;
		}
		return false;
	}
	
	@Override
	public void tick() {
		
	}
}
