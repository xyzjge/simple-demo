package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.cameracontroller.CameraController;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.input.InputHandler;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class CameraControllerDemoGameState extends AbstractGameState implements CrushHandler, InputHandler, CameraController {
	
	private static final String LEVEL = "s-box" ;
	private static final float VELOCITY = 50;
	
	protected CameraControllerDemoGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
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
		
		createInputHandler(
			mainGameLoop, getPlayer(), getCamera(), this, null, null
		) ;
		
		getCamera().setCameraController(this);
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_1, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_2, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_3, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_4, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_5, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_6, this);
	}

	float secondsSubtitles = 100 ;
	
	@Override
	public void tick(float tpf) {
		if (getPlayer().getPosition().y < -10) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
		secondsSubtitles += tpf ;
		
		if (secondsSubtitles > 10) {
			addNotification("Please enable subtitles !!!");
			secondsSubtitles = 0 ;
		}
		
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
		getMainGameLoop().setNextGameState(new SimpleDemoMenuGameState(getMainGameLoop(), "ZIPCLOSE.wav", "stone.png")) ;
	}

	@Override
	public boolean handleInput(int eventKey) {
		switch (eventKey) {
		case Keyboard.KEY_1:
			yaw = true ;
			break;
		case Keyboard.KEY_2:
			minusYaw = true ;
			break;
		case Keyboard.KEY_3:
			pitch = true ;
			break;
		case Keyboard.KEY_4:
			minusPitch = true ;
			break;
		case Keyboard.KEY_5:
			roll = true ;
			break;
		case Keyboard.KEY_6:
			minusRoll = true ;
			break;
		default:
			break;
		}
		return false;
	}

	private Vector3f cameraPosition = new Vector3f(2,2,2) ;
	private Vector3f cameraRotation = new Vector3f(0,0,0) ;
	
	private boolean yaw = false, minusYaw = false, pitch = false, minusPitch = false, roll = false , minusRoll = false ;
	
	@Override
	public Vector3f getPosition() {
		return cameraPosition;
	}

	@Override
	public float getPitch() {
		return cameraRotation.x;
	}

	@Override
	public float getYaw() {
		return cameraRotation.y;
	}

	@Override
	public float getRoll() {
		return cameraRotation.z;
	}
	
	@Override
	public void update(float fts) {
		if (yaw) {
			cameraRotation.y += (fts * VELOCITY);
			yaw = false ;
		} 
		if (minusYaw) {
			cameraRotation.y -= (fts * VELOCITY) ;
			minusYaw = false ;
		}
		if (pitch) {
			cameraRotation.x += (fts * VELOCITY) ;
			pitch = false ;
		} 
		if (minusPitch) {
			cameraRotation.x -= (fts * VELOCITY) ;
			minusPitch = false ;
		}
		
		if (roll) {
			cameraRotation.z += (fts * VELOCITY) ;
			roll = false ;
		} 
		if (minusRoll) {
			cameraRotation.z -= (fts * VELOCITY) ;
			minusRoll = false ;
		}
	}

	@Override
	public void resetDefaults() {
		cameraPosition.x = 0;
		cameraPosition.y = 0;
		cameraPosition.z = 0;
		cameraRotation.x = 0;
		cameraRotation.y = 0;
		cameraRotation.z = 0;
	}
	
}
