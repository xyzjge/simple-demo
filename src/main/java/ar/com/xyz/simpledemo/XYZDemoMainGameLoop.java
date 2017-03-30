package ar.com.xyz.simpledemo;

import ar.com.xyz.gameengine.AbstractMainGameLoop;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class XYZDemoMainGameLoop extends AbstractMainGameLoop {

	public XYZDemoMainGameLoop(String title) {
		super(title) ;
	}

	public static void main(String[] args) {
		SingletonManager.getInstance().getTextureManager().addTexturePath("/texture");
		XYZDemoMainGameLoop mainGameLoop = new XYZDemoMainGameLoop("Simple Demo - XYZ Java Game Engine [BETA]") ;
		mainGameLoop.setNextGameState(new XYZDemoLevelGameState(mainGameLoop));
		mainGameLoop.loop();
	}

}
