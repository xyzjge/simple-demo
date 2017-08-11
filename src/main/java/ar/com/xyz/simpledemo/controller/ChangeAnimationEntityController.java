package ar.com.xyz.simpledemo.controller;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.collada.AlfreAnimationInfo;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.font.fontMeshCreator.FontType;
import ar.com.xyz.gameengine.font.fontMeshCreator.GUIText;
import ar.com.xyz.gameengine.particle.ParticleEmission;
import ar.com.xyz.gameengine.particle.ParticleSystem;
import ar.com.xyz.gameengine.particle.ParticleTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.AnimationEventHandler;

public class ChangeAnimationEntityController extends EntityController implements CrushHandler, AnimationEventHandler { 
	
	private AlfreAnimationInfo runAnimationInfo = new AlfreAnimationInfo("run", .25f) ;
	private AlfreAnimationInfo walkAnimationInfo = new AlfreAnimationInfo("walk", .25f) ;
	private AlfreAnimationInfo waitAnimationInfo = new AlfreAnimationInfo("wait", .25f) ;
	private AlfreAnimationInfo attackAnimationInfo = new AlfreAnimationInfo("attack", .25f) ;
	
	private String[] animations = {"run", "walk", "wait", "attack", "wait", "walk"} ;
	// private String[] animations = { "walk", "wait", "attack", "wait"} ;
	// private String[] animations = { "walk", "wait"} ;
//	private String[] animations = { "walk", "run"} ;
	
	private int currentAnimationIndex = 0 ;
	
	private AbstractGameState gameState ;
	
	public ChangeAnimationEntityController(AbstractGameState gameState) {
		this.gameState = gameState ;
		attackAnimationInfo.setDebug(true);
		waitAnimationInfo.setDebug(true);
		walkAnimationInfo.setDebug(true);
		runAnimationInfo.setDebug(true);
		runAnimationInfo.setAnimationEventHandler(this);
		walkAnimationInfo.setAnimationEventHandler(this);
		waitAnimationInfo.setAnimationEventHandler(this);
		attackAnimationInfo.setAnimationEventHandler(this);
	}

	
	@Override
	public void update(float tpf) {

	}

	@Override
	public void crush() {

		// Mostrar mensaje ?
		FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("harrington") ;
		GUIText fps = new GUIText("Enemy died", 3f, font, new Vector2f(0.64f, 0.0f), 1f, false, gameState);
		fps.setColour(1, 0, 0);
		fps.show();
		
		gameState.scheduleEntityForRemoval(getEntity());
		
		ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("particleAtlas"), 4, false) ;
		ParticleSystem particleSystem = new ParticleSystem(particleTexture, 15, 5, 0.3f, 1f, .2f) ;
		particleSystem.randomizeRotation();
		particleSystem.setDirection(new Vector3f(0,1,0), 0.1f);
		particleSystem.setLifeError(0.1f);
		particleSystem.setSpeedError(0.1f);
		particleSystem.setScaleError(0.1f);
		
		Vector3f position = getEntity().getPosition() ;
		position.y += .5f ;
		
		ParticleEmission particleEmission = new ParticleEmission(particleSystem, position) ;
		particleEmission.setLiveFor(2.5f);
		gameState.addParticleEmission(particleEmission);

	}
	
	@Override
	public void postConstruct() {
//		gameState.enableDebug(getEntity());
	}

	@Override
	public void animationLoopEnd(String anim) {
		// Cada vez que termina una animacion pasar a la siguiente ... dsps agregarle que loopee varias veces en algunas ...
		currentAnimationIndex++ ;
		currentAnimationIndex %= animations.length ;
		
		AlfreAnimationInfo animationInfo ;
		if (animations[currentAnimationIndex].equals("run")) {
			animationInfo = runAnimationInfo ;
		} else if (animations[currentAnimationIndex].equals("walk")) {
			animationInfo = walkAnimationInfo ;
		} else if (animations[currentAnimationIndex].equals("wait")) {
			animationInfo = waitAnimationInfo ;
		} else {
			animationInfo = attackAnimationInfo ;
		}
		getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(animationInfo, false);
		
	}

}
