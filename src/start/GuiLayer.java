package start;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import exceptions.NoItemFoundException;

import API.Layer;

public class GuiLayer extends Layer {
	
	public GuiLayer () {
		doRenderInside = true;
		doRenderOutSide = false;
		name = "graphical user's interface";
	}

	@Override
	public void render(BufferedImage canvas, int x, int y, int cw, int ch, double z) {
		//TODO: make something that fit a bit more than a empty methode
	}

	@Override
	public void render(Graphics2D g, int x, int y,int w, int h, int cw, int ch, double z) {
		g.fillRect(0, h - 20, w, 20);
		g.setColor(Color.black);
		g.drawRect(x, y, (int) (cw*z), (int) (ch*z));
		g.setColor(new Color (50,50,50,50));
		g.fillRect(0, 0, w, y);
		g.fillRect(0, ch + y, w, h - ch - y);
		g.fillRect(0, y, x, ch);
		g.fillRect(x + cw, y, w - cw - x, ch);		g.setColor(Color.white);
		try {
			g.drawString(Start.getMainWindow().getSelectedItem().getInfo(), 10, h - 5);
		} catch (IndexOutOfBoundsException | NoItemFoundException e) {
			g.drawString("no item selected", 10, h - 5);
		}
	}

}
