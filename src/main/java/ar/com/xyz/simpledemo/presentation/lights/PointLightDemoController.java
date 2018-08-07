package ar.com.xyz.simpledemo.presentation.lights;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.light.PointLight;

public class PointLightDemoController {

	private AbstractGameState gameState ;
	
	public PointLightDemoController(AbstractGameState gameState) {
		this.gameState = gameState ;
	}
	
	public void tick(float tpf) {
		
		PointLight pointLight = gameState.getPointLightList().get(0) ;
		
		pointLight.getPosition().x = gameState.getPlayer().getPosition().x ;
		pointLight.getPosition().y = gameState.getPlayer().getPosition().y + 2 ;
		pointLight.getPosition().z = gameState.getPlayer().getPosition().z ;
		
	}

}
