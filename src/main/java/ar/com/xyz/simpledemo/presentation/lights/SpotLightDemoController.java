package ar.com.xyz.simpledemo.presentation.lights;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.AbstractMainCharacterGameState;
import ar.com.xyz.gameengine.light.SpotLight;

public class SpotLightDemoController {

	private AbstractMainCharacterGameState gameState ;
	
	public SpotLightDemoController(AbstractMainCharacterGameState gameState) {
		this.gameState = gameState ;
	}
	
	public void tick(float tpf) {
		
		SpotLight spotLight = gameState.getSpotLightList().get(0) ;
		
		float rotY = gameState.getPlayer().getRotation().y ;
		Vector3f rayDirection = new Vector3f(
			(float)Math.sin(Math.toRadians(rotY)), 
			(float) - Math.sin(Math.toRadians(gameState.getCamera().getPitch())) , 
			(float)Math.cos(Math.toRadians(rotY))
		) ;
		spotLight.getConeDirection().x = rayDirection.x ;
		spotLight.getConeDirection().y = rayDirection.y ;
		spotLight.getConeDirection().z = rayDirection.z ;
		
		spotLight.getPointLight().getPosition().x = gameState.getPlayer().getPosition().x ;
		spotLight.getPointLight().getPosition().y = gameState.getPlayer().getPosition().y + 1;
		spotLight.getPointLight().getPosition().z = gameState.getPlayer().getPosition().z ;
		
	}

}
