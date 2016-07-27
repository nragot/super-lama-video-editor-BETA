package mod.slve.items;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ShapeRect extends Shape {
	static int nbofshape = 0;
	int roundBoundX = 30, roundBoundY = 30;
	
	public ShapeRect() {
		m_id = 401;
		nbofshape++;
		m_name = "rect n#"+nbofshape;
		setWidth(300);
		setHeight(300);
		reload();
	}
	
	public void reload () {
		bi = new BufferedImage(cacheWidth, cacheHeight , BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRoundRect(0, 0, Integer.parseInt(m_width)-1, Integer.parseInt(m_height)-1, roundBoundX, roundBoundY);
	}
	
	public void setRoundBoundX (int i) {
		roundBoundX = i;
	}
	
	public int getRoundBoundX () {
		return roundBoundX;
	}
	
	public void setRoundBoundY (int i) {
		roundBoundY = i;
	}
	
	public int getRoundBoundY () {
		return roundBoundY;
	}

}
