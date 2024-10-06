package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.control2d.Button2d;
import ar.com.xyz.gameengine.control2d.Control2d;
import ar.com.xyz.gameengine.control2d.Panel2d;
import ar.com.xyz.gameengine.control2d.builder.Button2dBuilder;
import ar.com.xyz.gameengine.control2d.builder.Control2dEventHandler;
import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.input.manager.InputEvent;
import ar.com.xyz.gameengine.input.manager.InputEventListener;
import ar.com.xyz.simpledemo.control2d.menu.Control2DMenuMenuItem;

/**
 * Cuatro paneles con dos botones cada uno.
 * Maneja mouse over y click por separado en cada botón.
 * @author alfredo
 *
 */
public class Control2D007GameState extends AbstractGameState implements InputEventListener {
	
	private static final float RED = 0.5f ;
	private static final float GREEN = 0.5f ;
	private static final float BLUE = 0.5f ;
	
	public Control2D007GameState() {
		
		getDefaultColor().x = RED ;
		getDefaultColor().y = GREEN ;
		getDefaultColor().z = BLUE ;
		
		Panel2d panel2d = new Panel2d(this, new Vector2f(0,0), new Vector2f(1,1)) ;
		panel2d.setColor(1, 0, 1);
		
		Panel2d superiorIzquierdo = crearPanel(new Vector2f(0, .5f), new Vector2f(.5f, .5f), "superior izquierdo") ;
		panel2d.add(superiorIzquierdo);
		Panel2d superiorDerecho = crearPanel(new Vector2f(.5f, .5f), new Vector2f(.5f, .5f), "superior derecho") ;
		panel2d.add(superiorDerecho);
		Panel2d inferiorIzquierdo = crearPanel(new Vector2f(0, 0), new Vector2f(.5f, .5f), "inferior izquierdo") ;
		panel2d.add(inferiorIzquierdo);
		Panel2d inferiorDerecho = crearPanel(new Vector2f(.5f, 0), new Vector2f(.5f, .5f), "inferior derecho") ;
		panel2d.add(inferiorDerecho);
		
		for (Control2d control2d : superiorIzquierdo.getControl2dList()) {
			control2d.getPadding().x = .01f ;
			control2d.getPadding().y = .2f ;
		}

		panel2d.show();

		System.out.println("panel2d: " + panel2d.getArea()) ;
		System.out.println("superiorIzquierdo: " + superiorIzquierdo.getArea()) ;
//		System.out.println("superiorDerecho: " + superiorDerecho.getArea()) ;
//		System.out.println("inferiorIzquierdo: " + inferiorIzquierdo.getArea()) ;
//		System.out.println("inferiorDerecho: " + inferiorDerecho.getArea()) ;
		
		for (Control2d control2d : superiorIzquierdo.getControl2dList()) {
			System.out.println("" + control2d.getArea());
		}
	}

	private Panel2d crearPanel(Vector2f origin, Vector2f size, String panel) {
		Panel2d panel2d = new Panel2d(this, origin, size) ;
		panel2d.setColor(0, 0, 1);
		{
			Control2dEventHandler buttonEventHandler = new Control2dEventHandler() {
				@Override
				public void clickHandler() {
					System.out.println("En clickHandler boton 1 !!! (panel " + panel + ")");
				}
			};
			Button2d button = new Button2dBuilder(this)
				.setControl2dEventHandler(buttonEventHandler)
				.setOriginAndSize(new Vector2f(0.0f,0.0f), new Vector2f(.5f,1f))
				.setTextures("green", "red", "yellow", "white")
				.setLabel("Label !!!")
				.build() ;
			panel2d.add(button);
		}
		{
		Control2dEventHandler buttonEventHandler = new Control2dEventHandler() {
			@Override
			public void clickHandler() {
				System.out.println("En clickHandler boton 2 !!! (panel " + panel + ")");
			}
		};
		Button2d button = new Button2dBuilder(this)
			.setControl2dEventHandler(buttonEventHandler)
			.setOriginAndSize(new Vector2f(0.5f,0.0f), new Vector2f(.5f,1f))
			.setTextures("green", "red", "yellow", "white")
			.setLabel("Label !!!")
			.build() ;
		panel2d.add(button);
		}
		// .005f, .005f
//		panel2d.getPadding().x = .001f ;
		return panel2d ;
	}

	@Override
	public void attachedToMainLoop() {
		super.attachedToMainLoop();
//		System.out.println("getInputManager().getNumberOfConfiguredInputEventListener(): " + getInputManager().getNumberOfConfiguredInputEventListener());
		addInputEventListener(this);
	}
	
	@Override
	public void tick(float tpf) {
		
	}
	
	@Override
	public boolean handleEvent(InputEvent inputEvent) {
		switch (inputEvent.getEventKeyOrButton()) {
		case Keyboard.KEY_ESCAPE:
			returnToMenu() ;
			break;
		default:
			break;
		}
		return false; 
	}

	@Override
	public boolean accept(InputEvent inputEvent) {
		if (inputEvent.getOrigin() == EventOriginEnum.KEYBOARD) {
			return true ;
		}
		return false;
	}

	@Override
	public void tickInputEventListener(float tpf) {
		// TODO Auto-generated method stub
		
	}

	private void returnToMenu() {
		getMainGameLoop().setNextGameState(Control2DMenuMenuItem.getInstance().getGameStateInstance()) ;
	}
	
}
