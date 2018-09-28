package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.cameracontroller.DefaultCameraController;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.reflectivequad.ReflectiveQuadTile;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.controller.SimpleDemoEntityController;
import ar.com.xyz.simpledemo.handler.PlayerDeathHandler;
import ar.com.xyz.simpledemo.handler.RemoveEntitySweepSphereInAABBHandler;
import ar.com.xyz.simpledemo.handler.UpdateHUDSweepSphereInAABBHandler;

public class CollisionTypeNoneDemoGameState extends AbstractGameState {
	
	private static final String LEVEL = "simple-environment" ;
	
	private GuiTexture sweepSphereInAABBGuiTexture ;
	private boolean sweepSphereInAABB ;
	
	private PlayerDeathHandler playerDeathHandler ;
	
	public CollisionTypeNoneDemoGameState() {
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			createEntity(entitySpec);
		}
		
		{	// Create EntityCollisionTypeEnum.NONE for the box that is over the other boxes and update the HUD if touched by the player
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("woodenBox.jpg");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(0,4.75f,-1.5f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			RotationEntityController rec = new RotationEntityController(1,1,1) ;
			entitySpec.setEntityController(rec);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setSweepSphereInAABBHandler(new UpdateHUDSweepSphereInAABBHandler());
			createEntity(entitySpec);
			this.enableDebug(rec.getEntity());
		}
		
		{	// Create EntityCollisionTypeEnum.NONE for the box that is removable
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("woodenBox.jpg");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(6f, .75f,-6f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			RotationEntityController rec = new RotationEntityController(1,1,1) ;
			entitySpec.setEntityController(rec);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setSweepSphereInAABBHandler(new RemoveEntitySweepSphereInAABBHandler());
			createEntity(entitySpec);
			this.enableDebug(rec.getEntity());
		}
		
		
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box") ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
			entitySpec.setPosition(new Vector3f(-6f, .75f,6f)) ;
			entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
			SimpleDemoEntityController rec = new SimpleDemoEntityController() ;
			entitySpec.setEntityController(rec);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.SOLID_DYNAMIC);
//			entitySpec.setSweepSphereInAABBHandler(new RemoveEntitySweepSphereInAABBHandler(this));
			createEntity(entitySpec);
			this.enableDebug(rec.getEntity());
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);

		sweepSphereInAABBGuiTexture = new GuiTexture(
			SingletonManager.getInstance().getTextureManager().loadTexture("cube-wireframe-black"), 
			new Vector2f(0.80f, -0.76f), 
			new Vector2f(.1f, .1f)
		) ;
		
		this.getGuis().add(sweepSphereInAABBGuiTexture) ;
		
		// Este esta en plano con normal Y=1 y funciona ok
		// this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 0.01f, new Vector2f(8, 10), new Vector2f(0, 0), /*.25f*/ .75f)) ;
		
		// A ver si le pongo un toque de pitch ...
		// this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 0.01f, new Vector2f(8, 10), new Vector2f(45, 0), /*.25f*/ .75f)) ;
		
		// Este esta en plano con normal Y=-1 y funciona ok
		// this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 4 /* height */, new Vector2f(8, 10), new Vector2f(180, 0), /*.25f*/ .75f)) ;
		
		
		
		// Este esta en plano Z=1 y funciona ok
		// this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 0.01f, new Vector2f(8, 10), new Vector2f(90, 0), /*.25f*/ .75f)) ;
		
		
		// Esta funciono bien!!!!
		//this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 0.01f, new Vector2f(8, 10), new Vector2f(80, 0), /*.25f*/ .75f)) ;
		// Probe con varios valores de X entre 0 y 180 y va ... a ver mas de 180 ... parece que funciona
		this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 0.01f, new Vector2f(8, 10), new Vector2f(220, 0), /*.25f*/ .75f)) ;
		
		// Este esta en plano Z=1 y funciona ok
		// this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 0.01f, new Vector2f(8, 10), new Vector2f(270, 0), /*.25f*/ .75f)) ;
		
		// Aparentemente es lo mismo para X=1 y X=-1
		// Este esta en plano con normal X=-1 y funciona ok
		// this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 0.01f, new Vector2f(8, 10), new Vector2f(0, 90), /*.25f*/ .75f)) ;
		
		// Este esta en plano con normal X=1 y funciona ok
		// this.getReflectiveQuadTileList().add(new ReflectiveQuadTile(0, /*0*/ 4, 0.01f, new Vector2f(8, 10), new Vector2f(0, 270), /*.25f*/ .75f)) ;
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupPlayerAndCamera() ;
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
	}
	
	float seconds = 0 ;
	float secondsDebug = 0 ;
	boolean crear = true ;
	RotationEntityController recc = null;
	
	@Override
	public void tick(float tpf) {
		playerDeathHandler.tick();
		
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
				// Create EntityCollisionTypeEnum.NONE for the box that updates the HUD if touched by the player (and is created dynamically)
				EntitySpec entitySpec ;
				entitySpec = new EntitySpec("box") ;
				entitySpec.setTexture("woodenBox.jpg");
				entitySpec.setRotation(new Vector3f(0, 0, 90)) ;
				entitySpec.setPosition(new Vector3f(6,0,7f)) ;
				entitySpec.setScale(new Vector3f(.5f, .5f, .5f));
				recc = new RotationEntityController(1,1,1) ;
				entitySpec.setEntityController(recc);
				entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
				entitySpec.setSweepSphereInAABBHandler(new UpdateHUDSweepSphereInAABBHandler());
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

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(0, 100, 0),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, false,
			null
		) ;

		((DefaultCameraController)getCamera().getCameraController()).decPitch(-90);
		
		playerDeathHandler = new PlayerDeathHandler(getMainGameLoop(), this) ;
		getPlayer().setCrushHandler(playerDeathHandler);
		getPlayer().setRunSpeed(1f);
		getPlayer().setStrafeSpeed(1f);
		this.enableDebug(getPlayer());
	}

	public void setSweepSphereInAABB(boolean sweepSphereInAABB) {
		this.sweepSphereInAABB = sweepSphereInAABB ;
	}

}
