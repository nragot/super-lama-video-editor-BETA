package browser;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FileBrowser {
	
	Thread MainThread;
	File file = new File(System.getProperty("user.home"));
	ArrayList<File> files = new ArrayList<File>();
	String valueToReturn, okButtonText;
	int selected = 0, menuHeight = 0;
	boolean hidden = false, HaveTODO = false, tourist = false;
	Font font ;
	BrowserActions actions;
	
	public FileBrowser () {
		MainThread = Thread.currentThread();
	}
	
	public FileBrowser(BrowserActions actions) {
		setActions(actions);
		HaveTODO = true;
	}
	
	public void setActions (BrowserActions action) {
		this.actions = action;
	}
	
	public String getPath () {
		return valueToReturn;
	}

	/**
	 * 
	 * @param tourist : if true, when user select OK! button, return path will be the one of the folder the user is in, instead of the that is selected
	 * @param hold
	 * @return
	 */
	public String go(boolean tourist, String okButtonText, final boolean hold) {
		this.tourist = tourist;
		this.okButtonText = okButtonText;
		InputStream is = this.getClass().getResourceAsStream("/browser/DejaVuSansMono.ttf");
		try {
			font = Font.createFont(Font.TRUETYPE_FONT,is);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
		if (hold) {
			new Thread () {

				public void run () {
					new MyWindow();
				}

			}.start();
			try {
				synchronized (MainThread) {
					MainThread.wait(); // THREAD IS INTERUPTED, ONLY BROWSER REMAIN, BROWSER GIVE BACK MONITOR IF CLOSEN
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return file.getAbsolutePath();
			} else {
				new MyWindow(); //THREAD IS NOT INTERUPTED, EVERYTHING GOES AS NORMAL
			return "thread not hold";
		}
	}
	
  /*/******************
	 * ADDED CLASSES
	 ******************/

	private class MyWindow extends JFrame implements WindowListener, MouseWheelListener, MouseListener, KeyListener{
		
		ImageIcon folder_icon = new ImageIcon(getClass().getResource("/icon folder.png"));
		ImageIcon file_icon = new ImageIcon(getClass().getResource("/icon file.png"));
		int addY, filesSizeHeight;
		MyPanel myPanel;
		
		private static final long serialVersionUID = 1L;
		
		public MyWindow () {
			myPanel = new MyPanel();
			setBounds (0,0,200,200);
			setMaximumSize(new Dimension(300, 200));
			addMouseWheelListener(this);
			setContentPane(myPanel);
			addWindowListener(this);
			myPanel.addMouseListener(this);
			addKeyListener(this);
			setVisible(true);
			
			readFolder(file.getPath());
		}
		
		private void readFolder (String str) {
			files.clear();
			file = new File(str);
			files.add(new File(file.getPath() + "/.."));
			for (File f : file.listFiles()) {
				if (hidden || !(f.getName().startsWith(".")))
					files.add(f);
			}
			if (tourist)
				setTitle(file.getAbsolutePath() + " (<- here's what you are choosing)");
			else 
				setTitle(file.getAbsolutePath());
		}

		private class MyPanel extends JPanel {
			public void paintComponent (Graphics graphics) {
				Graphics2D g = (Graphics2D) graphics.create();
				
				g.translate(0, addY);
				menuHeight = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()/30;
				setFont(font.deriveFont(15.0f));
				g.setRenderingHint( RenderingHints.  KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
				
				g.setColor(Color.RED);
				g.drawRect(0, selected*50, getWidth()-41, 50);
				
				g.setColor(Color.black);
				
				for (int i = 0; i < files.size(); i++) {
					if (files.get(i).isDirectory()) {
						g.drawImage(folder_icon.getImage(), 10, i*50, 50, 50, null);
					} else {
						g.drawImage(file_icon.getImage(), 10, i*50, 50, 50, null);
					}
					g.drawString(files.get(i).getName(), 100, i*50+30);
				}
				
				if (files.size()*50 > filesSizeHeight) {
					int br = (int) ( (filesSizeHeight)/( (files.size()*50.0)/filesSizeHeight) );//BAR HEIGHT
					filesSizeHeight = getHeight() - menuHeight;
					g.setColor(Color.gray);
					g.fillRect(getWidth()-40, -addY, 40, filesSizeHeight);
					g.setColor(Color.RED);
					g.fillRect(getWidth()-40, -addY + (int) ((-addY)/(files.size()*50.0 - filesSizeHeight)*(filesSizeHeight-br)), 40, br );
				}
				
				g.setColor(Color.BLACK);
				g.fillRect(0, getHeight() - menuHeight -addY, getWidth(), menuHeight);
				g.setColor(Color.WHITE);
				g.drawString(okButtonText, getWidth()/2 - g.getFontMetrics().stringWidth(okButtonText)/2, getHeight()-10 -addY);
			}
		}

		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosing(WindowEvent e) {
			System.out.println("releasing mainThread");
			valueToReturn = "failed!";
			if (HaveTODO) {
				synchronized (MainThread) {
					MainThread.notify();
				}
			}
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			addY -= e.getUnitsToScroll();
			if ((-addY)/(files.size()*50.0 - filesSizeHeight) > 1.0) {
				addY = (int) (-50.0*files.size() + filesSizeHeight);
			}
			if (addY > 0) {
				addY = 0;
			} 
			repaint();
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getY() > myPanel.getHeight() - menuHeight) {
				if (e.getX() < getWidth()) {
					if (tourist) //only return the folder the user is in
						valueToReturn = file.getAbsolutePath() + "/";
					else 
						valueToReturn = file.getAbsolutePath() + "/" + files.get(selected).getName();
					if (HaveTODO) {
						actions.done(valueToReturn);
					} else {
						synchronized (MainThread) {
							MainThread.notify();
						}
					}
					dispose();
				}
			} else {
				selected = (e.getY() -addY)/50;
				if (selected < 0) selected = 0;
				else if (selected >= files.size()) selected = files.size()-1;
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case 38:
				if (selected>0){
					selected--;
				}
				if ( (selected-1)*50+addY < -50) {
					addY += 50;
				}
				break;
			case 40:
				if (selected<files.size()-1) {
					selected++;
				}
				if ( (selected+2)*50+addY > myPanel.getHeight()) {
					addY -= 50;
				}
				if (selected == files.size()-1 && files.size()*50 > myPanel.getHeight()) {
					addY = (int) (-50.0*files.size() + filesSizeHeight);
				}
				break;
			case 10:
				try {
					if (files.get(selected).isDirectory())
						readFolder(files.get(selected).getCanonicalPath());
					else {
						valueToReturn = file.getAbsolutePath() + "/" + files.get(selected).getName();
						if (HaveTODO) {
							actions.done(valueToReturn);
						} else {
							synchronized (MainThread) {
								MainThread.notify();
							}
						}
						dispose();
					}
						
				} catch (IOException e1) {
					System.err.println("Aouch !!! something wrong happend");
				}
				addY = 0;
			}
			repaint();
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

}
