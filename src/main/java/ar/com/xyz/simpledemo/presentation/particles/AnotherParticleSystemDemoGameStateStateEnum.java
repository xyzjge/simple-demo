package ar.com.xyz.simpledemo.presentation.particles;

public enum AnotherParticleSystemDemoGameStateStateEnum {
/*
	 * F1: liveFor (+-)
	 * F2: disturb (x+ x- y+ y- z+ z-) (disturb posicion particula) 
	 * 
	 * F3: pps (+-)
	 * F4: averageSpeed/speedError (E): (+-)
	 * F5: gravityComplient/gravityComplientError (E): (+-)
	 * F6: averageLifeLength/lifeLengthError (E): (+-) (durante cuanto tiempo (en segundos) va a existir el PS, null hasta que alguien lo quite o termine el GS)
	 * F7: averageScale/scaleError (E): (+-)
	 * F8: randomRotation: s/n o y/n
	 * F9: Activar direccion (s/n o y/n)
	 * F10: direction (x+ x- y+ y- z+ z-)
	 * F11: directionDeviation (+-)
	 * F12: additiveBlending (s/n o y/n)
*/
	LIVE_FOR, DISTURB, PPS, SPEED, GRAVITY, LIFE_LENGTH, SCALE, RANDOM_ROT, ACTIVAR_DIRECCION, DIRECCION, DESVIO_DIRECCION, ADDITIVE_BLENDING ;
	
}
