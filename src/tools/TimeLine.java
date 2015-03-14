package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import start.MainWindow;

public class TimeLine extends JFrame implements KeyListener{
	static int time;
	static int addX;
	static short type;
	
	public TimeLine () {
		setBounds (0,0,1920,80);
		setContentPane(new MyPanel());
		addKeyListener(this);
		setTitle("timeline");
		setVisible(true);
	}
	
	private class MyPanel extends JPanel{
		public void paintComponent (Graphics g) {
			g.setColor(Color.white);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.red);
			g.fillRect(time*10+addX+1, 0, 9, getHeight() - 15);
			g.setColor(Color.black);
			g.drawLine(0, getHeight() - 15, getWidth(), getHeight() - 15);
			for (int i =0; i < 191; i++) {
				g.drawLine(i*10 , 0, i*10 , getHeight() - 15);
				if (i%10 == 0) {
					g.setColor(Color.black);
					g.drawString(-(addX/10 - (addX/10)%10) + i +"", i*10 + (addX%100), getHeight());
					g.setColor(new Color(10,10,10,40));
					g.fillRect(i*10 + addX%100, 0, 10, getHeight() - 15);
				}
			}
			if (MainWindow.getNumberOfImages() > 0 && MainWindow.getSelectedItemId() == 1) {
				g.setColor(Color.blue);
				for (int index = 1; index < MainWindow.getSelectedImageItem().getAllKeyFramesTranslation().length; index ++) {
					String str1 = MainWindow.getSelectedImageItem().getKeyFrameTranslate(index - 1);
					String str2 = MainWindow.getSelectedImageItem().getKeyFrameTranslate(index);
					int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
					int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
					
					if (index%2 != 0) {
						g.drawLine(str1T*10 + addX + 5, 12, str2T*10 + addX + 5, 12);
					} else {
						g.drawLine(str1T*10 + addX + 5, 10, str2T*10 + addX + 5, 10);
					}
				}
				g.setColor(Color.green);
				for (int index = 1; index < MainWindow.getSelectedImageItem().getAllKeyFramesRotation().length; index ++) {
					String str1 = MainWindow.getSelectedImageItem().getKeyFrameRotation(index - 1);
					String str2 = MainWindow.getSelectedImageItem().getKeyFrameRotation(index);
					int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
					int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
					
					if (index%2 != 0) {
						g.drawLine(str1T*10 + addX + 5, 22, str2T*10 + addX + 5, 22);
					} else {
						g.drawLine(str1T*10 + addX + 5, 20, str2T*10 + addX + 5, 20);
					}
				}
			}
			else if (MainWindow.getNumberOfTextItem() > 0 && MainWindow.getSelectedItemId() == 2) {
				g.setColor(Color.blue);
				for (int index = 1; index < MainWindow.getSelectedTextItem().getAllKeyFramesTranslation().length; index ++) {
					String str1 = MainWindow.getSelectedTextItem().getKeyFrameTranslate(index - 1);
					String str2 = MainWindow.getSelectedTextItem().getKeyFrameTranslate(index);
					int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
					int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
					
					if (index%2 != 0) {
						g.drawLine(str1T*10 + addX + 5, 12, str2T*10 + addX + 5, 12);
					} else {
						g.drawLine(str1T*10 + addX + 5, 10, str2T*10 + addX + 5, 10);
					}
				}
				for (int index = 1; index < MainWindow.getSelectedTextItem().getAllKeyFramesRotation().length; index ++) {
					String str1 = MainWindow.getSelectedTextItem().getKeyFrameRotation(index - 1);
					String str2 = MainWindow.getSelectedTextItem().getKeyFrameRotation(index);
					int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
					int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
					g.setColor(Color.green);
					if (index%2 != 0) {
						g.drawLine(str1T*10 + addX + 5, 22, str2T*10 + addX + 5, 22);
					} else {
						g.drawLine(str1T*10 + addX + 5, 20, str2T*10 + addX + 5, 20);
					}
				}
			}
		}
	}
	
	public void setTime (int i) {
		time = i;
	}
	
	public void addTime (int i) {
		time += i;
	}
	
	public int getTime () {
		return time;
	}

	public void calculateItemsState () {
		for (int i = 0; i < MainWindow.getListSprites().size() ; i++) {
			{ // translation
				String str1 = MainWindow.getListSprites().get(i).getLastKeyFrameTranslate(time);
				String str2 = MainWindow.getListSprites().get(i).getNextKeyFrameTranslate(time);

				int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
				int str1X = (int) Double.parseDouble(MainWindow.getSelectedImageItem().calculeVariable(str1.substring(str1.indexOf(':') + 1, str1.indexOf(','))));
				int str1Y = Integer.parseInt(str1.substring(str1.indexOf(',') + 1));

				int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
				int str2X = (int) Double.parseDouble(MainWindow.getSelectedImageItem().calculeVariable(str2.substring(str2.indexOf(':') + 1, str2.indexOf(','))));
				int str2Y = Integer.parseInt(str2.substring(str2.indexOf(',') + 1));

				//System.out.println("strings" + str1 + " :: "+ str2);

				try {
					if (str1T == str2T) {
						MainWindow.getListSprites().get(i).setPosX(str1X);
						MainWindow.getListSprites().get(i).setPosY(str1Y);
					} else {
						MainWindow.getListSprites().get(i).setPosX((int) (str1X + ((str2X - str1X) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
						MainWindow.getListSprites().get(i).setPosY((int) (str1Y + ((str2Y - str1Y) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
					}
				} catch (java.lang.ArithmeticException e) {

				}
			}
			{ //rotation
				String str1 = MainWindow.getListSprites().get(i).getLastKeyFrameRotation(time);
				String str2 = MainWindow.getListSprites().get(i).getNextKeyFrameRotation(time);
				
				int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
				int str1R = Integer.parseInt(str1.substring(str1.indexOf(':') + 1));
				int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
				int str2R = Integer.parseInt(str2.substring(str2.indexOf(':') + 1));
				
				try {
					if (str1T == str2T) {
						MainWindow.getListSprites().get(i).setRotation(str1R);
					} else {
						MainWindow.getListSprites().get(i).setRotation((int) (str1R + ((str2R - str1R) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
					}
				} catch (java.lang.ArithmeticException e) {

				}
			}
			
		}
		for (int i = 0; i < MainWindow.getListTextItem().size() ; i++) {
			{ // translation
				String str1 = MainWindow.getListTextItem().get(i).getLastKeyFrameTranslate(time);
				String str2 = MainWindow.getListTextItem().get(i).getNextKeyFrameTranslate(time);

				int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
				int str1X = Integer.parseInt(str1.substring(str1.indexOf(':') + 1, str1.indexOf(',')));
				int str1Y = Integer.parseInt(str1.substring(str1.indexOf(',') + 1));

				int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
				int str2X = Integer.parseInt(str2.substring(str2.indexOf(':') + 1, str2.indexOf(',')));
				int str2Y = Integer.parseInt(str2.substring(str2.indexOf(',') + 1));

				//System.out.println("strings" + str1 + " :: "+ str2);

				try {
					if (str1T == str2T) {
						MainWindow.getListTextItem().get(i).setPosX(str1X);
						MainWindow.getListTextItem().get(i).setPosY(str1Y);
					} else {
						MainWindow.getListTextItem().get(i).setPosX((int) (str1X + ((str2X - str1X) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
						MainWindow.getListTextItem().get(i).setPosY((int) (str1Y + ((str2Y - str1Y) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
					}
				} catch (java.lang.ArithmeticException e) {

				}
			}
			{ //rotation
				String str1 = MainWindow.getListTextItem().get(i).getLastKeyFrameRotation(time);
				String str2 = MainWindow.getListTextItem().get(i).getNextKeyFrameRotation(time);
				
				int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
				int str1R = Integer.parseInt(str1.substring(str1.indexOf(':') + 1));
				int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
				int str2R = Integer.parseInt(str2.substring(str2.indexOf(':') + 1));
				
				try {
					if (str1T == str2T) {
						MainWindow.getListTextItem().get(i).setRotation(str1R);
					} else {
						MainWindow.getListTextItem().get(i).setRotation((int) (str1R + ((str2R - str1R) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
					}
				} catch (java.lang.ArithmeticException e) {

				}
			}
		}
		for (int i = 0; i < MainWindow.getListVideo().size() ; i++) {
			MainWindow.getListVideo().get(i).CalculeAndSetProperFrame();
		}
		MainWindow.refreshItemStatFromFormula();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 37) {
			time --;
			setTitle("timeline ("+time+")");
			calculateItemsState();
		} else if (e.getKeyCode() == 39) {
			time ++;
			setTitle("timeline ("+time+")");
			calculateItemsState();
		} else if (e.getKeyCode() == 38) {
			addX += 10;
		} else if (e.getKeyCode() == 40) {
			addX -= 10;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
