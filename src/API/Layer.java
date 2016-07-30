package API;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;


public abstract class Layer {
	
	protected boolean doRenderInside;
	protected boolean doRenderOutSide;
	protected ArrayList<SlveButton> leftButtons = new ArrayList<SlveButton>();
	protected ArrayList<SlveButton> rightButtons = new ArrayList<SlveButton>();
	protected String name;
	
	public abstract void render (BufferedImage canvas);
	public abstract void render (Graphics2D g);
	
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
		return rightButtons; //TODO:draw em
	}
	public String getName () {
		return name;
	}

}
