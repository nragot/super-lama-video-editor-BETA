package items;

import java.awt.Image;
import java.util.ArrayList;

import start.MainWindow;

public class ImageItem extends Item{
	int focusImage = 0;
	ArrayList<Image> images = new ArrayList<Image>();
	
	public ImageItem (Image img, String name, int posX, int posY, int width, int height) {
		images.add(img);
		m_name = name;
		m_posX = posX+"";
		m_posY = posY+"";
		m_width = width+"";
		m_height = height+"";
		cachePosX = posX;
		cachePosY = posY;
		cacheWidth = width;
		cacheHeight = height;
		cacheRotation = 0;
		m_ratio = width/(height*1.0);
		m_id = 1;
		addKeyFrameTranslate(0, posX+"", posY+"",1);
		addKeyFrameRotation(0, 0+"");
	}
	
	public ImageItem (Image img, String name, int posX, int posY) {
		images.add(img);
		m_name = name;
		m_posX = posX+"";
		m_posY = posY+"";
		m_width = img.getWidth(null)+"";
		m_height = img.getHeight(null)+"";
		cachePosX = posX;
		cachePosY = posY;
		cacheWidth = img.getWidth(null);
		cacheHeight = img.getHeight(null);
		cacheRotation = 0;
		m_ratio = getWidth()/(getHeight()*1.0);
		m_id = 1;
		addKeyFrameTranslate(0, posX+"", posY+"",1);
		addKeyFrameRotation(0, 0+"");
	}
	
	public Image getImage () {
		return images.get(focusImage);
	}

}
