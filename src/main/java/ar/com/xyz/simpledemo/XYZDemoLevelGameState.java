package ar.com.xyz.simpledemo;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.entity.Camera;
import ar.com.xyz.gameengine.entity.SweepSphereCollisionEntity;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.LevelGameStateDefaultPlayerInputHandler;

public class XYZDemoLevelGameState extends AbstractGameState {
	
	private static final String LEVEL = "simple-environment" ;
	
	private LevelGameStateDefaultPlayerInputHandler levelGameStateDefaultPlayerInputHandler ;
	
	protected XYZDemoLevelGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		loadPlayerAndCamera() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			createEntity(entitySpec);
		}
		
		getAabbManager().createTree();
		
		levelGameStateDefaultPlayerInputHandler = new LevelGameStateDefaultPlayerInputHandler(mainGameLoop, getPlayer(), getCamera(), this, null, null) ;
		
		grabMouseIfNotGrabbed() ;

	}

	@Override
	public void tick(float tpf) {
		levelGameStateDefaultPlayerInputHandler.handlePlayerInput();
	}

	private void loadPlayerAndCamera() {

		setPlayer(
			new SweepSphereCollisionEntity(
				new Vector3f(0, 100, 0),
				new Vector3f(0, 0, 0),
				new Vector3f(1, 1, 1),
				true,
				new Vector3f(.5f, 1f, .5f),
				new Vector3f(.5f, .5f, .5f)
			)
		) ;
		
		getPlayer().setModel(null /* new PlayerModel()*/);
			
		getPlayer().setGameState(this);
		getPlayer().setRun(true);
		getPlayer().getRotation().y = 90;

		// Si se setea un ESpaceUtil distinto tiene que ser antes de esta linea ...
		SingletonManager.getInstance().getEntityUtil().calcularVerticesTransformados(getPlayer());
		getAabbManager().updateSSCE(getPlayer());
		
		setCamera( new Camera(getPlayer()) );
		
		getCamera().decPitch(-90);
		
		addEntity(getPlayer()) ;
		
	}

}
