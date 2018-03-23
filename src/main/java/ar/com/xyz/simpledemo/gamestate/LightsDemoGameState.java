package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.debug.ResaltarXYZ;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.InputHandler;
import ar.com.xyz.gameengine.light.DirectionalLight;
import ar.com.xyz.gameengine.light.PointLight;
import ar.com.xyz.gameengine.light.SpotLight;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.enumerator.LigthsDemoEnum;

/**
 * @author alfredo
 *
 */
public class LightsDemoGameState extends AbstractGameState implements CrushHandler, InputHandler {

	private static final String LEVEL = "s-box" ;
	
	private LigthsDemoEnum state ;
	private int subState = 1 ;
	
	private SpotLight spotLight ;
	
	private ResaltarXYZ resaltarXYZ ;
	
	protected LightsDemoGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		loadPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(15,15,15));
			// entitySpec.setPosition(new Vector3f(0,-50,0));
			entitySpec.setPosition(new Vector3f(0,-30,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for the mesh
			SingletonManager.getInstance().getTextureManager().addTexturePath("/home/alfredo/tiles-jpg/");
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("e1m1") ;
//			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(90, 0, 0)) ;
//			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,0,0));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture(ColorEnum.RED.getName());
			entitySpec.setPosition(new Vector3f(-11, 1, 90));
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
//			entitySpec.setEntityController(xEntityController);
			createEntity(entitySpec);
		}
		
		createInputHandler(
			mainGameLoop, getPlayer(), getCamera(), this, null, null
		) ;
		configureKeys();
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(false);
		
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).setAbstractGameState(this);
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).setAbstractGameState(this);
		
		Vector3f lightPosition = new Vector3f(-10, 2, 58);
		PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, .1f /* lightIntensity */);
		PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f, 0.002f);
		pointLight.setAttenuation(att);
		Vector3f coneDir = new Vector3f(0, 0, -1);
		float cutoff = 10 ; //.195f ; // (float) Math.cos(Math.toRadians(.1f /* 100 *//*140*/ /*180*/ /* 240*/));
		spotLight = new SpotLight(pointLight, coneDir, cutoff);
		
		resaltarXYZ = new ResaltarXYZ(this) ;
	}
	
	private void configureKeys() {
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_G, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_1, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_2, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_3, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_4, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_5, this);
		getHandlePlayerInput().addInputHandler(Keyboard.KEY_6, this);
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		actualizarEstado(LigthsDemoEnum.AMBIENT) ;
	}

	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
		resaltarXYZ.restaltar(getPlayer().getPosition(), 3);
		// Actualizar la posicion y la direccion de la spotLight en funcion de la posicion y la direccion del jugador (como si fuese una linterna)
		if (state.equals(LigthsDemoEnum.SPOT)) {
			if (getSpotLightList().isEmpty()) {
				getSpotLightList().add(spotLight);
			}
			
			float rotY = getPlayer().getRotation().y ;
			Vector3f rayDirection = new Vector3f(
				(float)Math.sin(Math.toRadians(rotY)), 
				(float) - Math.sin(Math.toRadians(getCamera().getPitch())) , 
				(float)Math.cos(Math.toRadians(rotY))
			) ;
			spotLight.getConeDirection().x = rayDirection.x ;
			spotLight.getConeDirection().y = rayDirection.y ;
			spotLight.getConeDirection().z = rayDirection.z ;
			
//			if (subState == 1) {
//				spotLight.getPointLight().getPosition().x = getPlayer().getPosition().x ;
//				spotLight.getPointLight().getPosition().y = getPlayer().getPosition().y ;
//				spotLight.getPointLight().getPosition().z = getPlayer().getPosition().z ;
//			} else if (subState == 2) {
				spotLight.getPointLight().getPosition().x = getPlayer().getPosition().x ;
				spotLight.getPointLight().getPosition().y = getPlayer().getPosition().y + 1;
				spotLight.getPointLight().getPosition().z = getPlayer().getPosition().z ;
//			}
		}
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(-5, 0, 94), // new Vector3f(-5, 10, 90), // new Vector3f(5, 10, 5), //new Vector3f(10, 10, 10),
			new Vector3f(0, -30, 0), // new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null
		) ;

		// getCamera().decPitch(-90);
		
		getPlayer().setCrushHandler(this);
		
//		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));

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
	
	@Override
	public boolean handleInput(int eventKey) {
		switch (eventKey) {
		case Keyboard.KEY_G: {
			subState = 1 ;
			actualizarEstado(state.next()) ;
		}
		break;
		case Keyboard.KEY_1:
			subState = 1 ;
			switch (state) {
			case AMBIENT:
				ambientUno() ;
				break;
			case DIRECTIONAL:
				directionalUno() ;
				break;
			case POINT:
				pointUno() ;
				break;
			default:
				break;
			}
			break;
		case Keyboard.KEY_2:
			subState = 2 ;
			switch (state) {
			case AMBIENT:
				ambientDos() ;
				break;
			case DIRECTIONAL:
				directionalDos() ;
				break;
			case POINT:
				pointDos() ;
				break;
			default:
				break;
			}
			break;
		case Keyboard.KEY_3:
			subState = 3 ;
			switch (state) {
			case AMBIENT:
				ambientTres() ;
				break;
			case DIRECTIONAL:
				directionalTres() ;
				break;
			default:
				break;
			}
			break;
		case Keyboard.KEY_4:
			subState = 4 ;
			switch (state) {
			case AMBIENT:
				ambientCuatro() ;
				break;
			case DIRECTIONAL:
				directionalCuatro() ;
				break;
			default:
				break;
			}
			break;
		case Keyboard.KEY_5:
			subState = 5 ;
			switch (state) {
			case AMBIENT:
				ambientCinco() ;
				break;
			case DIRECTIONAL:
				directionalCinco() ;
				break;
			default:
				break;
			}
			break;
		case Keyboard.KEY_6:
			subState = 6 ;
			switch (state) {
			case AMBIENT:
				ambientSeis() ;
				break;
			case DIRECTIONAL:
				directionalSeis() ;
				break;
			default:
				break;
			}
			break;
		default:
			break;
		}
		return false;
	}
	
	private void actualizarEstado(LigthsDemoEnum state) {
		this.state = state ;
		addNotification(state + " light demo");
		switch (state) {
		case AMBIENT:
			getDirectionalLight().setIntensity(0);
			getPointLightList().clear();
			getSpotLightList().clear();
			ambientUno();
			break;
		case DIRECTIONAL:
			directionalUno();
			break;
		case POINT:
			pointUno();
			break;
		case SPOT:
			getSpotLightList().add(spotLight);
			break;
		default:
			break;
		}
	}

	private void ambientUno() {
		addNotification("Red (1, 0, 0) ambient light");
		getAmbientLight().x = 1 ;
		getAmbientLight().y = 0 ;
		getAmbientLight().z = 0 ;
	}
	private void ambientDos() {
		addNotification("Green (0, 1, 0) ambient light");
		getAmbientLight().x = 0 ;
		getAmbientLight().y = 1 ;
		getAmbientLight().z = 0 ;
	}
	private void ambientTres() {
		addNotification("Blue (0, 0, 1) ambient light");
		getAmbientLight().x = 0 ;
		getAmbientLight().y = 0 ;
		getAmbientLight().z = 1 ;
	}
	private void ambientCuatro() {
		addNotification("White (1, 1, 1) ambient light");
		getAmbientLight().x = 1 ;
		getAmbientLight().y = 1 ;
		getAmbientLight().z = 1 ;
	}
	private void ambientCinco() {
		addNotification("Black (0, 0, 0) ambient light");
		getAmbientLight().x = 0 ;
		getAmbientLight().y = 0 ;
		getAmbientLight().z = 0 ;
	}
	private void ambientSeis() {
		addNotification("Gray (.5, .5, .5) ambient light");
		getAmbientLight().x = .5f ;
		getAmbientLight().y = .5f ;
		getAmbientLight().z = .5f ;
	}
	
	private void directionalUno() {
		addNotification("PURPLE (0.5f,0,0.5f) directional light with direction (1,-1,1) and intensity " + 1);
		Vector3f color = ColorEnum.PURPLE.getColor() ;
		Vector3f direction = new Vector3f(1,-1,1) ;
		float intensity = 1 ;
		DirectionalLight directionalLight = new DirectionalLight(color, direction, intensity) ;
		setDirectionalLight(directionalLight) ;
	}
	private void directionalDos() {
		addNotification("PURPLE (0.5f,0,0.5f) directional light with direction (1,-1,-1) and intensity " + 1);
		Vector3f color = ColorEnum.PURPLE.getColor() ;
		Vector3f direction = new Vector3f(1,-1,-1) ;
		float intensity = 1f ;
		DirectionalLight directionalLight = new DirectionalLight(color, direction, intensity) ;
		setDirectionalLight(directionalLight) ;
	}
	private void directionalTres() {
		addNotification("PURPLE (0.5f,0,0.5f) directional light with direction (-1,-1,-1) and intensity " + 1);
		Vector3f color = ColorEnum.PURPLE.getColor() ;
		Vector3f direction = new Vector3f(-1,-1,-1) ;
		float intensity = 1f ;
		DirectionalLight directionalLight = new DirectionalLight(color, direction, intensity) ;
		setDirectionalLight(directionalLight) ;
	}
	private void directionalCuatro() {
		addNotification("PURPLE (0.5f,0,0.5f) directional light with direction (-1,-1,1) and intensity " + 1);
		Vector3f color = ColorEnum.PURPLE.getColor() ;
		Vector3f direction = new Vector3f(-1,-1,1) ;
		float intensity = 1 ;
		DirectionalLight directionalLight = new DirectionalLight(color, direction, intensity) ;
		setDirectionalLight(directionalLight) ;
	}
	private void directionalCinco() {
		addNotification("PURPLE (0.5f,0,0.5f) directional light with direction (0,-1,0) and intensity " + 1);
		Vector3f color = ColorEnum.PURPLE.getColor() ;
		Vector3f direction = new Vector3f(0,-1,0) ;
		float intensity = 1 ;
		DirectionalLight directionalLight = new DirectionalLight(color, direction, intensity) ;
		setDirectionalLight(directionalLight) ;
	}
	private void directionalSeis() {
		addNotification("PURPLE (0.5f,0,0.5f) directional light with direction (0,1,0) and intensity " + 1);
		Vector3f color = ColorEnum.PURPLE.getColor() ;
		Vector3f direction = new Vector3f(0,1,0) ;
		float intensity = 1 ;
		DirectionalLight directionalLight = new DirectionalLight(color, direction, intensity) ;
		setDirectionalLight(directionalLight) ;
	}
	private void pointUno() {
		// TODO: search with equals ...
		if (getPointLightList().size() > 0) {
			return ;
		}
		addNotification("WHITE (1,1,1) point light at (-5, 0, 94) with intensity " + .2f);
		Vector3f lightPosition = new Vector3f(-5, 0, 94);
		float lightIntensity = .2f;
		PointLight pointLight = new PointLight(new Vector3f(1, 1, 1), lightPosition, lightIntensity);
		PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f,.1f/*.5f*/ /*1.0f*/);
		pointLight.setAttenuation(att);
		getPointLightList().add(pointLight) ;
	}
	
	private void pointDos() {
		// TODO: search with equals ...
		if (getPointLightList().size() > 1) {
			return ;
		}
		addNotification("GRAY (.5f, .5f, .5f) point light at (1, -1, 94) with intensity " + .4f);
		Vector3f lightPosition = new Vector3f(1, -1, 94);
		float lightIntensity = .4f;
		PointLight pointLight = new PointLight(new Vector3f(.5f, .5f, .5f), lightPosition, lightIntensity);
		PointLight.Attenuation att = new PointLight.Attenuation(0.0f, 0.0f,.1f/*.5f*/ /*1.0f*/);
		pointLight.setAttenuation(att);
		getPointLightList().add(pointLight) ;
	}

}
