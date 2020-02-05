package ar.com.xyz.simpledemo.presentation.particles;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.particle.ParticleEmission;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.*;

/**
 * 0-9: cambiar de emisor (para probar diferentes texturas)
 * 
 * F1: liveFor (+-)
 * F2: disturb (x+ x- y+ y- z+ z-) (disturb posicion particula) 
 * 
 * F3: pps (+-)
 * F4: averageSpeed/speedError (E): (+-)
 * F5: gravityComplient/gravityComplientError (E): (+-)
 * F6: averageLifeLength/lifeLengthError (E): (+-) (durante cuanto tiempo (en segundos) va a existir el PS, null hasta que alguien lo quite o termine el GS)
 * F7: averageScale/scaleError (E): (+-)
 * F8: randomRotation: s/n o y/n
 * F9: Activar direccion (s/n o y/n)
 * F10: direction (x+ x- y+ y- z+ z-)
 * F11: directionDeviation (+-)
 * F12: additiveBlending (s/n o y/n)
 * 
 * @author alfredo
 *
 */
public class AnotherParticleSystemDemoGameState extends AbstractGameState implements InputEventListener {
	
	private static final String LEVEL = "s-box" ;
	
	private AnotherParticleSystemDemoGameStateStateEnum status = null ;
	
	public AnotherParticleSystemDemoGameState() {
		
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

	//////
	
	private int pps = 10 ;
	private float speed = 10 ;
	private float gravity = 1 ;
	private float lifeLenght = 1 ;
	private float scale = 1 ;
	private boolean randomRotation = true ;
	private boolean activarDireccion = false;
	private Vector3f direccion = new Vector3f(1, 0, 0) ;
	private float desvioDireccion = 25 ;
	private boolean additiveBlending = false ;
	
	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		switch (keyOrButton) {
		case Keyboard.KEY_0:
			// Setear textura 0
			break;
		case Keyboard.KEY_1:
			// Setear textura 1
			break;
		case Keyboard.KEY_2:
			// Setear textura 2
			break;
		case Keyboard.KEY_3:
			// Setear textura 3
			break;
		case Keyboard.KEY_4:
			// Setear textura 4
			break;
		case Keyboard.KEY_5:
			// Setear textura 5
			break;
		case Keyboard.KEY_6:
			// Setear textura 6
			break;
		case Keyboard.KEY_F1:
			// liveFor: por ahora no lo voy a implementar
			break;
		case Keyboard.KEY_F2:
			// disturb: por ahora no lo voy a implementar
			break;
		case Keyboard.KEY_F3:
			// pps: por ahora no lo voy a implementar
			status = PPS ;
			break;
		case Keyboard.KEY_F4:
			// speed
			status = SPEED ;
			break;
		case Keyboard.KEY_F5:
			status = GRAVITY ;
			break;
		case Keyboard.KEY_F6:
			status = LIFE_LENGTH ;
			break;
		case Keyboard.KEY_F7:
			status = SCALE ;
			break;
		case Keyboard.KEY_F8:
			status = RANDOM_ROT ;
			break;
		case Keyboard.KEY_F9:
			status = ACTIVAR_DIRECCION ;
			break;
		case Keyboard.KEY_F10:
			status = DIRECCION ;
			break;
		case Keyboard.KEY_F11:
			status = DESVIO_DIRECCION ;
			break;
		case Keyboard.KEY_F12:
			status = ADDITIVE_BLENDING ;
			break;
		case Keyboard.KEY_ADD:
			handleAdd() ;
			break;
		case Keyboard.KEY_MINUS:
			handleMinus() ;
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		if (origin == EventOriginEnum.KEYBOARD) {
			return true ;
		}
		return false;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	private void handleAdd() {
		if (status == null) {
			return ;
		}
		
		switch (status) {
		case PPS:
			pps++ ;
			break;
		case SPEED:
			speed += .1f ;
			break;
		case GRAVITY:
			gravity += .1f ;
			if (gravity > 1) {
				gravity = 1 ;
			}
			break;
		case LIFE_LENGTH:
			lifeLenght += .1f ;
			break;
		case SCALE:
			scale += .1f ;
			break;
		case RANDOM_ROT:
			randomRotation = true ;
			break;
		case ACTIVAR_DIRECCION:
			activarDireccion = true ;
			break;
		case DIRECCION:
			// TODO: direccion teniendo en cuenta XYZ ...
			break;
		case DESVIO_DIRECCION:
			desvioDireccion++ ;
			break;
		case ADDITIVE_BLENDING:
			additiveBlending = true ;
			status = PPS ;
			break;
		default:
			break;
		}

	}

	private void handleMinus() {
		// TODO Auto-generated method stub
		
	}

}
