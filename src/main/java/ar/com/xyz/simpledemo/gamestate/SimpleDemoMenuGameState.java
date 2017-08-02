package ar.com.xyz.simpledemo.gamestate;

import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.util.AbstractMenuGameState;

public class SimpleDemoMenuGameState extends AbstractMenuGameState {
	
	private static final String[] OPCIONES = {"COLLISION TYPE NONE DEMO", "COLLISION TYPE SOLID DYNAMIC DEMO", "EXIT"} ;
	
	public SimpleDemoMenuGameState(AbstractMainGameLoop mainGameLoop, String font, String sound, String background) {
		super(mainGameLoop, OPCIONES, font, sound, background) ;
	}

	@Override
	protected void handleSelection(int selectionIndex) {
		if (selectionIndex == 0) {
			mainGameLoop.setNextGameState(new CollisionTypeNoneDemoGameState(mainGameLoop)); 
		} else if (selectionIndex == 1) {
			mainGameLoop.setNextGameState(new CollisionTypeSolidDynamicDemoGameState(mainGameLoop));
		} else {
			mainGameLoop.stop(); 
		}
	}

}
