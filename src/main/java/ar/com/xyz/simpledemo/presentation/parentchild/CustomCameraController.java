package ar.com.xyz.simpledemo.presentation.parentchild;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.cameracontroller.CameraController;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class CustomCameraController implements CameraController {
	
	private Vector3f cameraPosition = new Vector3f(0,15,0) ;
	private Vector3f cameraRotation = new Vector3f(0,0,0) ;
	
	@Override
	public Vector3f getPosition() {
		return cameraPosition;
	}

	@Override
	public Vector3f getRotation() {
		return cameraRotation;
	}

	@Override
	public float getPitch() {
		return cameraRotation.x;
	}

	@Override
	public float getYaw() {
		return cameraRotation.y;
	}

	@Override
	public float getRoll() {
		return cameraRotation.z;
	}

	@Override
	public void setPitch(float pitch) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(float fts) {
		
		cameraPosition.z -=  fts;
		cameraPosition.y -=  fts;
		
		cameraRotation = SingletonManager.getInstance().getEntityUtil().lookAt3d(cameraPosition, new Vector3f(0,-10,0)) ;
		cameraRotation.y = 180 - cameraRotation.y ;
		
		cameraRotation.z = -cameraRotation.z ;
		cameraRotation.x = -cameraRotation.x ;
	}

	@Override
	public void resetDefaults() {
		// TODO Auto-generated method stub
		
	}

}
