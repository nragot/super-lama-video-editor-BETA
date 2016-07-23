package items;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TextItem extends SlveItem {
	
	String text = "";
	static int lastText;
	BufferedImage bi;
	int fontSize = 16, canvasWidth, canvasHeight;
	String fontSizeFormula = fontSize+"";
	ArrayList<String> keyFrameText = new ArrayList<String>();
	
	public TextItem (String str) {
		text = str;
		setId(2);
		setName("Text #"+lastText);
		lastText ++;
		setHeight(16);
		setWidth(39);
		reload();
	}
	
	public void setText (String str) {
		text = str;
	}
	
	public void setFontSize (int i) {
		fontSize = i;
	}
	
	public void setFontSizeFormula (String formula) {
		fontSizeFormula = formula;
		if (formula.equals("")) return;
		String str = calculeVariable(formula);
		if (!str.equals("!"))
			fontSize = (int) Double.parseDouble(str);
		else {
			fontSize = 1;
		}
	}
	
	public void setFontSizeFormulaNoCache (String formula) {
		fontSizeFormula = formula;
	}
	/**
	 * redraw the panel containing the text
	 */
	public void reload () {
		String newTxt = calculeVariableNoChange(text);
		System.out.println("text:"+text);
		bi = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setFont(new Font("Dialog", Font.PLAIN, fontSize));
		canvasWidth = g2d.getFontMetrics().stringWidth(newTxt);
		try {
			bi =new BufferedImage(canvasWidth, fontSize+fontSize/4, BufferedImage.TYPE_INT_ARGB);
		} catch (IllegalArgumentException exc) {
			g2d.setFont(new Font("Dialog", Font.PLAIN, 20));
			canvasWidth = g2d.getFontMetrics().stringWidth("!");
			bi =new BufferedImage(canvasWidth, 20, BufferedImage.TYPE_INT_ARGB);
			
			g2d = bi.createGraphics();
			g2d.setColor(Color.black);
			g2d.setFont(new Font("Dialog", Font.PLAIN, 20));
			g2d.drawString(newTxt, 0,20);
			g2d.dispose();
			return;
		}
		
		g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.setFont(new Font("Dialog", Font.PLAIN, fontSize));
		g2d.drawString(newTxt, 0,fontSize);
		g2d.dispose();
	}
	
	public String getText () {
		return text;
	}
	
	public int getFontSize () {
		return fontSize;
	}
	
	public String getFontSizeFormula () {
		return fontSizeFormula;	
	}
	
	public BufferedImage getImage () {
		return bi;
	}
	
	@Override
	public boolean cache() {
		boolean isEveryThingRight = super.cache();
		String str = calculeVariable(fontSizeFormula);
		if (!str.equals("!")) {
			fontSize = (int) Double.parseDouble(str);
			isEveryThingRight = false;
		} else { 
			fontSize = 1;
		}
		return isEveryThingRight;
	}
	
	public boolean addKeyFrameText (int time,String x) {
		String str = getLastKeyFrameText(time);
		if (!str.equals("!")&&str.substring(1, str.indexOf(':')).equals(time+"")) return false;
		str = "m"+time+":"+x;
		
		int T = Integer.parseInt(str.substring(1, str.indexOf(':')));
		int finalIndex = 0;
		
		a:for (int index = 0; index < keyFrameText.size();index++) {
			int testedTime = Integer.parseInt(keyFrameText.get(index).substring(1, keyFrameText.get(index).indexOf(':')));
			if (T < testedTime) {
				break a;
			} else {
				finalIndex += 1;
			}
		}
		keyFrameText.add(finalIndex, str);
		return true;
	}
	
	public String getKeyFrameText (int i) {
		return keyFrameText.get(i);
	}
	
	/**
	 * return the last TextkeyFrame from time given, (if everything went fine). Otherwise it will return "!"
	 */
	public String getLastKeyFrameText (int i) {
		for (int index = 0; index < keyFrameText.size(); index++) {
			String str = keyFrameText.get(index);
			int testedTime = Integer.parseInt(str.substring(1,keyFrameText.get(index).indexOf(':')));
			
			if (testedTime > i) {
				return keyFrameText.get(index - 1);
			}
		}
		try {
			return keyFrameText.get(keyFrameText.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return "!";
		}
	}
	
	/**
	 * return the next translationkeyFrame from time given, (if everything went fine). Otherwise it will return "!"
	 */
	public String getNextKeyFrameText (int i) {
		for (int index = 0; index < keyFrameText.size(); index++) {
			String str = keyFrameText.get(index);
			int testedTime = Integer.parseInt(str.substring(1),keyFrameText.get(index).indexOf(':'));
			
			if (testedTime > i) {
				return keyFrameText.get(index);
			}
		}
		try {
			return keyFrameText.get(keyFrameText.size()-1);
		} catch (ArrayIndexOutOfBoundsException e) {
			return "!";
		}
	}
	
	public void deleteKeyFrameTextAt (int time) {
		for (int index = 0; index < keyFrameText.size();index++) {
			int testedTime = Integer.parseInt(keyFrameText.get(index).substring(1, keyFrameText.get(index).indexOf(':')));
			if (testedTime == time) 
				keyFrameText.remove(index);
		}
	}

}