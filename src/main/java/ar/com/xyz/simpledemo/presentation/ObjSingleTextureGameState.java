package ar.com.xyz.simpledemo.presentation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.InputHandler;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.VectorUtil;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

/**
 * @author alfredo
 *
 */
public class ObjSingleTextureGameState extends AbstractGameState implements CrushHandler, InputHandler {
	
	private RotationEntityController rotationEntityController = new RotationEntityController(0, 20, 0) ;
	
	public ObjSingleTextureGameState() {
		
		loadPlayerAndCamera() ;
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("lapida-draven") ;
			entitySpec.setTexture("lapida-draven");
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setModelRotation(new Vector3f(0, 90, 0)) ;
			entitySpec.setEntityController(rotationEntityController);
			createEntity(entitySpec);
		}
		
		grabMouseIfNotGrabbed() ;
		
//		setShowFps(true);
//		setShowPlayerPosition(true);
		
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).setAbstractGameState(this);
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).setAbstractGameState(this);
		
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getHandlePlayerInput() == null) {
			createInputHandler(
				getMainGameLoop(), getPlayer(), getCamera(), this, null, null
			) ;
		}
		getHandlePlayerInput().clearEvents();
		getHandlePlayerInput().clearMouseEvents();
	}
	
	Vector3f playerLastPosition = new Vector3f(0,0,0) ;
	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().equals(playerLastPosition)) {
			
		} else {
			agregarEsferaEn(getPlayer().getPosition()) ;
			VectorUtil.getInstance().copyValues(playerLastPosition, getPlayer().getPosition());
		}
		
		// System.out.println(rotationEntityController.getEntity().getPosition());
		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
	}

	private void agregarEsferaEn(Vector3f position) {
		{	// Create sphere to debug light position
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("red");
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(new Vector3f(.25f, .25f, .25f));
			entitySpec.setPosition(new Vector3f(position.x, position.y, position.z));
//			entitySpec.setEntityController(entityController);
			createEntity(entitySpec);
		}
		
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(0, -2, -5),
			new Vector3f(0, 0, 0), // new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			false /* true */,
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
