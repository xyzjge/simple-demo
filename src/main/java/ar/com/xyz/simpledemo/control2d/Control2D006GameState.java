package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.control2d.Button2d;
import ar.com.xyz.gameengine.control2d.Check2d;
import ar.com.xyz.gameengine.control2d.Panel2d;
import ar.com.xyz.gameengine.control2d.Text2d;
import ar.com.xyz.gameengine.control2d.builder.Button2dBuilder;
import ar.com.xyz.gameengine.control2d.builder.Check2dBuilder;
import ar.com.xyz.gameengine.control2d.builder.Control2dEventHandler;

/**
 * Mostrar un check y un boton y cuando se clickee el boton que imprima el estado del radio
 * @author alfredo
 *
 */
public class Control2D006GameState extends AbstractGameState {
	
	private static final float RED = 0.5f ;
	private static final float GREEN = 0.5f ;
	private static final float BLUE = 0.5f ;
	
	public Control2D006GameState() {
		
		getDefaultColor().x = RED ;
		getDefaultColor().y = GREEN ;
		getDefaultColor().z = BLUE ;
		
		Panel2d panel2d = new Panel2d(this, new Vector2f(0,0), new Vector2f(1,1)) ;
		
		final Text2d text = new Text2d(this, new Vector2f(0.5f,0.75f), new Vector2f(.5f,.25f), "Voces:") ;
		panel2d.add(text);
		
		final Check2d check = new Check2dBuilder(this)
			.setOriginAndSize(new Vector2f(0.5f,0.5f), new Vector2f(.5f,.25f))
			.setTextures("green", null, "red", "gray")
			.setLabel("Uno")
			.build() ;
		panel2d.add(check);
		
		// Cuadrante arriba a la izquierda
//		panel2d.add(new Button2d(new Vector2f(0.0f,0.5f), new Vector2f(.5f,.5f), "green", "red", "yellow"));
		
		// Cuadrante arriba a la derecha
//		panel2d.add(new Button2d(new Vector2f(0.5f,0.5f), new Vector2f(.5f,.5f), "green", "red", "yellow"));
		
		// Cuadrante abajo a la derecha
//		panel2d.add(new Button2d(new Vector2f(0.5f,0.0f), new Vector2f(.5f,.5f), "green", "red", "yellow"));
		
		// Cuadrante abajo a la izquierda
		Control2dEventHandler buttonEventHandler = new Control2dEventHandler() {
			@Override
			public void clickHandler() {
				System.out.println("En clickHandler !!! " + check.isSelected());
			}
		};
		Button2d button = new Button2dBuilder(this)
			.setControl2dEventHandler(buttonEventHandler)
			.setOriginAndSize(new Vector2f(0.0f,0.0f), new Vector2f(.5f,.5f))
			.setTextures("green", "red", "yellow", "white")
			.setLabel("Label !!!")
			.build() ;
		panel2d.add(button);
		
		panel2d.show();

	}
	
	@Override
	public void tick(float tpf) {
		
	}
	
}
