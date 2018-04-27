package ar.com.xyz.simpledemo.gamestate.guitexture;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.audio.AudioMaster;
import ar.com.xyz.gameengine.audio.Source;
import ar.com.xyz.gameengine.gui.GuiTexture;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class GuiDemoGameState extends AbstractGameState {
	
	// private static final String CIRCLE_TEXTURE = "circle" ;
	private static final String CIRCLE_TEXTURE = "circle-1024x1024" ;
	
	// private static final String SONIDO_AMBIENTE = "el-ambiente_2.wav" ;
	private static final String SONIDO_AMBIENTE = "el-ambiente_2.ogg" ;
	
	private static final float RED = 0.5f ;
	private static final float GREEN = 0.5f ;
	private static final float BLUE = 0.5f ;
	
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
		
		getDefaultColor().x = RED ;
		getDefaultColor().y = GREEN ;
		getDefaultColor().z = BLUE ;
		
//		AudioMaster.setListenerData(0,0,0);
		
//		AudioMaster.addSoundPath("/sounds") ;
		{ // TODO: Cuando termina un GE hay que cortar todos los sonidos ...
			int buffer = AudioMaster.loadSound(SONIDO_AMBIENTE) ;
			// Source source = new Source(16, 4, 128*2 /*64*/) ;
			Source source = SingletonManager.getInstance().getSourceMaster().createSource(1, 0, 20) ;
			source.setLooping(true);
			source.setPosition(0,0,0);
			source.setVolume(.5f);
			source.play(buffer);
		}
		
	}

	private GuiTexture createGuiTexture() {
		return new GuiTexture(
			SingletonManager.getInstance().getTextureManager().loadTexture(CIRCLE_TEXTURE), 
			new Vector2f(0.0f, 0.0f), 
			new Vector2f( 1/w * 300 /*.6f*/, 1/h * 300/*1f*/)
		);
	}

	boolean going = true ;
	
	boolean goingColor = true ;
	
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
		
		float velocityColor = tpf / 50 ;
		if (goingColor) {
			getDefaultColor().x += velocityColor ; 
			getDefaultColor().y += velocityColor ;
			getDefaultColor().z += velocityColor ;
			if (getDefaultColor().x > 1) {
				getDefaultColor().x = 1 ; 
				getDefaultColor().y = 1 ;
				getDefaultColor().z = 1 ;
				goingColor = false ;
			}
		} else {
			getDefaultColor().x -= velocityColor ; 
			getDefaultColor().y -= velocityColor ;
			getDefaultColor().z -= velocityColor ;
			if (getDefaultColor().x < RED) {
				getDefaultColor().x = RED ; 
				getDefaultColor().y = GREEN ;
				getDefaultColor().z = BLUE ;
				goingColor = true ;
			}
		}
	}
	
}
