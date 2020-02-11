package ar.com.xyz.simpledemo.controller;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.collada.AlfreAnimationInfo;
import ar.com.xyz.gameengine.collision.EntityUtil;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.font.fontMeshCreator.FontType;
import ar.com.xyz.gameengine.font.fontMeshCreator.GUIText;
import ar.com.xyz.gameengine.particle.ParticleEmission;
import ar.com.xyz.gameengine.particle.ParticleSystem;
import ar.com.xyz.gameengine.particle.ParticleTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.AnimationEventHandler;
import ar.com.xyz.gameengine.util.VectorUtil;

public class BasicEnemyEntityController extends EntityController implements CrushHandler, AnimationEventHandler { 
	
	private static final float RUN_DIST = 60f ; // Si esta a mas de 60 que corra
	private static final float WALK_DIST = 3f ; // Si esta entre 60 y 10 que camine
//	private static final float WAIT_DIST = 2f ; // Si esta entre 2 y 10 que espere

	private static final float WALK_SPEED = 2f /* 5 */ /* 10 */ ;
	private static final float RUN_SPEED = 6f /* 10 *//* 20 */ ;
	
	private AlfreAnimationInfo runAnimationInfo = new AlfreAnimationInfo("run", 2) ;
	private AlfreAnimationInfo walkAnimationInfo = new AlfreAnimationInfo("walk", 1) ;
	private AlfreAnimationInfo waitAnimationInfo = new AlfreAnimationInfo("wait", .25f) ;
	private AlfreAnimationInfo attackAnimationInfo = new AlfreAnimationInfo("attack", 2f) ;
	
	private SweepSphereCollisionEntity player ;
	
	private float initialWaitPeriod = 3f ;
	private float waitForAttackPeriod = 3f ;
	
	private BasicEnemyStatesEnum state = BasicEnemyStatesEnum.WAIT;
	
	public BasicEnemyEntityController(SweepSphereCollisionEntity player) {
		this.player = player ;
		waitAnimationInfo.setDebug(true);
		attackAnimationInfo.setAnimationEventHandler(this);
	}

	private Vector3f distanciaVector = new Vector3f() ;
	
	private EntityUtil entityUtil = new EntityUtil () ;
	
	@Override
	public void update(float tpf) {
		
		entityUtil.lookAt(getEntity(), player.getPosition()) ;

		if (initialWaitPeriod > 0) {
			initialWaitPeriod -= tpf ;
			return ;
		}
		
		if (state == BasicEnemyStatesEnum.ATTACK) { // Vuelve a WAIT en el animationLoopEnd
			return ; 
		}

		Vector3f.sub(player.getPosition() , getEntity().getPosition(), distanciaVector) ;
		float distanciaSquared = distanciaVector.lengthSquared() ;
		
		if (state == BasicEnemyStatesEnum.WAIT) {
			if (distanciaSquared > RUN_DIST) {
				state = BasicEnemyStatesEnum.RUN ;
				((SweepSphereCollisionEntity)getEntity()).setRunSpeed(RUN_SPEED);
				getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(runAnimationInfo, true);
				((SweepSphereCollisionEntity)getEntity()).moveForward();
			} else if (distanciaSquared > WALK_DIST) {
				state = BasicEnemyStatesEnum.WALK ;
				((SweepSphereCollisionEntity)getEntity()).setRunSpeed(WALK_SPEED);
				getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(walkAnimationInfo, true);
				((SweepSphereCollisionEntity)getEntity()).moveForward();
			} else {
				waitForAttackPeriod -= tpf ;
				if (waitForAttackPeriod < 0) {
					state = BasicEnemyStatesEnum.ATTACK ;
					getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(attackAnimationInfo, true);
				}
			}
		}

		if (state == BasicEnemyStatesEnum.WALK) {
			if (distanciaSquared > RUN_DIST) {
				state = BasicEnemyStatesEnum.RUN ;
				((SweepSphereCollisionEntity)getEntity()).setRunSpeed(RUN_SPEED);
				getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(runAnimationInfo, true);
				((SweepSphereCollisionEntity)getEntity()).moveForward();
			} else if (distanciaSquared > WALK_DIST) {
//				((SweepSphereCollisionEntity)getEntity()).setRunSpeed(WALK_SPEED);
//				getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(walkAnimationInfo, true);
//				((SweepSphereCollisionEntity)getEntity()).moveForward();
			} else {
				waitForAttackPeriod = 3 ;
				state = BasicEnemyStatesEnum.WAIT ;
				((SweepSphereCollisionEntity)getEntity()).dontMove();
				getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(waitAnimationInfo, false);
			}
		}

		if (state == BasicEnemyStatesEnum.RUN) {
			if (distanciaSquared > RUN_DIST) {
//				((SweepSphereCollisionEntity)getEntity()).setRunSpeed(RUN_SPEED);
//				getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(runAnimationInfo, true);
//				((SweepSphereCollisionEntity)getEntity()).moveForward();
			} else if (distanciaSquared > WALK_DIST) {
				state = BasicEnemyStatesEnum.WALK ;
				((SweepSphereCollisionEntity)getEntity()).setRunSpeed(WALK_SPEED);
				getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(walkAnimationInfo, true);
				((SweepSphereCollisionEntity)getEntity()).moveForward();
			} else {
				waitForAttackPeriod = 3 ;
				state = BasicEnemyStatesEnum.WAIT ;
				((SweepSphereCollisionEntity)getEntity()).dontMove();
				getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(waitAnimationInfo, false);
			}
		}
		
	}

	@Override
	public void crush() {

		// Mostrar mensaje ?
		FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("harrington") ;
		GUIText fps = new GUIText("Enemy died", 3f, font, new Vector2f(0.64f, 0.0f), 1f, false, getGameState());
		fps.setColour(1, 0, 0);
		fps.show();
		
		getGameState().scheduleEntityForRemoval(getEntity());
		
		ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("particleAtlas"), 4, false) ;
		ParticleSystem particleSystem = new ParticleSystem(particleTexture, 15, 5, 0.3f, 1f, .2f) ;
		particleSystem.randomizeRotation();
		particleSystem.setDirection(new Vector3f(0,1,0), 0.1f);
//		particleSystem.setLifeError(0.1f);
//		particleSystem.setSpeedError(0.1f);
//		particleSystem.setScaleError(0.1f);
		
		Vector3f position = VectorUtil.getInstance().clone( getEntity().getPosition() );
		position.y += .5f ;
		
		ParticleEmission particleEmission = new ParticleEmission(particleSystem, position) ;
		particleEmission.setLiveFor(2.5f);
		getGameState().addParticleEmission(particleEmission);

	}
	
	@Override
	public void postConstruct() {
		getGameState().enableDebug(getEntity());
	}

	@Override
	public void animationLoopEnd(String anim) {
		if (anim.equals("attack")) {
			waitForAttackPeriod = 3 ;
			state = BasicEnemyStatesEnum.WAIT ;
			((SweepSphereCollisionEntity)getEntity()).dontMove();
			getEntity().getAnimatedModelDynamicData().setNextAnimationInfo(waitAnimationInfo, false);
		} else {
//			logger.info("") ;
		}
		
	}

	@Override
	public void animationProgress(String anim, float loopTime) {
		// TODO Auto-generated method stub
		
	}

}
