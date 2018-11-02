package ar.com.xyz.simpledemo.presentation.cubemapreflection;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.client.entitycontroller.DummyEntityController;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.font.fontMeshCreator.FontType;
import ar.com.xyz.gameengine.font.fontMeshCreator.GUIText;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.skybox.SkyboxTexture;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

/**
 * @author alfredo
 *
 */
public class CubeMapReflectionDemoGameState extends AbstractGameState implements InputEventListener {
	
	private static final String[] ENVIRO_MAP_INSIDE = {"lposx", "lnegx", "lposy", "lnegy", "lposz", "lnegz"};

	private SkyboxTexture environmentImages = new SkyboxTexture("/textures/enviro/", ENVIRO_MAP_INSIDE) ;
	
	private EntityController entityController = new DummyEntityController() ;
	
	private static final String LEVEL = "s-box" ;
	
	private static final boolean BOX = true ;
	
	private CubeMapReflectionDemoModel model = new CubeMapReflectionDemoModel() ;
	
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
		
		setSkyboxTextureA(environmentImages);

		initTitle();
		
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
		
		if (model.getTitleActualSeconds() < model.getTitleDuration()) {
			float x = (model.getTitleDuration() - model.getTitleActualSeconds()) / model.getTitleDuration() ;
			title .setColour(1 * x, 1 * x, 1 * x);
			subtitle .setColour(1 * x, 1 * x, 1 * x);
			model.increaseTitleActualSeconds(tpf);
		} else {
			if (title != null) {
				title.remove();
				title = null ;
				subtitle.remove();
				subtitle = null ;
			}
			updateSubtitles() ;
		}
		

		if (model.isRotateEntity()) {
			entityController.getEntity().increaseRotation(0, tpf * 10, 0);
			
		}
		// Por ahora funciona solo para z < -5.5f (es una demo ...)
		if (model.getCaso() != null && model.getCaso().equals(CubeMapReflectionDemoCaseEnum.DYNAMIC_ADJUSTING_CAMERA_LOCATION)) {
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
	
	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		switch (keyOrButton) {
		case Keyboard.KEY_F1:
			model.setCaso(CubeMapReflectionDemoCaseEnum.STATIC);
			activateEnvironmentCubeMap();
			configureStatic(environmentImages) ;
			break;
		case Keyboard.KEY_F2:
			model.setCaso(CubeMapReflectionDemoCaseEnum.DYNAMIC);
			activateEnvironmentCubeMap();
			configureDynamic(new Vector3f(0,1,0));
			break;
		case Keyboard.KEY_F3:
			activateEnvironmentCubeMap();
			model.setCaso(CubeMapReflectionDemoCaseEnum.DYNAMIC_ADJUSTING_CAMERA_LOCATION);
			break;
		case Keyboard.KEY_F4:
			model.setRefractionEnabled(!model.isRefractionEnabled());
			if (model.isRefractionEnabled()) {
				entityController.getEntity().setCubeMapRefractionFactor(0.2f);
				entityController.getEntity().setCubeMapRefractionRatio(0.9f);
			} else {
				entityController.getEntity().setCubeMapRefractionFactor(0.0f);
			}
			break;
		case Keyboard.KEY_F5:
			model.setRotateEntity(!model.isRotateEntity());
			break;
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
	
	private FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("tahoma") ;
	private GUIText title ;
	private GUIText subtitle ;
	
	private void initTitle() {
		String text = "Environment cube map reflections and refractions" ;
		Vector2f posicion = new Vector2f(0, .05f ) ;
		title = new GUIText(text, 3f, font, posicion, 1f, true, this);
		title .setColour(1, 1, 1);
		title.show();
		
		String textSub = "(far from perfect but cheap effect)" ;
		Vector2f posicionSub = new Vector2f(0, .15f ) ;
		subtitle = new GUIText(textSub, 3f, font, posicionSub, 1f, true, this);
		subtitle .setColour(1, 1, 1);
		subtitle.show();
	}

	private GUIText rotateEntityGUIText ;
	private GUIText environmentCubeMapReflectionsGUIText ;
	private GUIText environmentCubeMapRefractionsGUIText ;
	private GUIText preGeneratedEnvironmentImagesGUIText ;
	private GUIText dynamicallyGeneratedEnvironmentImagesGUIText ;
	private GUIText dynamicallyGeneratedAdjunstingCameraEnvironmentImagesGUIText ;

	private void updateSubtitles() {
		{
			boolean enabled = model.isRotateEntity() ;
			if (rotateEntityGUIText != null) {
				rotateEntityGUIText.remove();
			}
			String text = "Rotate entity (F5): " + enabled;
			Vector2f posicion = new Vector2f(0, .05f) ;
			rotateEntityGUIText = new GUIText(text, 1.5f, font, posicion, 1f, false, this);
			rotateEntityGUIText .setColour(0, enabled ? 1 : .5f, 0);
			rotateEntityGUIText.show();
		}
		{
			boolean enabled = (model.getCaso() != null) ;
			if (environmentCubeMapReflectionsGUIText != null) {
				environmentCubeMapReflectionsGUIText.remove();
			}
			String text = "Environment cube map reflections: " + enabled;
			Vector2f posicion = new Vector2f(0, .10f ) ;
			environmentCubeMapReflectionsGUIText = new GUIText(text, 1.5f, font, posicion, 1f, false, this);
			environmentCubeMapReflectionsGUIText .setColour(0, enabled ? 1 : .5f, 0);
			environmentCubeMapReflectionsGUIText.show();
		}
		{
			boolean enabled = model.isRefractionEnabled();
			if (environmentCubeMapRefractionsGUIText != null) {
				environmentCubeMapRefractionsGUIText.remove();
			}
			String text = "Environment cube map refractions (F4): " + enabled;
			Vector2f posicion = new Vector2f(0, .15f ) ;
			environmentCubeMapRefractionsGUIText = new GUIText(text, 1.5f, font, posicion, 1f, false, this);
			environmentCubeMapRefractionsGUIText .setColour(0, enabled ? 1 : .5f, 0);
			environmentCubeMapRefractionsGUIText.show();
		}
		{
			boolean enabled = (model.getCaso() != null && model.getCaso().equals(CubeMapReflectionDemoCaseEnum.STATIC)) ;
			if (preGeneratedEnvironmentImagesGUIText != null) {
				preGeneratedEnvironmentImagesGUIText.remove();
			}
			String text = "Pre generated environment images (F1): " + enabled ;
			Vector2f posicion = new Vector2f(0, .20f ) ;
			preGeneratedEnvironmentImagesGUIText = new GUIText(text, 1.5f, font, posicion, 1f, false, this);
			preGeneratedEnvironmentImagesGUIText .setColour(0, enabled ? 1 : .5f, 0);
			preGeneratedEnvironmentImagesGUIText.show();
		}
		{
			boolean enabled = (model.getCaso() != null && model.getCaso().equals(CubeMapReflectionDemoCaseEnum.DYNAMIC));
			if (dynamicallyGeneratedEnvironmentImagesGUIText != null) {
				dynamicallyGeneratedEnvironmentImagesGUIText.remove();
			}
			String text = "Dinamically generated environment images (F2): " + enabled ;
			Vector2f posicion = new Vector2f(0, .25f ) ;
			dynamicallyGeneratedEnvironmentImagesGUIText = new GUIText(text, 1.5f, font, posicion, 1f, false, this);
			dynamicallyGeneratedEnvironmentImagesGUIText .setColour(0, enabled ? 1 : .5f, 0);
			dynamicallyGeneratedEnvironmentImagesGUIText.show();
		}
		{
			boolean enabled = (model.getCaso() != null && model.getCaso().equals(CubeMapReflectionDemoCaseEnum.DYNAMIC_ADJUSTING_CAMERA_LOCATION));
			if (dynamicallyGeneratedAdjunstingCameraEnvironmentImagesGUIText != null) {
				dynamicallyGeneratedAdjunstingCameraEnvironmentImagesGUIText.remove();
			}
			// String text = "F3 - Dinamically generated environment images (adjusting the camera location before rendering to the FBOs (only for Z < -5.5f)): " + 
			String text = "Dinamically generated environment images (adjusting camera location) (F3): " + enabled ;
			Vector2f posicion = new Vector2f(0, .30f ) ;
			dynamicallyGeneratedAdjunstingCameraEnvironmentImagesGUIText = new GUIText(text, 1.5f, font, posicion, 1f, false, this);
			dynamicallyGeneratedAdjunstingCameraEnvironmentImagesGUIText .setColour(0, enabled ? 1 : .5f, 0);
			dynamicallyGeneratedAdjunstingCameraEnvironmentImagesGUIText.show();
		}
	}
}
