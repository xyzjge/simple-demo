package ar.com.xyz.simpledemo.gamestate;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.collision.EntityUtil;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.AnimatedEntitySpec;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.LevelGameStateDefaultPlayerInputHandler;
import ar.com.xyz.simpledemo.controller.BasicEnemyEntityController;

public class CollisionTypeSweptSphereDemoGameState extends AbstractGameState implements CrushHandler {
	
	private static final String LEVEL = "s-box" ;
	
//	private static final String AXE_ZOMBIE_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-walk.dae" ;
//	private static final String AXE_ZOMBIE_WALK_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-walk.dae" ;
//	private static final String AXE_ZOMBIE_RUN_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-run.dae" ;
//	private static final String AXE_ZOMBIE_WAIT_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-wait.dae" ;
//	private static final String AXE_ZOMBIE_ATTACK_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-attack.dae" ;
	
	private static final String AXE_ZOMBIE_MODEL = "/models/axe-zombie-1-walk.dae" ;
	private static final String AXE_ZOMBIE_WALK_MODEL = "/models/axe-zombie-1-walk.dae" ;
	private static final String AXE_ZOMBIE_RUN_MODEL = "/models/axe-zombie-1-run.dae" ;
	private static final String AXE_ZOMBIE_WAIT_MODEL = "/models/axe-zombie-1-wait.dae" ;
	private static final String AXE_ZOMBIE_ATTACK_MODEL = "/models/axe-zombie-1-attack.dae" ;
	
	private LevelGameStateDefaultPlayerInputHandler levelGameStateDefaultPlayerInputHandler ;
	
	EntityUtil entityUtil = new EntityUtil () ;
	
	protected CollisionTypeSweptSphereDemoGameState(AbstractMainGameLoop mainGameLoop) {
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
		
		String axeZombieMoldel = AXE_ZOMBIE_MODEL ;
		Map<String, String> animationMap = new HashMap<String, String>() ;
		animationMap.put("walk", AXE_ZOMBIE_WALK_MODEL) ;
		animationMap.put("run", AXE_ZOMBIE_RUN_MODEL) ;
		animationMap.put("wait", AXE_ZOMBIE_WAIT_MODEL) ;
		animationMap.put("attack", AXE_ZOMBIE_ATTACK_MODEL) ;

		{
			BasicEnemyEntityController basicEnemyEntityController = new BasicEnemyEntityController(getPlayer(), this) ;
		
			AnimatedEntitySpec animatedEntitySpec = new AnimatedEntitySpec(axeZombieMoldel, animationMap, "wait", .5f) ;
			
			animatedEntitySpec.setTexture("axe-zombie-uv.png");
			animatedEntitySpec.setModelPosition(new Vector3f(0, 4.5f , 0));

			animatedEntitySpec.setScale(new Vector3f(.2f,.2f,.2f));
			animatedEntitySpec.setEntityController(basicEnemyEntityController);
		
//			animatedEntitySpec.seteSpace(new Vector3f(.4f, 1.35f, .4f));
//			animatedEntitySpec.seteSpaceCrouch(new Vector3f(.4f, .4f, .4f));
			
			animatedEntitySpec.seteSpace(new Vector3f(.5f, 1f, .5f)) ;
			animatedEntitySpec.seteSpaceCrouch(new Vector3f(.5f, .5f, .5f)) ;
		
			animatedEntitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SWEPT_SPHERE);
//			animatedEntitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
		
			animatedEntitySpec.setModelRotation(new Vector3f(270, 0, 0));
		
			animatedEntitySpec.setCrushHandler(basicEnemyEntityController);
			createAnimatedEntity(animatedEntitySpec) ;
		}
		
		levelGameStateDefaultPlayerInputHandler = new LevelGameStateDefaultPlayerInputHandler(mainGameLoop, getPlayer(), getCamera(), this, null, null) ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		SingletonManager.getInstance().getGraphicDebugger(SingletonManager.DEBUG_ROT_Y).setAbstractGameState(this);
		SingletonManager.getInstance().getGraphicDebugger(SingletonManager.DEBUG_SS).setAbstractGameState(this);

	}

	float secondsSubtitles = 100 ;
	
	@Override
	public void tick(float tpf) {
		if (getPlayer().getPosition().y < -10) {
			handlePlayerDeath() ;
		}
		
		levelGameStateDefaultPlayerInputHandler.handlePlayerInput();
		
		if (levelGameStateDefaultPlayerInputHandler.testAndClearFire()) {
			SingletonManager.getInstance().getGraphicDebugger(SingletonManager.DEBUG_ROT_Y).hide();
			SingletonManager.getInstance().getGraphicDebugger(SingletonManager.DEBUG_SS).hide();
		}
		
		secondsSubtitles += tpf ;
		
		if (secondsSubtitles > 10) {
			addNotification("Please enable subtitles !!!");
			secondsSubtitles = 0 ;
		}
		
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(10, 10, 10),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setCrushHandler(this);
		
		entityUtil.lookAt(getPlayer(), new Vector3f(0, 0, 0));

		this.enableDebug(getPlayer());
	}

	@Override
	public void crush() {
//		handlePlayerDeath();
		getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		mainGameLoop.setNextGameState(new SimpleDemoMenuGameState(mainGameLoop, "ZIPCLOSE.wav", "stone.png")) ;
	}

}
