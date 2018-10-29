package ar.com.xyz.simpledemo.presentation.cubemapreflection;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.skybox.SkyboxTexture;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

/**
 * Terrain
 * @author alfredo
 *
 */
public class CubeMapReflectionDemoGameState extends AbstractGameState {
	
	private static String[] DAY_TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"} ;
	private static String[] NIGHT_TEXTURE_FILES = {"nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront"} ;
	private static String[] RED_TEXTURE_FILES = {"red_rt", "red_lf", "red_up", "red_dn", "red_bk", "red_ft"} ;
	
	private static final String[] ENVIRO_MAP_INSIDE = {"lposx", "lnegx", "lposy", "lnegy", "lposz", "lnegz"};

	private SkyboxTexture daySkyboxTexture = new SkyboxTexture("/texture/skybox/", DAY_TEXTURE_FILES) ;
	private SkyboxTexture nightSkyboxTexture = new SkyboxTexture("/texture/skybox/", NIGHT_TEXTURE_FILES) ;
	private SkyboxTexture redSkyboxTexture = new SkyboxTexture("/texture/skybox/", RED_TEXTURE_FILES) ;
	
	// /textures/enviro
	private SkyboxTexture environmentMap = new SkyboxTexture("/textures/enviro/", ENVIRO_MAP_INSIDE) ;
	
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
			createEntity(entitySpec);
		} else {
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture(ColorEnum.MAROON.getName());
			entitySpec.setPosition(new Vector3f(0, 3, 0));
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
//			entitySpec.setEntityController(xEntityController);
			createEntity(entitySpec);
		}
		
		setupPlayerAndCamera() ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		getAmbientLight().x = .5f ;
		getAmbientLight().y = .5f ;
		getAmbientLight().z = .5f ;
		
		setSkyboxTextureA(environmentMap);
		
		// Muestras las esferas en 000 ...
//		enableDebugKeys();
		
	}
	
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
	}
	
	float xxx = 0 ;
	
	@Override
	public void tick(float tpf) {

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
	
}
