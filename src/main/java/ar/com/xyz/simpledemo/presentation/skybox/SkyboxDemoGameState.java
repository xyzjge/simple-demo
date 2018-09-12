package ar.com.xyz.simpledemo.presentation.skybox;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.skybox.SkyboxTexture;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

/**
 * Terrain
 * @author alfredo
 *
 */
public class SkyboxDemoGameState extends AbstractGameState {

	private static float STATE_TIME_UNIT = 8 ;
	
	private static String[] DAY_TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"} ;
	private static String[] NIGHT_TEXTURE_FILES = {"nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront"} ;
	private static String[] RED_TEXTURE_FILES = {"red_rt", "red_lf", "red_up", "red_dn", "red_bk", "red_ft"} ;

	private SkyboxTexture daySkyboxTexture = new SkyboxTexture("/texture/skybox/", DAY_TEXTURE_FILES) ;
	private SkyboxTexture nightSkyboxTexture = new SkyboxTexture("/texture/skybox/", NIGHT_TEXTURE_FILES) ;
	private SkyboxTexture redSkyboxTexture = new SkyboxTexture("/texture/skybox/", RED_TEXTURE_FILES) ;
	
	private StateEnum state = null ;
	
	private static final String LEVEL = "s-box" ;
	
	public SkyboxDemoGameState() {
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}
		
		setupPlayerAndCamera() ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		getAmbientLight().x = .5f ;
		getAmbientLight().y = .5f ;
		getAmbientLight().z = .5f ;
		
		// Muestras las esferas en 000 ...
//		enableDebugKeys();
		
	}
	
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getPlayerInputEventListener() == null) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
	}
	
	float xxx = 0 ;
	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
		xxx+=tpf ;
		
		if (state != null && (state == StateEnum.DAY_RED || state == StateEnum.RED_NIGHT)) {
			setSkyboxBlendFactor(xxx / STATE_TIME_UNIT);
			// System.out.println(getSkyboxBlendFactor());
		}
		
		if (xxx > STATE_TIME_UNIT) {
			xxx = 0 ;
			if (state == null) {
				state = StateEnum.DAY ;
				setSkyboxTextureA(daySkyboxTexture);
				return ;
			}
			
			switch (state) {
			case DAY:
				state = StateEnum.DAY_RED ;
				setSkyboxTextureA(daySkyboxTexture);
				setSkyboxTextureB(redSkyboxTexture);
				setSkyboxBlendFactor(0);
				break;
			case DAY_RED:
				state = StateEnum.RED_NIGHT ;
				setSkyboxTextureA(redSkyboxTexture);
				setSkyboxTextureB(nightSkyboxTexture);
				setSkyboxBlendFactor(0);
				break;
			case RED_NIGHT:
				state = StateEnum.NIGHT ;
				setSkyboxTextureA(nightSkyboxTexture);
				setSkyboxTextureB(null);
				break;
			default:
				break;
			}
			
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

	float skyboxRotationSpeed = -10;

	@Override
	public float getSkyboxRotationSpeed() {
		return skyboxRotationSpeed ;
	}
	
}
