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
	private static final float VELOCITY_2 = 25;
	
	private CameraController cameraController ;
	
	private boolean automatic = false ;
	
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
		
		cameraController = getCamera().getCameraController() ;
		
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
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_0, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_9, this);
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

	/**
	 * aca moviendo tambien se ve que si no se hacen en orden no queda bien
	 * ej si primero hago el pitch el yaw no le da boliya ...
	 */
	@Override
	public boolean handleInput(int eventKey) {
		switch (eventKey) {
		case Keyboard.KEY_0:
			if (getCamera().getCameraController().equals(this)) {
				getCamera().setCameraController(cameraController);
			} else {
				getCamera().setCameraController(this);
			}
			break;
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
		case Keyboard.KEY_9:
			automatic = !automatic ;
//			if (automatic) {
				cameraPosition.y = 4 ;
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
//			}
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
	public void setPitch(float pitch) {
		cameraRotation.x = pitch ;
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
		if (automatic) {
			// El problema es que necesito que el ROLL quede para el final ... si no es un lio ubicar la camara ...
			// Lo ideal seria que funcione igual que las entidades ... primero YAW, despues PITCH y despues ROLL ...
			// Pareceria que ahi quedo ...
			if (cameraRotation.y < 10) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else if (cameraRotation.x < 10) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else if (cameraRotation.z < 150) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}
			// PYR: el YAW no tiene en cuenta el PITCH (-> el YAW tiene que ir antes que el PITCH)
			/* 
			if (cameraRotation.x < 15) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else if (cameraRotation.y < 360) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else if (cameraRotation.z < 15) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}
			*/
			// YPR: el PITCH tiene en cuenta el YAW (-> el YAW tiene que ir antes que el PITCH)
			// MMM aca el rol no esta andando bien me pa ...
			/* 
			if (cameraRotation.y < 30) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else if (cameraRotation.x < 30) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else if (cameraRotation.z < 150) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}*/
			// Este pareceria que funciona correctamente !!!
			/* if (cameraRotation.y < 30) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else if (cameraRotation.z < 30) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else if (cameraRotation.x < 360) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}*/
			// Este tambien
			/*
			if (cameraRotation.z < 30) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else if (cameraRotation.y < 30) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else if (cameraRotation.x < 360) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}*/
			// RYP: el PITCH tiene en cuenta el ROLL !!! (-> el PITCH tiene que ir despues del ROLL)
			/*
			if (cameraRotation.z < 30) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else if (cameraRotation.x < 360) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else if (cameraRotation.y < 15) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}
			*/
			// RYP: el YAW tiene en cuenta el ROLL !!! (-> el YAW tiene que ir despues del ROLL)
			/*
			if (cameraRotation.z < 30) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else if (cameraRotation.y < 360) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else if (cameraRotation.x < 15) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}
			*/
			/*if (cameraRotation.y < 15) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else if (cameraRotation.x < 15) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else if (cameraRotation.z < 15) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}*/
/*
			if (cameraRotation.z < 15) {
				cameraRotation.z += (fts * VELOCITY_2);
			} else if (cameraRotation.y < 15) {
				cameraRotation.y += (fts * VELOCITY_2);
			} else if (cameraRotation.x < 15) {
				cameraRotation.x += (fts * VELOCITY_2);
			} else {
				cameraRotation.x = 0 ;
				cameraRotation.y = 0 ;
				cameraRotation.z = 0 ;
			}*/
		}
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

	@Override
	public Vector3f getRotation() {
		return cameraRotation;
	}
	
}
