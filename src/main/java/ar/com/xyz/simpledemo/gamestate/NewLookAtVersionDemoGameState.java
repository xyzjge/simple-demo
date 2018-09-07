package ar.com.xyz.simpledemo.gamestate;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.client.entitycontroller.DummyEntityController;
import ar.com.xyz.gameengine.client.entitycontroller.YawPitchRollEntityController;
import ar.com.xyz.gameengine.collision.Triangle;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.ray.RayTracerVO;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

public class NewLookAtVersionDemoGameState extends AbstractGameState implements CrushHandler, InputEventListener {
	
	private static final String LEVEL = "s-box" ;
	
	private static final boolean DEBUG_ROTATION = false ;
	
	private static final boolean DEBUG_PF_MARK_ENABLED = false ;
	private static final String DEBUG_PF_MARK = "DEBUG_PF_MARK" ;

	private static final boolean DEBUG_RAY_ENABLED = false ;
	private static final String DEBUG_RAY = "DEBUG_RAY" ;
	
	private static final boolean DEBUG_TRIANGLE_ENABLED = true ;
	private static final String DEBUG_TRIANGLE = "DEBUG_TRIANGLE" ;
	
	private List<EntityController> entityControllerList = new ArrayList<EntityController>() ;
	
	public NewLookAtVersionDemoGameState() {
		loadPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for X+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("x-box-texture");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
//			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(20,1,0));
			createEntity(entitySpec);
		}

		{	// Create SOLID_STATIC for X-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-x-box-texture");
			entitySpec.setPosition(new Vector3f(-20,1,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for Y+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("y-box-texture");
			entitySpec.setPosition(new Vector3f(0,20,0));
			createEntity(entitySpec);
		}
		{	// Create SOLID_STATIC for Y-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-y-box-texture");
			entitySpec.setPosition(new Vector3f(0,-20,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for Z+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("z-box-texture");
			entitySpec.setPosition(new Vector3f(0,1,20));
			createEntity(entitySpec);
		}
		{	// Create SOLID_STATIC for Z-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-z-box-texture");
			entitySpec.setPosition(new Vector3f(0,1,-20));
			createEntity(entitySpec);
		}
		
		{	// Esfera en 000
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("texturaEsfera");
			entitySpec.setScale(new Vector3f(2,2,2));
			entitySpec.setPosition(new Vector3f(0,0,0));
			createEntity(entitySpec);
		}
		
		{	// Esfera en 000
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("texturaEsfera");
			entitySpec.setScale(new Vector3f(2,2,2));
			entitySpec.setPosition(new Vector3f(8,2,0));
			createEntity(entitySpec);
		}
		
		{	// Esfera en 000
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("texturaEsfera");
			entitySpec.setScale(new Vector3f(2,2,2));
			entitySpec.setPosition(new Vector3f(16,4,0));
			createEntity(entitySpec);
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
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
			addInputEventListener(this);
		}
	}
	
	@Override
	public void tick(float tpf) {
		if (getPlayer().getPosition().y < -10) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
		if (getHandlePlayerInput().testAndClearFire()) {
			SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).hide();
			SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).hide();
			
			Vector3f rayOrigin ;
			if (getPlayer().isCrouch()) {
				rayOrigin = new Vector3f(
					getPlayer().getPosition().x, 
					getPlayer().getPosition().y + (getPlayer().getESpaceUtil().getRadius().y * 2), 
					getPlayer().getPosition().z
				) ;
			} else {
				rayOrigin = new Vector3f(
					getPlayer().getPosition().x, 
					getPlayer().getPosition().y + (getPlayer().getESpaceUtil().getRadius().y * 2), 
					getPlayer().getPosition().z
				) ;
			}

			float rotY = getPlayer().getRotation().y ;
			Vector3f rayDirection = new Vector3f((float)Math.sin(Math.toRadians(rotY)), (float) - Math.sin(Math.toRadians(getCamera().getPitch())) , (float)Math.cos(Math.toRadians(rotY))) ;

			if (DEBUG_RAY_ENABLED) {
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_RAY).activate();
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_RAY).setAbstractGameState(this);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_RAY).addMensaje(rayOrigin.x, rayOrigin.y, rayOrigin.z, ColorEnum.BLACK.getName() , true);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_RAY).addMensaje(rayDirection.x, rayDirection.y, rayDirection.z, ColorEnum.GRAY.getName(), true);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_RAY).addMensaje(rayOrigin.x + rayDirection.x, rayOrigin.y + rayDirection.y, rayOrigin.z + rayDirection.z, ColorEnum.GREEN.getName(), true);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_RAY).deactivateAndShowMessages();
			}
			
			// TODO: Pq sera necesario el ignore ??? En teoria los triangulos no estan enfrentados al rayo ...
			// RayTracerVO rayTracerVO = getMainGameLoop().getRayTracer().getTriangle(rayOrigin, rayDirection, 1/* 20*/, getAabbManager()) ;
			RayTracerVO rayTracerVO = getMainGameLoop().getRayTracer().getTriangle(rayOrigin, rayDirection, 5/* 20*/, getAabbManager(), getPlayer()) ;
			if (rayTracerVO != null) {
				Triangle triangle = rayTracerVO.getTriangle() ;
				
				if (DEBUG_TRIANGLE_ENABLED) {
					SingletonManager.getInstance().getGraphicDebugger(DEBUG_TRIANGLE).activate();
					SingletonManager.getInstance().getGraphicDebugger(DEBUG_TRIANGLE).setAbstractGameState(this);
					SingletonManager.getInstance().getGraphicDebugger(DEBUG_TRIANGLE).addMensaje(triangle.getP1().x, triangle.getP1().y, triangle.getP1().z, ColorEnum.GREEN.getName() , true);
					SingletonManager.getInstance().getGraphicDebugger(DEBUG_TRIANGLE).addMensaje(triangle.getP2().x, triangle.getP2().y, triangle.getP2().z, ColorEnum.GREEN.getName(), true);
					SingletonManager.getInstance().getGraphicDebugger(DEBUG_TRIANGLE).addMensaje(triangle.getP3().x, triangle.getP3().y, triangle.getP3().z, ColorEnum.GREEN.getName(), true);
					SingletonManager.getInstance().getGraphicDebugger(DEBUG_TRIANGLE).deactivateAndShowMessages();
				}
				
				addNotification("shoot " + triangle.toShortString());
				addMark(rayTracerVO);
			}
		}
		
//		SingletonManager.getInstance().getEntityUtil().lookAt3d(movimientoCircularEntityController.getEntity(), lookAtEntityController.getEntity().getPosition()) ;
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(5, 5, 5),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null
		) ;
		
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
		getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

	private void addMark(RayTracerVO rayTracerVO) {

			Triangle triangle = rayTracerVO.getTriangle() ;
			Vector3f normal = Vector3f.cross(Vector3f.sub(triangle.getP2(), triangle.getP1(), null), Vector3f.sub(triangle.getP3(), triangle.getP1(), null), null) ;
			normal.normalise() ;
			
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("s-box") ;
			entitySpec.setTexture("box-xyz-texture.png");
			
			entitySpec.setPosition(rayTracerVO.getContactPoint()) ;

			entitySpec.setScale(new Vector3f(.1f, .1f, .1f));
			
			
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);

			EntityController entityController ;
			if (DEBUG_ROTATION) {
				entityController = new YawPitchRollEntityController() ;
			} else {
				entityController = new DummyEntityController() ;
			}
			entitySpec.setEntityController(entityController);
			
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			
			createEntity(entitySpec);
			entityControllerList.add(entityController) ;
			
			Vector3f lookAt = Vector3f.add(rayTracerVO.getContactPoint(), normal, null) ;
			
			if (DEBUG_PF_MARK_ENABLED) {
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_PF_MARK).activate();
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_PF_MARK).setAbstractGameState(this);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_PF_MARK).addMensaje(lookAt.x, lookAt.y, lookAt.z, ColorEnum.BLACK.getName() /* "green"*/, true);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_PF_MARK).addMensaje(rayTracerVO.getContactPoint().x, rayTracerVO.getContactPoint().y, rayTracerVO.getContactPoint().z, ColorEnum.GRAY.getName() /* "red" */, true);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_PF_MARK).addMensaje(rayTracerVO.getContactPoint().x, rayTracerVO.getContactPoint().y + 1, rayTracerVO.getContactPoint().z, ColorEnum.GREEN.getName() /* "red" */, true);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_PF_MARK).addMensaje(rayTracerVO.getContactPoint().x, rayTracerVO.getContactPoint().y, rayTracerVO.getContactPoint().z + 1, ColorEnum.BLUE.getName() /* "red" */, true);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_PF_MARK).addMensaje(rayTracerVO.getContactPoint().x + 1, rayTracerVO.getContactPoint().y, rayTracerVO.getContactPoint().z, ColorEnum.RED.getName() /* "red" */, true);
				SingletonManager.getInstance().getGraphicDebugger(DEBUG_PF_MARK).deactivateAndShowMessages();
			}
			/*
			// TODO: lookAt 3d ... en principio es bastante complicado ...
			SingletonManager.getInstance().getEntityUtil().lookAt(entityController.getEntity(), lookAt);
			*/
			
			Vector3f xxx = SingletonManager.getInstance().getEntityUtil().lookAt3d(entityController.getEntity(), lookAt);
			
			if (DEBUG_ROTATION) {
				/* Para ver como giraban en el look at */
				((YawPitchRollEntityController)entityController).setPitch(360 - xxx.x);
				((YawPitchRollEntityController)entityController).setYaw(xxx.y);
			// entityController.setRoll(70); Asi en este caso queda bastante bien ... capaz q es pq casi coincide con xxx.x ...
				((YawPitchRollEntityController)entityController).setRoll(360);
				
				/* Para ver para que lado giraban ...
				((YawPitchRollEntityController)entityController).setPitch(360);
				((YawPitchRollEntityController)entityController).setYaw(360);
				((YawPitchRollEntityController)entityController).setRoll(360);
				*/
				/*
				((YawPitchRollEntityController)entityController).setPitch(0);
				((YawPitchRollEntityController)entityController).setYaw(0);
				((YawPitchRollEntityController)entityController).setRoll(0);
				*/
			} else {
//				entityController.getEntity().getRotation().x = 360 - xxx.x ;
//				entityController.getEntity().getRotation().y = xxx.y ;
				entityController.getEntity().setRotation(360 - xxx.x, xxx.y, entityController.getEntity().getRotation().z);
			}

	}

	@Override
	public void handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton) {
		switch (keyOrButton) {
		case Keyboard.KEY_1:
			SingletonManager.getInstance().getGraphicDebugger(DEBUG_TRIANGLE).hide();
			break;
		case Keyboard.KEY_2:
			for (EntityController entityController : entityControllerList) {
				scheduleEntityForRemoval(entityController.getEntity());
			}
			break;
		default:
			break;
		}
		// return false;
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton) {
		if (origin == EventOriginEnum.KEYBOARD) {
			return true ;
		}
		return false;
	}
	
}
