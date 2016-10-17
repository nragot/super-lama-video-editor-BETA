package start;


import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JButton;

import API.Item;
import API.Layer;
import API.SlveButton;

public class BasicLayer extends Layer {
	
	static Image image;
	
	public BasicLayer (String name) {
		doRenderInside = true;
		doRenderOutSide = true;
		this.name = name;
		
		leftButtons.add(new SlveButton("in") {
			
			@Override
			public void push() {
				doRenderInside = !doRenderInside;
				if (doRenderInside) 
					name = "in";
				else 
					name = "[in]";
			}
			
		});
		
		leftButtons.add(new SlveButton("out") {
			
			@Override
			public void push() {
				doRenderOutSide = !doRenderOutSide;
				if (doRenderOutSide) 
					name = "out";
				else 
					name = "[out]";
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
	
	public void addItem (Item item) {
		items.add(item);
	}
	
	public ArrayList<Item> getItemList () {
		return items;
	}
	
	ArrayList<Item> items = new ArrayList<Item>();
	
	@Override
	public void render(BufferedImage canvas, int x, int y, int cw, int ch, double z) {
		Graphics2D g = canvas.createGraphics();
		render (g,x,y,canvas.getWidth(),canvas.getHeight(),cw,ch,z);
	}
	
	@Override
	public void render(Graphics2D g, int x, int y, int w, int h, int cw, int ch, double z) {
		for (Item item : items) {
			item.getParentMod().render(item, g, x, y, w , h, cw, ch, z);
		}
	}
	
	@Override
	public Container getOutline () {
		Container cont = new Container();
		for (final Item item : items) {
			JButton button = new JButton(item.getName());
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Start.getMainWindow().getItemSelection().add(item);
					Start.getItemOption().loadOptions();
				}
			});
			
			cont.add(button);
		}
		cont.setLayout(new FlowLayout());
		return cont;
	}

	@Override
	public Image getIcon() {
		return image;
	}
	
	public static void setIcon (Image img) {
		image = img;
	}

}
