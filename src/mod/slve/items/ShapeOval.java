package mod.slve.items;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ShapeOval extends Shape{
	static int nbOfShape = 0;
	
	public ShapeOval () {
		m_id = 402;
		nbOfShape++;
		m_name = "Oval n#" + nbOfShape;
		reload();
	}
	
	public void reload () {
		bi = new BufferedImage(100, 100 , BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillOval(0, 0, Integer.parseInt(m_width)-1, Integer.parseInt(m_height)-1);
	}
}
