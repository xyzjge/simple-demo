package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.control2d.Button2d;
import ar.com.xyz.gameengine.control2d.Panel2d;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;

/**
 * Un control que ocupe toda la pantalla y que detecte mouse over y click
 * @author alfredo
 *
 */
public class Control2DUnoGameState extends AbstractGameState {
	
	private static final float RED = 0.5f ;
	private static final float GREEN = 0.5f ;
	private static final float BLUE = 0.5f ;
	
	public Control2DUnoGameState() {
		
//		guiUno = createGuiTexture() ;
//		getGuis().add(guiUno) ;
				
//		grabMouseIfNotGrabbed() ;
		
		getDefaultColor().x = RED ;
		getDefaultColor().y = GREEN ;
		getDefaultColor().z = BLUE ;
		
		Panel2d panel2d = new Panel2d(new Vector2f(0,0), new Vector2f(1,1), this) ;
//		panel2d.add(new Button2d(new Vector2f(0.5f,0.5f), new Vector2f(.25f,.25f), "green", "red", "yellow"));
		
		// Cuadrante arriba a la izquierda
//		panel2d.add(new Button2d(new Vector2f(0.0f,0.5f), new Vector2f(.5f,.5f), "green", "red", "yellow"));
		
		// Cuadrante arriba a la derecha
//		panel2d.add(new Button2d(new Vector2f(0.5f,0.5f), new Vector2f(.5f,.5f), "green", "red", "yellow"));
		
		// Cuadrante abajo a la derecha
//		panel2d.add(new Button2d(new Vector2f(0.5f,0.0f), new Vector2f(.5f,.5f), "green", "red", "yellow"));
		
		// Cuadrante abajo a la izquierda
		panel2d.add(new Button2d(new Vector2f(0.0f,0.0f), new Vector2f(.5f,.5f), "green", "red", "yellow"));
		
		
//		panel2d.add(new Button2d(new Vector2f(0.25f,0.25f), new Vector2f(.5f,.5f), "green", "red", "yellow"));
		panel2d.show(this);

	}
	
	@Override
	public void tick(float tpf) {
		
	}
	
}
