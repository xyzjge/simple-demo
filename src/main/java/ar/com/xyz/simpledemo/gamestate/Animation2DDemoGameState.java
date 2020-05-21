package ar.com.xyz.simpledemo.gamestate;

import java.util.HashMap;
import java.util.Map;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.entity.CrushHandler;
import ar.com.xyz.gameengine.entity.spec.EntitySpec;
import ar.com.xyz.gameengine.gui.AnimatedGuiTexture;
import ar.com.xyz.gameengine.gui.SpriteSheetAnimationSpec;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.gameengine.util.SpriteSheetAnimationEventHandler;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;

public class Animation2DDemoGameState extends AbstractGameState implements CrushHandler, SpriteSheetAnimationEventHandler {
	
	private static final String LEVEL = "s-box" ;
	
	private static final String ANIM_DYNAMITE_BUNDLES = "dynamite-bundles";
	private static final String ANIM_DYNAMITE_BUNDLES_LOOP = "dynamite-bundles-loop";
	// private static final String ANIM_DYNAMITE_BUNDLES_TA = "dynamite-bundles-ta";
	private static final String ANIM_DYNAMITE_BUNDLES_TA = "animated-texture-ta";
	
	private AnimatedGuiTexture dynamiteBundlesAnimatedTexture ;
	
	public Animation2DDemoGameState() {
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
		
		{
			Map<String, SpriteSheetAnimationSpec> spriteSheetAnimationSpecMap = new HashMap<String, SpriteSheetAnimationSpec>() ;
			SpriteSheetAnimationSpec dynamiteBundlesSpec = new SpriteSheetAnimationSpec(ANIM_DYNAMITE_BUNDLES, new int[]{6,7,8,9}) ;
			dynamiteBundlesSpec.setSpriteSheetAnimationEventHandler(this);
			spriteSheetAnimationSpecMap.put(ANIM_DYNAMITE_BUNDLES, dynamiteBundlesSpec) ;
			dynamiteBundlesSpec.setDebug(true);
			SpriteSheetAnimationSpec dynamiteBundlesLoopSpec = new SpriteSheetAnimationSpec(ANIM_DYNAMITE_BUNDLES_LOOP, new int[]{1,2,3,4}) ;
			dynamiteBundlesLoopSpec.setSpriteSheetAnimationEventHandler(this);
			spriteSheetAnimationSpecMap.put(ANIM_DYNAMITE_BUNDLES_LOOP, dynamiteBundlesLoopSpec) ;
			dynamiteBundlesLoopSpec.setDebug(true);
			
			dynamiteBundlesAnimatedTexture = new AnimatedGuiTexture(
				SingletonManager.getInstance().getTextureManager().loadTexture(ANIM_DYNAMITE_BUNDLES_TA),
				new Vector2f(0f, -0.52f),
				new Vector2f(0.4f, 0.4f),
				4, spriteSheetAnimationSpecMap, ANIM_DYNAMITE_BUNDLES, 1f, this
			) ;
			getGuis().add(dynamiteBundlesAnimatedTexture) ;
//			dynamiteBundlesAnimatedTexture.setEnabled(false);
//			weaponTypeAnimatedGuiTextureMap.put(WeaponTypeEnum.DYNAMITE_BUNDLES, dynamiteBundlesAnimatedTexture) ;
		}
		
		grabMouseIfNotGrabbed() ;
		
		setShowFps(true);
		setShowPlayerPosition(true);
		
	}

	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
		if (getInputManager().getNumberOfConfiguredInputEventListener() == 0) {
			setupInputEventListeners(getMainGameLoop(), getPlayer(), null) ;
		}
	}

	float secondsAnim = 0 ;
	
	@Override
	public void tick(float tpf) {
		if (getPlayer().getPosition().y < -10) {
			handlePlayerDeath() ;
		}
		
//		getHandlePlayerInput().handlePlayerInput();
		
		dynamiteBundlesAnimatedTexture.update(tpf);
//		secondsAnim += tpf ;
//		if (secondsAnim > 5) {
//			secondsAnim = 0 ;
//			dynamiteBundlesAnimatedTexture.addAnimRequest(ANIM_DYNAMITE_BUNDLES_LOOP, 1f);
//			dynamiteBundlesAnimatedTexture.addAnimRequest(ANIM_DYNAMITE_BUNDLES, 1f);
//		}
	}

	private void setupPlayerAndCamera() {

		setupPlayerAndCamera(
			new Vector3f(0, 0, 0),
			new Vector3f(0, 0, 0),
			new Vector3f(1, 1, 1),
			true,
			new Vector3f(.5f, 1f, .5f),
			new Vector3f(.5f, .5f, .5f), null, true,
			null, true
		) ;
		
		getPlayer().setCrushHandler(this);

//		this.enableDebug(getPlayer());
	}

	@Override
	public void crush() {
		getPlayer().setPosition(new Vector3f(10, 10, 10));
	}

	private void handlePlayerDeath() {
		getMainGameLoop().setNextGameState(SimpleDemoMenuMenuItem.getInstance().getGameStateInstance()) ;
	}
	
	@Override
	public void animationLoopEnd(String anim) {
		if (anim.equals(ANIM_DYNAMITE_BUNDLES_LOOP)) {
			dynamiteBundlesAnimatedTexture.addAnimRequest(ANIM_DYNAMITE_BUNDLES, 1f);
		} else if (anim.equals(ANIM_DYNAMITE_BUNDLES)) {
			dynamiteBundlesAnimatedTexture.addAnimRequest(ANIM_DYNAMITE_BUNDLES_LOOP, 1f);
		}
	}

	@Override
	public void animationProgress(String anim, float frameIndex) {
		if (anim.equals(ANIM_DYNAMITE_BUNDLES_LOOP)) {
		}
	}

}
