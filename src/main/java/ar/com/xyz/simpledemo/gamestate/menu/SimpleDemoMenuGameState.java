package ar.com.xyz.simpledemo.gamestate.menu;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector3f;

import ar.com.xyz.gameengine.input.manager.EventOriginEnum;
import ar.com.xyz.gameengine.util.AbstractMenuItemBasedMenuGameState;
import ar.com.xyz.gameengine.util.FontSpec;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.control2d.menu.Control2DMenuMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.Animation2DDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.CameraControllerDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.CameraQuadsDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.GuiDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.LightsDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.LookAtDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.NewLookAtVersionDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.NormalMapDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.ObjLoaderDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.PentagonAnimationMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.RagDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.ReflectiveQuadsDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.TerrainDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.ViewStaticSceneDemoMenuItem;
import ar.com.xyz.simpledemo.gamestate.menuitem.WaterDemoMenuItem;
import ar.com.xyz.simpledemo.lightandshadow.menuitem.LightsAndShadowsMenuMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.EntitiesMenuMenuItem;
import ar.com.xyz.simpledemo.presentation.menuitem.PresentationMenuMenuItem;

public class SimpleDemoMenuGameState extends AbstractMenuItemBasedMenuGameState {

	private static FontSpec normalFontSpec = new FontSpec("arial", 2f, new Vector3f(.5f, .5f, .5f));
	private static FontSpec seleccionadoFontSpec = new FontSpec("arial", 2f, new Vector3f(1f, 1f, 1f));
	
	public SimpleDemoMenuGameState(String sound, String background) {
		super(normalFontSpec, seleccionadoFontSpec, sound, background, 0.05f, "EXIT", EventOriginEnum.MOUSE) ;
	}

	private List<MenuItem> menuItemList = new ArrayList<MenuItem>() ;

	@Override
	protected List<MenuItem> getMenuItemList() {
		if (menuItemList.size() == 0) {

			menuItemList.add(Control2DMenuMenuItem.getInstance());
			menuItemList.add(EntitiesMenuMenuItem.getInstance());
			
			menuItemList.add(new LookAtDemoMenuItem()) ;
			menuItemList.add(new NewLookAtVersionDemoMenuItem());
			menuItemList.add(new Animation2DDemoMenuItem());
			menuItemList.add(new CameraControllerDemoMenuItem());
			
			menuItemList.add(new ObjLoaderDemoMenuItem());
			menuItemList.add(new PentagonAnimationMenuItem());
			menuItemList.add(new ViewStaticSceneDemoMenuItem());
			menuItemList.add(new LightsDemoMenuItem());
			menuItemList.add(new WaterDemoMenuItem());
			menuItemList.add(new RagDemoMenuItem());
			menuItemList.add(new GuiDemoMenuItem());
			menuItemList.add(LightsAndShadowsMenuMenuItem.getInstance());
			menuItemList.add(new TerrainDemoMenuItem());
			menuItemList.add(PresentationMenuMenuItem.getInstance());
			menuItemList.add(ReflectiveQuadsDemoMenuItem.getInstance());
			menuItemList.add(CameraQuadsDemoMenuItem.getInstance());
			menuItemList.add(NormalMapDemoMenuItem.getInstance());
		}
		return menuItemList;
	}

	@Override
	public void tickInputEventListener(float tpf) {
		// TODO Auto-generated method stub
		
	}
	
}
