package ar.com.xyz.simpledemo.control2d;

import org.lwjgl.util.vector.Vector2f;

import ar.com.xyz.gameengine.control2d.Button2d;
import ar.com.xyz.gameengine.control2d.TextArea2d;

public class BotonLimpiar extends Button2d {

	private TextArea2d textArea2d ;
	public BotonLimpiar(
		Vector2f origin, Vector2f size, String texturaNormal, String texturaMouseOver, String texturaClick, TextArea2d textArea2d
	) {
		super(origin, size, texturaNormal, texturaMouseOver, texturaClick);
		this.textArea2d = textArea2d ;
	}

	@Override
	protected void clickHandler() {
		textArea2d.removeText();
	}

}
