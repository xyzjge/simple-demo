package ar.com.xyz.simpledemo;

import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.util.AbstractMenuGameState;

public class SimpleDemoMenuGameState extends AbstractMenuGameState {
	
	private static final String[] OPCIONES = {"PLAY AGAIN", "EXIT"} ;
	
	public SimpleDemoMenuGameState(AbstractMainGameLoop mainGameLoop, String font, String sound, String background) {
		super(mainGameLoop, OPCIONES, font, sound, background) ;
	}

	@Override
	protected void handleSelection(int selectionIndex) {
		if (selectionIndex == 0) {
			mainGameLoop.setNextGameState(new XYZDemoLevelGameState(mainGameLoop)); 
		} else {
			mainGameLoop.stop(); 
		}
	}

}
