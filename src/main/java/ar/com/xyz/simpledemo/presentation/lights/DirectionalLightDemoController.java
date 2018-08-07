package ar.com.xyz.simpledemo.presentation.lights;

import ar.com.xyz.gameengine.AbstractGameState;

public class DirectionalLightDemoController {

	private AbstractGameState gameState ;
	
	public DirectionalLightDemoController(AbstractGameState gameState) {
		this.gameState = gameState ;
	}
	
	float angle = 0 ;
	
	
//	x = R * Math.cos(t) + 0;
//	y = R * Math.sin(t) + 0;
	public void tick(float tpf) {
		
		gameState.getDirectionalLight().getDirection().normalise() ;
		
		angle += tpf ;
		
		gameState.getDirectionalLight().getDirection().x = /* radio **/ ((float)Math.cos(angle)) ; 
		gameState.getDirectionalLight().getDirection().y = -1 ;
		gameState.getDirectionalLight().getDirection().z = /* radio **/ ((float)Math.sin(angle)) ;
//		radio, 
//		initialPosition.z + ((float)Math.cos(angle + Math.PI))*radio
	}

}
