package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.debug.ResaltarXYZ;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.EventTypeEnum;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.gameengine.reflectivequad.ReflectiveQuadTile;
import ar.com.xyz.simpledemo.lightandshadow.menuitem.LightsAndShadowsMenuMenuItem;

/**
 * @author alfredo
 *
 */
public class ReflectiveQuadsGameState extends AbstractGameState implements CrushHandler, InputEventListener {
	
	private ResaltarXYZ resaltarXYZ ;
	
	public ReflectiveQuadsGameState() {
		setupPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the box
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box-2x2-000-in-normals") ;
			// entitySpec.setTexture("stone.png");
			entitySpec.setTexture("grid-256x256.png");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(10,10,10));
			// entitySpec.setPosition(new Vector3f(0,-50,0));
			entitySpec.setPosition(new Vector3f(0,0,0));
			createEntity(entitySpec);
		}
		
		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture(ColorEnum.MAROON.getName());
			entitySpec.setPosition(new Vector3f(0, 1, 0));
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
//			entitySpec.setScale(new Vector3f(.1f,.1f,.1f));
//			entitySpec.setEntityController(xEntityController);
			createEntity(entitySpec);
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		resaltarXYZ = new ResaltarXYZ(this) ;
		
		// addInputEventListener(this) ;

	}

	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			addInputEventListener(this) ;
		}
	}
	
	@Override
	public void tick(float tpf) {
		
		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
		resaltarXYZ.restaltar(getPlayer().getPosition(), 3);
		
		if (getReflectiveQuadTileList().isEmpty()) {
			return ;
		}
		if (getReflectiveQuadTileList().contains(reflectiveQuadTileF7)) {
			reflectiveQuadTileF7.getRotation().x+= tpf * 10 ;
		} else if (getReflectiveQuadTileList().contains(reflectiveQuadTileF8)) {
			reflectiveQuadTileF8.getRotation().y += tpf * 10;
		}
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(0, 2, 0),
			new Vector3f(0, -30, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;
		
		getPlayer().setCrushHandler(this);

		this.enableDebug(getPlayer());
	}

	@Override
	public void crush() {
//		handlePlayerDeath();
		getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(LightsAndShadowsMenuMenuItem.getInstance().getGameStateInstance()) ;
	}

	@Override
	public boolean handleEvent(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		switch (keyOrButton) {
		case Keyboard.KEY_F1:
			handleF1();
			break;
		case Keyboard.KEY_F2:
			handleF2();
			break;
		case Keyboard.KEY_F3:
			handleF3();
			break;
		case Keyboard.KEY_F4:
			handleF4();
			break;
		case Keyboard.KEY_F5:
			handleF5();
			break;
		case Keyboard.KEY_F6:
			handleF6();
			break;
		case Keyboard.KEY_F7:
			handleF7();
			break;
		case Keyboard.KEY_F8:
			handleF8();
			break;
		default:
			break;
		}
		return false;
	}

	@Override
	public boolean accept(EventOriginEnum origin, EventTypeEnum type, int keyOrButton, boolean isRepeatEvent) {
		if (origin == EventOriginEnum.KEYBOARD && type == EventTypeEnum.RELEASED) {
			return true ;
		}
		return false;
	}
	
	@Override
	public void tick() {
		
	}
	
	private ReflectiveQuadTile reflectiveQuadTileF1 = new ReflectiveQuadTile(0, 0, -9.99f, new Vector2f(10, 10), new Vector2f(0, 0), /*.25f*/ .75f) ;
	
	private void handleF1() {
		getReflectiveQuadTileList().clear() ;
		getReflectiveQuadTileList().add(reflectiveQuadTileF1) ;
	}
	
	private ReflectiveQuadTile reflectiveQuadTileF2 = new ReflectiveQuadTile(0, -9.99f, 0.0f, new Vector2f(10, 10), new Vector2f(90, 0), /*.25f*/ .75f) ;
	
	private void handleF2() {
		getReflectiveQuadTileList().clear() ;
		getReflectiveQuadTileList().add(reflectiveQuadTileF2) ;
	}
	
	private ReflectiveQuadTile reflectiveQuadTileF3 = new ReflectiveQuadTile(0, 0, 9.99f, new Vector2f(10, 10), new Vector2f(180, 0), /*.25f*/ .75f) ;
	
	private void handleF3() {
		getReflectiveQuadTileList().clear() ;
		getReflectiveQuadTileList().add(reflectiveQuadTileF3) ;
	}
	
	private ReflectiveQuadTile reflectiveQuadTileF4 = new ReflectiveQuadTile(0, 9.99f, 0.0f, new Vector2f(10, 10), new Vector2f(270, 0), /*.25f*/ .75f) ;
	
	private void handleF4() {
		getReflectiveQuadTileList().clear() ;
		getReflectiveQuadTileList().add(reflectiveQuadTileF4) ;
	}
	
	private ReflectiveQuadTile reflectiveQuadTileF5 = new ReflectiveQuadTile(9.99f, 0f, 0.0f, new Vector2f(10, 10), new Vector2f(90, 90), /*.25f*/ .75f) ;
	
	private void handleF5() {
		getReflectiveQuadTileList().clear() ;
		getReflectiveQuadTileList().add(reflectiveQuadTileF5) ;
	}
	
	private ReflectiveQuadTile reflectiveQuadTileF6 = new ReflectiveQuadTile(-9.99f, 0f, 0.0f, new Vector2f(10, 10), new Vector2f(90, 90 + 180), /*.25f*/ .75f) ;
	
	private void handleF6() {
		getReflectiveQuadTileList().clear() ;
		getReflectiveQuadTileList().add(reflectiveQuadTileF6) ;
	}
	
	private ReflectiveQuadTile reflectiveQuadTileF7 = new ReflectiveQuadTile(0.0f, 0.0f, 0.0f, new Vector2f(10, 10), new Vector2f(0, 0), /*.25f*/ .75f) ;
	
	private void handleF7() {
		getReflectiveQuadTileList().clear() ;
		getReflectiveQuadTileList().add(reflectiveQuadTileF7) ;
	}
	
	private ReflectiveQuadTile reflectiveQuadTileF8 = new ReflectiveQuadTile(0.0f, 0.0f, 0.0f, new Vector2f(10, 10), new Vector2f(90, 0), /*.25f*/ .75f) ;
	
	private void handleF8() {
		getReflectiveQuadTileList().clear() ;
		getReflectiveQuadTileList().add(reflectiveQuadTileF8) ;
	}
	
}
