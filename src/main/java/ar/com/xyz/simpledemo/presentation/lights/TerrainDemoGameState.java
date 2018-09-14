package ar.com.xyz.simpledemo.presentation.lights;

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
import ar.com.xyz.gameengine.light.DirectionalLight;
import ar.com.xyz.gameengine.light.PointLight;
import ar.com.xyz.gameengine.light.SpotLight;
import ar.com.xyz.gameengine.render.Loader;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.terrain.Terrain;
import ar.com.xyz.gameengine.texture.TerrainTexture;
import ar.com.xyz.gameengine.texture.TerrainTexturePack;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

/**
 * Terrain
 * @author alfredo
 *
 */
public class TerrainDemoGameState extends AbstractGameState implements InputEventListener {
	
	private static final boolean CAST_SHADOWS = true;
	
	private Terrain terrain ;
	
	private DemoState state = DemoState.AMBIENT_LIGHT_DEMO ;

	private EntityController entityController = new DummyEntityController() ;
	
	public TerrainDemoGameState() {
		
		{	// Create sphere to debug light position
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("red");
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(new Vector3f(.25f, .25f, .25f));
			entitySpec.setEntityController(entityController);
			createEntity(entitySpec);
		}
		
		loadTerrain();
		setupPlayerAndCamera() ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		getAmbientLight().x = .5f ;
		getAmbientLight().y = .5f ;
		getAmbientLight().z = .5f ;
		
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.0f,.75f,.75f), new Vector3f(-10000,-10000,-10000), .75f) ;
		directionalLight.setCastShadows(CAST_SHADOWS);
		setDirectionalLight(directionalLight);
		
		{
			Vector3f lightPosition = new Vector3f(20, 0.2f, 20);
			float lightIntensity = 0 /* .2f */;
			PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f,.1f/*.5f*/ /*1.0f*/);
			pointLight.setAttenuation(att);
			getPointLightList().add(pointLight) ;
		}
		
		{
			Vector3f lightPosition = new Vector3f(-10, 2, 58);
			PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, 0 /*.1f *//* lightIntensity */);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.002f);
			pointLight.setAttenuation(att);
			Vector3f coneDir = new Vector3f(0, 0, -1);
			float cutoff = 10 ; //.195f ; // (float) Math.cos(Math.toRadians(.1f /* 100 *//*140*/ /*180*/ /* 240*/));
			SpotLight spotLight = new SpotLight(pointLight, coneDir, cutoff);
			getSpotLightList().add(spotLight) ;
		}
		
		enableDebugKeys();
		
		agregarEsferas() ;
		
	}
	
	
	private AmbientLightDemoController ambientLightDemoController ;
	private DirectionalLightDemoController directionalLightDemoController ;
	private PointLightDemoController pointLightDemoController ;
	private SpotLightDemoController spotLightDemoController ;
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			addInputEventListener(this);
		}

		ambientLightDemoController = new AmbientLightDemoController(this) ;
		directionalLightDemoController = new DirectionalLightDemoController(this) ;
		pointLightDemoController = new PointLightDemoController(this) ;
		spotLightDemoController = new SpotLightDemoController(this) ;
		
		{
			Vector3f color = ColorEnum.RED.getColor() ;
			Vector3f direction = new Vector3f(1,-1,1) ;
			float intensity = 0 ;
			DirectionalLight directionalLight = new DirectionalLight(color, direction, intensity) ;
			setDirectionalLight(directionalLight) ;
		}
	}

	private void agregarEsferas() {
		for (int x = 0; x < 80; x+=20) {
			for (int z = 0; z < 80; z+=20) {
				{
					EntitySpec entitySpec = new EntitySpec("esfera") ;
					entitySpec.setTexture(ColorEnum.MAROON.getName());
					entitySpec.setPosition(new Vector3f(x, terrain.getHeightOfTerrain(x, z), z));
					entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
		//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
					createEntity(entitySpec);
				}
			}
		}
		
	}

	private void loadTerrain() {
		Loader loader = SingletonManager.getInstance().getLoader() ;

		// Terrain
		TerrainTexture backgroundTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("grassy")) ;
		// TerrainTexture backgroundTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("socuwan")) ;
		TerrainTexture rTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("dirt")) ;
		TerrainTexture gTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("pinkFlowers")) ;
		TerrainTexture bTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("path")) ;

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture) ;
		TerrainTexture blendMap = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("blendMap")) ;

		// (0, 0) arranca en (0, 0) y crece hacia los positivos
		// (x, z) arranca en (x * size, z * size) y crece hacia los positivos
		terrain = new Terrain(0, 0 /* -1*/, loader, texturePack, blendMap, "heightmap", 100, 10) ;
		getTerrainList().add( terrain );
		getAabbManager().addTerrain(terrain);
		
	}
	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
		switch (state) {
		case AMBIENT_LIGHT_DEMO:
			ambientLightDemoController.tick(tpf);
			break;
		case DIRECTIONAL_LIGHT_DEMO:
		case DIRECTIONAL_LIGHT_CASTING_SHADOWS_DEMO:
			directionalLightDemoController.tick(tpf);
			break;
		case POINT_LIGHT_DEMO:
			pointLightDemoController.tick(tpf, entityController);
			break ;
		case SPOT_LIGHT_DEMO:
			spotLightDemoController.tick(tpf);
			break ;
		default:
			break;
		}
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(20, 10, 20),
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
		// getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
		getMainGameLoop().setNextGameState(PresentationMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

	private void setupAmbientLightDemo() {
		state = DemoState.AMBIENT_LIGHT_DEMO ;
	}

	private void setupDirectionalLightDemo() {
		state = DemoState.DIRECTIONAL_LIGHT_DEMO ;
		getDirectionalLight().setIntensity(0.5f);
		getDirectionalLight().setCastShadows(false);
	}

	private void setupDirectionalLightCastingShadowsDemo() {
		state = DemoState.DIRECTIONAL_LIGHT_CASTING_SHADOWS_DEMO ;
		getDirectionalLight().setIntensity(0.5f);
		getDirectionalLight().setCastShadows(true);
	}
	
	private void setupPointLightDemo() {
		state = DemoState.POINT_LIGHT_DEMO ;
		getPointLightList().get(0).setIntensity(.5f);
	}

	private void setupSpotLightDemo() {
		state = DemoState.SPOT_LIGHT_DEMO ;
		getSpotLightList().get(0).getPointLight().setIntensity(.5f);
	}

	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		switch (keyOrButton) {
		case Keyboard.KEY_1:
			setupAmbientLightDemo() ;
			break;
		case Keyboard.KEY_2:
			setupDirectionalLightDemo() ;
			break;
		case Keyboard.KEY_3:
			setupDirectionalLightCastingShadowsDemo() ;
			break;
		case Keyboard.KEY_4:
			setupPointLightDemo() ;
			break;
		case Keyboard.KEY_5:
			setupSpotLightDemo() ;
			break;
		default:
			break;
		}
		return false; 
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		if (origin == EventOriginEnum.KEYBOARD) {
			return true ;
		}
		return false;
	}

	@Override
	public void tick() {
		
	}
	
}
