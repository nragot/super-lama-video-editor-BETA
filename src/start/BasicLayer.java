package start;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import API.Item;
import API.Mod;

public class BasicLayer extends Layer {
	
	public BasicLayer () {
		doRenderInside = true;
		doRenderOutSide = true;
	}

	ArrayList<Item> items = new ArrayList<Item>();
	
	@Override
	public BufferedImage render(BufferedImage canvas) {
		Graphics2D g = canvas.createGraphics();
		for (Item item : items) {
			for (Mod mod : AppProperties.getMods()) {
				
			}
		}
		return null;
	}

}
