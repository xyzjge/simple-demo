package ar.com.xyz.simpledemo.presentation.sound;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.audio.AudioMaster;
import ar.com.xyz.gameengine.audio.Source;
import ar.com.xyz.gameengine.client.entitycontroller.PositionEntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

/**
 * Terrain
 * @author alfredo
 *
 */
public class SoundDemoGameState extends AbstractGameState {

	private static final String LEVEL = "s-box" ;
	
	private Source source ;
	
	private PositionEntityController entityController = new PositionEntityController(new Vector3f(1, 0, 0), 10, 2) ;
	
	public SoundDemoGameState() {
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}
		
		{	// Create sphere for sound source
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("red");
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(entityController);
			createEntity(entitySpec);
		}
		
		{ // TODO: Cuando termina un GE hay que cortar todos los sonidos ...
			int buffer = AudioMaster.loadSound("ZIPCLOSE.wav") ;
			// source = SingletonManager.getInstance().getSourceMaster().createSource(1, 0, 10) ;
			source = createSource("EXAMPLE-SOURCE", 1, 0, 10) ; // TODO: En el cleanUp eliminarlas ... siempre y cuando no sea un menu ??? Analizar ...
			// Si no siguen sonando en otro ge !!!
			source.setLooping(true);
			source.setPosition(0, 0, 0);
			source.setVolume(.5f);
			source.play(buffer);
		}
		
		
		loadPlayerAndCamera() ;
		
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
		if (getHandlePlayerInput() == null) {
			createInputHandler(
				getMainGameLoop(), getPlayer(), getCamera(), this, null, null
			) ;
		}
	}
	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
		source.setPosition(entityController.getEntity().getPosition().x, entityController.getEntity().getPosition().y, entityController.getEntity().getPosition().z);
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(0, 1, 0),
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
