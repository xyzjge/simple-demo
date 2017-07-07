package ar.com.xyz.simpledemo;

import ar.com.xyz.gameengine.entity.EntityController;

/**
 * 
 * @author alfredo
 *
 */
public class SimpleDemoEntityController extends EntityController {

	private int maxY = 2 ;
	private int minY = -2 ;

	private boolean subiendo = true ;
	
	public SimpleDemoEntityController() {
	}

	@Override
	public void update(float tpf) {
		if (subiendo) {
			if (getEntity().getPosition().y > maxY) {
				subiendo = false ;
			} else {
				getEntity().increasePosition(0, tpf, 0);
			}
		} else {
			if (getEntity().getPosition().y < minY) {
				subiendo = true ;
			} else {
				getEntity().increasePosition(0, -tpf, 0);
			}
		}
	}

}
