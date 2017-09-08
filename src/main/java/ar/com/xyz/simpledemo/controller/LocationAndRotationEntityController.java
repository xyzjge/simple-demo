package ar.com.xyz.simpledemo.controller;

import ar.com.xyz.gameengine.entity.EntityController;

/**
 * 
 * @author alfredo
 *
 */
public class LocationAndRotationEntityController extends EntityController {

	private LocationAndRotationStatesEnum state = LocationAndRotationStatesEnum.X ;
	
	private float seconds ;
	private float maxSeconds = 2 ;
	
	public LocationAndRotationEntityController() {
	}
	
	@Override
	public void postConstruct() {
		getGameState().addNotification("X");
	}

	@Override
	public void update(float tpf) {
		seconds+= tpf ;
		if (state.equals(LocationAndRotationStatesEnum.X)) {
			getEntity().increasePosition(tpf, 0, 0); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("-X");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.MINUS_X ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.MINUS_X)) {
			getEntity().increasePosition(-tpf, 0, 0); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("Y");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.Y ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.Y)) {
			getEntity().increasePosition(0, tpf, 0); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("-Y");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.MINUS_Y ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.MINUS_Y)) {
			getEntity().increasePosition(0, -tpf, 0); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("Z");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.Z ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.Z)) {
			getEntity().increasePosition(0, 0, tpf); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("-Z");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.MINUS_Z ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.MINUS_Z)) {
			getEntity().increasePosition(0, 0, -tpf); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("X rotation (pitch)");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.ROT_X ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.ROT_X)) {
			getEntity().increaseRotation(tpf*10, 0, 0); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("-X rotation (-pitch)");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.ROT_MINUS_X ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.ROT_MINUS_X)) {
			getEntity().increaseRotation(-tpf*10, 0, 0); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("Y rotation (yaw)");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.ROT_Y ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.ROT_Y)) {
			getEntity().increaseRotation(0, tpf*10, 0); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("-Y rotation (-yaw)");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.ROT_MINUS_Y ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.ROT_MINUS_Y)) {
			getEntity().increaseRotation(0, -tpf*10, 0); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("Z rotation (roll)");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.ROT_Z ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.ROT_Z)) {
			getEntity().increaseRotation(0, 0, tpf*10); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("-Z rotation (-roll)");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.ROT_MINUS_Z ;
			}
		} else if (state.equals(LocationAndRotationStatesEnum.ROT_MINUS_Z)) {
			getEntity().increaseRotation(0, 0, -tpf*10); 
			if (seconds > maxSeconds) {
				getGameState().addNotification("X");
				seconds = 0 ;
				state = LocationAndRotationStatesEnum.X ;
			}
		}

	}

}
