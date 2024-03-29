package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainCharacterGameState;
import ar.com.xyz.gameengine.client.entitycontroller.CircularMotionEntityController;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEvent;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.controller.LookAtEntityController;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

public class LookAtDemoGameState extends AbstractMainCharacterGameState implements CrushHandler, InputEventListener {
	
	private static final String LEVEL = "s-box" ;
	
	private EntityController movimientoCircularEntityController = new CircularMotionEntityController(6, 1, 1) ;
	
	private EntityController lookAtEntityController = null ;
	
	public LookAtDemoGameState() {
		setupPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("x-box-texture");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
//			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(20,1,0));
			createEntity(entitySpec);
		}

		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-x-box-texture");
			entitySpec.setPosition(new Vector3f(-20,1,0));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("y-box-texture");
			entitySpec.setPosition(new Vector3f(0,20,0));
			createEntity(entitySpec);
		}
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-y-box-texture");
			entitySpec.setPosition(new Vector3f(0,-20,0));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("z-box-texture");
			entitySpec.setPosition(new Vector3f(0,1,20));
			createEntity(entitySpec);
		}
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-z-box-texture");
			entitySpec.setPosition(new Vector3f(0,1,-20));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("arrow") ;
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(movimientoCircularEntityController);
			entitySpec.setTexture("yellow");
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setPosition(new Vector3f(0,8,0));
			entitySpec.setModelRotation(new Vector3f(90,90,0));
			createEntity(entitySpec);
		}

		lookAtEntityController = new LookAtEntityController(movimientoCircularEntityController.getEntity()) ; 
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("arrow") ;
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(lookAtEntityController);
			entitySpec.setTexture("yellow");
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setPosition(new Vector3f(0,1,0));
			entitySpec.setModelRotation(new Vector3f(90,90,0));
			createEntity(entitySpec);
		}
		
		createExampleAt(10, 0) ;
		createExampleAt(-10, 0) ;
		
		createExampleAt(0, 10) ;
		createExampleAt(0, -10) ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).setAbstractGameState(this);
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).setAbstractGameState(this);

	}

	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
	}
	
	@Override
	public void tick(float tpf) {
		if (getPlayer().getPosition().y < -10) {
			handlePlayerDeath() ;
		}
		
		SingletonManager.getInstance().getEntityUtil().stackOverflowLookAt3d(movimientoCircularEntityController.getEntity(), lookAtEntityController.getEntity().getPosition()) ;
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(0, 0, 0),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setCrushHandler(this);
		
//		entityUtil.lookAt(getPlayer(), new Vector3f(0, 0, 0));

//		this.enableDebug(getPlayer());
	}

	@Override
	public void crush() {
//		handlePlayerDeath();
		getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
	}
	
	private void createExampleAt(float x, float z) {
		EntityController movimientoCircularEntityController = new CircularMotionEntityController(6, 1, 1) ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("arrow") ;
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(movimientoCircularEntityController);
			entitySpec.setTexture("yellow");
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setPosition(new Vector3f(x,8,z));
			entitySpec.setModelRotation(new Vector3f(90,90,0));
			createEntity(entitySpec);
		}

		EntityController lookAtEntityController = new LookAtEntityController(movimientoCircularEntityController.getEntity()) ; 
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("arrow") ;
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(lookAtEntityController);
			entitySpec.setTexture("yellow");
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setPosition(new Vector3f(x,1,z));
			entitySpec.setModelRotation(new Vector3f(90,90,0));
			createEntity(entitySpec);
		}
	}

	@Override
	public boolean handleEvent(InputEvent inputEvent) {
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).hide();
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).hide();
		return false;
	}

	@Override
	public boolean accept(InputEvent inputEvent) {
		if (inputEvent.getOrigin() == EventOriginEnum.KEYBOARD && inputEvent.getType() == EventTypeEnum.RELEASED && inputEvent.getEventKeyOrButton() == Keyboard.KEY_X) {
			return true ;
		}
		return false;
	}

	@Override
	public void tickInputEventListener(float tpf) {
		// TODO Auto-generated method stub
		
	}

}
