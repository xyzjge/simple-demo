package ar.com.xyz.simpledemo.controller;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.collada.AlfreAnimationInfo;
import ar.com.xyz.gameengine.collision.EntityUtil;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.font.fontMeshCreator.FontType;
import ar.com.xyz.gameengine.font.fontMeshCreator.GUIText;
import ar.com.xyz.gameengine.particle.ParticleEmission;
import ar.com.xyz.gameengine.particle.ParticleSystem;
import ar.com.xyz.gameengine.particle.ParticleTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class BasicEnemyEntityController extends EntityController implements CrushHandler { 
	
	private static final float WALK_SPEED = 2f /* 5 */ /* 10 */ ;
	private static final float RUN_SPEED = 6f /* 10 *//* 20 */ ;
	
	private AlfreAnimationInfo runAnimationInfo = new AlfreAnimationInfo("run", 2) ;
	private AlfreAnimationInfo walkAnimationInfo = new AlfreAnimationInfo("walk", 1) ;
	private AlfreAnimationInfo waitAnimationInfo = new AlfreAnimationInfo("wait", .25f) ;
	private AlfreAnimationInfo attackAnimationInfo = new AlfreAnimationInfo("attack", 2f) ;
	
	private SweepSphereCollisionEntity player ;
	private AbstractGameState gameState ;
	
	private float waitPeriod = 7f ;
	private float waitForAttackPeriod = 3f ;
	private float attackTime = 1f ;
	
	private BasicEnemyStatesEnum state = BasicEnemyStatesEnum.WAIT;
	
	public BasicEnemyEntityController(SweepSphereCollisionEntity player, AbstractGameState gameState) {
		this.player = player ;
		this.gameState = gameState ;
		waitAnimationInfo.setDebug(true);
	}

	private Vector3f distanciaVector = new Vector3f() ;
	
	private EntityUtil entityUtil = new EntityUtil () ;
	
	@Override
	public void update(float tpf) {
		
		entityUtil.lookAt(entity, player.getPosition()) ;

		if (waitPeriod > 0) {
			waitPeriod -= tpf ;
			return ;
		}
		
		if (entity.getEntityCollisionType() == EntityCollisionTypeEnum.SWEPT_SPHERE) {
			Vector3f.sub(player.getPosition() , entity.getPosition(), distanciaVector) ;
			float distanciaSquared = distanciaVector.lengthSquared() ;
			if (distanciaSquared < 6) {
				if (state == BasicEnemyStatesEnum.ATTACK) {
					attackTime -= tpf ;
					if (attackTime > 0) {
						return ; 
					}
				}
				if (state == BasicEnemyStatesEnum.WAIT) {
					// Si pasaron mas de ... pasarlo a ATTACK
					waitForAttackPeriod-= tpf ;
					if (waitForAttackPeriod < 0) {
						attackTime = 1 ;
						state = BasicEnemyStatesEnum.ATTACK ;
						entity.getAnimatedModelDynamicData().setNextAnimationInfo(attackAnimationInfo, true);
					}
				} else {
					waitForAttackPeriod = 3 ;
					state = BasicEnemyStatesEnum.WAIT ;
					((SweepSphereCollisionEntity)entity).dontMove();
					entity.getAnimatedModelDynamicData().setNextAnimationInfo(waitAnimationInfo, true);
				}
			} else {
				if (distanciaSquared > 60) {
					((SweepSphereCollisionEntity)entity).setRunSpeed(RUN_SPEED);
					entity.getAnimatedModelDynamicData().setNextAnimationInfo(runAnimationInfo, true);
				} else {
					((SweepSphereCollisionEntity)entity).setRunSpeed(WALK_SPEED);
					entity.getAnimatedModelDynamicData().setNextAnimationInfo(walkAnimationInfo, true);
				}
				((SweepSphereCollisionEntity)entity).moveForward();
			}
		}

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
		gameState.enableDebug(entity);
	}

}
