package ar.com.xyz.simpledemo.presentation.particles;

import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.ACTIVAR_DIRECCION;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.ADDITIVE_BLENDING;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.DIRECCION;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.GRAVITY;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.LIFE_LENGTH;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.PPS;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.RANDOM_ROT;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.SCALE;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateStateEnum.SPEED;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateSubStateEnum.ALTERNATIVE;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateSubStateEnum.NORMAL;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateVectorCoordinateEnum.X;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateVectorCoordinateEnum.Y;
import static ar.com.xyz.simpledemo.presentation.particles.AnotherParticleSystemDemoGameStateVectorCoordinateEnum.Z;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.font.fontMeshCreator.FontType;
import ar.com.xyz.gameengine.font.fontMeshCreator.GUIText;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.particle.ParticleEmission;
import ar.com.xyz.gameengine.particle.ParticleSystem;
import ar.com.xyz.gameengine.particle.ParticleTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.StringUtil;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

/**
 * 0-9 cambiar texturas / ParticleEmission
 * 
 * F1-F12 seleccionar variable a modificar
 * 
 * E: Para cambiar el error
 * 
 * Boolean: s/n p y/n para setear el valor
 * Numero: +- para cambiar el valor
 * Vector: xyz para seleccionar la coordenada
 * 
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
	
	private static final float fontSize = 1f ;
	
	private static final float yBase = 0.06f ;
	private static final float yStep = 0.05f ;

	private static final String TEXTURE_ATLAS_FUEGO = "particleAtlas";
	private static final int TEXTURE_ATLAS_FUEGO_SIZE = 4 ;
	
	private static final String TEXTURE_ATLAS_DUST = "dust2";
	private static final int TEXTURE_ATLAS_DUST_SIZE = 2 ;
	
	private static final String TEXTURE_ATLAS_STAR = "particleStar";
	private static final int TEXTURE_ATLAS_STAR_SIZE = 1 ;
	
	private static final String TEXTURE_ATLAS = TEXTURE_ATLAS_DUST;
	private static final int TEXTURE_ATLAS_SIZE = TEXTURE_ATLAS_DUST_SIZE ;
	
	/**
	 * TODO: Mostrar los valores
	 * 
	 * Agregar los PS ...
	 * 
	 * 
	 * 
	 * 
	 */
	
	private static final FontType FONT_TYPE = SingletonManager.getInstance().getFontTypeManager().getFontType("arial") ;
	
	private static final String LEVEL = "s-box" ;
	
	private AnotherParticleSystemDemoGameStateStateEnum status = null ;
	private AnotherParticleSystemDemoGameStateSubStateEnum subStatus = AnotherParticleSystemDemoGameStateSubStateEnum.NORMAL ;
	private AnotherParticleSystemDemoGameStateVectorCoordinateEnum vectorCoordinate = AnotherParticleSystemDemoGameStateVectorCoordinateEnum.X ;
	
	//////
	
	private float pps = 2 ;
	
	private float lowestSpeed = 1f ;
	private float highestSpeed = 1f ;
	
	private float lowestGravity = -2 ;
	private float higestGravity = -1 ; // TODO: Tambien estaria bueno que vaya cambiando durante la vida de la particula ...
	
	private float lowestLifeLength = 1 ;
	private float higestLifeLenght = 1 ;
	
	private float lowestScale = 1 ;
	private float higestScale = 1 ;
	
	private boolean randomRotation = true ;
	
	private boolean activarDireccion = false;
	private Vector3f direccion = new Vector3f(1, 0, 0) ;
	private float desvioDireccion = 25 ;
	
//	private float atlasTransitionSpeed = 1 ;
	
	private boolean additiveBlending = false ;
	
	////
	
	// Para mostrar estado, subEstado y vectorCoordinate
	private GUIText statusGUIText ;
	
	private GUIText ppsGUIText ;
	
	private GUIText speedGUIText ;
	
	private GUIText gravityGUIText ;
	
	private GUIText lifeLenghtGUIText ;
	
	private GUIText scaleGUIText ;
	
	private GUIText randomRotationGUIText ;
	
	private GUIText activarDireccionGUIText ;
	
	private GUIText direccionGUIText ;
	
//	private GUIText atlasTransitionSpeedGUIText ;
	
	private GUIText additiveBlendingGUIText ;
	
	
	private ParticleTexture particleTexture ;
	private ParticleSystem particleSystem ;
	private ParticleEmission particleEmission ;
	
	public AnotherParticleSystemDemoGameState() {
		
		SingletonManager.getInstance().getTextureManager().addTexturePath("/textures/particles");

		particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture(TEXTURE_ATLAS), TEXTURE_ATLAS_SIZE, additiveBlending) ;
		particleSystem = new ParticleSystem(particleTexture, pps, lowestSpeed, lowestGravity, lowestLifeLength, lowestScale) ;
		particleEmission = new ParticleEmission(particleSystem, new Vector3f(0,2,0)) ;
		
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
		
		addParticleEmission(particleEmission);
		// Muestras las esferas en 000 ...
//		enableDebugKeys();
		
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
		addInputEventListener(this) ;
	}
	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		updateGuiText() ;
		
		updateParticleTextureAndParticleEmission();

	}

	private void updateParticleTextureAndParticleEmission() {
		if (particleTexture.usesAdditiveBlending() != additiveBlending) {
			particleTexture.setAdditive(additiveBlending);
		}
		
		if (particleSystem.getPps() != pps) {
			particleSystem.setPps(pps);
		}
		
		if (particleSystem.getLowestSpeed() != lowestSpeed) {
			particleSystem.setLowestSpeed(lowestSpeed);
		}
		if (particleSystem.getHighestSpeed() != highestSpeed) {
			particleSystem.setHighestSpeed(highestSpeed);
		}
		
		if (particleSystem.getLowestGravity() != lowestGravity) {
			particleSystem.setLowestGravity(lowestGravity);
		}
		if (particleSystem.getHighestGravity() != higestGravity) {
			particleSystem.setHighestGravity(higestGravity);
		}
		
		if (particleSystem.getLowestLifeLength() != lowestLifeLength) {
			particleSystem.setLowestLifeLength(lowestLifeLength);
		}
		if (particleSystem.getHighestLifeLength() != higestLifeLenght) {
			particleSystem.setHighestLifeLength(higestLifeLenght);
		}
		
		if (particleSystem.getLowestScale() != lowestScale) {
			particleSystem.setLowestScale(lowestScale);
		}
		if (particleSystem.getHighestScale() != higestScale) {
			particleSystem.setHighestScale(higestScale);
		}

		if (particleSystem.isRandomRotation() != randomRotation) {
			particleSystem.setRandomRotation(randomRotation);
		}

		if (activarDireccion) {
			if (particleSystem.getDirection() == null) {
				particleSystem.setDirection(direccion);
			}
			if (particleSystem.getDirectionDeviation() != desvioDireccion) {
				particleSystem.setDirectionDeviation(desvioDireccion);
			}
		} else {
			if (particleSystem.getDirection() != null) {
				particleSystem.setDirection(null);
			}
		}
		
		// TODO: atlasTransitionSpeed
		
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
			// Particulas por segundo
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
//		case Keyboard.KEY_F11: Igual no andaba el F11 ... lo tomaba 1ro el X ...
//			status = ATLAS_TRANSITION_SPEED ; // TODO: ...
//			break;
		case Keyboard.KEY_F12:
			status = ADDITIVE_BLENDING ;
			break;
		case 0x9c: // Enter del pad
			if (subStatus != NORMAL) {
				subStatus = NORMAL ;
			} else {
				subStatus = ALTERNATIVE ;
			}
			break;
		case Keyboard.KEY_X:
			vectorCoordinate = X ;
			break;
		case Keyboard.KEY_Y:
			vectorCoordinate = Y ;
			break;
		case Keyboard.KEY_Z:
			vectorCoordinate = Z ;
			break;
		case 0x4e: // + del pad
			handleAdd() ;
			break;
		case 0x4a: // - del pad
			handleMinus() ;
			break;
		case 0xd2: // 0 (Ins) del pad
			printValues() ;
			break;
		default:
			System.out.println(Integer.toHexString(keyOrButton));
			break;
		}
		return false;
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		if (origin == EventOriginEnum.KEYBOARD && type == EventTypeEnum.RELEASED) {
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
			pps += .25f ;
			break;
		case SPEED:
			if (subStatus.equals(NORMAL)) {
				lowestSpeed += .1f ;
				if (lowestSpeed > highestSpeed) {
					lowestSpeed = highestSpeed ;
				}
			} else {
				highestSpeed += .1f ;
			}
			break;
		case GRAVITY:
			if (subStatus.equals(NORMAL)) {
				lowestGravity += .1f ;
				if (lowestGravity > higestGravity) {
					lowestGravity = higestGravity ;
				}
			} else {
				higestGravity += .1f ;
				if (higestGravity > 0) {
					higestGravity = 0 ;
				}
			}
			break;
		case LIFE_LENGTH:
			if (subStatus.equals(NORMAL)) {
				lowestLifeLength += .1f ;
				if (lowestLifeLength > higestLifeLenght) {
					lowestLifeLength = higestLifeLenght ;
				}
			} else {
				higestLifeLenght += .1f ;
			}
			break;
		case SCALE:
			if (subStatus.equals(NORMAL)) {
				lowestScale+= .1f ;
				if (lowestScale > higestScale) {
					lowestScale = higestScale ;
				}
			} else {
				higestScale += .1f ;
			}
			break;
		case RANDOM_ROT:
			randomRotation = true ;
			break;
		case ACTIVAR_DIRECCION:
			activarDireccion = true ;
			break;
		case DIRECCION:
			if (subStatus.equals(NORMAL)) {
				switch (vectorCoordinate) {
				case X:
					direccion.x += .1f ;
					if (direccion.x > 1) {
						direccion.x = 1 ;
					}
					break;
				case Y:
					direccion.y += .1f ;
					if (direccion.y > 1) {
						direccion.y = 1 ;
					}
					break;
				case Z:
					direccion.z += .1f ;
					if (direccion.z > 1) {
						direccion.z = 1 ;
					}
					break;
				default:
					break;
				}
			} else {
				desvioDireccion += .1f ;
			}
			break;
//		case ATLAS_TRANSITION_SPEED:
//			atlasTransitionSpeed += .1f ;
//			break;
		case ADDITIVE_BLENDING:
			additiveBlending = true ;
			break;
		default:
			break;
		}

	}

	private void handleMinus() {
		if (status == null) {
			return ;
		}
		
		switch (status) {
		case PPS:
			pps -= .25f ;
			if (pps < 0) {
				pps = 0 ;
			}
			break;
		case SPEED:
			if (subStatus.equals(NORMAL)) {
				lowestSpeed -= .1f ;
				if (lowestSpeed < 0) {
					lowestSpeed = 0 ;
				}
			} else {
				highestSpeed -= .1f ;
				if (highestSpeed < lowestSpeed) {
					highestSpeed = lowestSpeed ;
				}
			}
			break;
		case GRAVITY:
			if (subStatus.equals(NORMAL)) {
				lowestGravity -= .1f ;
			} else {
				higestGravity -= .1f ;
				if (higestGravity < lowestGravity) {
					higestGravity = lowestGravity ;
				}
			}
			break;
		case LIFE_LENGTH:
			if (subStatus.equals(NORMAL)) {
				lowestLifeLength -= .1f ;
				if (lowestLifeLength < 0) {
					lowestLifeLength = 0 ;
				}
			} else {
				higestLifeLenght -= .1f ;
				if (higestLifeLenght < lowestLifeLength) {
					higestLifeLenght = lowestLifeLength ;
				}
			}
			break;
		case SCALE:
			if (subStatus.equals(NORMAL)) {
				lowestScale -= .1f ;
				if (lowestScale <= 0) {
					lowestScale = .01f ;
				}
			} else {
				higestScale -= .1f ;
				if (higestScale < lowestScale) {
					higestScale = lowestScale ;
				}
			}
			break;
		case RANDOM_ROT:
			randomRotation = false ;
			break;
		case ACTIVAR_DIRECCION:
			activarDireccion = false ;
			break;
		case DIRECCION:
			if (subStatus.equals(NORMAL)) {
				switch (vectorCoordinate) {
				case X:
					direccion.x -= .1f ;
					if (direccion.x < -1) {
						direccion.x = -1 ;
					}
					break;
				case Y:
					direccion.y -= .1f ;
					if (direccion.y < -1) {
						direccion.y = -1 ;
					}
					break;
				case Z:
					direccion.z -= .1f ;
					if (direccion.z < -1) {
						direccion.z = -1 ;
					}
					break;
				default:
					break;
				}
			} else {
				desvioDireccion -= .1f ;
			}
			break;
//		case ATLAS_TRANSITION_SPEED:
//			atlasTransitionSpeed -= .1f ;
//			break;
		case ADDITIVE_BLENDING:
			additiveBlending = false ;
			break;
		default:
			break;
		}
		
	}

	private void updateGuiText() {
		updateStatus() ;
		updatePPS() ;
		updateSpeed() ;
		updateGravity() ;
		updateLifeLenght() ;
		updateScale() ;
		updateRandomRotation() ;
		updateActivarDireccion() ;
		updateDireccion() ;
//		updateAtlasTransitionSpeed() ;
		updateAdditiveBlending() ;
	}
	
	private void updateStatus() {
		if (statusGUIText != null) {
			statusGUIText.remove();
		}
		StringBuffer sb = new StringBuffer() ;
		if (status != null) {
			sb.append(status) ;
			if (subStatus != null) {
				sb.append("/" + subStatus) ;
				if (status == DIRECCION && subStatus == NORMAL && vectorCoordinate != null) {
					sb.append("/" + vectorCoordinate) ;
				}
			}
		}
		statusGUIText = new GUIText("Status: " + sb.toString(), fontSize, FONT_TYPE, new Vector2f(0.7f, yBase), 1f, false, this);
		statusGUIText .setColour(0, 1, 0);
		statusGUIText.show();
	}
	
	private void updatePPS() {
		if (ppsGUIText != null) {
			ppsGUIText.remove();
		}
		ppsGUIText = new GUIText("F3 PPS: " + StringUtil.getInstance().toString(pps), fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + yStep), 1f, false, this);
		ppsGUIText .setColour(0, 1, 0);
		ppsGUIText.show();
	}
	
	private void updateSpeed() {
		if (speedGUIText != null) {
			speedGUIText.remove();
		}
		speedGUIText = new GUIText("F4 Speed: " + StringUtil.getInstance().toString(lowestSpeed) + " (" + StringUtil.getInstance().toString(highestSpeed) + ")", fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*2)), 1f, false, this);
		speedGUIText .setColour(0, 1, 0);
		speedGUIText.show();
	}
	
	private void updateGravity() {
		if (gravityGUIText != null) {
			gravityGUIText.remove();
		}
		gravityGUIText = new GUIText("F5 Gravity: " + StringUtil.getInstance().toString(lowestGravity) + " (" + StringUtil.getInstance().toString(higestGravity) + ")", fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*3)), 1f, false, this);
		gravityGUIText .setColour(0, 1, 0);
		gravityGUIText.show();
	}
	
	private void updateLifeLenght() {
		if (lifeLenghtGUIText != null) {
			lifeLenghtGUIText.remove();
		}
		lifeLenghtGUIText = new GUIText("F6 lifeLenght: " + StringUtil.getInstance().toString(lowestLifeLength) + " (" + StringUtil.getInstance().toString(higestLifeLenght) + ")", fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*4)), 1f, false, this);
		lifeLenghtGUIText .setColour(0, 1, 0);
		lifeLenghtGUIText.show();
	}
	
	private void updateScale() {
		if (scaleGUIText != null) {
			scaleGUIText.remove();
		}
		scaleGUIText = new GUIText("F7 scale: " + StringUtil.getInstance().toString(lowestScale) + " (" + StringUtil.getInstance().toString(higestScale) + ")", fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*5)), 1f, false, this);
		scaleGUIText .setColour(0, 1, 0);
		scaleGUIText.show();
	}
	
	private void updateRandomRotation() {
		if (randomRotationGUIText != null) {
			randomRotationGUIText.remove();
		}
		randomRotationGUIText = new GUIText("F8 randomRotation: " + randomRotation , fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*6)), 1f, false, this);
		randomRotationGUIText .setColour(0, 1, 0);
		randomRotationGUIText.show();
	}
	
	private void updateActivarDireccion() {
		if (activarDireccionGUIText != null) {
			activarDireccionGUIText.remove();
		}
		activarDireccionGUIText = new GUIText("F9 activarDireccion: " + activarDireccion , fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*7)), 1f, false, this);
		activarDireccionGUIText .setColour(0, 1, 0);
		activarDireccionGUIText.show();
	}
	
	private void updateDireccion() {
		if (direccionGUIText != null) {
			direccionGUIText.remove();
		}
		direccionGUIText = new GUIText("F10 direccion: " + StringUtil.getInstance().toStringMath(direccion) + " (" + StringUtil.getInstance().toString(desvioDireccion) + ")", fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*8)), 1f, false, this);
		direccionGUIText .setColour(0, 1, 0);
		direccionGUIText.show();
	}
	
//	private void updateAtlasTransitionSpeed() {
//		if (atlasTransitionSpeedGUIText != null) {
//			atlasTransitionSpeedGUIText.remove();
//		}
//		atlasTransitionSpeedGUIText = new GUIText("atlasTransitionSpeed: " + atlasTransitionSpeed + "(TODO)", fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*9)), 1f, false, this);
//		atlasTransitionSpeedGUIText .setColour(0, 1, 0);
//		atlasTransitionSpeedGUIText.show();
//	}
	
	private void updateAdditiveBlending() {
		if (additiveBlendingGUIText != null) {
			additiveBlendingGUIText.remove();
		}
		additiveBlendingGUIText = new GUIText("F12 additiveBlending: " + additiveBlending, fontSize, FONT_TYPE, new Vector2f(0.7f, yBase + (yStep*9)), 1f, false, this);
		additiveBlendingGUIText .setColour(0, 1, 0);
		additiveBlendingGUIText.show();
	}

	private void printValues() {
		System.out.println("particleTexture = new ParticleTexture(SingletonManager.getInstance().getTextureManager().loadTexture(\"" + TEXTURE_ATLAS + "\"), " + TEXTURE_ATLAS_SIZE + ", additiveBlending)") ;
		
		System.out.println("particleSystem = new ParticleSystem(particleTexture, " + pps + "f, " + lowestSpeed + "f, " + lowestGravity + "f, " + lowestLifeLength + "f, " + lowestScale + "f)") ;
		System.out.println("particleSystem.setSpeedError(" + highestSpeed + "f)") ;
		System.out.println("particleSystem.setLifeError(" + higestLifeLenght + "f)") ;
		System.out.println("particleSystem.setScaleError(" + higestScale + "f)") ;
		System.out.println("particleSystem.setRandomRotation(" + randomRotation + ")") ;
		if (activarDireccion) {
			System.out.println("particleSystem.setDirection(" + direccion + ", " + desvioDireccion + "f)") ;
		}
		
		System.out.println("particleEmission = new ParticleEmission(particleSystem, new Vector3f(0,1,0))") ;
		System.out.println();		
	}

}
