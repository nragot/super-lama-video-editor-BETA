package items;

import java.awt.Image;
import java.util.ArrayList;

import javax.swing.JFrame;

public class Item extends JFrame{
	ArrayList<String> keyFrameTranslation = new ArrayList<String>();
	ArrayList<String> keyFrameRotation = new ArrayList<String>();
	int m_posX, m_posY, m_width, m_height, m_rotation, m_id;
	double m_ratio;
	String m_name;
	
	public int getPosX () {
		return m_posX;
	}
	
	public int getPosY () {
		return m_posY;
	}
	
	public int getWidth () {
		return m_width;
	}
	
	public int getHeight () {
		return m_height;
	}
	
	public void setPosX (int i) {
		m_posX = i;
	}
	
	public void setPosY (int i) {
		m_posY = i;
	}
	
	public void setWidth (int i) {
		m_width = i;
	}
	
	public void setHeight (int i) {
		m_height = i;
	}
	
	public void setName (String name) {
		m_name = name;
	}
	
	public String getName () {
		return m_name;
	}
	
	public void setId (int i) {
		m_id = i;
	}
	
	public int getId () {
		return m_id;
	}
	
	public void setRotation (int i) {
		m_rotation = i;
	}
	
	public int getRotation () {
		return m_rotation;
	}
	
	public double getRatio () {
		return m_ratio;
	}
	
	public void addKeyFrameTranslate (int time,int x, int y) {
		String str = "t"+time+":"+x+","+y;
		System.out.println("keyframe added :" + str);
		
		int T = Integer.parseInt(str.substring(1, str.indexOf(':')));
		int finalIndex = 0;
		
		a:for (int index = 0; index < keyFrameTranslation.size();index++) {
			int testedTime = Integer.parseInt(keyFrameTranslation.get(index).substring(1, keyFrameTranslation.get(index).indexOf(':')));
			if (T < testedTime) {
				break a;
			} else {
				finalIndex += 1;
			}
		}
		keyFrameTranslation.add(finalIndex, str);
	}
	
	public String getKeyFrameTranslate (int i) {
		return keyFrameTranslation.get(i);
	}
	
	public String getLastKeyFrameTranslate (int i) {
		for (int index = 0; index < keyFrameTranslation.size(); index++) {
			String str = keyFrameTranslation.get(index);
			int testedTime = Integer.parseInt(str.substring(1,str.indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameTranslation.get(index - 1);
			}
		}
		return keyFrameTranslation.get(keyFrameTranslation.size()-1);
	}
	
	public String getNextKeyFrameTranslate (int i) {
		for (int index = 0; index < keyFrameTranslation.size(); index++) {
			String str = keyFrameTranslation.get(index);
			int testedTime = Integer.parseInt(str.substring(1,str.indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameTranslation.get(index);
			}
		}
		return keyFrameTranslation.get(keyFrameTranslation.size()-1);
	}
	
	public String[] getAllKeyFramesTranslation () {
		String[] str = new String[keyFrameTranslation.size()];
		for (int index = 0; index < keyFrameTranslation.size(); index++) {
			str[index] = keyFrameTranslation.get(index);
		}
		return str;
	}
	
	public void deleteKeyFrameTranslationAt (int time) {
		for (int index = 0; index < keyFrameTranslation.size();index++) {
			int testedTime = Integer.parseInt(keyFrameTranslation.get(index).substring(1, keyFrameTranslation.get(index).indexOf(':')));
			if (testedTime == time) 
				keyFrameTranslation.remove(index);
		}
	}
	
	public void addKeyFrameRotation (int time, int rotation) {
		String str = "r"+time+":"+rotation;
		System.out.println("keyframe added :" + str);
		
		int T = Integer.parseInt(str.substring(1, str.indexOf(':')));
		int finalIndex = 0;
		
		a:for (int index = 0; index < keyFrameRotation.size();index++) {
			int testedTime = Integer.parseInt(keyFrameRotation.get(index).substring(1, keyFrameRotation.get(index).indexOf(':')));
			if (T < testedTime) {
				break a;
			} else {
				finalIndex += 1;
			}
		}
		keyFrameRotation.add(finalIndex, str);
	}
	
	public String getLastKeyFrameRotation (int i) {
		for (int index = 0; index < keyFrameRotation.size(); index++) {
			String str = keyFrameRotation.get(index);
			int testedTime = Integer.parseInt(str.substring(1,str.indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameRotation.get(index - 1);
			}
		}
		return keyFrameRotation.get(keyFrameRotation.size()-1);
	}
	
	public String getNextKeyFrameRotation (int i) {
		for (int index = 0; index < keyFrameRotation.size(); index++) {
			String str = keyFrameRotation.get(index);
			int testedTime = Integer.parseInt(str.substring(1,str.indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameRotation.get(index);
			}
		}
		return keyFrameRotation.get(keyFrameRotation.size()-1);
	}
	
	public void deleteKeyFrameRotationAt (int time) {
		for (int index = 0; index < keyFrameRotation.size();index++) {
			int testedTime = Integer.parseInt(keyFrameRotation.get(index).substring(1, keyFrameRotation.get(index).indexOf(':')));
			if (testedTime == time) 
				keyFrameRotation.remove(index);
		}
	}
	
	public String[] getAllKeyFramesRotation () {
		String[] str = new String[keyFrameRotation.size()];
		for (int index = 0; index < keyFrameRotation.size(); index++) {
			str[index] = keyFrameRotation.get(index);
		}
		return str;
	}
	
	public String getKeyFrameRotation (int i) {
		return keyFrameRotation.get(i);
	}

}
