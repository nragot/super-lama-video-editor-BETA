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
	}
}
