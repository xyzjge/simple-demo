package ar.com.xyz.simpledemo.presentation;

import ar.com.xyz.gameengine.entity.EntityController;

public class YEntityController extends EntityController {

	private XEntityController xEntityController ; 
	private boolean donePosition = false ;
	private boolean doneRotation = false ;
	
	public YEntityController(XEntityController xEntityController) {
		this.xEntityController = xEntityController ;
	}
	
	/**
	 * Ir de 10 a 0 rotando en Y
	 */
	@Override
	public void update(float tpf) {
		
		if (!xEntityController.isDone()) {
			return ;
		}

		if (getEntity().getPosition().y > 2) {
			getEntity().increasePosition(0, -tpf * PresentationGameState.LOC_VEL, 0);
		} else {
			donePosition = true ;
		}
		
		if (doneRotation) {
			return ;
		}

		if (donePosition) {
//			System.out.println(Math.abs(getEntity().getRotation().x % 180f) + "");
			if (Math.abs(getEntity().getRotation().y % 180f) < 2f) {
				doneRotation = true ;
			} else {
				getEntity().increaseRotation(0, tpf * -1 * PresentationGameState.ROT_VEL, 0);
			}
		} else {
			getEntity().increaseRotation(0, tpf * -1 * PresentationGameState.ROT_VEL, 0);
		}

	}
	
	public boolean isDone() {
		return donePosition && doneRotation ;
	}

}
