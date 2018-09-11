package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.collision.ESpaceUtil;
import ar.com.xyz.gameengine.collision.Triangle;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.debug.collision.ActualizarPosicionEntityController;
import ar.com.xyz.gameengine.debug.collision.ResaltarTrianguloYCaso;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

/**
 * Ver un elipsoide y un triangulo ... para debugear colisiones
 * @author alfredo
 *
 */
public class ViewStaticSceneDemoGameState extends AbstractGameState implements CrushHandler {

	// Caso viejo (cuando se salia del mapa)
//	private static final Vector3f POSICION_INICIAL = new Vector3f(-6.834506f,-0.75807726f + 1,96.00639f);
//	private static final Vector3f MOVIMIENTO_DESEADO = new Vector3f(-0.058697283f,0.0f,-0.042142812f);
//	private static final Vector3f POSICION_DE_CONTACTO_EN_R3 = new Vector3f(-6.858233f, -0.75807726f + 1, 95.98936f);
//	private static final Vector3f POSICION_DE_CONTACTO_CORREGIDA_EN_R3 = new Vector3f(-6.824721f, -0.75807726f + 1, 96.01342f);

/*	"posicionInicial":{"x":0.47274402,"y":-1.9812711,"z":95.76924},
	"movimientoDeseado":{"x":-0.1776495,"y":0.0,"z":0.2898977},
	"posicionFinalSinCorregir":{"x":0.38602895,"y":-0.98127115,"z":95.91074},
	"posicionFinal":{"x":0.47274157,"y":-1.9812711,"z":95.76924},
	"nuevoMovimientoDeseado":{"x":-0.004065834,"y":0.0052333474,"z":0.0069353133}*/
	
	// Caso que me anulaba el movimiento ... cuando estaba subiendo una pendiente poco inclinada la diferencia entre la posicion de contacto y la corregida era muy grande y eso casi anulaba el movimiento ...
	private static final Vector3f POSICION_INICIAL = new Vector3f(0.47274402f,-1.9812711f,95.76924f);
	private static final Vector3f MOVIMIENTO_DESEADO = new Vector3f(-0.1776495f,0.0f,0.2898977f);
	private static final Vector3f POSICION_DE_CONTACTO_EN_R3 = new Vector3f(0.38602895f,-0.98127115f - 1,95.91074f);
	private static final Vector3f POSICION_DE_CONTACTO_CORREGIDA_EN_R3 = new Vector3f(0.47274157f,-1.9812711f,95.76924f);
	
	
	// private static final Vector3f SCALE = new Vector3f(.5f, 1f, .5f);
	private static final Vector3f SCALE = new Vector3f(.05f, .1f, .05f);
	// private static final Vector3f SCALE = new Vector3f(.005f, .01f, .005f);

	private static final String LEVEL = "s-box" ;
	
	private ActualizarPosicionEntityController posicionInicialEntityController ;
	private ActualizarPosicionEntityController movimientoDeseadoEntityController ;
	private ActualizarPosicionEntityController posicionDeContactoEntityController ;
	private ActualizarPosicionEntityController posicionDeContactoCorregidaEntityController ;
	 
	public ViewStaticSceneDemoGameState() {
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
			entitySpec.setTexture("green");
			entitySpec.setWireframe(true);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(SCALE);
			posicionInicialEntityController = new ActualizarPosicionEntityController() ;
			entitySpec.setEntityController(posicionInicialEntityController);
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("red");
			entitySpec.setWireframe(true);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(SCALE);
			movimientoDeseadoEntityController = new ActualizarPosicionEntityController() ;
			entitySpec.setEntityController(movimientoDeseadoEntityController);
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("black");
			entitySpec.setWireframe(true);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(SCALE);
			posicionDeContactoEntityController = new ActualizarPosicionEntityController() ;
			entitySpec.setEntityController(posicionDeContactoEntityController);
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("white");
			entitySpec.setWireframe(true);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(SCALE);
			posicionDeContactoCorregidaEntityController = new ActualizarPosicionEntityController() ;
			entitySpec.setEntityController(posicionDeContactoCorregidaEntityController);
			createEntity(entitySpec);
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

		if (getHandlePlayerInput() == null) {
			createInputHandler(getMainGameLoop(), getPlayer(), null) ;
		}
		
		Vector3f posicionInicial = POSICION_INICIAL ;
		Vector3f movimientoDeseado = MOVIMIENTO_DESEADO ;
		
		// Validando posicion (posicionDeContactoEnR3) Vector3f[-6.858233, -0.75807726, 95.98936]
		Vector3f posicionDeContactoEnR3 = POSICION_DE_CONTACTO_EN_R3;

		// Validando posicion (posicionDeContactoCorregidaEnR3) Vector3f[-6.824721, -0.75807726, 96.01342]
		Vector3f posicionDeContactoCorregidaEnR3 = POSICION_DE_CONTACTO_CORREGIDA_EN_R3 ;

		posicionInicialEntityController.updatePosition(posicionInicial);
		movimientoDeseadoEntityController.updatePosition(Vector3f.add(posicionInicial, movimientoDeseado, null));
		posicionDeContactoEntityController.updatePosition(posicionDeContactoEnR3);
		posicionDeContactoCorregidaEntityController.updatePosition(posicionDeContactoCorregidaEnR3);

		{
			// new Triangle(new Vector3f(-13.999999f*.5f,-0.79695874f,193.0f*.5f), new Vector3f(-12.736841f*.5f,-0.65852267f,189.0f*.5f), new Vector3f(-19.999998f*.5f,-0.3630977f,189.0f*.5f)) ;
			Vector3f p1 = new Vector3f(-13.999999f*.5f,-0.79695874f,193.0f*.5f) ;
			Vector3f p2 = new Vector3f(-12.736841f*.5f,-0.65852267f,189.0f*.5f) ;
			Vector3f p3 = new Vector3f(-19.999998f*.5f,-0.3630977f,189.0f*.5f) ;
			resaltarTriangulo(p1, p2, p3);
		}
		{
			// new Triangle(new Vector3f(-2.9999998f, -1.6112059f, 96.75f), new Vector3f(-6.9999995f, -1.1250042f, 96.5f), new Vector3f(-2.9999998f, 5.4999957f, 96.75f)),
			Vector3f p1 = new Vector3f(-2.9999998f, -1.6112059f, 96.75f) ;
			Vector3f p2 = new Vector3f(-6.9999995f, -1.1250042f, 96.5f) ;
			Vector3f p3 = new Vector3f(-2.9999998f, 5.4999957f, 96.75f) ;
			resaltarTriangulo(p1, p2, p3);
		}

	}

	private void resaltarTriangulo(Vector3f p1, Vector3f p2, Vector3f p3) {
		ResaltarTrianguloYCaso resaltarTrianguloYCaso = new ResaltarTrianguloYCaso(this) ;
		Triangle triangle = new Triangle(p1,p2,p3) ;
		resaltarTrianguloYCaso.resaltar(
			triangle, p1,p2,p3, 
			new ESpaceUtil(new Vector3f(1,1,1))
		);
	}

	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
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
		getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

}
