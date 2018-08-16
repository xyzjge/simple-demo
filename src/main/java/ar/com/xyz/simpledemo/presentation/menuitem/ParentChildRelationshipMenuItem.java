package ar.com.xyz.simpledemo.presentation.menuitem;

import ar.com.xyz.gameengine.AbstractGameState;
import ar.com.xyz.gameengine.util.MenuItem;
import ar.com.xyz.simpledemo.presentation.parentchild.ParentChildRelationshipGameState;

public class ParentChildRelationshipMenuItem extends MenuItem {

	public ParentChildRelationshipMenuItem() {
		setText("PARENT CHILD RELATIONSHIP");
	}

	@Override
	public AbstractGameState getGameStateInstance() {
		return new ParentChildRelationshipGameState();
	}

}
