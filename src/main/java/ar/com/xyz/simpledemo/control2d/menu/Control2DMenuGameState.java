package ar.com.xyz.simpledemo.control2d.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.util.AbstractMenuItemBasedMenuGameState;
import ar.com.xyz.gameengine.util.FontSpec;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.SimpleDemoMenuMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.TerrainDemoMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.AnotherParticleSystemDemoMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.ColladaAnimationDemoMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.CubeMapReflectionDemoMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.ObjMultipleTextureMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.ObjSingleTextureMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.ParentChildRelationshipMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.ParticleSystemDemoMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.PositionRotationScaleMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.SkyboxDemoMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.SoundDemoMenuItem;

public class Control2DMenuGameState extends AbstractMenuItemBasedMenuGameState {
	
	private static FontSpec normalFontSpec = new FontSpec("arial", 2f, new Vector3f(.5f, .5f, .5f));
	private static FontSpec seleccionadoFontSpec = new FontSpec("arial", 2f, new Vector3f(1f, 1f, 1f));
	
	public Control2DMenuGameState(String sound, String background) {
		super(normalFontSpec, seleccionadoFontSpec, sound, background, 0.15f, null, EventOriginEnum.MOUSE) ;
	}

	private List<MenuItem> menuItemList = new ArrayList<MenuItem>() ;

	@Override
	protected List<MenuItem> getMenuItemList() {
		if (menuItemList.size() == 0) {
			menuItemList.add(new Control2DUnoMenuItem()) ;
			menuItemList.add(new Control2DDosMenuItem()) ;
			menuItemList.add(SimpleDemoMenuMenuItem.getInstance());
			
		}
		return menuItemList;
	}

	@Override
	public void tick() {
		
	}

}
