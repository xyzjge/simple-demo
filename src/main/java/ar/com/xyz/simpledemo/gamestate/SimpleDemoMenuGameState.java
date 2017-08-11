package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.util.AbstractMenuGameState;
import ar.com.xyz.gameengine.util.FontSpec;

public class SimpleDemoMenuGameState extends AbstractMenuGameState {
	
	private static final String[] OPCIONES = {
		"NONE COLLISION TYPE ENTITIES DEMO", 
		"SOLID DYNAMIC COLLISION TYPE ENTITIES DEMO", 
		"SOLID DYNAMIC COLLISION TYPE ENTITIES DEMO (ROTATION)", 
		"SWEPT SPHERE COLLISION TYPE ENTITIES DEMO",
		"SWEPT SPHERE COLLISION TYPE ENTITIES DEMO (ANIMATIONS)",
		"EXIT"
	} ;
	
	private static FontSpec normalFontSpec = new FontSpec("arial", 2f, new Vector3f(.5f, .5f, .5f));
	private static FontSpec seleccionadoFontSpec = new FontSpec("arial", 2f, new Vector3f(1f, 1f, 1f));
	
	public SimpleDemoMenuGameState(AbstractMainGameLoop mainGameLoop, String sound, String background) {
		super(mainGameLoop, OPCIONES, normalFontSpec, seleccionadoFontSpec, sound, background, 0.15f) ;
	}

	@Override
	protected void handleSelection(int selectionIndex) {
		if (selectionIndex == 0) {
			mainGameLoop.setNextGameState(new CollisionTypeNoneDemoGameState(mainGameLoop)); 
		} else if (selectionIndex == 1) {
			mainGameLoop.setNextGameState(new CollisionTypeSolidDynamicDemoGameState(mainGameLoop));
		} else if (selectionIndex == 2) {
			mainGameLoop.setNextGameState(new CollisionTypeSolidDynamicRotationDemoGameState(mainGameLoop));
		} else if (selectionIndex == 3) {
			mainGameLoop.setNextGameState(new CollisionTypeSweptSphereDemoGameState(mainGameLoop));
		} else if (selectionIndex == 4) {
			mainGameLoop.setNextGameState(new CollisionTypeSweptSphereAnimationDemoGameState(mainGameLoop));
		} else {
			mainGameLoop.stop(); 
		}
	}

}
