package ar.com.xyz.simpledemo;

import ar.com.xyz.gameengine.entity.Entity;
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
	public void setEntity(Entity entity) {
		super.setEntity(entity);
	}

	public void update(float tpf) {
		if (subiendo) {
			if (entity.getPosition().y > maxY) {
				subiendo = false ;
			} else {
				entity.increasePosition(0, tpf, 0);
			}
		} else {
			if (entity.getPosition().y < minY) {
				subiendo = true ;
			} else {
				entity.increasePosition(0, -tpf, 0);
			}
		}
	}

}
