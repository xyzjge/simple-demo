package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.control2d.Button2d;
import ar.com.xyz.gameengine.control2d.TextArea2d;

public class BotonEscribir extends Button2d {

	private TextArea2d textArea2d ;
	
	private String text ;
	
	public BotonEscribir(
		AbstractGameState gameState,
		Vector2f origin, Vector2f size, String texturaNormal, String texturaMouseOver, String texturaClick, String texturaDisabled,
		TextArea2d textArea2d, String text
	) {
		super(gameState, origin, size, texturaNormal, texturaMouseOver, texturaClick, texturaDisabled);
		this.textArea2d = textArea2d ;
		this.text = text ;
	}

	@Override
	protected void clickHandler() {
		textArea2d.addTextLine(text);
	}

}
