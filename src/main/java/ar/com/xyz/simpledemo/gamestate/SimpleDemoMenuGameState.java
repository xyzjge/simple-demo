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
		"SWEPT SPHERE COLLISION TYPE ENTITIES DEMO (3D ANIMATIONS)",
		"LOCATION AND ROTATION DEMO",
		"LOOK AT 3D DEMO",
		"2D ANIMATIONS DEMO",
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
			getMainGameLoop().setNextGameState(new CollisionTypeNoneDemoGameState(getMainGameLoop())); 
		} else if (selectionIndex == 1) {
			getMainGameLoop().setNextGameState(new CollisionTypeSolidDynamicDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 2) {
			getMainGameLoop().setNextGameState(new CollisionTypeSolidDynamicRotationDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 3) {
			getMainGameLoop().setNextGameState(new CollisionTypeSweptSphereDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 4) {
			getMainGameLoop().setNextGameState(new CollisionTypeSweptSphereAnimationDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 5) {
			getMainGameLoop().setNextGameState(new LocationAndRotationDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 6) {
			getMainGameLoop().setNextGameState(new LookAtDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 7) {
			getMainGameLoop().setNextGameState(new Animation2DDemoGameState(getMainGameLoop())); 
		} else {
			getMainGameLoop().stop(); 
		}
	}

}
