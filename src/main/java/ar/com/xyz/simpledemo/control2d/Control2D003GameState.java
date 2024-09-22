package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.control2d.Button2d;
import ar.com.xyz.gameengine.control2d.Panel2d;
import ar.com.xyz.gameengine.control2d.TextArea2d;
import ar.com.xyz.gameengine.control2d.builder.Button2dBuilder;
import ar.com.xyz.gameengine.control2d.builder.Control2dEventHandler;

/**
 * Cuatro botones y un text area en la que se vaya mostrando la actividad.
 * El 1ro limpia el text area y los otros tres escriben en el text area.
 * @author alfredo
 *
 */
public class Control2D003GameState extends AbstractGameState {
	
	private static final String NORMAL = "olive";
	private static final String MOUSE_OVER = "lime";
	private static final String CLICK = "green";
	private static final String DISABLED = "red";
	
	private static final float RED = 0.5f ;
	private static final float GREEN = 0.5f ;
	private static final float BLUE = 0.5f ;
	
	public Control2D003GameState() {
		
//		guiUno = createGuiTexture() ;
//		getGuis().add(guiUno) ;
				
//		grabMouseIfNotGrabbed() ;
		
		getDefaultColor().x = RED ;
		getDefaultColor().y = GREEN ;
		getDefaultColor().z = BLUE ;
		
		Panel2d panel2d = new Panel2d(this, new Vector2f(0,0), new Vector2f(1,1)) ;
		
		TextArea2d textArea2d = new TextArea2d(this, new Vector2f(0.0f,0.0f), new Vector2f(1f,.5f), NORMAL) ;
		
		// Cuadrante arriba a la izquierda
		Control2dEventHandler botonLimpiarEventHandler = new Control2dEventHandler() {
			@Override
			public void clickHandler() {
				textArea2d.removeText();
			}
		};
		
		Button2d botonLimpiar = new Button2dBuilder(this)
				.setControl2dEventHandler(botonLimpiarEventHandler)
				.setOriginAndSize(new Vector2f(0.0f,0.75f), new Vector2f(.5f,.25f))
				.setTextures(NORMAL, MOUSE_OVER, CLICK, DISABLED)
				.build() ;
		panel2d.add( botonLimpiar ) ;
		
		// Cuadrante arriba a la derecha
		{
			Control2dEventHandler botonEscribirEventHandler = new Control2dEventHandler() {
				String text = "Superior derecho" ;
				@Override
				public void clickHandler() {
					textArea2d.addTextLine(text);
				}
			};		
			Button2d botonEscribir = new Button2dBuilder(this)
				.setControl2dEventHandler(botonEscribirEventHandler)
				.setOriginAndSize(new Vector2f(0.5f,0.75f), new Vector2f(.5f,.25f))
				.setTextures(NORMAL, MOUSE_OVER, CLICK, DISABLED)
				.build() ;
			panel2d.add( botonEscribir ) ;
		}
		
		// Cuadrante abajo a la derecha
		{
			Control2dEventHandler botonEscribirEventHandler = new Control2dEventHandler() {
				String text = "Inferior derecho" ;
				@Override
				public void clickHandler() {
					textArea2d.addTextLine(text);
				}
			};		
			Button2d botonEscribir = new Button2dBuilder(this)
				.setControl2dEventHandler(botonEscribirEventHandler)
				.setOriginAndSize(new Vector2f(0.5f,0.5f), new Vector2f(.5f,.25f))
				.setTextures(NORMAL, MOUSE_OVER, CLICK, DISABLED)
				.build() ;
			panel2d.add( botonEscribir ) ;
		}
		
		// Cuadrante abajo a la izquierda
		{
			Control2dEventHandler botonEscribirEventHandler = new Control2dEventHandler() {
				String text = "Inferior izquierdo" ;
				@Override
				public void clickHandler() {
					textArea2d.addTextLine(text);
				}
			};		
			Button2d botonEscribir = new Button2dBuilder(this)
				.setControl2dEventHandler(botonEscribirEventHandler)
				.setOriginAndSize(new Vector2f(0.0f,0.5f), new Vector2f(.5f,.25f))
				.setTextures(NORMAL, MOUSE_OVER, CLICK, DISABLED)
				.build() ;
			panel2d.add( botonEscribir ) ;
		}
		
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
