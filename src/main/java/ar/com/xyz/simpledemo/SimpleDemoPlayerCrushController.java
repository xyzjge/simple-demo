package ar.com.xyz.simpledemo;

import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.entity.CrushController;
import ar.com.xyz.gameengine.font.fontMeshCreator.FontType;
import ar.com.xyz.gameengine.font.fontMeshCreator.GUIText;
import ar.com.xyz.gameengine.singleton.SingletonManager;

/**
 * 
 * @author alfredo
 *
 */
public class SimpleDemoPlayerCrushController implements CrushController {

	private AbstractGameState gameState ;
	
	public SimpleDemoPlayerCrushController(AbstractGameState gameState) {
		this.gameState = gameState ;
	}

	@Override
	public void crush() {

		// TODO: Hacer algo similar a lo que hago con xyzDemoLevelGameState.setSweepSphereInAABB(true); para que se vaya refrescando constantemente ?
		
		// Mostrar mensaje ?
		FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("harrington") ;
		GUIText fps = new GUIText("Player died", 3f, font, new Vector2f(0.64f, 0.0f), 1f, false, gameState);
		fps.setColour(1, 0, 0);
		fps.show();
		
//		gameState.scheduleEntityForRemoval(getEntity());
	}

}
