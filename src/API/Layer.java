package API;


import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public abstract class Layer {
	
	protected boolean doRenderInside;
	protected boolean doRenderOutSide;
	protected ArrayList<SlveButton> leftButtons = new ArrayList<SlveButton>();
	protected ArrayList<SlveButton> rightButtons = new ArrayList<SlveButton>();
	protected String name;
	
	public abstract void render (BufferedImage canvas, int x, int y, int cw, int ch, double z);
	public abstract void render (Graphics2D g, int x, int y, int w, int h, int cw, int ch, double z);
	
	public boolean doRenderInside() {
		return doRenderInside;
	}
	public boolean doRenderOutside() {
		return doRenderOutSide;
	}
	public ArrayList<SlveButton> getLeftButtons () {
		return leftButtons;
	}
	public ArrayList<SlveButton> getRightButtons () {
		return rightButtons; 
	}
	public String getName () {
		return name;
	}
	public Container getOutline () {
		return new Container();
	}
	public abstract Image getIcon ();
	

}
