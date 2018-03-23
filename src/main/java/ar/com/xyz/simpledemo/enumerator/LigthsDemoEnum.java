package ar.com.xyz.simpledemo.enumerator;

public enum LigthsDemoEnum {

	AMBIENT, DIRECTIONAL, POINT, SPOT ;
	
	public LigthsDemoEnum next() {
		 return values()[(this.ordinal()+1) % values().length];
	}

}
