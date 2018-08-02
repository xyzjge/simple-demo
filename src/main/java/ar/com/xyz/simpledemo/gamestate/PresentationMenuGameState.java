package ar.com.xyz.simpledemo.gamestate;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.util.AbstractMenuGameState;
import ar.com.xyz.gameengine.util.FontSpec;
import ar.com.xyz.simpledemo.presentation.ColladaAnimationDemoGameState;
import ar.com.xyz.simpledemo.presentation.ObjMultipleTextureGameState;
import ar.com.xyz.simpledemo.presentation.ObjSingleTextureGameState;
import ar.com.xyz.simpledemo.presentation.PositionRotationScaleGameState;
import ar.com.xyz.simpledemo.presentation.PresentationGameState;
import ar.com.xyz.simpledemo.presentation.parentchild.ParentChildRelationshipGameState;

public class PresentationMenuGameState extends AbstractMenuGameState {
	
	private static final String[] OPCIONES = {
		"PRESENTATION",
		"OBJ (SINGLE TEXTURE)",
		"OBJ (MULTIPLE TEXTURE)",
		"DAE (SINGLE TEXTURE)",
		"POSITION ROTATION SCALE",
		"PARENT CHILD RELATIONSHIP",
		"MAIN MENU"
	} ;
	
	private static FontSpec normalFontSpec = new FontSpec("arial", 2f, new Vector3f(.5f, .5f, .5f));
	private static FontSpec seleccionadoFontSpec = new FontSpec("arial", 2f, new Vector3f(1f, 1f, 1f));
	
	private SimpleDemoMenuGameState simpleDemoMenuGameState ;
	
	public PresentationMenuGameState(AbstractMainGameLoop mainGameLoop, String sound, String background, SimpleDemoMenuGameState simpleDemoMenuGameState) {
		super(mainGameLoop, OPCIONES, normalFontSpec, seleccionadoFontSpec, sound, background, 0.15f) ;
		this.simpleDemoMenuGameState = simpleDemoMenuGameState ;
	}

	@Override
	protected void handleSelection(int selectionIndex) {
		if (selectionIndex == 0) {
			getMainGameLoop().setNextGameState(new PresentationGameState(getMainGameLoop())); 
		} else if (selectionIndex == 1) {
			getMainGameLoop().setNextGameState(new ObjSingleTextureGameState(getMainGameLoop()));
		} else if (selectionIndex == 2) {
			getMainGameLoop().setNextGameState(new ObjMultipleTextureGameState(getMainGameLoop()));
		} else if (selectionIndex == 3) {
			getMainGameLoop().setNextGameState(new ColladaAnimationDemoGameState(getMainGameLoop()));
		} else if (selectionIndex == 4) {
			getMainGameLoop().setNextGameState(new PositionRotationScaleGameState(getMainGameLoop()));
		} else if (selectionIndex == 5) {
			getMainGameLoop().setNextGameState(new ParentChildRelationshipGameState(getMainGameLoop()));
		} else {
			getMainGameLoop().setNextGameState(simpleDemoMenuGameState); 
		}
	}

}
