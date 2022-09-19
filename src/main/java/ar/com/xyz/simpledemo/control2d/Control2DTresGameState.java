package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.control2d.Panel2d;
import ar.com.xyz.gameengine.control2d.TextArea2d;

/**
 * Cuatro botones y un text area en la que se vaya mostrando la actividad.
 * El 1ro limpia el text area y los otros tres escriben en el text area.
 * @author alfredo
 *
 */
public class Control2DTresGameState extends AbstractGameState {
	
	private static final String NORMAL = "olive";
	private static final String MOUSE_OVER = "lime";
	private static final String CLICK = "green";
	private static final String DISABLED = "red";
	
	private static final float RED = 0.5f ;
	private static final float GREEN = 0.5f ;
	private static final float BLUE = 0.5f ;
	
	public Control2DTresGameState() {
		
//		guiUno = createGuiTexture() ;
//		getGuis().add(guiUno) ;
				
//		grabMouseIfNotGrabbed() ;
		
		getDefaultColor().x = RED ;
		getDefaultColor().y = GREEN ;
		getDefaultColor().z = BLUE ;
		
		Panel2d panel2d = new Panel2d(this, new Vector2f(0,0), new Vector2f(1,1)) ;
		
		TextArea2d textArea2d = new TextArea2d(this, new Vector2f(0.0f,0.0f), new Vector2f(1f,.5f), NORMAL) ;
		
		// Cuadrante arriba a la izquierda
		BotonLimpiar botonLimpiar = new BotonLimpiar(this, new Vector2f(0.0f,0.75f), new Vector2f(.5f,.25f), NORMAL, MOUSE_OVER, CLICK, DISABLED, textArea2d) ;
		panel2d.add( botonLimpiar ) ;
//		panel2d.add(new Button2d(new Vector2f(0.0f,0.75f), new Vector2f(.5f,.25f), NORMAL, MOUSE_OVER, CLICK) {
//			@Override
//			protected void clickHandler() {
//				System.out.println("En clickHandler arriba a la izquierda !!!");
//			}
//		});
		
		// Cuadrante arriba a la derecha
		BotonEscribir botonEscribir = new BotonEscribir(this, new Vector2f(0.5f,0.75f), new Vector2f(.5f,.25f), NORMAL, MOUSE_OVER, CLICK, DISABLED, textArea2d,  "Superior derecho") ;
		panel2d.add( botonEscribir ) ;
		
		// Cuadrante abajo a la derecha
		BotonEscribir botonEscribirInferiorDerecho = new BotonEscribir(this, new Vector2f(0.5f,0.5f), new Vector2f(.5f,.25f), NORMAL, MOUSE_OVER, CLICK, DISABLED, textArea2d,  "Inferior derecho") ;
		panel2d.add(botonEscribirInferiorDerecho);
		
		// Cuadrante abajo a la izquierda
		BotonEscribir botonEscribirInferiorIzquierdo = new BotonEscribir(this, new Vector2f(0.0f,0.5f), new Vector2f(.5f,.25f), NORMAL, MOUSE_OVER, CLICK, DISABLED, textArea2d,  "Inferior izquierdo") ;
		panel2d.add(botonEscribirInferiorIzquierdo);
		
		panel2d.add(textArea2d);
		
		panel2d.show();

		textArea2d.addTextLine("Hola");
		textArea2d.addTextLine("que tal");
		textArea2d.addTextLine("como estas?");
	}
	
	@Override
	public void tick(float tpf) {
		
	}
	
}
