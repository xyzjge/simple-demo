package ar.com.xyz.simpledemo.presentation;

import ar.com.xyz.gameengine.entity.EntityController;

public class XEntityController extends EntityController {

	private float wait = .75f ;
	
	private boolean donePosition = false ;
	private boolean doneRotation = false ;
	
	/**
	 * Ir de -10 a 1 rotando en X
	 */
	@Override
	public void update(float tpf) {
		if (wait > 0) {
			wait -= tpf ;
			return ;
		}
		// TODO Auto-generated method stub
		if (getEntity().getPosition().x < 2) {
			getEntity().increasePosition(tpf * PresentationGameState.LOC_VEL, 0, 0);
		} else {
			donePosition = true ;
		}
		
		if (doneRotation) {
			return ;
		}

		if (donePosition) {
//			System.out.println(Math.abs(getEntity().getRotation().x % 180f) + "");
			if (Math.abs(getEntity().getRotation().x % 180f) < 5f) {
				doneRotation = true ;
			} else {
				getEntity().increaseRotation(tpf * -1 * PresentationGameState.ROT_VEL, 0, 0);
			}
		} else {
			getEntity().increaseRotation(tpf * -1 * PresentationGameState.ROT_VEL, 0, 0);
		}

	}
	
	public boolean isDone() {
		return donePosition && doneRotation ;
	}

}
