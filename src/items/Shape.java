package items;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Shape extends Item {
	Color color = new Color (0, 0, 0, 255);
	BufferedImage bi;
	int angle;
	
	public Shape() {
		m_width = 100+"";
		m_height = 100+"";
		bi = new BufferedImage(100, 100 , BufferedImage.TYPE_INT_ARGB);
	}
	
	public Image getImage () {
		return bi;
	}

}
