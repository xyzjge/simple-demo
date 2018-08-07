package ar.com.xyz.simpledemo.presentation.lights;

import ar.com.xyz.gameengine.AbstractGameState;

public class AmbientLightDemoController {

	private static final float SPEED = 0.25f;

	private AbstractGameState gameState ;
	
	private boolean yendo = true ;
	
	public AmbientLightDemoController(AbstractGameState gameState) {
		this.gameState = gameState ;
	}
	
	float change ;
	
	public void tick(float tpf) {
		
		change = tpf * SPEED ;
		
		if (yendo) {
			if (gameState.getAmbientLight().x + change > 1) {
				yendo = false ;
			}
		} else {
			if (gameState.getAmbientLight().x - change < 0) {
				yendo = true ;
			}
		}
		
		if (yendo) {
			gameState.getAmbientLight().x += change ; 
			gameState.getAmbientLight().y += change ;
			gameState.getAmbientLight().z += change ;
		} else {
			gameState.getAmbientLight().x -= change ; 
			gameState.getAmbientLight().y -= change ;
			gameState.getAmbientLight().z -= change ;
		}
		
		if (gameState.getAmbientLight().x > 1) {
			gameState.getAmbientLight().x = 1 ;
			gameState.getAmbientLight().y = 1 ;
			gameState.getAmbientLight().z = 1 ;
		}
		
		if (gameState.getAmbientLight().x < 0) {
			gameState.getAmbientLight().x = 0 ;
			gameState.getAmbientLight().y = 0 ;
			gameState.getAmbientLight().z = 0 ;
		}
	}

}
