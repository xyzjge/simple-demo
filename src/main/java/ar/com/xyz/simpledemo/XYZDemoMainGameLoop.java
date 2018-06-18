package ar.com.xyz.simpledemo;

import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.audio.AudioMaster;
import ar.com.xyz.gameengine.singleton.SingletonManager;
import ar.com.xyz.simpledemo.gamestate.SimpleDemoMenuGameState;

public class XYZDemoMainGameLoop extends AbstractMainGameLoop {

//	private static final int WIDTH = 800 ;
//	private static final int HEIGHT = 600 ;
	
	private static final int WIDTH = 1280 ;
	private static final int HEIGHT = 720 ;
	
	public XYZDemoMainGameLoop(String title) {
		super(title, WIDTH, HEIGHT, true) ;
	}

	public static void main(String[] args) {
		SingletonManager.getInstance().getTextureManager().addTexturePath("/texture");
		// TODO: SingletonManager.getInstance().get ...
		AudioMaster.addSoundPath("/sounds") ;
		XYZDemoMainGameLoop mainGameLoop = new XYZDemoMainGameLoop("Simple Demo - XYZ Java Game Engine") ;
		// mainGameLoop.setNextGameState(new XYZDemoLevelGameState(mainGameLoop));
		mainGameLoop.setNextGameState(new SimpleDemoMenuGameState(mainGameLoop, "ZIPCLOSE.wav", "stone.png")) ;
		mainGameLoop.loop();
	}

}
