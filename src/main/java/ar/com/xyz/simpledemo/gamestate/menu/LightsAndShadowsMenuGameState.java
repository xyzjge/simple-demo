package ar.com.xyz.simpledemo.gamestate.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.util.AbstractMenuItemBasedMenuGameState;
import ar.com.xyz.gameengine.util.FontSpec;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;
import ar.com.xyz.simpledemo.lightandshadow.menuitem.LightAndShadowBoxWithInNormalsMenuItem;
import ar.com.xyz.simpledemo.lightandshadow.menuitem.LightAndShadowMenuItem;

public class LightsAndShadowsMenuGameState extends AbstractMenuItemBasedMenuGameState {
	
	private static FontSpec normalFontSpec = new FontSpec("arial", 2f, new Vector3f(.5f, .5f, .5f));
	private static FontSpec seleccionadoFontSpec = new FontSpec("arial", 2f, new Vector3f(1f, 1f, 1f));
	
	public LightsAndShadowsMenuGameState(String sound, String background) {
		super(normalFontSpec, seleccionadoFontSpec, sound, background, 0.15f, null, EventOriginEnum.MOUSE) ;
	}

	private List<MenuItem> menuItemList = new ArrayList<MenuItem>() ;

	@Override
	protected List<MenuItem> getMenuItemList() {
		if (menuItemList.size() == 0) {
			menuItemList.add(new LightAndShadowMenuItem()) ;
			menuItemList.add(new LightAndShadowBoxWithInNormalsMenuItem()) ;
			menuItemList.add(SimpleDemoMenuMenuItem.getInstance());
		}
		return menuItemList;
	}

}
