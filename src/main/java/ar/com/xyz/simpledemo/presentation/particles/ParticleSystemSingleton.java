package ar.com.xyz.simpledemo.presentation.particles;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.particle.ParticleSystem;
import ar.com.xyz.gameengine.particle.ParticleTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class ParticleSystemSingleton {
	
	private static final boolean USE_ADDITIVE_BLENDING = true ;

	private static ParticleSystemSingleton instance = new ParticleSystemSingleton() ;
	
	public static ParticleSystemSingleton getInstance() {
		return instance ;
	}
	
	private ParticleSystem particleSystemBlood = null ;
	
	public ParticleSystem getParticleSystemBlood() {
		if (particleSystemBlood == null) {
		// ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("particleAtlas"), 4, false) ;
		//ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("dust"), 4, false) ;
		// ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("dust2"), 2, false) ;
			ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("blood"), 1, USE_ADDITIVE_BLENDING) ;
			particleSystemBlood = new ParticleSystem(particleTexture, 15, 5, 2f, .5f, .1f) ;
			particleSystemBlood.randomizeRotation();
			particleSystemBlood.setDirection(new Vector3f(0,1,0), 0.1f);
			particleSystemBlood.setLifeError(0.1f);
			particleSystemBlood.setSpeedError(0.1f);
			particleSystemBlood.setScaleError(0.1f);
		}
		return particleSystemBlood ;
	}

	private ParticleSystem particleSystemGhost = null ;
	
	public ParticleSystem getParticleSystemGhost() {
		if (particleSystemGhost == null) {
		// ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("particleAtlas"), 4, false) ;
		//ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("dust"), 4, false) ;
		// ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("dust2"), 2, false) ;
			// ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("smoke-2"), 1, false) ;
			ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("smoke-2"), 1, USE_ADDITIVE_BLENDING) ;
			particleSystemGhost = new ParticleSystem(particleTexture, 20f, 2f, .5f, .5f, 2f) ;
			// particleSystemGhost.randomizeRotation();
			particleSystemGhost.setDirection(new Vector3f(0,1,0), 0.2f);
			particleSystemGhost.setLifeError(0.1f);
			particleSystemGhost.setSpeedError(0.1f);
			particleSystemGhost.setScaleError(0.1f);
		}
		return particleSystemGhost ;
	}

	private ParticleSystem particleSystemFire = null ;
	
	public ParticleSystem getParticleSystemFire() {
		if (particleSystemFire == null) {
			ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("particleAtlas"), 4, USE_ADDITIVE_BLENDING) ;
			particleSystemFire = new ParticleSystem(particleTexture, 15, 5, 0.3f, .2f, .5f) ;
			particleSystemFire.randomizeRotation();
			particleSystemFire.setDirection(new Vector3f(0,1,0), 0.1f);
			particleSystemFire.setLifeError(0.1f);
			particleSystemFire.setSpeedError(0.1f);
			particleSystemFire.setScaleError(0.1f);
		}
		return particleSystemFire ;
	}

	private ParticleSystem particleSystemDust = null ;
	
	public ParticleSystem getParticleSystemDust() {
		if (particleSystemDust == null) {
			// ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("particleAtlas"), 4, false) ;
			// ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("dust"), 4, false) ;
			// ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("dust2"), 2, USE_ADDITIVE_BLENDING) ;
			
			String texture = "particle-system-test" ;
			texture = "dust2" ;
			texture = "dust3" ;
			boolean additiveBlending = true ;
			float particlesPerSecond = .25f ;
			float speed = 0.1f ;
			float gravityComplient = 0f ;
			float lifeLength = 4f ;
			float scale = 2f ;
			ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture(texture), 2 /* numberOfRows*/, additiveBlending /* USE_ADDITIVE_BLENDING*/) ;
			particleSystemDust = new ParticleSystem(particleTexture, particlesPerSecond, speed, gravityComplient, lifeLength, scale) ;
			// particleSystemDust = new ParticleSystem(particleTexture, .25f, .5f, 0f, 4f, 1f) ;
//			particleSystemDust.randomizeRotation();
			particleSystemDust.setDirection(new Vector3f(0,1,0), 0.0f /*0.1f*/);
//			particleSystemDust.setLifeError(0.1f);
//			particleSystemDust.setSpeedError(0.1f);
//			particleSystemDust.setScaleError(0.1f);
		}
		return particleSystemDust ;
	}

	private ParticleSystem particleSystemExplosion = null ;
	
	public ParticleSystem getParticleSystemExplosion() {
		if (particleSystemExplosion == null) {
			ParticleTexture particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture("particleAtlas"), 4, USE_ADDITIVE_BLENDING) ;
			particleSystemExplosion = new ParticleSystem(particleTexture, 15, 5, 0.3f, .2f, 2.5f) ;
			particleSystemExplosion.randomizeRotation();
			particleSystemExplosion.setDirection(new Vector3f(0,1,0), 0.1f);
			particleSystemExplosion.setLifeError(0.1f);
			particleSystemExplosion.setSpeedError(0.1f);
			particleSystemExplosion.setScaleError(0.1f);
		}
		return particleSystemExplosion ;
	}
}
