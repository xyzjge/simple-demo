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
	LIVE_FOR, // Cuantos segundos vive el PS (float - sin error)
	DISTURB, // A cuanto de la posicion de origen se puede desviar el origen de las particulas (Vector3f con error random entre origen y disturb)
	
	PPS, // Particulas por segundo (float por ahora no tiene error, podria tenerlo ...)
	SPEED, // Velocidad promedio de las particulas (float - tiene error)
	
	GRAVITY, // Cuanto obdecen en promedio a la gravedad las particulas (1: obedecen 100% - 0: nada) (float entre 0 y 1 con error) 
	LIFE_LENGTH, // Cuanto viven en promedio las particulas (float con error)
	SCALE, // Tamaño promedio de las particulas ()
	
	// SPEED, LIFE y SCALE A number between 0 and 1, where 0 means no error margin. ??? El error podria ser un float y el error real el promedio mas un numero entre +- ese float ???
	
	RANDOM_ROT, // Rotacion random si/no (boolean)
	ACTIVAR_DIRECCION, // Las particulas se generan en un cono (boolean) 
	DIRECCION, // La direccion del cono (vetor3f) - el error es el angulo (float)
	// DESVIO_DIRECCION,
	ATLAS_TRANSITION_SPEED, // TODO: cuan rapido recorren el atlas (podria tener error)
	ADDITIVE_BLENDING // Additive blending si/no 
	;
	
}
