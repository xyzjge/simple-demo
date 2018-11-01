package ar.com.xyz.simpledemo.presentation.cubemapreflection;

/**
 * @author alfredo
 *
 */
public class CubeMapReflectionDemoModel {
	
	private float titleDuration = 5 ;
	private float titleActualSeconds = 0 ;
	
	private boolean rotateEntity = false ;
	
	private boolean refractionEnabled = false ;
	
	private CubeMapReflectionDemoCaseEnum caso = null ;

	public float getTitleDuration() {
		return titleDuration;
	}

	public float getTitleActualSeconds() {
		return titleActualSeconds;
	}
	
	public void increaseTitleActualSeconds(float seconds) {
		titleActualSeconds += seconds ;
	}

	public boolean isRotateEntity() {
		return rotateEntity;
	}

	public void setRotateEntity(boolean rotateEntity) {
		this.rotateEntity = rotateEntity;
	}

	public CubeMapReflectionDemoCaseEnum getCaso() {
		return caso;
	}

	public void setCaso(CubeMapReflectionDemoCaseEnum caso) {
		this.caso = caso;
	}

	public boolean isRefractionEnabled() {
		return refractionEnabled;
	}

	public void setRefractionEnabled(boolean refractionEnabled) {
		this.refractionEnabled = refractionEnabled;
	}

}
