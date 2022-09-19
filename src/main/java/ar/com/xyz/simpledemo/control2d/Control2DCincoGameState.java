package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.control2d.Button2d;
import ar.com.xyz.gameengine.control2d.Panel2d;

/**
 * Muestra cuatro botones, uno por cuadrante. Maneja mouse over y click por separado en cada botón.
 * @author alfredo
 *
 */
public class Control2DCincoGameState extends AbstractGameState {
	
	private static final float RED = 0.5f ;
	private static final float GREEN = 0.5f ;
	private static final float BLUE = 0.5f ;
	
	public Control2DCincoGameState() {
		
		getDefaultColor().x = RED ;
		getDefaultColor().y = GREEN ;
		getDefaultColor().z = BLUE ;
		
		Panel2d panel2d = new Panel2d(this, new Vector2f(.25f,.25f), new Vector2f(.5f,.5f)) ;
		
		// Cuadrante arriba a la izquierda
		panel2d.add(new Button2d(this, new Vector2f(0.0f,0.5f), new Vector2f(.5f,.5f), "green", "red", "yellow", "white") {
			@Override
			protected void clickHandler() {
				System.out.println("En clickHandler arriba a la izquierda !!!");
			}
		});
		
		// Cuadrante arriba a la derecha
		panel2d.add(new Button2d(this, new Vector2f(0.5f,0.5f), new Vector2f(.5f,.5f), "green", "red", "yellow", "white") {
			@Override
			protected void clickHandler() {
				System.out.println("En clickHandler arriba a la derecha !!!");
			}
		});
		
		// Cuadrante abajo a la derecha
		panel2d.add(new Button2d(this, new Vector2f(0.5f,0.0f), new Vector2f(.5f,.5f), "green", "red", "yellow", "white") {
			@Override
			protected void clickHandler() {
				System.out.println("En clickHandler abajo a la derecha !!!");
			}
		});
		
		// Cuadrante abajo a la izquierda
		panel2d.add(new Button2d(this, new Vector2f(0.0f,0.0f), new Vector2f(.5f,.5f), "green", "red", "yellow", "white") {
			@Override
			protected void clickHandler() {
				System.out.println("En clickHandler abajo a la izquierda !!!");
			}
		});
		
		panel2d.show();

	}
	
	@Override
	public void tick(float tpf) {
		
	}
	
}
