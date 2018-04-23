package ar.com.xyz.simpledemo.gamestate.guitexture;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class GuiDemoGameState extends AbstractGameState {
	
	private static final String CIRCLE_TEXTURE = "circle" ;
	
	float w = Display.getWidth() ;
	float h = Display.getHeight() ;
	
	private GuiTexture guiUno ;
	private GuiTexture guiDos ;
	
	private GuiTexture guiTres ;
	private GuiTexture guiCuatro ;
	
	private GuiTexture guiCinco ;
	
	public GuiDemoGameState(AbstractMainGameLoop mainGameLoop) {
		super(mainGameLoop);
		
		guiUno = createGuiTexture() ;
		getGuis().add(guiUno) ;
		
		guiDos = createGuiTexture() ;
		getGuis().add(guiDos) ;
		
		guiTres = createGuiTexture() ;
		getGuis().add(guiTres) ;
		
		guiCuatro = createGuiTexture() ;
		getGuis().add(guiCuatro) ;
		
		guiCinco = createGuiTexture() ;
		getGuis().add(guiCinco) ;
		
		grabMouseIfNotGrabbed() ;
	}

	private GuiTexture createGuiTexture() {
		return new GuiTexture(
			SingletonManager.getInstance().getTextureManager().loadTexture(CIRCLE_TEXTURE), 
			new Vector2f(0.0f, 0.0f), 
			new Vector2f( 1/w * 300 /*.6f*/, 1/h * 300/*1f*/)
		);
	}

	boolean going = true ;
	
	@Override
	public void tick(float tpf) {
		
		float velocity = tpf * 10f ;
		if (!going) {
			velocity *= -1 ;
		}
		
		if (guiUno.getPosition().x > .31f) {
			going = false ;
		}
		
		if (guiUno.getPosition().x < 0) {
			going = true ;
		}
		
		guiUno.getPosition().x += velocity / w;
		guiDos.getPosition().x -= velocity / w;
		
		guiTres.getPosition().y += velocity / h;
		guiCuatro.getPosition().y -= velocity / h;
		
//		guiCinco.getScale().x += (velocity / w) * 1.4f ;
//		guiCinco.getScale().y += (velocity / h ) * 1.4f;
		
	}
	
}
