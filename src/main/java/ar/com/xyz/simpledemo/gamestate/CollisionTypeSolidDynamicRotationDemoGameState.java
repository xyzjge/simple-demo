package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.presentation.menuitem.EntitiesMenuMenuItem;

public class CollisionTypeSolidDynamicRotationDemoGameState extends AbstractGameState implements CrushHandler, InputEventListener {
	
	private static final String LEVEL = "s-box" ;
	
	public CollisionTypeSolidDynamicRotationDemoGameState() {
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
		
/*		{ // Caja de 222 centrada en el origen
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("s-box") ;
			entitySpec.setTexture("brickWall.jpg");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			RotationEntityController rotationEntityController = new RotationEntityController(0,100,0) ;
			entitySpec.setEntityController(rotationEntityController);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
			createEntity(entitySpec);
			this.enableDebug(rotationEntityController.getEntity());
		}*/
		
/*		{ // Caja 222 apoyada en el piso
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("s-box") ;
			entitySpec.setTexture("brickWall.jpg");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setPosition(new Vector3f(0, 1, 0)) ;
			RotationEntityController rotationEntityController = new RotationEntityController(0,100,0) ;
			entitySpec.setEntityController(rotationEntityController);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
			createEntity(entitySpec);
			this.enableDebug(rotationEntityController.getEntity());
		}*/
		
/*		{ // Caja 228 apoyada en el piso
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("s-box") ;
			entitySpec.setTexture("brickWall.jpg");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setPosition(new Vector3f(0, 1, 0)) ;
			entitySpec.setScale(new Vector3f(1, 1, 4));
			RotationEntityController rotationEntityController = new RotationEntityController(0,100,0) ;
			entitySpec.setEntityController(rotationEntityController);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
			createEntity(entitySpec);
			this.enableDebug(rotationEntityController.getEntity());
		}*/
		
/*		{ // Caja 228 apoyada en el piso yendo a las chapas
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("s-box") ;
			entitySpec.setTexture("brickWall.jpg");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setPosition(new Vector3f(0, 1, 0)) ;
			entitySpec.setScale(new Vector3f(1, 1, 4));
			RotationEntityController rotationEntityController = new RotationEntityController(0,1000,0) ;
			entitySpec.setEntityController(rotationEntityController);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
			createEntity(entitySpec);
			this.enableDebug(rotationEntityController.getEntity());
		}*/
		
	{ // Caja de 222 centrada en el origen
		EntitySpec entitySpec ;
		entitySpec = new EntitySpec("box") ;
		entitySpec.setTexture("brickWall.jpg");
		entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
		entitySpec.setPosition(new Vector3f(0, 1, 0)) ;
		RotationEntityController rotationEntityController = new RotationEntityController(0,100,0) ;
		entitySpec.setEntityController(rotationEntityController);
		entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
		createEntity(entitySpec);
		this.enableDebug(rotationEntityController.getEntity());
	}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).setAbstractGameState(this);

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
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(10, 10, 10),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setCrushHandler(this);
		
		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));

		this.enableDebug(getPlayer());
	}

	@Override
	public void crush() {
//		handlePlayerDeath();
		getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(EntitiesMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).hide();
		return false;
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		if (origin == EventOriginEnum.KEYBOARD && type == EventTypeEnum.RELEASED && keyOrButton == Keyboard.KEY_X) {
			return true ;
		}
		return false;
	}
	
	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

}
