package start;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import exceptions.NoItemFoundException;

import API.Item;
import API.Layer;
import API.SlveButton;

public class BasicLayer extends Layer {
	
	public BasicLayer (String name) {
		doRenderInside = true;
		doRenderOutSide = true;
		this.name = name;
		
		leftButtons.add(new SlveButton("in") {
			
			@Override
			public void push() {
				//TODO: render in and out status switch -> MAKE IT WORK
			}
			
		});
		
		leftButtons.add(new SlveButton("out") {
			
			@Override
			public void push() {
				System.out.println("render2");
			}
			
		});
		
		rightButtons.add(new SlveButton("X") {
			
			@Override
			public void push() {
				System.out.println("out");
				Start.getMainWindow().removeLayerByName(BasicLayer.this.getName());
			}
		});
	}
	

	ArrayList<Item> items = new ArrayList<Item>();
	
	@Override
	public void render(BufferedImage canvas) {
		Graphics2D g = canvas.createGraphics();
		render (g);
	}
	
	@Override
	public void render(Graphics2D g) {
		for (Item item : items) {
			item.getParentMod().render(item, g);
		}
	}

}
