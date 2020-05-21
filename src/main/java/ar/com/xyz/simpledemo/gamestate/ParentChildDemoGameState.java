package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.client.entitycontroller.DummyEntityController;
import ar.com.xyz.gameengine.client.entitycontroller.PositionEntityController;
import ar.com.xyz.gameengine.client.entitycontroller.RotationEntityController;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.EntityController;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.light.DirectionalLight;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

public class ParentChildDemoGameState extends AbstractGameState implements CrushHandler, InputEventListener {
	
	private static final float Y_ROT_VEL = 10;

	private static final String LEVEL = "s-box" ;
	
	EntityController grandparentEntityController = null ;
	EntityController parentEntityController = null ;
	EntityController childEntityController = null ;
	
	public ParentChildDemoGameState() {
		setupPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(0,-50,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for X+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("x-box-texture");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
//			entitySpec.setScale(new Vector3f(50,50,50));
			entitySpec.setPosition(new Vector3f(20,1,0));
			createEntity(entitySpec);
		}

		{	// Create SOLID_STATIC for X-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-x-box-texture");
			entitySpec.setPosition(new Vector3f(-20,1,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for Y+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("y-box-texture");
			entitySpec.setPosition(new Vector3f(0,20,0));
			createEntity(entitySpec);
		}
		{	// Create SOLID_STATIC for Y-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-y-box-texture");
			entitySpec.setPosition(new Vector3f(0,-20,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for Z+
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("z-box-texture");
			entitySpec.setPosition(new Vector3f(0,1,20));
			createEntity(entitySpec);
		}
		{	// Create SOLID_STATIC for Z-
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("minus-z-box-texture");
			entitySpec.setPosition(new Vector3f(0,1,-20));
			createEntity(entitySpec);
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		DirectionalLight directionalLight = new DirectionalLight(new Vector3f(.5f,.5f,.5f), new Vector3f(-10000,-10000,-10000), 1) ;
		directionalLight.setCastShadows(true);
		setDirectionalLight(directionalLight);
	}
	
	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			addInputEventListener(this);
		}
	}
	
/*
	private void demo1() {
		// Create parent and child
		parentEntityController = new ParentEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setPosition(new Vector3f(15,1,15));
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(parentEntityController);
			createEntity(entitySpec);
		}
		childEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setPosition(new Vector3f(3,0,0));
//			entitySpec.setPosition(new Vector3f(15,3,15));
//			entitySpec.setPosition(new Vector3f(0,0,0));
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			
			entitySpec.setEntityController(childEntityController);
			createEntity(entitySpec);
			childEntityController.getEntity().setParent(parentEntityController.getEntity());
		}
	}
*/
	
	private void demo1() {
		grandparentEntityController = new RotationEntityController(0, Y_ROT_VEL, 0) ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(grandparentEntityController);
			entitySpec.setPosition(new Vector3f(0,1,0));
			createEntity(entitySpec);
		}
		parentEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(parentEntityController);
			entitySpec.setPosition(new Vector3f(4,0,0));
			createEntity(entitySpec);
			parentEntityController.getEntity().setParent(grandparentEntityController.getEntity());
		}
		childEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(childEntityController);
			entitySpec.setPosition(new Vector3f(4,0,0));
			createEntity(entitySpec);
			childEntityController.getEntity().setParent(parentEntityController.getEntity());
		}
	}
	
	private void demo2() {
		grandparentEntityController = new RotationEntityController(0, Y_ROT_VEL, 0) ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(grandparentEntityController);
			entitySpec.setPosition(new Vector3f(0,1,0));
			createEntity(entitySpec);
		}
		parentEntityController = new RotationEntityController(0, Y_ROT_VEL, 0) ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(parentEntityController);
			entitySpec.setPosition(new Vector3f(4,0,0));
			createEntity(entitySpec);
			parentEntityController.getEntity().setParent(grandparentEntityController.getEntity());
		}
		childEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(childEntityController);
			entitySpec.setPosition(new Vector3f(4,0,0));
			createEntity(entitySpec);
			childEntityController.getEntity().setParent(parentEntityController.getEntity());
		}
	}
	
	private void demo3() {
		grandparentEntityController = new PositionEntityController(new Vector3f(10, 0,0), 20) ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(grandparentEntityController);
			entitySpec.setPosition(new Vector3f(0,1,0));
			createEntity(entitySpec);
		}
		parentEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(parentEntityController);
			entitySpec.setPosition(new Vector3f(4,0,0));
			createEntity(entitySpec);
			parentEntityController.getEntity().setParent(grandparentEntityController.getEntity());
		}
		childEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(childEntityController);
			entitySpec.setPosition(new Vector3f(4,0,0));
			createEntity(entitySpec);
			childEntityController.getEntity().setParent(parentEntityController.getEntity());
		}
	}
	
	private void demo4() {
		grandparentEntityController = new PositionEntityController(new Vector3f(10, 0,0), 20) ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(grandparentEntityController);
			entitySpec.setPosition(new Vector3f(0,1,0));
			createEntity(entitySpec);
		}
		parentEntityController = new PositionEntityController(new Vector3f(10, 0,0), 20) ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(parentEntityController);
			entitySpec.setPosition(new Vector3f(4,0,0));
			createEntity(entitySpec);
			parentEntityController.getEntity().setParent(grandparentEntityController.getEntity());
		}
		childEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(childEntityController);
			entitySpec.setPosition(new Vector3f(4,0,0));
			createEntity(entitySpec);
			childEntityController.getEntity().setParent(parentEntityController.getEntity());
		}
	}
	@SuppressWarnings("unused")
	private void demo2_() {
		// Create parent and child
		grandparentEntityController = new RotationEntityController(0, Y_ROT_VEL, 0) ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setPosition(new Vector3f(5,1,5));
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			
			entitySpec.setEntityController(grandparentEntityController);
			createEntity(entitySpec);
		}
		parentEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setPosition(new Vector3f(3,0,0));
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(parentEntityController);
			createEntity(entitySpec);
			parentEntityController.getEntity().setParent(grandparentEntityController.getEntity());
		}
		childEntityController = new DummyEntityController() ;
		{
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("box-xyz-texture");
			entitySpec.setPosition(new Vector3f(3,0,0));
//			entitySpec.setPosition(new Vector3f(15,3,15));
//			entitySpec.setPosition(new Vector3f(0,0,0));
			entitySpec.setScale(new Vector3f(.5f,.5f,.5f));
			entitySpec.setModelRotation(new Vector3f(0,90,0));
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setEntityController(childEntityController);
			createEntity(entitySpec);
			childEntityController.getEntity().setParent(parentEntityController.getEntity());
		}
	}

	float secondsSubtitles = 100 ;
	
	@Override
	public void tick(float tpf) {
		if (getPlayer().getPosition().y < -10) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
		secondsSubtitles += tpf ;
		
		if (secondsSubtitles > 10) {
			addNotification("Please enable subtitles !!!");
			secondsSubtitles = 0 ;
		}
		
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(5, 5, 5),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;
		
		getPlayer().setCrushHandler(this);
		
		SingletonManager.getInstance().getEntityUtil().lookAt(getPlayer(), new Vector3f(0, 0, 0));

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

	private void cleanup() {
		if (grandparentEntityController != null) {
			scheduleEntityForRemoval(grandparentEntityController.getEntity());
			grandparentEntityController = null ;
		}
		if (parentEntityController != null) {
			scheduleEntityForRemoval(parentEntityController.getEntity());
			parentEntityController = null ;
		}
		if (childEntityController != null) {
			scheduleEntityForRemoval(childEntityController.getEntity());
			childEntityController = null ;
		}
		
	}

	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		switch (keyOrButton) {
		case Keyboard.KEY_1:
			cleanup() ;
			demo1() ;
			break;
		case Keyboard.KEY_2:
			cleanup() ;
			demo2() ;
			break;
		case Keyboard.KEY_3:
			cleanup();
			demo3() ;
			break;
		case Keyboard.KEY_4:
			cleanup();
			demo4() ;
			break;
		case Keyboard.KEY_5:
			break;
		case Keyboard.KEY_6:
			break;
		case Keyboard.KEY_9:
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		if (origin == EventOriginEnum.KEYBOARD) {
			return true ;
		}
		return false;
	}

	@Override
	public void tick() {
		
	}

}
