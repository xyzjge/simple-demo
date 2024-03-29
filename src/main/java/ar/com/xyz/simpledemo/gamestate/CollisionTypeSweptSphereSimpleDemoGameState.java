package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainCharacterGameState;
import ar.com.xyz.gameengine.collision.Triangle;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.debug.collision.DetailedCollisionDataPlayer;
import ar.com.xyz.gameengine.debug.collision.Player;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEvent;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.ray.RayTracerVO;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

/**
 * Para debugear colisiones con una parte de un nivel ...
 * @author alfredo
 *
 */
public class CollisionTypeSweptSphereSimpleDemoGameState extends AbstractMainCharacterGameState implements CrushHandler, InputEventListener {
	
	private static final String LEVEL = "s-box" ;
	
	private static final boolean PLAY = false ;
	
	private Player collisionDataPlayer ;
	
	public CollisionTypeSweptSphereSimpleDemoGameState() {
		setupPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(15,15,15));
			// entitySpec.setPosition(new Vector3f(0,-50,0));
			entitySpec.setPosition(new Vector3f(0,-30,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for the mesh
			SingletonManager.getInstance().getTextureManager().addTexturePath("/home/alfredo/tiles-jpg/");
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("e1m1") ;
//			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(90, 0, 0)) ;
//			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,0,0));
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
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			addInputEventListener(this);
		}
		if (!PLAY) {
			SingletonManager.getInstance().getCollisionDataRecorder().setActive(true);
		} else {
			// collisionDataPlayer = new CollisionDataPlayer(this) ;
			collisionDataPlayer = new DetailedCollisionDataPlayer(this) ;
		}
	}

	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		if (PLAY) {
			collisionDataPlayer.tick(tpf);
//			return ;
		}
		
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(-5, 0, 94), // new Vector3f(-5, 10, 90), // new Vector3f(5, 10, 5), //new Vector3f(10, 10, 10),
			new Vector3f(0, -30, 0), // new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setCrushHandler(this);
		
//		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));

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

	@Override
	public boolean handleEvent(InputEvent inputEvent) {
		if (inputEvent.getEventKeyOrButton() == Keyboard.KEY_C && inputEvent.getType() == EventTypeEnum.RELEASED) {
			// La posicion del jugador es en el piso, con el espace y si esta agachado o no podría determinar el y
			// TODO: Si esta volviendo de crouch el rayo sale de mas arriba de la camara ... por ahora queda asi ...
			Vector3f rayOrigin = new Vector3f(
				getPlayer().getPosition().x, 
				getPlayer().getPosition().y + (getPlayer().getESpaceUtil().getRadius().y * 2) * getPlayer().getPorcentajeAltura(), 
				getPlayer().getPosition().z
			) ;

			// Tener en cuenta hacia donde esta mirando ...
			float rotY = getPlayer().getRotation().y ;
			Vector3f rayDirection = new Vector3f(
				(float)Math.sin(Math.toRadians(rotY)), 
				(float) - Math.sin(Math.toRadians(getCamera().getPitch())) ,
				(float)Math.cos(Math.toRadians(rotY))
			) ;

			float distance = 100 ;
			RayTracerVO rayTracerVO = getMainGameLoop().getRayTracer().getTriangle(rayOrigin, rayDirection, distance, this.getAabbManager()) ;
			if (rayTracerVO != null) {
				Triangle triangle = rayTracerVO.getTriangle() ;
				addNotification("touch " + triangle.toShortString());
				SingletonManager.getInstance().getLogUtil().logInfo("XYZBloodLevelGameState", "testAndClearEmpujar", "Triangulo: " + triangle.toString() + ", triangle.getRayHitHandler(): " + triangle.getRayHitHandler());

			}
		} else if (inputEvent.getEventKeyOrButton() == Keyboard.KEY_X && inputEvent.getType() == EventTypeEnum.PRESSED) {
			if (collisionDataPlayer != null) {
				collisionDataPlayer.print();
			}
		}
		return false;
	}

	@Override
	public boolean accept(InputEvent inputEvent) {
		return inputEvent.getOrigin() == EventOriginEnum.KEYBOARD ;
	}

	@Override
	public void tickInputEventListener(float tpf) {
		// TODO Auto-generated method stub
		
	}

}
