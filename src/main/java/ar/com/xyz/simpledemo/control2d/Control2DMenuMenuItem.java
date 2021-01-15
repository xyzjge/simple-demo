package ar.com.xyz.simpledemo.control2d;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;

public class Control2DMenuMenuItem extends MenuItem {

	public static Control2DMenuMenuItem getInstance() { return control2DMenuMenuItem ; }
	
	private static final Control2DMenuMenuItem control2DMenuMenuItem = new Control2DMenuMenuItem() ;
	
	private static final Control2DMenuGameState control2DMenuGameState = new Control2DMenuGameState("ZIPCLOSE.wav", "stone.png") ;
	
	public Control2DMenuMenuItem() {
		setText("2D CONTROLS");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return control2DMenuGameState ;
	}

}
