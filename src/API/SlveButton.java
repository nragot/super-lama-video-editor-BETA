package API;

import javax.swing.ImageIcon;

public abstract class SlveButton {
	
	protected ImageIcon icon;
	protected String name;
	private int w,h;
	
	public SlveButton (String str) {
		name = str;
		w = 40;
		h = 30;
	}
	
	public SlveButton (ImageIcon img) {
		icon = img;
		w = img.getIconWidth();
		h = img.getIconHeight();
	}
	
	public ImageIcon getIcon () {
		return icon;
	}
	
	public String getName () {
		return name;
	}
	
	public int getWidth () {
		return w;
	}
	
	public int getHeight () {
		return h;
	}
	
	public abstract void push ();

}
