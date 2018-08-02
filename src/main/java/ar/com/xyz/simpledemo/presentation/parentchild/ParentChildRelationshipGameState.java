package ar.com.xyz.simpledemo.presentation.parentchild;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.InputHandler;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.SimpleDemoMenuGameState;

/**
 * @author alfredo
 *
 */
public class ParentChildRelationshipGameState extends AbstractGameState implements CrushHandler, InputHandler {
	
	private List<EntityController> entityControllerList = new ArrayList<EntityController>() ;
	
	public ParentChildRelationshipGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		
		SingletonManager.getInstance().getObjWithMaterialFileLoader().addObjPath("/models/presentation") ;
		SingletonManager.getInstance().getObjWithMaterialFileLoader().addMtlPath("/models/presentation") ;
		SingletonManager.getInstance().getTextureManager().addTexturePath("/texture/presentation/");
		
		loadPlayerAndCamera() ;
		
		RotationEntityController parentEntityController = new RotationEntityController(0, 10, 0) ;
		{	// Parent
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("s-box") ;
			entitySpec.setTexture("red");
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(parentEntityController);
//			entitySpec.setScale(new Vector3f(2,2,2));
			createEntity(entitySpec);
		}
		
		for (int i = 0; i<4; i++) {
			createChildEntity(parentEntityController, i, -10, 10, -30, true, "green");
		}
		
		for (EntityController entityController : entityControllerList) {
			for (int i = 0; i<4; i++) {
				createChildEntity(entityController, i, -5, 5, 30, false, "blue");
			}
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

	private void createChildEntity(EntityController parentEntityController, int childNumber, float y, float xz, float yaw, boolean addEntityController, String texture) {
		EntitySpec entitySpec ;
		entitySpec = new EntitySpec("s-box") ;
		entitySpec.setTexture(texture);
		entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
		if (childNumber == 0) {
			entitySpec.setPosition(Vector3f.add(parentEntityController.getEntity().getPosition(), new Vector3f(-xz, y, 0), null)) ;
		} else if (childNumber == 1) {
			entitySpec.setPosition(Vector3f.add(parentEntityController.getEntity().getPosition(), new Vector3f(0, y, -xz), null)) ;
		} else if (childNumber == 2) {
			entitySpec.setPosition(Vector3f.add(parentEntityController.getEntity().getPosition(), new Vector3f(xz, y, 0), null)) ;
		} else if (childNumber == 3) {
			entitySpec.setPosition(Vector3f.add(parentEntityController.getEntity().getPosition(), new Vector3f(0, y, xz), null)) ;
		}
		RotationEntityController entityController = new RotationEntityController(0, yaw, 0) ;
		entitySpec.setEntityController(entityController);
		createEntity(entitySpec);
		entityController.getEntity().setParent(parentEntityController.getEntity());
		if (addEntityController) {
			entityControllerList.add(entityController) ;
		}
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		getHandlePlayerInput().clearEvents();
		getHandlePlayerInput().clearMouseEvents();
	}

	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
		Vector3f lookAt = SingletonManager.getInstance().getEntityUtil().lookAt3d(getCamera().getCameraController().getPosition(), new Vector3f(0, 0, 0));
		
		System.out.println("lookAt: " + lookAt);
		
		
		getCamera().getCameraController().getRotation().x = -lookAt.x ;
		getCamera().getCameraController().getRotation().y = lookAt.y ;
		getCamera().getCameraController().getRotation().z = lookAt.z ;

	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			// new Vector3f(-15, -(10 + (10 + 5)) /* -25 *//* -15 */ /* -10*/, -15),
			new Vector3f(0, -(10 + (10 + 5)) /* -25 *//* -15 */ /* -10*/, 0), // TODO: esto falla con NaN en XZ !!!
//			new Vector3f(0.1f, -(10 + (10 + 5)) /* -25 *//* -15 */ /* -10*/, 0.1f),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setCrushHandler(this);
		
//		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));
		
//		((DefaultCameraController)getCamera().getCameraController()).decDistanceFromPlayer(50);
		
		getCamera().setCameraController(new CustomCameraController());
		
//		this.enableDebug(getPlayer());
		
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
