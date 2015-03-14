package items;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TextItem extends Item {
	
	String text = "";
	static int lastText;
	BufferedImage bi;
	int fontSize = 16, canvasWidth, canvasHeight;
	
	public TextItem (String str) {
		text = str;
		setId(2);
		setName("Text #"+lastText);
		lastText ++;
		m_height = 16+"";
		m_width = 39+"";
		reload();
	}
	
	public void setText (String str) {
		text = str;
	}
	
	public void setFontSize (int i) {
		fontSize = i;
	}
	
	public void reload () {
		bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setFont(new Font("Dialog", Font.PLAIN, fontSize));
		canvasWidth = g2d.getFontMetrics().stringWidth(text);
		bi =new BufferedImage(canvasWidth, fontSize+fontSize/4, BufferedImage.TYPE_INT_ARGB);
		
		g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Dialog", Font.PLAIN, fontSize));
		g2d.drawString(text, 0,fontSize);
		g2d.dispose();
	}
	
	public String getText () {
		return text;
	}
	
	public int getFontSize () {
		return fontSize;
	}
	
	public BufferedImage getImage () {
		return bi;
	}

}
