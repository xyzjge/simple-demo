package ar.com.xyz.simpledemo.presentation.skybox;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.skybox.SkyboxTexture;
import ar.com.xyz.simpledemo.gamestate.SimpleDemoMenuGameState;

/**
 * Terrain
 * @author alfredo
 *
 */
public class SkyboxDemoGameState extends AbstractGameState {

	private static float UNIT = 8 ;
	
	private static String[] DAY_TEXTURE_FILES = {"right", "left", "top", "bottom", "back", "front"} ;
	private static String[] NIGHT_TEXTURE_FILES = {"nightRight", "nightLeft", "nightTop", "nightBottom", "nightBack", "nightFront"} ;
	private static String[] RED_TEXTURE_FILES = {"red_rt", "red_lf", "red_up", "red_dn", "red_bk", "red_ft"} ;

	private SkyboxTexture daySkyboxTexture = new SkyboxTexture("/texture/skybox/", DAY_TEXTURE_FILES) ;
	private SkyboxTexture nightSkyboxTexture = new SkyboxTexture("/texture/skybox/", NIGHT_TEXTURE_FILES) ;
	private SkyboxTexture redSkyboxTexture = new SkyboxTexture("/texture/skybox/", RED_TEXTURE_FILES) ;
	
	private static final String LEVEL = "s-box" ;
	
	public SkyboxDemoGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}
		
		loadPlayerAndCamera() ;
		
		createInputHandler(
			mainGameLoop, getPlayer(), getCamera(), this, null, null
		) ;
		
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
	}

	float xxx = 0 ;
	boolean nightSkyboxTextureSet = false ;
	boolean redSkyboxTextureSet = false ;
	
	@Override
	public void tick(float tpf) {
		xxx+=tpf ;

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
		if (xxx>UNIT && !nightSkyboxTextureSet) {
			getMainGameLoop().getMasterRenderer().setSkyboxTexture(nightSkyboxTexture);
			nightSkyboxTextureSet = true ;
		}
		if (xxx>UNIT*2 && !redSkyboxTextureSet) {
			getMainGameLoop().getMasterRenderer().setSkyboxTexture(redSkyboxTexture);
			redSkyboxTextureSet = true ;
		}
		
		if (xxx>UNIT*3) {
			xxx = 0 ;
			nightSkyboxTextureSet = false ;
			redSkyboxTextureSet = false ;
			getMainGameLoop().getMasterRenderer().setSkyboxTexture(daySkyboxTexture);
		}
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(0, 1, 0),
			new Vector3f(0, 0, 0), 
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null
		) ;

		// getCamera().decPitch(-90);
		
//		getPlayer().setCrushHandler(this);
		
//		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));

		getPlayer().setRunSpeed(3);
		getPlayer().setStrafeSpeed(3);
		this.enableDebug(getPlayer());
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(new SimpleDemoMenuGameState(getMainGameLoop(), "ZIPCLOSE.wav", "stone.png")) ;
	}

	@Override
	protected SkyboxTexture getSkyboxTexture() {
		return daySkyboxTexture ;
//		return new SkyboxTexture("/texture/skybox/", TEXTURE_FILES) ;
	}
	
}
