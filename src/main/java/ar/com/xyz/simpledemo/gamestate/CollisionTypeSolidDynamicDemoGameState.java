package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.client.entitycontroller.PositionEntityController;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.handler.PlayerDeathHandler;

public class CollisionTypeSolidDynamicDemoGameState extends AbstractGameState {
	
	private static final String LEVEL = "simple-environment" ;
	
	private GuiTexture sweepSphereInAABBGuiTexture ;
	private boolean sweepSphereInAABB ;
	
	private PlayerDeathHandler playerDeathHandler ;
	
	protected CollisionTypeSolidDynamicDemoGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		loadPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			createEntity(entitySpec);
		}
		
//		{	// Create EntityCollisionTypeEnum.NONE for the box that is over the other boxes and update the HUD if touched by the player
//			EntitySpec entitySpec ;
//			entitySpec = new EntitySpec("box") ;
//			entitySpec.setTexture("woodenBox.jpg");
//			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
//			entitySpec.setPosition(new Vector3f(0,4.75f,-1.5f)) ;
//			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
//			RotEntityController rec = new RotEntityController(1,1,1) ;
//			entitySpec.setEntityController(rec);
//			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setSweepSphereInAABBHandler(new UpdateHUDSweepSphereInAABBHandler(this));
//			createEntity(entitySpec);
//			this.enableDebug(rec.getEntity());
//		}
		
//		{	// Create EntityCollisionTypeEnum.NONE for the box that is removable
//			EntitySpec entitySpec ;
//			entitySpec = new EntitySpec("box") ;
//			entitySpec.setTexture("woodenBox.jpg");
//			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
//			entitySpec.setPosition(new Vector3f(6f, .75f,-6f)) ;
//			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
//			RotEntityController rec = new RotEntityController(1,1,1) ;
//			entitySpec.setEntityController(rec);
//			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setSweepSphereInAABBHandler(new RemoveEntitySweepSphereInAABBHandler(this));
//			createEntity(entitySpec);
//			this.enableDebug(rec.getEntity());
//		}
		
		
		{ // Caja que sube y baja
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(-6f, .75f,6f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			PositionEntityController positionEntityController = new PositionEntityController(new Vector3f(0,1,0), 2) ;
			entitySpec.setEntityController(positionEntityController);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
			createEntity(entitySpec);
			this.enableDebug(positionEntityController.getEntity());
		}
		
		{ // Caja que se mueve en X
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(6f, .75f,6f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			PositionEntityController positionEntityController = new PositionEntityController(new Vector3f(1,0,0), 2) ;
			entitySpec.setEntityController(positionEntityController);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
			createEntity(entitySpec);
			this.enableDebug(positionEntityController.getEntity());
		}
		
		{ // Caja que se mueve en Z
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(6f, .75f,-6f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			PositionEntityController positionEntityController = new PositionEntityController(new Vector3f(0,0,1), 2) ;
			entitySpec.setEntityController(positionEntityController);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
			createEntity(entitySpec);
			this.enableDebug(positionEntityController.getEntity());
		}
		
		{ // Caja que gira en Y
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(-6f, .75f,-6f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			RotationEntityController rotationEntityController = new RotationEntityController(0,100,0) ;
			entitySpec.setEntityController(rotationEntityController);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
			createEntity(entitySpec);
			this.enableDebug(rotationEntityController.getEntity());
		}
		
		createInputHandler(
			mainGameLoop, getPlayer(), getCamera(), this, null, null
		) ;
		
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

	float secondsSubtitles = 100 ;
	
	float seconds = 0 ;
	float secondsDebug = 0 ;
	boolean crear = true ;

	PositionEntityController positionEntityController = new PositionEntityController(new Vector3f(0,1,0), 2) ;
	
	@Override
	public void tick(float tpf) {
		playerDeathHandler.tick();
		
		handlePlayerInput.handlePlayerInput();
		if (sweepSphereInAABB) {
			sweepSphereInAABBGuiTexture.setTexture(SingletonManager.getInstance().getTextureManager().loadTexture("cube-wireframe-white")) ;
			sweepSphereInAABB = false ;
		} else {
			sweepSphereInAABBGuiTexture.setTexture(SingletonManager.getInstance().getTextureManager().loadTexture("cube-wireframe-black")) ;
		}
		
		seconds += tpf ;
		secondsDebug += tpf ;
		secondsSubtitles += tpf ;
		
		if (secondsSubtitles > 10) {
			addNotification("Please enable subtitles !!!");
			secondsSubtitles = 0 ;
		}
		
		if (seconds > 10) {
			seconds = 0;
			if (crear) {
				// Create EntityCollisionTypeEnum.SOLID_DYNAMIC (created and removed dynamically)
				{ // Caja que sube y baja
					EntitySpec entitySpec ;
					entitySpec = new EntitySpec("box") ;
					entitySpec.setTexture("stone.png");
					entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
					entitySpec.setPosition(new Vector3f(-5f, .75f,5f)) ;
					entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
					entitySpec.setEntityController(positionEntityController);
					entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
					createEntity(entitySpec);
					this.enableDebug(positionEntityController.getEntity()); // ??????
				}
			} else {
				this.scheduleEntityForRemoval(positionEntityController.getEntity());
			}
			crear = !crear ;
		} else {
			if (!crear) {
				if (secondsDebug > 5) {
					secondsDebug = 0 ;
//					if (this.isDebugEnabled(recc.getEntity())) {
//						this.disableDebug(recc.getEntity());
//					} else {
//						this.enableDebug(recc.getEntity());
//					}
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
		
		playerDeathHandler = new PlayerDeathHandler(mainGameLoop, this) ;
		getPlayer().setCrushHandler(playerDeathHandler);
		this.enableDebug(getPlayer());
	}

	public void setSweepSphereInAABB(boolean sweepSphereInAABB) {
		this.sweepSphereInAABB = sweepSphereInAABB ;
	}

}
