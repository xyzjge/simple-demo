package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.debug.ResaltarXYZ;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.light.DirectionalLight;
import ar.com.xyz.gameengine.light.PointLight;
import ar.com.xyz.gameengine.light.SpotLight;
import ar.com.xyz.gameengine.reflectivequad.ReflectiveQuadTile;
import ar.com.xyz.simpledemo.lightandshadow.menuitem.LightsAndShadowsMenuMenuItem;

/**
 * @author alfredo
 *
 */
public class NormalMapDemoGameState extends AbstractGameState implements CrushHandler, InputEventListener {
	
	private ResaltarXYZ resaltarXYZ ;
	
	public NormalMapDemoGameState() {
		setupPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the box
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box-2x2-000-in-normals") ;
			// entitySpec.setTexture("stone.png");
			entitySpec.setTexture("grid-256x256.png");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(10,10,10));
			// entitySpec.setPosition(new Vector3f(0,-50,0));
			entitySpec.setPosition(new Vector3f(0,0,0));
			createEntity(entitySpec);
		}
		
/*		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture(ColorEnum.MAROON.getName());
			entitySpec.setPosition(new Vector3f(0, 1, 0));
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
//			entitySpec.setEntityController(xEntityController);
			createEntity(entitySpec);
		}*/
		
		{
			EntitySpec entitySpec = new EntitySpec("barrel") ;
			entitySpec.setTexture("barrel");
			entitySpec.setPosition(new Vector3f(0, 1, -3));
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(new Vector3f(.5f,.5f,.5f));
			entitySpec.setEntityController(new RotationEntityController(0, 10, 0));
//			entitySpec.setEntityController(xEntityController);
			entitySpec.setNormalMap("barrelNormal");
			createEntity(entitySpec);
		}

		{
			EntitySpec entitySpec = new EntitySpec("barrel2") ;
			entitySpec.setTexture("barrel2");
			entitySpec.setPosition(new Vector3f(0, 1, 3));
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(new Vector3f(.5f,.5f,.5f));
			entitySpec.setEntityController(new RotationEntityController(0, 10, 0));
//			entitySpec.setEntityController(xEntityController);
			// entitySpec.setNormalMap("barrelNormal");
			createEntity(entitySpec);
		}

		// DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.0f,.75f,.75f), new Vector3f(-10000,-10000,-10000), .75f) ;
		// DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.75f,.0f,.0f), new Vector3f(-10000,-10000,-10000), .75f) ;
		// DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.75f,.0f,.0f), new Vector3f(-10,-10000,-10), .75f) ;
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.75f,.0f,.0f), new Vector3f(-10000,0,0), .75f) ;
		directionalLight.setCastShadows(false);
		setDirectionalLight(directionalLight);
		
		{
			Vector3f lightPosition = new Vector3f(0, 1f, 0);
			float lightIntensity = 0 /* .2f */;
			PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f,.1f/*.5f*/ /*1.0f*/);
			pointLight.setAttenuation(att);
			getPointLightList().add(pointLight) ;
		}
		
		{
			Vector3f lightPosition = new Vector3f(8, 2, 0);
			PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, 0 /*.1f *//* lightIntensity */);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.002f);
			pointLight.setAttenuation(att);
			Vector3f coneDir = new Vector3f(-1, 0, 0);
			float cutoff = 10 ; //.195f ; // (float) Math.cos(Math.toRadians(.1f /* 100 *//*140*/ /*180*/ /* 240*/));
			SpotLight spotLight = new SpotLight(pointLight, coneDir, cutoff);
			getSpotLightList().add(spotLight) ;
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		resaltarXYZ = new ResaltarXYZ(this) ;
		
		// addInputEventListener(this) ;

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
		
		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		resaltarXYZ.restaltar(getPlayer().getPosition(), 3);
		
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(0, 2, 0),
			new Vector3f(0, -30, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;
		
		getPlayer().setCrushHandler(this);

		this.enableDebug(getPlayer());
	}

	@Override
	public void crush() {
//		handlePlayerDeath();
		getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(LightsAndShadowsMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		
		switch (keyOrButton) {
		case Keyboard.KEY_F1:
			getDirectionalLight().setIntensity(0f);
			getPointLightList().get(0).setIntensity(0f);
			getSpotLightList().get(0).getPointLight().setIntensity(0f);
			break;
		case Keyboard.KEY_F2:
			getDirectionalLight().setIntensity(0.75f);
			break;
		case Keyboard.KEY_F3:
			getPointLightList().get(0).setIntensity(0.5f);
			break;
		case Keyboard.KEY_F4:
			getSpotLightList().get(0).getPointLight().setIntensity(0.5f);
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
	
}
