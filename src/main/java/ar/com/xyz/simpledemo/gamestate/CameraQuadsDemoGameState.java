package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.cameracontroller.PositionAndRotationCameraController;
import ar.com.xyz.gameengine.cameraquad.CameraQuadTile;
import ar.com.xyz.gameengine.client.entitycontroller.DummyEntityController;
import ar.com.xyz.gameengine.debug.ResaltarXYZ;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.reflectivequad.ReflectiveQuadTile;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.lightandshadow.menuitem.LightsAndShadowsMenuMenuItem;

/**
 * @author alfredo
 *
 */
public class CameraQuadsDemoGameState extends AbstractGameState implements CrushHandler, InputEventListener {
	
//	private ResaltarXYZ resaltarXYZ ;
	
	public CameraQuadsDemoGameState() {
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
		
		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture(ColorEnum.MAROON.getName());
			entitySpec.setPosition(new Vector3f(0, -10, 0));
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
//			entitySpec.setEntityController(xEntityController);
			createEntity(entitySpec);
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
//		resaltarXYZ = new ResaltarXYZ(this) ;

	}

	private EntityController entityControllerRed = null ;
	private EntityController entityControllerGreen = null ;
	private EntityController entityControllerBlue = null ;
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			addInputEventListener(this) ;
			
			{
				entityControllerRed = new DummyEntityController() ;
				EntitySpec entitySpec ;
				entitySpec = new EntitySpec("esfera") ;
				entitySpec.setTexture("red");
				entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
				entitySpec.setEntityController(entityControllerRed);
				entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
				createEntity(entitySpec);
			}
			{
				entityControllerGreen = new DummyEntityController() ;
				EntitySpec entitySpec ;
				entitySpec = new EntitySpec("esfera") ;
				entitySpec.setTexture("green");
				entitySpec.setScale(new Vector3f(.25f, .25f, .25f));
				entitySpec.setEntityController(entityControllerGreen);
				entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
				createEntity(entitySpec);
			}
			{
				entityControllerBlue = new DummyEntityController() ;
				EntitySpec entitySpec ;
				entitySpec = new EntitySpec("esfera") ;
				entitySpec.setTexture("blue");
				entitySpec.setScale(new Vector3f(0, 0, 0));
				entitySpec.setEntityController(entityControllerBlue);
				entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
				createEntity(entitySpec);
			}
		}
		
		configure() ;
		
	}
	
	@Override
	public void tick(float tpf) {
		
		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		resaltarXYZ.restaltar(getPlayer().getPosition(), 3);
		
//		if (getReflectiveQuadTileList().isEmpty()) {
//			return ;
//		}
//		if (getReflectiveQuadTileList().contains(reflectiveQuadTileF7)) {
//			reflectiveQuadTileF7.getRotation().x+= tpf * 10 ;
//		} else if (getReflectiveQuadTileList().contains(reflectiveQuadTileF8)) {
//			reflectiveQuadTileF8.getRotation().y += tpf * 10;
//		}
		
		positionAndRotationCameraController.update(tpf);
		
		Vector3f cameraLookAt = SingletonManager.getInstance().getEntityUtil().directionFromPitchAndYaw(positionAndRotationCameraController.getRotation().x, positionAndRotationCameraController.getRotation().y) ;
		Vector3f posicionCamaraMasNormal = Vector3f.add(positionAndRotationCameraController.getPosition(), cameraLookAt, null) ;
		entityControllerGreen.getEntity().setPosition(posicionCamaraMasNormal);
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
			handleF1();
			break;
		case Keyboard.KEY_F2:
			positionAndRotationCameraController.setSwing(!positionAndRotationCameraController.isSwing()) ;
			break;
		case Keyboard.KEY_X:
			if (entityControllerRed.getEntity().getScale().x == 0) {
				entityControllerRed.getEntity().setScale(.5f, .5f, .5f);
				entityControllerGreen.getEntity().setScale(.25f, .25f, .25f);
				entityControllerBlue.getEntity().setScale(0,0,0);
			} else {
				entityControllerRed.getEntity().setScale(0,0,0);
				entityControllerGreen.getEntity().setScale(0,0,0);
				entityControllerBlue.getEntity().setScale(0,0,0);
			}
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
	
	private CameraQuadTile cameraQuadTileF1 ;
	
	PositionAndRotationCameraController positionAndRotationCameraController = new PositionAndRotationCameraController(new Vector3f(9f, 9f ,9f), new Vector3f(45,-45,0), -70, -20) ;
	
	private void configure() {

		//cameraQuadTileF1 = new CameraQuadTile(0, 0, -8f, new Vector2f(1.280f, 0.720f), new Vector2f(90, 0), /*0*//*.25f*/ /*.75f*/1, positionAndRotationCameraController) ;
		

		entityControllerRed.getEntity().setPosition(positionAndRotationCameraController.getPosition());
		
//		private EntityController entityControllerBlue = null ;
		cameraQuadTileF1 = new CameraQuadTile(0, -9, -7f, new Vector2f(3, 2), new Vector2f(90, 0), /*0*//*.25f*/ /*.75f*/1, positionAndRotationCameraController) ;
	}

	private void handleF1() {
		getCameraQuadTileList().clear() ;
		getCameraQuadTileList().add(cameraQuadTileF1) ;
	}
	
}
