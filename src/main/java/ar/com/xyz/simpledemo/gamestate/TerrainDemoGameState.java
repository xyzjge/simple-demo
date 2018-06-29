package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.render.Loader;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.terrain.Terrain;
import ar.com.xyz.gameengine.texture.TerrainTexture;
import ar.com.xyz.gameengine.texture.TerrainTexturePack;

/**
 * Terrain
 * @author alfredo
 *
 */
public class TerrainDemoGameState extends AbstractGameState {

//	private static final String LEVEL = "water-demo-level" ;
	
	protected TerrainDemoGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		
		loadTerrain();
		loadPlayerAndCamera() ;
		
//		{	// Create SOLID_STATIC for the LEVEL
//			EntitySpec entitySpec ;
//			entitySpec = new EntitySpec(LEVEL) ;
//			entitySpec.setTexture("grass");
//			entitySpec.setScale(new Vector3f(1,1,1));
//			entitySpec.setPosition(new Vector3f(0, 0 /*-10*/,0));
//			createEntity(entitySpec);
//		}
		
		createInputHandler(
			mainGameLoop, getPlayer(), getCamera(), this, null, null
		) ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
	}
	
	private void loadTerrain() {
		Loader loader = SingletonManager.getInstance().getLoader() ;

		// Terrain
		TerrainTexture backgroundTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("grassy")) ;
		// TerrainTexture backgroundTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("socuwan")) ;
		TerrainTexture rTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("dirt")) ;
		TerrainTexture gTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("pinkFlowers")) ;
		TerrainTexture bTexture = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("path")) ;

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture, bTexture) ;
		TerrainTexture blendMap = new TerrainTexture(SingletonManager.getInstance().getTextureManager().loadTexture("blendMap")) ;

		// (0, 0) arranca en (0, 0) y crece hacia los positivos
		// (x, z) arranca en (x * size, z * size) y crece hacia los positivos
		setTerrain( new Terrain(0, 0 /* -1*/, loader, texturePack, blendMap, "heightmap", 100, 10) );
		getAabbManager().addTerrain(getTerrain());

	}
	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		getHandlePlayerInput().handlePlayerInput();
		
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(20, 10, 20),
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

		this.enableDebug(getPlayer());
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(new SimpleDemoMenuGameState(getMainGameLoop(), "ZIPCLOSE.wav", "stone.png")) ;
	}

}
