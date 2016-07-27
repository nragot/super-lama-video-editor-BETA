package start;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import API.Item;

public abstract class Layer {
	
	boolean doRenderInside, doRenderOutSide;
	
	public abstract BufferedImage render (BufferedImage canvas);
	public boolean doRenderInside() {
		return doRenderInside;
	}
	public boolean doRenderOutside() {
		return doRenderOutSide;
	}

}
