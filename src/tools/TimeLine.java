package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import start.Start;

import exceptions.NoItemFoundException;

public class TimeLine extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;

	static int time;
	static int addX;
	static short type;
	static ArrayList<Point> items = new ArrayList<Point>();
	
	public TimeLine () {
		setTitle("timeline");
	}

	public void GO() {
		setContentPane(new KeyframePanel());
		addKeyListener(this);
		setVisible(true);
	}

	private class KeyframePanel extends JPanel{
		
		private static final long serialVersionUID = 1L;

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
			g.setColor(Color.blue);
			try {
				for (int index = 1; index < Start.getMainWindow().getSelectedItem().getAllKeyFramesTranslation().length; index ++) {
					String str1 = Start.getMainWindow().getSelectedItem().getKeyFrameTranslate(index - 1);
					String str2 = Start.getMainWindow().getSelectedItem().getKeyFrameTranslate(index);
					int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
					int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
					g.fillRect(str1T*10 + addX + 1 , 8, 8, 8);

					if (index%2 != 0) {
						if (str2.substring(0, 1).equals("T")||str1.substring(0, 1).equals("T")) g.drawLine(str1T*10 + addX + 5, 12, str2T*10 + addX + 5, 12);
						else g.drawLine(str1T*10 + addX + 5, 12, str2T*10 + addX + 5, 10);
					} else {
						if (str2.substring(0, 1).equals("T")||str1.substring(0, 1).equals("T")) g.drawLine(str1T*10 + addX + 5, 10, str2T*10 + addX + 5, 10);
						else g.drawLine(str1T*10 + addX + 5, 10, str2T*10 + addX + 5, 12);
					}
				}
				if (Start.getMainWindow().getSelectedItem().getAllKeyFramesTranslation().length > 0) {
					String str1 = Start.getMainWindow().getSelectedItem().getKeyFrameTranslate(0);
					g.fillRect(Integer.parseInt(str1.substring(1,str1.indexOf(':')))*10 + addX + 1 , 8, 8, 8);
				}
			} catch (NumberFormatException | NoItemFoundException | IndexOutOfBoundsException e) {}
			g.setColor(Color.green);
			try {
				for (int index = 1; index < Start.getMainWindow().getSelectedItem().getAllKeyFramesRotation().length; index ++) {
					String str1 = Start.getMainWindow().getSelectedItem().getKeyFrameRotation(index - 1);
					String str2 = Start.getMainWindow().getSelectedItem().getKeyFrameRotation(index);
					int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
					int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
					g.fillRect(str1T*10 + addX + 1 , 18, 8, 8);

					if (index%2 != 0) {
						if (str2.substring(0, 1).equals("R")||str1.substring(0, 1).equals("R")) g.drawLine(str1T*10 + addX + 5, 22, str2T*10 + addX + 5, 22);
						else g.drawLine(str1T*10 + addX + 5, 22, str2T*10 + addX + 5, 20);
					} else {
						if (str2.substring(0, 1).equals("R")||str1.substring(0, 1).equals("R")) g.drawLine(str1T*10 + addX + 5, 20, str2T*10 + addX + 5, 20);
						else g.drawLine(str1T*10 + addX + 5, 20, str2T*10 + addX + 5, 22);
					}
				}
				
				if (Start.getMainWindow().getSelectedItem().getAllKeyFramesRotation().length > 0) {
					String str1 = Start.getMainWindow().getSelectedItem().getKeyFrameRotation(0);
					g.fillRect(Integer.parseInt(str1.substring(1,str1.indexOf(':')))*10 + addX + 1 , 18, 8, 8);
				}
			} catch (NumberFormatException | NoItemFoundException | IndexOutOfBoundsException e) {
			}
		}
	}

		private class VideoFramePanel extends JPanel{

			private static final long serialVersionUID = 1L;

			public void paintComponent (Graphics g) {
				g.setColor(Color.white);
				g.fillRect(0, 0, getWidth(), getHeight());
				g.setColor(Color.BLACK);
				for (int i = 0; i < getHeight()/50+2; i ++) {
					g.drawLine(0, i*50, getWidth(), i*50);
				}

				g.setColor(Color.black);
				g.drawLine(200, 50, 200, getHeight());
				g.setColor(new Color (40,40,40,80));
				g.fillRect(0, 0, 200, getHeight());
				g.fillRect(0, 0, getWidth(), 50);

				g.setColor(Color.black);
				for (int i = 0 ; i < Start.getMainWindow().getItemSelection().size();i++) {
					try {
						g.drawString(Start.getMainWindow().getSelectedItem(i).getName(), 5, 50*(i) + 70);
						int y = 0;
						for (y = 0; y < Start.getMainWindow().getSelectedItem(i).getAllKeyframeActiv().size()-1; y++) {
							if (y%2 == 1)
								g.fillRect(Start.getMainWindow().getSelectedItem(i).getKeyframeActiv(y)*10+200, 50*(i+1), Start.getMainWindow().getSelectedItem(i).getKeyframeActiv(y+1)*10 - Start.getMainWindow().getSelectedItem(i).getKeyframeActiv(y)*10, 50);
						}
						if (y%2 == 1) {
							g.fillRect(200+Start.getMainWindow().getSelectedItem(i).getKeyframeActiv(Start.getMainWindow().getSelectedItem(i).getAllKeyframeActiv().size()-1)*10, 50*(i+1), getWidth(), 50);
						}
					} catch (ArrayIndexOutOfBoundsException | NoItemFoundException e) {
						e.printStackTrace();
					}
				}
				
				g.setColor(Color.GREEN);
				g.drawLine(200 + time*10, 50, 200 + time*10, getHeight());
			}
		}

		public static void setTime (int i) {
			time = i;
		}

		public static void addTime (int i) {
			time += i;
		}

		public static  int getTime () {
			return time;
		}

		public static void calculateItemsState () {
			for (int i = 0; i < Start.getMainWindow().getListSprites().size() ; i++) {
				{ // translation
					String str1 = Start.getMainWindow().getListSprites().get(i).getLastKeyFrameTranslate(time);
					String str2 = Start.getMainWindow().getListSprites().get(i).getNextKeyFrameTranslate(time);

					if (str2.substring(0, 1).equals("t")&& str1.substring(0, 1).equals("t")) {
						int str1T = Integer.parseInt(str1.substring(1,str1.indexOf(':')));
						int str1X = (int) Double.parseDouble(Start.getMainWindow().getListSprites().get(i).calculeVariable(str1.substring(str1.indexOf(':') + 1, str1.indexOf(','))));
						int str1Y = (int) Double.parseDouble(Start.getMainWindow().getListSprites().get(i).calculeVariable(str1.substring(str1.indexOf(',') + 1)));

						int str2T = Integer.parseInt(str2.substring(1,str2.indexOf(':')));
						int str2X = (int) Double.parseDouble(Start.getMainWindow().getListSprites().get(i).calculeVariable(str2.substring(str2.indexOf(':') + 1, str2.indexOf(','))));
						int str2Y = (int) Double.parseDouble(Start.getMainWindow().getListSprites().get(i).calculeVariable(str2.substring(str2.indexOf(',') + 1)));


						//System.out.println("strings" + str1 + " :: "+ str2);

						try {
							if (str1T == str2T) {
								Start.getMainWindow().getListSprites().get(i).setPosX(str1X);
								Start.getMainWindow().getListSprites().get(i).setPosY(str1Y);
							} else {
								Start.getMainWindow().getListSprites().get(i).setPosX((int) (str1X + ((str2X - str1X) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
								Start.getMainWindow().getListSprites().get(i).setPosY((int) (str1Y + ((str2Y - str1Y) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
							}
						} catch (java.lang.ArithmeticException e) {

						}
					} else {
						Start.getMainWindow().getListSprites().get(i).setPosX((int) Double.parseDouble(Start.getMainWindow().getListSprites().get(i).calculeVariable(str1.substring(str1.indexOf(':') + 1, str1.indexOf(',')))));
						Start.getMainWindow().getListSprites().get(i).setPosY((int) Double.parseDouble(Start.getMainWindow().getListSprites().get(i).calculeVariable(str1.substring(str1.indexOf(',') + 1))));
					}
				}
				{ //rotation
					String str1 = Start.getMainWindow().getListSprites().get(i).getLastKeyFrameRotation(time);
					String str2 = Start.getMainWindow().getListSprites().get(i).getNextKeyFrameRotation(time);

					int str1T = (int) Double.parseDouble(str1.substring(1,str1.indexOf(':')));
					int str1R = (int) Double.parseDouble(str1.substring(str1.indexOf(':') + 1));
					int str2T = (int) Double.parseDouble(str2.substring(1,str2.indexOf(':')));
					int str2R = (int) Double.parseDouble(str2.substring(str2.indexOf(':') + 1));

					try {
						if (str1T == str2T) {
							Start.getMainWindow().getListSprites().get(i).setRotation(str1R);
						} else {
							Start.getMainWindow().getListSprites().get(i).setRotation((int) (str1R + ((str2R - str1R) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
						}
					} catch (java.lang.ArithmeticException e) {

					}
				}
				Start.getMainWindow().getListSprites().get(i).cache();
			}
			for (int i = 0; i < Start.getMainWindow().getListTextItem().size() ; i++) {
				{ // translation
					String str1 = Start.getMainWindow().getListTextItem().get(i).getLastKeyFrameTranslate(time);
					String str2 = Start.getMainWindow().getListTextItem().get(i).getNextKeyFrameTranslate(time);

					int str1T = (int) Double.parseDouble(str1.substring(1,str1.indexOf(':')));
					int str1X = (int) Double.parseDouble(str1.substring(str1.indexOf(':') + 1, str1.indexOf(',')));
					int str1Y = (int) Double.parseDouble(str1.substring(str1.indexOf(',') + 1));

					int str2T = (int) Double.parseDouble(str2.substring(1,str2.indexOf(':')));
					int str2X = (int) Double.parseDouble(str2.substring(str2.indexOf(':') + 1, str2.indexOf(',')));
					int str2Y = (int) Double.parseDouble(str2.substring(str2.indexOf(',') + 1));

					//System.out.println("strings" + str1 + " :: "+ str2);

					try {
						if (str1T == str2T) {
							Start.getMainWindow().getListTextItem().get(i).setPosX(str1X);
							Start.getMainWindow().getListTextItem().get(i).setPosY(str1Y);
						} else {
							Start.getMainWindow().getListTextItem().get(i).setPosX((int) (str1X + ((str2X - str1X) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
							Start.getMainWindow().getListTextItem().get(i).setPosY((int) (str1Y + ((str2Y - str1Y) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
						}
					} catch (java.lang.ArithmeticException e) {

					}
				}
				{ //rotation
					String str1 = Start.getMainWindow().getListTextItem().get(i).getLastKeyFrameRotation(time);
					String str2 = Start.getMainWindow().getListTextItem().get(i).getNextKeyFrameRotation(time);

					int str1T = (int) Double.parseDouble(str1.substring(1,str1.indexOf(':')));
					int str1R = (int) Double.parseDouble(str1.substring(str1.indexOf(':') + 1));
					int str2T = (int) Double.parseDouble(str2.substring(1,str2.indexOf(':')));
					int str2R = (int) Double.parseDouble(str2.substring(str2.indexOf(':') + 1));

					try {
						if (str1T == str2T) {
							Start.getMainWindow().getListTextItem().get(i).setRotation(str1R);
						} else {
							Start.getMainWindow().getListTextItem().get(i).setRotation((int) (str1R + ((str2R - str1R) / ((str2T - str1T) + 0.0) ) * (time - str1T)));
						}
					} catch (java.lang.ArithmeticException e) {

					}
				}
				{ // text's keyframe
					String str1 = Start.getMainWindow().getListTextItem().get(i).getLastKeyFrameText(time);
					Start.getMainWindow().getListTextItem().get(i).setText(str1.substring(str1.indexOf(':')+1));
				}
				{//Reload
					Start.getMainWindow().getListTextItem().get(i).reload();
				}
				Start.getMainWindow().getListTextItem().get(i).cache();
			}
			for (int i = 0; i < Start.getMainWindow().getListVideo().size() ; i++) {
				Start.getMainWindow().getListVideo().get(i).CalculeAndSetProperFrame();
				Start.getMainWindow().getListVideo().get(i).cache();
			}
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
			} else if (e.getKeyCode() == 71) {
				String str = (String)JOptionPane.showInputDialog( TimeLine.this,"time to jump in the tardis !","travel in time",JOptionPane.QUESTION_MESSAGE,new ImageIcon(
						getClass().getResource("/timeline jump.png")),null,"give the frame you want to go now");
				try {
					time = Integer.parseInt(str);
					calculateItemsState();
				} catch (NumberFormatException exc) {
					
				}
			} else if (e.getKeyChar() == '1' ) {
				setContentPane(new KeyframePanel());
				revalidate();
			} else if (e.getKeyChar() == '2') {
				setContentPane(new VideoFramePanel());
				revalidate();
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