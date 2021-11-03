package ar.com.xyz.simpledemo.gamestate;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainCharacterGameState;
import ar.com.xyz.gameengine.collada.AnimationInstance;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.AnimatedEntitySpec;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.controller.BasicEnemyEntityController;
import ar.com.xyz.simpledemo.presentation.menuitem.EntitiesMenuMenuItem;

public class CollisionTypeSweptSphereDemoGameState extends AbstractMainCharacterGameState implements CrushHandler, InputEventListener {
	
	private static final String LEVEL = "s-box" ;
	
//	private static final String AXE_ZOMBIE_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-walk.dae" ;
//	private static final String AXE_ZOMBIE_WALK_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-walk.dae" ;
//	private static final String AXE_ZOMBIE_RUN_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-run.dae" ;
//	private static final String AXE_ZOMBIE_WAIT_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-wait.dae" ;
//	private static final String AXE_ZOMBIE_ATTACK_MODEL = "/home/alfredo/Downloads/enemigos/axe-zombie-1-attack.dae" ;
	
	private static final String AXE_ZOMBIE_MODEL = "/models/axe-zombie-walk.dae" ;
	private static final String AXE_ZOMBIE_WALK_MODEL = "/models/axe-zombie-walk.dae" ;
	private static final String AXE_ZOMBIE_RUN_MODEL = "/models/axe-zombie-run.dae" ;
	private static final String AXE_ZOMBIE_WAIT_MODEL = "/models/axe-zombie-wait.dae" ;
	private static final String AXE_ZOMBIE_ATTACK_MODEL = "/models/axe-zombie-attack.dae" ;
	
	public CollisionTypeSweptSphereDemoGameState() {
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
		
		String axeZombieMoldel = AXE_ZOMBIE_MODEL ;
		Map<String, String> animationMap = new HashMap<String, String>() ;
		animationMap.put("walk", AXE_ZOMBIE_WALK_MODEL) ;
		animationMap.put("run", AXE_ZOMBIE_RUN_MODEL) ;
		animationMap.put("wait", AXE_ZOMBIE_WAIT_MODEL) ;
		animationMap.put("attack", AXE_ZOMBIE_ATTACK_MODEL) ;

		{
			BasicEnemyEntityController basicEnemyEntityController = new BasicEnemyEntityController(getPlayer()) ;
		
			AnimatedEntitySpec animatedEntitySpec = new AnimatedEntitySpec(axeZombieMoldel, animationMap, new AnimationInstance("wait", .5f)) ;
			
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
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).hide();
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
