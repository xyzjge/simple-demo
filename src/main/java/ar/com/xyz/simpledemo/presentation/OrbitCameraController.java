package ar.com.xyz.simpledemo.presentation;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.cameracontroller.CameraController;
import ar.com.xyz.gameengine.singleton.SingletonManager;

public class OrbitCameraController implements CameraController {

	// private Vector3f cameraInitialPosition = new Vector3f(0,12,10) ;
	private Vector3f cameraInitialPosition = new Vector3f(0,12,0) ;
	
	private Vector3f cameraPosition = new Vector3f(0,12,10) ;
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

	float radio = 10 ;
	float angle = 0 ;

	boolean yendo = true ;
	
	@Override
	public void update(float fts) {
		angle += fts ;
		
		float xxx = fts * 2.5f ;
		if (yendo) {
			if (radio > 2) {
				radio -= xxx ;
			} else {
				yendo = !yendo ;
				radio += xxx ;
			}
		} else {
			if (radio < 10) {
				radio += xxx ;
			} else {
				yendo = !yendo ;
				radio -= xxx ;
			}
		}
		
		cameraPosition.z = cameraInitialPosition.z + ((float)Math.cos(angle + Math.PI))*radio ;
		cameraPosition.x = cameraInitialPosition.x + ((float)Math.sin(angle + Math.PI ))*radio ;
		cameraPosition.y = radio ;
				
		cameraRotation = SingletonManager.getInstance().getEntityUtil().lookAt3d(cameraPosition, new Vector3f(0,2,0)) ;
		cameraRotation.y = 180 - cameraRotation.y ;
		
		cameraRotation.z = -cameraRotation.z ;
		cameraRotation.x = -cameraRotation.x ;
		
	}

	@Override
	public void resetDefaults() {
		// TODO Auto-generated method stub
		
	}

}
