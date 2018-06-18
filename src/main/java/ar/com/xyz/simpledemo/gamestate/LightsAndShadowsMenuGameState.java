package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.util.AbstractMenuGameState;
import ar.com.xyz.gameengine.util.FontSpec;
import ar.com.xyz.simpledemo.lightandshadow.LightAndShadowBoxWithInNormalsGameState;
import ar.com.xyz.simpledemo.lightandshadow.LightAndShadowGameState;

public class LightsAndShadowsMenuGameState extends AbstractMenuGameState {
	
	private static final String[] OPCIONES = {
		"SIMPLE",
		"INSIDE NORMALS",
		"MAIN MENU"
	} ;
	
	private static FontSpec normalFontSpec = new FontSpec("arial", 2f, new Vector3f(.5f, .5f, .5f));
	private static FontSpec seleccionadoFontSpec = new FontSpec("arial", 2f, new Vector3f(1f, 1f, 1f));
	
	private SimpleDemoMenuGameState simpleDemoMenuGameState ;
	
	public LightsAndShadowsMenuGameState(AbstractMainGameLoop mainGameLoop, String sound, String background, SimpleDemoMenuGameState simpleDemoMenuGameState) {
		super(mainGameLoop, OPCIONES, normalFontSpec, seleccionadoFontSpec, sound, background, 0.15f) ;
		this.simpleDemoMenuGameState = simpleDemoMenuGameState ;
	}

	@Override
	protected void handleSelection(int selectionIndex) {
		if (selectionIndex == 0) {
			getMainGameLoop().setNextGameState(new LightAndShadowGameState(getMainGameLoop())); 
		} else if (selectionIndex == 1) {
			getMainGameLoop().setNextGameState(new LightAndShadowBoxWithInNormalsGameState(getMainGameLoop()));
		} else {
			getMainGameLoop().setNextGameState(simpleDemoMenuGameState); 
		}
	}

}
