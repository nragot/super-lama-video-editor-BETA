package mod.slve.items;

import java.awt.Image;

public abstract class ItemThatReturnAnImage extends SlveItem {
	
	public ItemThatReturnAnImage () {
		super("no name");
	}
	
	public abstract Image getImage ();

}
