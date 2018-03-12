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
		"SWEPT SPHERE COLLISION TYPE ENTITIES SIMPLE DEMO",
		"SWEPT SPHERE COLLISION TYPE ENTITIES DEMO",
		"SWEPT SPHERE COLLISION TYPE ENTITIES DEMO (3D ANIMATIONS)",
		"LOCATION AND ROTATION DEMO",
		"LOOK AT 3D DEMO",
		"NEW LOOK AT 3D VERSION DEMO",
		"2D ANIMATIONS DEMO",
		"CAMERA CONTROLLER DEMO",
		"PARENT CHILD DEMO",
		"OBJ LOADER",
		"VIEW",
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
			getMainGameLoop().setNextGameState(new CollisionTypeSweptSphereSimpleDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 4) {
			getMainGameLoop().setNextGameState(new CollisionTypeSweptSphereDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 5) {
			getMainGameLoop().setNextGameState(new CollisionTypeSweptSphereAnimationDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 6) {
			getMainGameLoop().setNextGameState(new LocationAndRotationDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 7) {
			getMainGameLoop().setNextGameState(new LookAtDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 8) {
			getMainGameLoop().setNextGameState(new NewLookAtVersionDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 9) {
			getMainGameLoop().setNextGameState(new Animation2DDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 10) {
			getMainGameLoop().setNextGameState(new CameraControllerDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 11) {
			getMainGameLoop().setNextGameState(new ParentChildDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 12) {
			getMainGameLoop().setNextGameState(new ObjLoaderDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 13) {
			getMainGameLoop().setNextGameState(new ViewStaticSceneDemoGameState(getMainGameLoop())); 
		} else {
			getMainGameLoop().stop(); 
		}
	}

}
