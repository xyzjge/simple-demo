package ar.com.xyz.simpledemo;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.client.entitycontroller.RotEntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.LevelGameStateDefaultPlayerInputHandler;

public class XYZDemoLevelGameState extends AbstractGameState {
	
	private static final String LEVEL = "simple-environment" ;
	
	private LevelGameStateDefaultPlayerInputHandler levelGameStateDefaultPlayerInputHandler ;
	
	private GuiTexture sweepSphereInAABBGuiTexture ;
	private boolean sweepSphereInAABB ;
	
	protected XYZDemoLevelGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		loadPlayerAndCamera() ;
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("woodenBox.jpg");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(0,4.75f,-1.5f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			RotEntityController rec = new RotEntityController(1,1,1) ;
			entitySpec.setEntityController(rec);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setSweepSphereInAABBHandler(new UpdateHUDSweepSphereInAABBHandler(this));
			createEntity(entitySpec);
			this.enableDebug(rec.getEntity());
		}
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("woodenBox.jpg");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(6f, .75f,-6f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			RotEntityController rec = new RotEntityController(1,1,1) ;
			entitySpec.setEntityController(rec);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setSweepSphereInAABBHandler(new RemoveEntitySweepSphereInAABBHandler(this));
			createEntity(entitySpec);
			this.enableDebug(rec.getEntity());
		}
		
		levelGameStateDefaultPlayerInputHandler = new LevelGameStateDefaultPlayerInputHandler(mainGameLoop, getPlayer(), getCamera(), this, null, null) ;
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);

		sweepSphereInAABBGuiTexture = new GuiTexture(
			SingletonManager.getInstance().getTextureManager().loadTexture("cube-wireframe-black"), 
			new Vector2f(0.80f, -0.76f), 
			new Vector2f(.1f, .1f)
		) ;
		
		this.getGuis().add(sweepSphereInAABBGuiTexture) ;
	}

	float seconds = 0 ;
	float secondsDebug = 0 ;
	boolean crear = true ;
	RotEntityController recc = null;
	
	@Override
	public void tick(float tpf) {
		levelGameStateDefaultPlayerInputHandler.handlePlayerInput();
		if (sweepSphereInAABB) {
			sweepSphereInAABBGuiTexture.setTexture(SingletonManager.getInstance().getTextureManager().loadTexture("cube-wireframe-white")) ;
			sweepSphereInAABB = false ;
		} else {
			sweepSphereInAABBGuiTexture.setTexture(SingletonManager.getInstance().getTextureManager().loadTexture("cube-wireframe-black")) ;
		}
		
		seconds += tpf ;
		secondsDebug += tpf ;
		if (seconds > 20) {
			seconds = 0;
			if (crear) {
				EntitySpec entitySpec ;
				entitySpec = new EntitySpec("box") ;
				entitySpec.setTexture("woodenBox.jpg");
				entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
				entitySpec.setPosition(new Vector3f(6,0,7f)) ;
				entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
				recc = new RotEntityController(1,1,1) ;
				entitySpec.setEntityController(recc);
				entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
				entitySpec.setSweepSphereInAABBHandler(new UpdateHUDSweepSphereInAABBHandler(this));
				// Si no tiene SweepSphereInAABBHandler no la agrega a ... y getAabbManager().getNonSolidAabb(asociatedEntity) retorna null ...
				createEntity(entitySpec);
				this.enableDebug(recc.getEntity());
			} else {
				this.scheduleEntityForRemoval(recc.getEntity());
			}
			crear = !crear ;
		} else {
			if (!crear) {
				if (secondsDebug > 5) {
					secondsDebug = 0 ;
					if (this.isDebugEnabled(recc.getEntity())) {
						this.disableDebug(recc.getEntity());
					} else {
						this.enableDebug(recc.getEntity());
					}
				}
			}
		}
	}

	private void loadPlayerAndCamera() {

		createPlayerAndCamera(
			new Vector3f(0, 100, 0),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true
		) ;

		getCamera().decPitch(-90);
		
		this.enableDebug(getPlayer());
	}

	public void setSweepSphereInAABB(boolean sweepSphereInAABB) {
		this.sweepSphereInAABB = sweepSphereInAABB ;
	}

}
