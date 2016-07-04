package browser;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class FileBrowser {
	
	Thread MainThread;
	File file = new File(System.getProperty("user.home"));
	ArrayList<File> files = new ArrayList<File>();
	int selected = 0;
	boolean hidden = false;
	
	public FileBrowser (boolean hold, boolean go) {
		MainThread = Thread.currentThread();
		if (go) {
			go(hold);
		}
	}
	
	private void go(final boolean hold) {
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
		} else {
			new MyWindow(); //THREAD IS NOT INTERUPTED, EVERYTHING GOES AS NORMAL
		}
	}

	private class MyWindow extends JFrame implements WindowListener, MouseWheelListener, MouseListener, KeyListener{
		
		ImageIcon folder_icon = new ImageIcon(getClass().getResource("/icon folder.png"));
		ImageIcon file_icon = new ImageIcon(getClass().getResource("/icon file.png"));
		int addY;
		
		private static final long serialVersionUID = 1L;
		
		public MyWindow () {
			setBounds (0,0,200,200);
			setContentPane(new MyPanel());
			addWindowListener(this);
			addMouseWheelListener(this);
			addKeyListener(this);
			setTitle (file.getAbsolutePath());
			setVisible(true);
			
			readFolder();
		}
		
		private void readFolder () {
			for (File f : file.listFiles()) {
				if (hidden || !(f.getName().startsWith(".")))
					files.add(f);
			}
		}

		private class MyPanel extends JPanel {
			public void paintComponent (Graphics graphics) {
				Graphics2D g = (Graphics2D) graphics.create();
				
				g.translate(0, addY);
				
				g.setColor(Color.RED);
				g.drawRect(0, selected*50, getWidth()-41, 50);
				
				g.setColor(Color.black);
				
				for (int i = 0; i < files.size(); i++) {
					if (files.get(i).isDirectory()) {
						g.drawImage(folder_icon.getImage(), 10, i*50, 50, 50, null);
					} else {
						g.drawImage(file_icon.getImage(), 10, i*50, 50, 50, null);
					}
					g.drawString(files.get(i).getName(), 100, i*50+20);
				}
				
				if (files.size()*50 > getHeight()) {
					int br = (int) (getHeight()/( (files.size()*50.0)/getHeight()) );//BAR HEIGHT
					g.setColor(Color.gray);
					g.fillRect(getWidth()-40, -addY, 40, getHeight());
					g.setColor(Color.RED);
					g.fillRect(getWidth()-40, (int) (-addY + (-addY/ (files.size()*50+0.0) )*getHeight()), 40, br );
				}
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
			synchronized (MainThread) {
				MainThread.notify();
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
			if (-addY > (files.size()+1)*50-getHeight()) {
				addY = -((files.size()+1)*50-getHeight());
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
				if ( (selected-1)*50+addY < 0) {
					addY += 50;
				}
				break;
			case 40:
				if (selected<files.size()-1) {
					selected++;
				}
				if ( (selected+2)*50+addY > getHeight()) {
					addY -= 50;
				}
				System.out.println((selected*50+addY) + " " + getHeight() );
				break;
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
