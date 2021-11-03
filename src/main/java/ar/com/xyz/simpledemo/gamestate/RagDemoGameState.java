package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainCharacterGameState;
import ar.com.xyz.gameengine.configuration.Configuration;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.entity.spec.RagEntityPivotPositionEnum;
import ar.com.xyz.gameengine.entity.spec.RagEntitySpec;
import ar.com.xyz.gameengine.format.obj.Material;
import ar.com.xyz.gameengine.format.obj.Mesh;
import ar.com.xyz.gameengine.format.obj.MeshModel;
import ar.com.xyz.gameengine.light.DirectionalLight;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.rag.RagGridUtil;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

/**
 * Water demo
 * @author alfredo
 *
 */
public class RagDemoGameState extends AbstractMainCharacterGameState implements CrushHandler {

	private static final boolean CAST_SHADOWS = true;

	private static final String LEVEL = "s-box" ;
		
	public RagDemoGameState() {
		setupPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("grass");
			entitySpec.setScale(new Vector3f(10,9,10));
			entitySpec.setPosition(new Vector3f(0,-10,0));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setScale(new Vector3f(2,2,2));
			entitySpec.setPosition(new Vector3f(6,-10 + 1,-4));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setScale(new Vector3f(2,2,2));
			entitySpec.setPosition(new Vector3f(-7,-10 + 1,-4));
			createEntity(entitySpec);
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_ROT_Y).setAbstractGameState(this);
		SingletonManager.getInstance().getGraphicDebugger(Configuration.DEBUG_SS).setAbstractGameState(this);
		
		{
			RagEntitySpec entitySpec ;
			entitySpec = new RagEntitySpec(null) ;
			entitySpec.setTexture("tile0146.jpg");
			
			entitySpec.setAmplitude(.25f);
			entitySpec.setPeriod(2f);
			entitySpec.setWindSpeed(5f);
			entitySpec.setMovementAmplitude(0.01f);
			entitySpec.setPivotPosition(RagEntityPivotPositionEnum.TOP);			
			
//			MeshModel meshModel = RagGridUtil.getInstance().createGrid(40, 4, true) ;
//			MeshModel meshModel = RagGridUtil.getInstance().createGrid(2, 4, true, 2, 1) ;
			MeshModel meshModel = RagGridUtil.getInstance().createGrid(40, 4, true, 8, 2) ;
			Mesh mesh = new Mesh() ;
			Material material = new Material("Custom") ;
			material.setR(1);
			material.setG(0);
			material.setB(0);
			mesh.add(material, meshModel.getVertices(), meshModel.getTextureCoords(), null, null, meshModel.getIndices());
			mesh.calculateMeshModel(); // TODO: En este caso no deberia ser necesario o si ?
			entitySpec.setMesh(mesh);
			
			entitySpec.setScale(new Vector3f(1, 2, 1));
			entitySpec.setRemoveBackwardFacingFaces(false);
			entitySpec.setId("RAG");
			createEntity(entitySpec);
		}
		
		{
			RagEntitySpec entitySpec ;
			entitySpec = new RagEntitySpec(40, 4, .25f, 2f, 5f, 0.01f, RagEntityPivotPositionEnum.TOP, "tile0146.jpg", 8f, 1f) ;
			
			entitySpec.setPosition(new Vector3f(1, 0, 1));
			entitySpec.setScale(new Vector3f(1, 2, 1));
			entitySpec.setId("RAG2");
			createEntity(entitySpec);
		}
		
		{
			RagEntitySpec entitySpec ;
			entitySpec = new RagEntitySpec(40, 4, .25f, 2f, 5f, 0.01f, RagEntityPivotPositionEnum.TOP, .5f, 0, 0) ;
			
			entitySpec.setPosition(new Vector3f(2, 0, 2));
			entitySpec.setScale(new Vector3f(1, 2, 1));
			entitySpec.setId("RAG3");
			createEntity(entitySpec);
		}
		
		{
			RagEntitySpec entitySpec ;
			entitySpec = new RagEntitySpec(40, 4, .0f, 1f, 5f, .0f, RagEntityPivotPositionEnum.TOP, "tile0146.jpg", .5f, 1f) ;
			
			entitySpec.setPosition(new Vector3f(3, 0, 3));
			entitySpec.setScale(new Vector3f(1, 2, 1));
			entitySpec.setId("RAG4");
			createEntity(entitySpec);
		}
		
		// DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.5f,.5f,.5f), new Vector3f(-10000,-10000,-10000), 1) ;
		// DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1,.5f,.5f), new Vector3f(-10000,-10000,-10000), 1) ;
		// DirectionalLight directionalLight = new DirectionalLight(new Vector3f(1,0,0), new Vector3f(-10000,-10000,-10000), 1) ;
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.5f,.5f,.5f), new Vector3f(10000,-10000,10000), 1) ;
		directionalLight.setCastShadows(CAST_SHADOWS);
		setDirectionalLight(directionalLight);
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
	}
	
	@Override
	public void tick(float tpf) {

		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(-1 /*-5*/, 0, 6 /*94*/), // new Vector3f(-5, 10, 90), // new Vector3f(5, 10, 5), //new Vector3f(10, 10, 10),
			new Vector3f(0, -30, 0), // new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
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

