package ar.com.xyz.simpledemo.presentation;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.entity.EntityController;

public class OrbitYEntityController extends EntityController {

	private Vector3f initialPosition = new Vector3f(0,0,0) ;
		
	float radio = 10 ;
	float angle = 0 ;

	boolean yendo = true ;
	
	@Override
	public void update(float fts) {
		angle += fts ;
		
//		float xxx = fts * 2.5f ;
//		if (yendo) {
//			if (radio > 2) {
//				radio -= xxx ;
//			} else {
//				yendo = !yendo ;
//				radio += xxx ;
//			}
//		} else {
//			if (radio < 10) {
//				radio += xxx ;
//			} else {
//				yendo = !yendo ;
//				radio -= xxx ;
//			}
//		}
		
		getEntity().setPosition(
			initialPosition.x + ((float)Math.sin(angle + Math.PI ))*radio, 
			radio, 
			initialPosition.z + ((float)Math.cos(angle + Math.PI))*radio
		);
		
	}

}
