package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.control2d.Button2d;
import ar.com.xyz.gameengine.control2d.Panel2d;
import ar.com.xyz.gameengine.control2d.builder.Button2dBuilder;
import ar.com.xyz.gameengine.control2d.builder.Control2dEventHandler;

/**
 * Muestra cuatro botones (pero achicados y agrupados en el centro), uno por cuadrante. Maneja mouse over y click por separado en cada botón.
 * @author alfredo
 *
 */
public class Control2D005GameState extends AbstractGameState {
	
	private static final float RED = 0.5f ;
	private static final float GREEN = 0.5f ;
	private static final float BLUE = 0.5f ;
	
	public Control2D005GameState() {
		
		getDefaultColor().x = RED ;
		getDefaultColor().y = GREEN ;
		getDefaultColor().z = BLUE ;
		
		Panel2d panel2d = new Panel2d(this, new Vector2f(.25f,.25f), new Vector2f(.5f,.5f)) ;
		{
			// Cuadrante arriba a la izquierda
			Control2dEventHandler buttonEventHandler = new Control2dEventHandler() {
				@Override
				public void clickHandler() {
					System.out.println("En clickHandler arriba a la izquierda !!!");
				}
			};
			Button2d button = new Button2dBuilder(this)
				.setControl2dEventHandler(buttonEventHandler)
				.setOriginAndSize(new Vector2f(0.0f,0.5f), new Vector2f(.5f,.5f))
				.setTextures("green", "red", "yellow", "white")
				.setLabel("Label !!!")
				.build() ;
			panel2d.add(button);
		}
		{
			// Cuadrante arriba a la derecha
			Control2dEventHandler buttonEventHandler = new Control2dEventHandler() {
				@Override
				public void clickHandler() {
					System.out.println("En clickHandler arriba a la derecha !!!");
				}
			};
			Button2d button = new Button2dBuilder(this)
				.setControl2dEventHandler(buttonEventHandler)
				.setOriginAndSize(new Vector2f(0.5f,0.5f), new Vector2f(.5f,.5f))
				.setTextures("green", "red", "yellow", "white")
				.setLabel("Label !!!")
				.build() ;
			panel2d.add(button);
		}
		{
			// Cuadrante abajo a la derecha
			Control2dEventHandler buttonEventHandler = new Control2dEventHandler() {
				@Override
				public void clickHandler() {
					System.out.println("En clickHandler abajo a la derecha !!!");
				}
			};
			Button2d button = new Button2dBuilder(this)
				.setControl2dEventHandler(buttonEventHandler)
				.setOriginAndSize(new Vector2f(0.5f,0.0f), new Vector2f(.5f,.5f))
				.setTextures("green", "red", "yellow", "white")
				.setLabel("Label !!!")
				.build() ;
			panel2d.add(button);
		}
		{
			// Cuadrante abajo a la izquierda
			Control2dEventHandler buttonEventHandler = new Control2dEventHandler() {
				@Override
				public void clickHandler() {
					System.out.println("En clickHandler abajo a la izquierda !!!");
				}
			};
			Button2d button = new Button2dBuilder(this)
				.setControl2dEventHandler(buttonEventHandler)
				.setOriginAndSize(new Vector2f(0.0f,0.0f), new Vector2f(.5f,.5f))
				.setTextures("green", "red", "yellow", "white")
				.setLabel("Label !!!")
				.build() ;
			panel2d.add(button);
		}
		panel2d.show();

	}
	
	@Override
	public void tick(float tpf) {
		
	}
	
}
