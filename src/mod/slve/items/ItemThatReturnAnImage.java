package mod.slve.items;

import java.awt.Image;

public abstract class ItemThatReturnAnImage extends SlveItem {
	
	private static final long serialVersionUID = 1L;

	public ItemThatReturnAnImage () {
		super("no name");
	}
	
	public abstract Image getImage ();

}
