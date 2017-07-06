package ar.com.xyz.simpledemo;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.audio.AudioMaster;
import ar.com.xyz.gameengine.audio.Source;
import ar.com.xyz.gameengine.font.fontMeshCreator.FontType;
import ar.com.xyz.gameengine.font.fontMeshCreator.GUIText;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class SimpleDemoMenuGameState extends AbstractGameState {
	
	private String[] opciones = {"PLAY AGAIN", "EXIT"} ;
	
	private GUIText[] normal ;
	private GUIText[] seleccionado ;
	
	private int previousIndex = 0 ;
	private int index = 0 ;
	
	private Source source ;
	private int buffer ;
	
	public SimpleDemoMenuGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop) ;
//		loadEntities() ;
//		loadPlayerAndCamera() ;
		loadGuis();
		
		setupMenu();

	}

	private void setupMenu() {
		// arial, calibri, harrington, sans, segoe, segoeUI, tahoma
		// FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("arial") ;
		// FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("calibri") ;
		// FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("harrington") ;
		// FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("sans") ;
		// FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("segoe") ;
		// FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("segoeUI") ;
		FontType font = SingletonManager.getInstance().getFontTypeManager().getFontType("tahoma") ;
		
		normal = new GUIText[opciones.length] ;
		seleccionado = new GUIText[opciones.length] ;

		for (int i = 0; i < opciones.length; i++) {
			normal[i] = new GUIText(opciones[i], 3f, font, new Vector2f(0 , 0.2f + i/8f ), 1f, true, this);
			normal[i].setColour(.5f, .5f, .5f);
			seleccionado[i] = new GUIText(opciones[i], 3f, font, new Vector2f(0, 0.2f + i/8f), 1f, true, this);
			seleccionado[i].setColour(1, 1, 1);
			if (i == 0) {
				seleccionado[i].show();
			} else {
				normal[i].show();
			}
		}

		{
			buffer = AudioMaster.loadSound("ZIPCLOSE.wav") ;
			source = new Source(16, 4, 128*2 /*64*/) ;
			source.setLooping(false);
			source.setPosition(0, 0, 0);
			source.setVolume(.1f);
		}
	}

	private void loadGuis() {
		// GuiTexture gui = new GuiTexture(SingletonManager.getInstance().getTextureManager().loadTexture("stone.png"), new Vector2f(0.5f, -0.2f), new Vector2f(1.3f, 1f)) ;
		GuiTexture gui = new GuiTexture(SingletonManager.getInstance().getTextureManager().loadTexture("stone.png"), new Vector2f(0, 0), new Vector2f(1f, 1f)) ;
		getGuis().add(gui) ;
	}
	
//	private void loadPlayerAndCamera() {
//	}

//	private void loadEntities() {
//		// Armar la particion de AABBs de los estaticos
//		getAabbManager().createTree();
//	}
	
	boolean end = false ;

	@Override
	public void tick(float tpf) {
		if (Mouse.isGrabbed()) {
			Mouse.setGrabbed(false);
		}
		handleKeyboardInput() ;
		if (end) {
			return ;
		}
		if (index < 0) {
			index = opciones.length - 1 ;
		}
		if (index > ( opciones.length - 1 )) {
			index = 0 ;
		}

		// Si no hacia esto los fps se iban a los caños ...
		if (index == previousIndex) {
			return ;
		}
		previousIndex = index ;
		for (int i = 0; i < normal.length; i++) {
			if (i == index) {
				normal[i].hide();
				seleccionado[i].show();
			} else {
				seleccionado[i].hide();
				normal[i].show();
			}
		}
	}

	private void handleKeyboardInput() {
		while (Keyboard.next()) {
			if (Keyboard.getEventKeyState()) { // Tener en cuenta cuando se presiona nomas ...
				
				if (Keyboard.getEventKey() == Keyboard.KEY_W || Keyboard.getEventKey() == Keyboard.KEY_UP) {
					playSound();
					index -- ;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_S || Keyboard.getEventKey() == Keyboard.KEY_DOWN) {
					playSound();
					index ++ ;
				}
				
				if (Keyboard.getEventKey() == Keyboard.KEY_D || Keyboard.getEventKey() == Keyboard.KEY_RIGHT) {
					playSound();
					index ++ ;
				} else if (Keyboard.getEventKey() == Keyboard.KEY_A || Keyboard.getEventKey() == Keyboard.KEY_LEFT) {
					playSound();
					index -- ;
				}

				if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
					if (index == 0) {
						mainGameLoop.setNextGameState(new XYZDemoLevelGameState(mainGameLoop));
						end = true ; 
					} else {
						mainGameLoop.stop(); 
					}
				}
			}
		}
	}

	private void playSound() {
		source.play(buffer);
	}

}
