package ar.com.xyz.simpledemo.presentation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.cameracontroller.DefaultCameraController;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.InputHandler;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.SimpleDemoMenuGameState;

/**
 * @author alfredo
 *
 */
public class ObjMultipleTextureGameState extends AbstractGameState implements CrushHandler, InputHandler {
	
//	private RotationEntityController rotationEntityController = new RotationEntityController(0, 20, 0) ;
	
	public ObjMultipleTextureGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		
		loadPlayerAndCamera() ;
		
		{
			SingletonManager.getInstance().getObjWithMaterialFileLoader().addObjPath("/models/presentation") ;
			SingletonManager.getInstance().getObjWithMaterialFileLoader().addMtlPath("/models/presentation") ;
			SingletonManager.getInstance().getTextureManager().addTexturePath("/home/alfredo/tiles-jpg/");
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("e1m1-full") ;
//			entitySpec.setTexture("lapida-draven");
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setModelRotation(new Vector3f(90, 0, 0)) ;
//			entitySpec.setEntityController(rotationEntityController);
			createEntity(entitySpec);
		}
		
		createInputHandler(
			mainGameLoop, getPlayer(), getCamera(), this, null, null
		) ;
		
		grabMouseIfNotGrabbed() ;
		
//		setShowFps(true);
//		setShowPlayerPosition(true);
		
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).setAbstractGameState(this);
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).setAbstractGameState(this);
		
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		getHandlePlayerInput().clearEvents();
		getHandlePlayerInput().clearMouseEvents();
	}

	
	@Override
	public void tick(float tpf) {

//		System.out.println(rotationEntityController.getEntity().getPosition());
		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
		// ((DefaultCameraController)getCamera().getCameraController()).decPitch(-20);
		getPlayer().moveForward();
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(-40, 20, 60),
			new Vector3f(0, 90, 0), // new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setRunSpeed(5);
		getPlayer().setCrushHandler(this);
		
//		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));
		
//		Vector3f lookAt = SingletonManager.getInstance().getEntityUtil().lookAt3d(getCamera().getCameraController().getPosition(), new Vector3f(0, 0, 0));
		
//		System.out.println("lookAt: " + lookAt);
		
//		getCamera().getCameraController().getRotation().x = -lookAt.x ;
//		getCamera().getCameraController().getRotation().y = lookAt.y ;
//		getCamera().getCameraController().getRotation().z = lookAt.z ;

		((DefaultCameraController)getCamera().getCameraController()).decPitch(-20);
		
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
		getMainGameLoop().setNextGameState(new SimpleDemoMenuGameState(getMainGameLoop(), "ZIPCLOSE.wav", "stone.png")) ;
	}
	
	@Override
	public boolean handleInput(int eventKey) {
		switch (eventKey) {
		case Keyboard.KEY_G: {
		}
		break;
		case Keyboard.KEY_1:
		default:
			break;
		}
		return false;
	}
	
}