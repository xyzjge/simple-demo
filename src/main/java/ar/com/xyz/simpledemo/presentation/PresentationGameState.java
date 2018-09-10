package ar.com.xyz.simpledemo.presentation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.cameracontroller.DefaultCameraController;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.light.PointLight;
import ar.com.xyz.gameengine.light.SpotLight;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

/**
 * @author alfredo
 *
 */
public class PresentationGameState extends AbstractGameState implements CrushHandler, InputEventListener {

	public static final float ROT_VEL = 200f ;
	public static final float LOC_VEL = 2.5f ;
	
	private PointLight pointLightUno ;
	private PointLight pointLightDos ;
	
	private SpotLight spotLight ;
	
	XEntityController xEntityController = new XEntityController() ;
	YEntityController yEntityController = new YEntityController(xEntityController) ;
	ZEntityController zEntityController = new ZEntityController(yEntityController) ;
	
	public PresentationGameState() {
		
		SingletonManager.getInstance().getObjWithMaterialFileLoader().addObjPath("/models/presentation") ;
		SingletonManager.getInstance().getObjWithMaterialFileLoader().addMtlPath("/models/presentation") ;
		SingletonManager.getInstance().getTextureManager().addTexturePath("/texture/presentation/");
		
		loadPlayerAndCamera() ;
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("flechas-xyz") ;
			entitySpec.setTexture("flechas-xyz-tex.png");
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
//			entitySpec.setScale(new Vector3f(15,15,15));
			// entitySpec.setPosition(new Vector3f(0,-50,0));
//			entitySpec.setPosition(new Vector3f(0,-30,0));
			createEntity(entitySpec);
		}
		
		{	// X
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("x") ;
			entitySpec.setTexture("red");
			entitySpec.setModelRotation(new Vector3f(0, 90, 0)) ;
			// entitySpec.setRotation(new Vector3f(0, 90, 0)) ;
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setPosition(new Vector3f(-10,2,-1));
			entitySpec.setEntityController(xEntityController);
			createEntity(entitySpec);
		}
		{	// Y
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("y") ;
			entitySpec.setTexture("green");
			entitySpec.setModelRotation(new Vector3f(0, 90, 0)) ;
			// entitySpec.setRotation(new Vector3f(0, 90, 0)) ;
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setPosition(new Vector3f(0,10,-1));
			entitySpec.setEntityController(yEntityController);
			createEntity(entitySpec);
		}
		
		{	// Z
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("z") ;
			entitySpec.setTexture("blue");
			entitySpec.setModelRotation(new Vector3f(0, 90, 0)) ;
			// entitySpec.setRotation(new Vector3f(0, 90, 0)) ;
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setPosition(new Vector3f(-2,2,-10));
			entitySpec.setEntityController(zEntityController);
			createEntity(entitySpec);
		}
/*
		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture(ColorEnum.RED.getName());
			entitySpec.setPosition(new Vector3f(-11, 1, 90));
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
//			entitySpec.setEntityController(xEntityController);
			createEntity(entitySpec);
		}
*/
		
		grabMouseIfNotGrabbed() ;
		
//		setShowFps(true);
//		setShowPlayerPosition(true);
		
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).setAbstractGameState(this);
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).setAbstractGameState(this);
		
		{
			Vector3f lightPosition = new Vector3f(-10, 2, 58);
			PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, 0 /*.1f *//* lightIntensity */);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.002f);
			pointLight.setAttenuation(att);
			Vector3f coneDir = new Vector3f(0, 0, -1);
			float cutoff = 10 ; //.195f ; // (float) Math.cos(Math.toRadians(.1f /* 100 *//*140*/ /*180*/ /* 240*/));
			spotLight = new SpotLight(pointLight, coneDir, cutoff);
			getSpotLightList().add(spotLight) ;
		}
		
		{
			Vector3f lightPosition = new Vector3f(-5, 0, 94);
			float lightIntensity = 0 /* .2f */;
			pointLightUno = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f,.1f/*.5f*/ /*1.0f*/);
			pointLightUno.setAttenuation(att);
			getPointLightList().add(pointLightUno) ;
		}
		{
			Vector3f lightPosition = new Vector3f(1, -1, 94);
			float lightIntensity = 0 ; // .4f;
			pointLightDos = new PointLight(new Vector3f(.5f, .5f, .5f), lightPosition, lightIntensity);
			PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f,.1f/*.5f*/ /*1.0f*/);
			pointLightDos.setAttenuation(att);
			getPointLightList().add(pointLightDos) ;
		}
		
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getHandlePlayerInput() == null) {
			createInputHandler(
				getMainGameLoop(), getPlayer(), getCamera(), this, null, null
			) ;
			addInputEventListener(this) ;
		}
	}
	
	private Vector3f origin = new Vector3f(0,0,0) ;
	private Vector3f cameraPositionMinusOrigin = new Vector3f(0,0,0) ;
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
		if (xEntityController.isDone()) {
			Vector3f cameraPosition = getCamera().getCameraController().getPosition() ;
			Vector3f.sub(cameraPosition, origin, cameraPositionMinusOrigin) ;
			if (cameraPositionMinusOrigin.length() > 9) {
				((DefaultCameraController)getCamera().getCameraController()).decDistanceFromPlayer(tpf * 2);
			}
		}
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(-6, 10, -21),
			new Vector3f(0, 0, 0), // new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setCrushHandler(this);
		
		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));
		
		Vector3f lookAt = SingletonManager.getInstance().getEntityUtil().lookAt3d(getCamera().getCameraController().getPosition(), new Vector3f(0, 0, 0));
		
		System.out.println("lookAt: " + lookAt);
		
		getCamera().getCameraController().getRotation().x = -lookAt.x ;
		getCamera().getCameraController().getRotation().y = lookAt.y ;
		getCamera().getCameraController().getRotation().z = lookAt.z ;

		this.enableDebug(getPlayer());
		
		getPlayer().setGravity(0);
		
		enableDebugKeys();
	}

	@Override
	public void crush() {
//		handlePlayerDeath();
		getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
	}
	
	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton) {
		switch (keyOrButton) {
		case Keyboard.KEY_G: {
		}
		break;
		case Keyboard.KEY_1:
		default:
			break;
		}
		return false;
		
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton) {
		if (origin == EventOriginEnum.KEYBOARD) {
			return true ;
		}
		return false;
	}
	
}
