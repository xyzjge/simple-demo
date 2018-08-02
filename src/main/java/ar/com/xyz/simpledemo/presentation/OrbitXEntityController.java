package ar.com.xyz.simpledemo.presentation;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.entity.EntityController;

public class OrbitXEntityController extends EntityController {

	private Vector3f initialPosition = new Vector3f(0,0,0) ;
		
	float radio = -10 ;
	float angle = 0 ;

	boolean yendo = true ;
	
	float seconds = 0 ;
	
	float scale = 2 ;
	
	@Override
	public void update(float fts) {
		seconds += fts ;
		angle += fts ;
		
		if (seconds > 10) {
			getEntity().increaseRotation(fts * -100, 0, 0);
		}
		
		if (seconds > 20) {
			
			float xxx = fts /* * 0.75f */;
			if (yendo) {
				if (scale < 3) {
					scale += xxx ;
				} else {
					yendo = !yendo ;
					scale -= xxx ;
				}
			} else {
				if (scale > 1) {
					scale -= xxx ;
				} else {
					yendo = !yendo ;
					scale += xxx ;
				}
			}
		
			getEntity().setScale(scale, scale, scale);
			
		}
		
		getEntity().setPosition(
			radio, 
			initialPosition.y + ((float)Math.sin(angle + Math.PI ))*radio, 
			initialPosition.z + ((float)Math.cos(angle + Math.PI))*radio
		);
		
	}

}
