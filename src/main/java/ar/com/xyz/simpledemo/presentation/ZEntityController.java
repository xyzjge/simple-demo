package ar.com.xyz.simpledemo.presentation;

import ar.com.xyz.gameengine.entity.EntityController;

public class ZEntityController extends EntityController {

	private YEntityController yEntityController ; 
	private boolean donePosition = false ;
	private boolean doneRotation = false ;
	
	public ZEntityController(YEntityController yEntityController) {
		this.yEntityController = yEntityController ;
	}
	
	/**
	 * Ir de -10 a -1 rotando en Z
	 */
	@Override
	public void update(float tpf) {
		
		if (!yEntityController.isDone()) {
			return ;
		}

		if (getEntity().getPosition().z < -1) {
			getEntity().increasePosition(0, 0, tpf * PresentationGameState.LOC_VEL);
		} else {
			donePosition = true ;
		}
		
		if (doneRotation) {
			return ;
		}

		if (donePosition) {
			if (Math.abs(getEntity().getRotation().z % 180f) < 3f) {
				doneRotation = true ;
			} else {
				getEntity().increaseRotation(0, 0, tpf * -1 * PresentationGameState.ROT_VEL);
			}
		} else {
			getEntity().increaseRotation(0, 0, tpf * -1 * PresentationGameState.ROT_VEL);
		}

	}
	
	public boolean isDone() {
		return donePosition && doneRotation ;
	}

}
