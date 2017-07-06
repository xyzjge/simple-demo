package ar.com.xyz.simpledemo;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.entity.CrushController;

/**
 * 
 * @author alfredo
 *
 */
public class SimpleDemoPlayerCrushController implements CrushController {

	private AbstractGameState gameState ;
	
	private AbstractMainGameLoop mainGameLoop ;
	
	public SimpleDemoPlayerCrushController(AbstractMainGameLoop mainGameLoop, AbstractGameState gameState) {
		this.gameState = gameState ;
		this.mainGameLoop = mainGameLoop ;
	}

	@Override
	public void crush() {

		// Asi cada vez que muere vuelve a arrancar, estaría ok ...
//		mainGameLoop.setNextGameState(new XYZDemoLevelGameState(mainGameLoop));
		
		mainGameLoop.setNextGameState(new SimpleDemoMenuGameState(mainGameLoop)) ;
/*
		// TODO: Hacer algo similar a lo que hago con xyzDemoLevelGameState.setSweepSphereInAABB(true); para que se vaya refrescando constantemente ?
		
		// Mostrar mensaje ?
		FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("harrington") ;
		GUIText fps = new GUIText("Player died", 3f, font, new Vector2f(0.64f, 0.0f), 1f, false, gameState);
		fps.setColour(1, 0, 0);
		fps.show();
		
//		gameState.scheduleEntityForRemoval(getEntity());
 */
	}

}
