package ar.com.xyz.simpledemo.lightandshadow;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainCharacterGameState;
import ar.com.xyz.gameengine.debug.ResaltarXYZ;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.enumerator.ColorEnum;
import ar.com.xyz.gameengine.enumerator.EntityCollisionTypeEnum;
import ar.com.xyz.gameengine.font.fontMeshCreator.GUIText;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.light.DirectionalLight;
import ar.com.xyz.gameengine.light.PointLight;
import ar.com.xyz.gameengine.light.SpotLight;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.enumerator.LigthsDemoEnum;
import ar.com.xyz.simpledemo.lightandshadow.menuitem.LightsAndShadowsMenuMenuItem;

/**
 * @author alfredo
 *
 */
public class LightAndShadowBoxWithInNormalsGameState extends AbstractMainCharacterGameState implements CrushHandler /*, InputHandler */{

	private static final int CAMERA_DISTANCE = 2;

	private static final Vector3f DIRECTIONAL_LIGHT_DIRECTION = new Vector3f(1,-1,1).normalise(null);

	private static final String LEVEL = "s-box" ;
	
	private LigthsDemoEnum state ;
	private int subState = 1 ;
	
	private PointLight pointLightUno ;
	private PointLight pointLightDos ;
	
	private SpotLight spotLight ;
	
	private ResaltarXYZ resaltarXYZ ;
	
	public LightAndShadowBoxWithInNormalsGameState() {
		setupPlayerAndCamera() ;
		
		{	// Create SOLID_STATIC for the LEVEL
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec(LEVEL) ;
			entitySpec.setTexture("stone.png");
//			entitySpec.setRotation(new Vector3f(0, 0, 0)) ;
			entitySpec.setScale(new Vector3f(15,1,15));
			// entitySpec.setPosition(new Vector3f(0,-50,0));
			entitySpec.setPosition(new Vector3f(0,0,0));
			createEntity(entitySpec);
		}
		
		{	// Create SOLID_STATIC for the box
			EntitySpec entitySpec ;
			entitySpec = new EntitySpec("box-2x2-000-in-normals") ;
			entitySpec.setTexture("stone.png");
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
		
		{
			EntitySpec entitySpec = new EntitySpec("esfera") ;
			entitySpec.setTexture(ColorEnum.MAROON.getName());
			
			Vector3f pos = Vector3f.add(new Vector3f(0, 1, 0), new Vector3f(DIRECTIONAL_LIGHT_DIRECTION.x * -CAMERA_DISTANCE, DIRECTIONAL_LIGHT_DIRECTION.y * -CAMERA_DISTANCE, DIRECTIONAL_LIGHT_DIRECTION.z * -CAMERA_DISTANCE), null) ;
			entitySpec.setPosition(pos);
//			entitySpec.setWireframe(false);
			entitySpec.setEntityCollisionType(EntityCollisionTypeEnum.NONE);
			entitySpec.setScale(new Vector3f(.25f,.25f,.25f));
//			entitySpec.setEntityController(xEntityController);
			createEntity(entitySpec);
		}
		
		//configureKeys();
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
		setupDirectionalLight();
		
		resaltarXYZ = new ResaltarXYZ(this) ;

	}

	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
			GuiTexture guiTexture2 = new GuiTexture(getMainGameLoop().getMasterRenderer().getShadowMapTexture(), new Vector2f(-.5f, .5f), new Vector2f(.25f, .25f)) ;
			guiTexture2.setFboTexture(true);
			getGuis().add(guiTexture2) ;
		}
	}
	
	@Override
	public void tick(float tpf) {

		showDistanceFromCameraToPlayer() ;
		
		if (getPlayer().getPosition().y < -100) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
		resaltarXYZ.restaltar(getPlayer().getPosition(), 3);
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
		getMainGameLoop().setNextGameState(LightsAndShadowsMenuMenuItem.getInstance().getGameStateInstance()) ;
	}
	
	private void setupDirectionalLight() {
		Vector3f color = ColorEnum.PURPLE.getColor() ;
		// Vector3f direction = new Vector3f(1,-1,1) ;
		Vector3f direction = DIRECTIONAL_LIGHT_DIRECTION ;
		// Vector3f direction = new Vector3f(0,-1,0) ;
		float intensity = 1 ;
		addNotification("PURPLE " + ColorEnum.PURPLE.getColor() + " directional light with direction " + direction + " and intensity " + intensity);
		DirectionalLight directionalLight = new DirectionalLight(color, direction, intensity) ;
		directionalLight.setCastShadows(true);
		setDirectionalLight(directionalLight) ;
	}
	
	private GUIText distanceFromCameraToPlayer ;
	
	private void showDistanceFromCameraToPlayer() {
		if (distanceFromCameraToPlayer != null) {
			distanceFromCameraToPlayer.remove();
		}
		distanceFromCameraToPlayer = new GUIText(
			"Distance From Camera To Player: " + String.format("%.02f", Vector3f.sub(getPlayer().getPosition(), getCamera().getPosition(), null).length()), 
			2f, SingletonManager.getInstance().getFontTypeManager().getFontType("tahoma"), new Vector2f(0.25f, 0.1f), 1f, false, this
		);
		distanceFromCameraToPlayer.setColour(1, 1, 1);
		distanceFromCameraToPlayer.show();
	}

}
