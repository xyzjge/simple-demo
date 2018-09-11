package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.cameracontroller.FollowEntityFromFixedDirectionCameraController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.light.DirectionalLight;
import ar.com.xyz.gameengine.render.Loader;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.terrain.Terrain;
import ar.com.xyz.gameengine.texture.TerrainTexture;
import ar.com.xyz.gameengine.texture.TerrainTexturePack;
import ar.com.xyz.gameengine.util.GuiCameraSpec;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

/**
 * Terrain
 * @author alfredo
 *
 */
public class TerrainDemoGameState extends AbstractGameState {

//	private static final String LEVEL = "water-demo-level" ;
	
	private static final boolean CAST_SHADOWS = true;
	
	private Terrain terrain ;

	public TerrainDemoGameState() {
		
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
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		getAmbientLight().x = .5f ;
		getAmbientLight().y = .5f ;
		getAmbientLight().z = .5f ;
		
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.0f,.75f,.75f), new Vector3f(-10000,-10000,-10000), .75f) ;
		directionalLight.setCastShadows(CAST_SHADOWS);
		setDirectionalLight(directionalLight);
		
		enableDebugKeys();
		
		agregarEsferas() ;
		
	}
	
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getHandlePlayerInput() == null) {
			createInputHandler(getMainGameLoop(), getPlayer(), null) ;
		}
		setupSecondCamera() ;
	}

	private FollowEntityFromFixedDirectionCameraController followEntityFromFixedDirectionCameraController ;
	
	private void setupSecondCamera() {
		// followEntityFromFixedDirectionCameraController = new FollowEntityFromFixedDirectionCameraController(1, new Vector3f(-10000,-10000,-10000), getPlayer()) ;
		followEntityFromFixedDirectionCameraController = new FollowEntityFromFixedDirectionCameraController(5, new Vector3f(1,-1,1), getPlayer(), getCamera().getCameraController()) ;
		GuiCameraSpec guiCameraSpec = new GuiCameraSpec(followEntityFromFixedDirectionCameraController) ;
		// GuiCameraSpec guiCameraSpec = new GuiCameraSpec(getCamera().getCameraController()) ;
		
		GuiTexture guiTexture = new GuiTexture(guiCameraSpec.getOutputFbo().getColourTexture(), new Vector2f(.5f, .5f), new Vector2f(.25f, .25f)) ;
		guiTexture.setFboTexture(true);
		getGuis().add(guiTexture) ;
		getGuiCameraSpecList().add(guiCameraSpec);
		
	}
	
	private void agregarEsferas() {
		for (int x = 0; x < 80; x+=20) {
			for (int z = 0; z < 80; z+=20) {
				{
					EntitySpec entitySpec = new EntitySpec("esfera") ;
					entitySpec.setTexture(ColorEnum.MAROON.getName());
					entitySpec.setPosition(new Vector3f(x, terrain.getHeightOfTerrain(x, z), z));
					entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
		//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
					createEntity(entitySpec);
				}
			}
		}
		
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
		terrain = new Terrain(0, 0 /* -1*/, loader, texturePack, blendMap, "heightmap", 100, 10) ;
		getTerrainList().add( terrain );
		getAabbManager().addTerrain(terrain);
		
/*		{
			Terrain terrain2 = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap", 100, 10) ;
			getTerrainList().add( terrain2 );
			getAabbManager().addTerrain(terrain2);
		}*/

	}
	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
		followEntityFromFixedDirectionCameraController.update(tpf);
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

		getPlayer().setRunSpeed(3);
		this.enableDebug(getPlayer());
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

}
