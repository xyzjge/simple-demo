package ar.com.xyz.simpledemo.presentation.particles;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.particle.ParticleEmission;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

/**
 * ParticleSystemDemoGameState
 * @author alfredo
 *
 */
public class ParticleSystemDemoGameState extends AbstractGameState {
	
	private static final String LEVEL = "s-box" ;
	
	public ParticleSystemDemoGameState() {
		
		SingletonManager.getInstance().getTextureManager().addTexturePath("/textures/particles");
		particleEmission = new ParticleEmission(ParticleSystemSingleton.getInstance().getParticleSystemDust(), new Vector3f(0,1,0)) ;
		particleEmission.setLiveFor(5f);
		addParticleEmission(particleEmission);
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("grid.png");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(5,5,1));
			entitySpec.setPosition(new Vector3f(0,5,5));
			createEntity(entitySpec);
		}
		
		setupPlayerAndCamera() ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		getAmbientLight().x = .5f ;
		getAmbientLight().y = .5f ;
		getAmbientLight().z = .5f ;
		
		// Muestras las esferas en 000 ...
//		enableDebugKeys();
		
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
	}
	
	float xxx = 0 ;

	private int current = 0 ;
	
	ParticleEmission particleEmission = null ;
	
	@Override
	public void tick(float tpf) {
		xxx+=tpf ;

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();

		if (xxx> 6) {
			xxx = 0 ;
			current++ ;
			if (current > 2) {
				current = 0 ;
			}
			switch (current) {
			case 0:
				particleEmission = new ParticleEmission(ParticleSystemSingleton.getInstance().getParticleSystemDust(), new Vector3f(0,1,0)) ;
				break;
			case 1:
				particleEmission = new ParticleEmission(ParticleSystemSingleton.getInstance().getParticleSystemFire(), new Vector3f(0,1,0)) ;
				break;
			case 2:
				particleEmission = new ParticleEmission(ParticleSystemSingleton.getInstance().getParticleSystemExplosion(), new Vector3f(0,1,0)) ;
				break;
			default:
				break;
			}
			
			particleEmission.setLiveFor(5f);
			addParticleEmission(particleEmission);
		
		}

	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(0, 1, -4),
			new Vector3f(0, 0, 0), 
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null
		) ;

		// getCamera().decPitch(-90);
		
//		getPlayer().setCrushHandler(this);
		
//		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));

		getPlayer().setRunSpeed(3);
		getPlayer().setStrafeSpeed(3);
		this.enableDebug(getPlayer());
	}

	private void handlePlayerDeath() {
		// getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
		getMainGameLoop().setNextGameState(PresentationMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

}
