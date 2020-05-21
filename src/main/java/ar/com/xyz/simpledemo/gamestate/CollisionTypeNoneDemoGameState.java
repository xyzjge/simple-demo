package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.cameracontroller.DefaultCameraController;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.collision.ESpaceUtil;
import ar.com.xyz.gameengine.debug.collision.GuiControlHelper;
import ar.com.xyz.gameengine.debug.collision.NewDetailedCollisionDataPlayer;
import ar.com.xyz.gameengine.debug.collision.ResaltarTrianguloYCaso;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.gui.control.GuiControlListener;
import ar.com.xyz.gameengine.gui.control.GuiControlManager;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.ray.RayTracerVO;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.controller.SimpleDemoEntityController;
import ar.com.xyz.simpledemo.handler.PlayerDeathHandler;
import ar.com.xyz.simpledemo.handler.RemoveEntitySweepSphereInAABBHandler;
import ar.com.xyz.simpledemo.handler.UpdateHUDSweepSphereInAABBHandler;

public class CollisionTypeNoneDemoGameState extends AbstractGameState implements InputEventListener, GuiControlListener {
	
	private static final boolean PLAY = true ;
	
	private NewDetailedCollisionDataPlayer collisionDataPlayer ;
	
	private static final String LEVEL = "simple-environment" ;
	
	private GuiTexture sweepSphereInAABBGuiTexture ;
	private boolean sweepSphereInAABB ;
	
	private PlayerDeathHandler playerDeathHandler ;
	
	private ResaltarTrianguloYCaso resaltarTrianguloYCaso = new ResaltarTrianguloYCaso(this) ;
	
	private boolean guiControlsEnabled = false ;
	
	private GuiControlHelper guiControlHelper = new GuiControlHelper() ;
	
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
		
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupPlayerAndCamera() ;
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			addInputEventListener(this);
			
			if (PLAY) {
				collisionDataPlayer = new NewDetailedCollisionDataPlayer(this, new ESpaceUtil(new Vector3f(.5f, 1f, .5f))) ;
				guiControlHelper.setup2dControls(this, this, collisionDataPlayer);
			} else {
				SingletonManager.getInstance().getCollisionDataRecorder().setActive(true);
			}
			
		}
	}
	
	float seconds = 0 ;
	float secondsDebug = 0 ;
	boolean crear = true ;
	RotationEntityController recc = null;
	
	@Override
	public void tick(float tpf) {
		
		if (PLAY) {
			collisionDataPlayer.tick(guiControlHelper);
//			return ;
		}
		
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
			null, true
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

	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		if (origin == EventOriginEnum.MOUSE) {
			if (guiControlsEnabled) {
				if (type == EventTypeEnum.RELEASED) {
					if (keyOrButton == 0) {
						Vector2f pos = getInputManager().getMousePosition() ;
						System.out.println("click boton izq (" + pos + ")");
						// float mouseY = 1 - ( (pos.y + 1f) / 2f );
						GuiControlManager.getInstance().notifyClick(pos);
					}
				}
			}
			return false ;
		}
		if (keyOrButton == Keyboard.KEY_X && type == EventTypeEnum.PRESSED) {
			if (collisionDataPlayer != null) {
				collisionDataPlayer.print();
			}
		}
		if (keyOrButton == Keyboard.KEY_Y && type == EventTypeEnum.PRESSED) {
			Vector3f rayOrigin = new Vector3f(
				getPlayer().getPosition().x, 
				getPlayer().getPosition().y + (getPlayer().getESpaceUtil().getRadius().y * 2) * getPlayer().getPorcentajeAltura(), 
				getPlayer().getPosition().z
			) ;
			
			float rotY = getPlayer().getRotation().y ;
			Vector3f rayDirection = new Vector3f(
				(float)Math.sin(Math.toRadians(rotY)), 
				(float) - Math.sin(Math.toRadians(getCamera().getPitch())) ,
				(float)Math.cos(Math.toRadians(rotY))
			) ;

			float distance = 100 ;
			RayTracerVO rayTracerVO = getMainGameLoop().getRayTracer().getTriangle(rayOrigin, rayDirection, distance, this.getAabbManager()) ;
			if (rayTracerVO != null) {
				String s = "Hit triangle " + rayTracerVO.getTriangle() + " at " + rayTracerVO.getContactPoint() ;
				addNotification(s) ;
				System.out.println(s) ;
				resaltarTrianguloYCaso.resaltarTriangulo(rayTracerVO.getTriangle());
			}
			
		}
		if (keyOrButton == Keyboard.KEY_ESCAPE && type == EventTypeEnum.RELEASED) {
			guiControlsEnabled = !guiControlsEnabled ;
			if (guiControlsEnabled) {
				releaseMouseIfGrabbed() ;
				getInputManager().stackPush();
				getInputEventListenerSet().add(this) ;
				// TODO: Que la camara deje de estar asociada al mouse ...
				// Avisarle al default camera controller que no tiene que hacer nada ... o al camera controller que este mejor dicho ...
				// getCamera().getCameraController().ignoreMouseEvents
				
				
				// Mmmm otra seria que el DCC use un IEL y sacar ese IEL de la lista de IEL activos ... lo mismo para el IEL del player ...
				
				// Esta es la que va ... que el DCC use un IEL ... <<<<<<<<<<<<<< !!!!!!!!!!
			} else {
				grabMouseIfNotGrabbed() ;
				getInputManager().stackPop();
			}
		}
		return false;
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		return true ;
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void handleEvent(String id) {
		System.out.println("Click on " + id);
		if (PLAY) {
			guiControlHelper.handleEvent(id);
			System.out.println(collisionDataPlayer.getIndex());
		}
	}


}
