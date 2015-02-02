package start;

import items.ImageItem;
import items.TextItem;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import tools.ImageSelector;
import tools.ItemOption;
import tools.Outline;
import tools.PropertiesWindow;
import tools.RendererTool;
import tools.TimeLine;

public class MainWindow extends JFrame{
	
	static WorkingPanel panel;
	//menu bar
	JMenuBar jmb = new JMenuBar();
	JMenu jm_add= new JMenu("add");
	JMenu jm_render = new JMenu("render");
	JMenuItem jmi_add_image = new JMenuItem("image");
	JMenuItem jmi_add_text = new JMenuItem("text");
	JMenuItem jmi_property = new JMenuItem("properties");
	JMenuItem jmi_shot = new JMenuItem("shot");
	JMenuItem jmi_video = new JMenuItem("video");

	//really usefull stuff
	static ArrayList<ImageItem> images = new ArrayList<ImageItem>();
	static ArrayList<TextItem> texts = new ArrayList<TextItem>();
	static int selectedSprite = 0;
	static int selectedText = 0;
	static int selectedItemId = 0;
	
	static Outline outline;
	static ItemOption itemOptions;
	static TimeLine timeline;
	
	static int cameraWidth = 854, cameraHeight = 480,cameraPosX, cameraPosY;
	static double viewerZoom = 1;
	
	static Redrawer redrawer;
	
	public MainWindow () {
		setBounds(0, 150, 1000, 600);
		setTitle("super lama video editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new WorkingPanel();
		setContentPane(panel);
		Mover mm = new Mover ();
		addMouseListener(mm);
		addMouseMotionListener(mm);
		
		//Menu bar settings
		Actions al = new Actions();
		jmi_add_image.addActionListener(al);
		jmi_add_text.addActionListener(al);
		jmi_property.addActionListener(al);
		jmi_shot.addActionListener(al);
		jmi_video.addActionListener(al);
		
		jm_add.add(jmi_add_image);
		jm_add.add(jmi_add_text);
		jm_render.add(jmi_shot);
		jm_render.add(jmi_video);
		jm_render.add(jmi_property);
		jmb.add(jm_render);
		jmb.add(jm_add);
		
		jmb.setPreferredSize(new Dimension(jmb.getWidth(),25));
		setJMenuBar(jmb);

		//loading tools
		setVisible(true);
		outline = new Outline();
		itemOptions = new ItemOption();
		timeline = new TimeLine();

		//prgm lunching
		redrawer = new Redrawer();
		redrawer.start();
		addKeyListener(new MyKeyListener());
		addKeyListener(timeline);
	}
	
	public static void addImageItem (ImageItem II) {
		images.add(II);
	}
	
	public static void setSelectedImageItem (int i) {
		selectedSprite = i;
	}
	
	public static int getSelectedImageNumber () {
		return selectedSprite;
	}
	
	public static ImageItem getSelectedImageItem () {
		return images.get(selectedSprite);
	}
	
	public static ArrayList<ImageItem> getListSprites () {
		return images;
	}
	
	public static int getSelectedItemId () {
		return selectedItemId;
	}
	
	public static void addTextItem (TextItem TI) {
		texts.add(TI);
	}
	
	public static void setSelectedTextItem (int i) {
		selectedText = i;
	}
	
	public static TextItem getSelectedTextItem () {
		return texts.get(selectedText);
	}
	
	public static int getSelectedTextItemNumber () {
		return selectedText;
	}
	
	public static ArrayList<TextItem> getListTextItem () {
		return texts;
	}
	
	public static void setSelectedItemId (int i) {
		selectedItemId = i;
	}
	
	public static JPanel getPanel() {
		return panel;
	}
	
	public static Outline getOutline () {
		return outline;
	}
	
	public static ItemOption getItemOption () {
		return itemOptions;
	}
	
	public static TimeLine getTimeLine () {
		return timeline;
	}
	
	public static int getCameraWidth () {
		return cameraWidth;
	}
	
	public static void setCameraWidth (int i) {
		cameraWidth = i;
	}
	
	public static int getCameraHeight () {
		return cameraHeight;
	}
	
	public static void setCameraHeight (int i) {
		cameraHeight = i;
	}
	
	public static void setViewerZoom (int i) {
		viewerZoom = i;
	}
	
	public static double getViewerZoom () {
		return viewerZoom;
	}
	
	public static int getNumberOfImages () {
		return images.size();
	}
	
	public static int getNumberOfTextItem () {
		return texts.size();
	}
	public static void secureRedrawerStop () {
		redrawer.secureStop();
	}
	
	public static void secureRedrawRestart () {
		redrawer.secureRestart();
	}
	
	public static void RedrawerSlow () {
		redrawer.slow();
	}
	
	/*
	 *  ----------------------------------------------------
	 *  |                 INNER CLASSES                    |
	 *  ----------------------------------------------------
	 */
	
	private class WorkingPanel extends JPanel{
		Font helpFont = new Font("Dialog", Font.PLAIN, 16);
		Font drawFont = new Font("Dialog", Font.PLAIN, 16);
		public WorkingPanel () {
			
		}
		
		@Override
		public void paintComponent (Graphics g) {
			g.setColor(Color.white);
			g.fillRect(0, 0, 1920, 1080);
			Graphics2D d = (Graphics2D) g.create();
			d.translate((int) ((getWidth()-(cameraWidth*viewerZoom))/2), (int) ((getHeight()-(cameraHeight*viewerZoom))/2));
			AffineTransform old = d.getTransform();
			for (int index = 0; index < images.size();index++) {
				d.rotate(Math.toRadians(images.get(index).getRotation()), (images.get(index).getWidth()*viewerZoom)/2 + images.get(index).getPosX()*viewerZoom, (images.get(index).getHeight()*viewerZoom)/2 + images.get(index).getPosY()*viewerZoom);
				d.drawImage(images.get(index).getImage(), (int) (images.get(index).getPosX()*viewerZoom), (int) (images.get(index).getPosY()*viewerZoom),(int) (images.get(index).getWidth() * viewerZoom), (int) (images.get(index).getHeight() * viewerZoom), null);
				d.rotate(Math.toRadians(-images.get(index).getRotation()), (images.get(index).getWidth()*viewerZoom)/2 + images.get(index).getPosX()*viewerZoom, (images.get(index).getHeight()*viewerZoom)/2 + images.get(index).getPosY()*viewerZoom);
			}
			g.setFont(helpFont);
			d.setFont(drawFont);
			g.setColor(Color.black);
			d.setColor(Color.black);
			for (int index = 0; index < texts.size();index++) {
				d.rotate(Math.toRadians(texts.get(index).getRotation()), (texts.get(index).getWidth()*viewerZoom)/2 + texts.get(index).getPosX()*viewerZoom, (texts.get(index).getHeight()*viewerZoom)/2 + texts.get(index).getPosY()*viewerZoom);
				d.drawImage(texts.get(index).getImage(), (int) (texts.get(index).getPosX()*viewerZoom), (int) (texts.get(index).getPosY()*viewerZoom),(int) (texts.get(index).getWidth() * viewerZoom), (int) (texts.get(index).getHeight() * viewerZoom), null);
				d.rotate(Math.toRadians(-texts.get(index).getRotation()), (texts.get(index).getWidth()*viewerZoom)/2 + texts.get(index).getPosX()*viewerZoom, (texts.get(index).getHeight()*viewerZoom)/2 + texts.get(index).getPosY()*viewerZoom);
			}
			
			g.setColor(new Color(30, 30, 30, 110));
			g.fillRect(0, 0, (int) ((getWidth()-(cameraWidth*viewerZoom))/2), 1080);
			g.fillRect((int) ((getWidth()-(cameraWidth*viewerZoom))/2 + ((cameraWidth) * viewerZoom)), 0, (int) ((getWidth() - cameraWidth * viewerZoom)/2 ) + 2, getHeight());
			g.fillRect((int) ((getWidth()-(cameraWidth*viewerZoom))/2), 0, (int) (cameraWidth * viewerZoom), (int) ((getHeight()-(cameraHeight * viewerZoom))/2));
			g.fillRect((int) ((getWidth()-(cameraWidth*viewerZoom))/2), (int) ((getHeight()-(cameraHeight*viewerZoom))/2 + cameraHeight*viewerZoom), (int) ((cameraWidth) * viewerZoom), (int) ((getHeight()-(cameraHeight*viewerZoom))/2));
			
			d.setColor(Color.red);

			if (selectedItemId == 1) {
				d.rotate(Math.toRadians(images.get(selectedSprite).getRotation()), (images.get(selectedSprite).getWidth()*viewerZoom)/2 + images.get(selectedSprite).getPosX()*viewerZoom, (images.get(selectedSprite).getHeight()*viewerZoom)/2 + images.get(selectedSprite).getPosY()*viewerZoom);
				d.drawRect((int) (images.get(selectedSprite).getPosX()*viewerZoom), (int) (images.get(selectedSprite).getPosY()*viewerZoom),(int) (images.get(selectedSprite).getWidth() * viewerZoom), (int) (images.get(selectedSprite).getHeight() * viewerZoom));
				d.drawOval((int) (images.get(selectedSprite).getPosX()*viewerZoom + (images.get(selectedSprite).getWidth()*viewerZoom)/2 - 3), (int) (images.get(selectedSprite).getPosY()*viewerZoom + (images.get(selectedSprite).getHeight()*viewerZoom)/2 - 3), 6, 6);
			} else if (selectedItemId == 2) {
				d.rotate(Math.toRadians(texts.get(selectedText).getRotation()), (texts.get(selectedText).getWidth()*viewerZoom)/2 + texts.get(selectedText).getPosX()*viewerZoom, (texts.get(selectedText).getHeight()*viewerZoom)/2 + texts.get(selectedText).getPosY()*viewerZoom);
				d.drawRect((int) (texts.get(selectedText).getPosX()*viewerZoom), (int) (texts.get(selectedText).getPosY()*viewerZoom),(int) (texts.get(selectedText).getWidth() * viewerZoom), (int) (texts.get(selectedText).getHeight() * viewerZoom));
				d.drawOval((int) (texts.get(selectedText).getPosX()*viewerZoom + (texts.get(selectedText).getWidth()*viewerZoom)/2 - 3), (int) (texts.get(selectedText).getPosY()*viewerZoom + (texts.get(selectedText).getHeight()*viewerZoom)/2 - 3), 6, 6);
			
			}
			g.setColor(Color.BLACK);
			g.drawRect((int) ((getWidth()-(cameraWidth * viewerZoom))/2),(int) ((getHeight()-(cameraHeight) * viewerZoom)/2),(int) (cameraWidth * viewerZoom),(int) (cameraHeight * viewerZoom));
			g.setColor(new Color(0, 0, 120, 40));
			g.drawRect((int) ((getWidth()-(cameraWidth))/2),(int) ((getHeight()-(cameraHeight))/2),(int) (cameraWidth),(int) (cameraHeight));
			g.setColor(Color.black);
			g.fillRect(0, getHeight() - 20, getWidth(), 20);
			if (images.size() > 0 && selectedItemId == 1) {
				g.setColor(Color.white);
				g.drawString(images.get(selectedSprite).getName() + "   X:" + images.get(selectedSprite).getPosX() + " Y:" + + images.get(selectedSprite).getPosY() + " width:" + images.get(selectedSprite).getWidth() + " height:" + images.get(selectedSprite).getHeight() + " rotation:" + images.get(selectedSprite).getRotation(), 20, getHeight() - 4);
				g.drawString("Zoom :" + viewerZoom, getWidth() - 120, getHeight() - 4);
			} else if (texts.size() > 0 && selectedItemId == 2) {
				g.setColor(Color.white);
				g.drawString(texts.get(selectedText).getName() + "  X:" + texts.get(selectedText).getPosX() + " Y:" + texts.get(selectedText).getPosY(), 20, getHeight() - 4);
			}
		}	
	}
	
	private class Actions implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem jmi= (JMenuItem)e.getSource();
			
			if (jmi == jmi_add_image) {
				new ImageSelector();
			}
			else if (jmi == jmi_add_text) {
				addTextItem(new TextItem("TEXT"));
				outline.refresh();
			}
			else if (jmi == jmi_property) {
				new PropertiesWindow();
			}
			else if (jmi == jmi_shot) {
				RendererTool renderer = new RendererTool();
				renderer.renderShot();
			}
			else if (jmi == jmi_video) {
				RendererTool renderer = new RendererTool();
				renderer.renderVideo();
			}
			
		}
		
	}
	
	class MyKeyListener implements KeyListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getModifiers() == KeyEvent.SHIFT_DOWN_MASK) {
				if (e.getKeyChar() == 'i') {
					getSelectedImageItem().deleteKeyFrameTranslationAt(timeline.getTime());
				} else if (e.getKeyChar() == '-') {
					viewerZoom += 0.1;
				}
			}
			else {
				if (e.getKeyChar() == 'i') {
					try {
						new Robot().mouseMove(itemOptions.getX() + 75, itemOptions.getY() + 100);
					} catch (AWTException e1) {
						e1.printStackTrace();
					}
				} else if (e.getKeyChar() == '-') {
					viewerZoom -= 0.1;
				} else if (e.getKeyChar() == '+') {
					viewerZoom += 0.1;
				}
			}
			System.out.println("key pressed :" + e.getKeyChar() + " code :" + e.getKeyCode());
			
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
		
	}
	
	private class Mover implements MouseMotionListener, MouseListener {
		int a,b,c,d;

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			if (selectedItemId == 1) {
				images.get(selectedSprite).setPosX((int) (c + (e.getX() - a)/viewerZoom));
				images.get(selectedSprite).setPosY((int) (d + (e.getY() - b)/viewerZoom));
			} else if (selectedItemId == 2) {
				texts.get(selectedText).setPosX((int) (c + (e.getX() - a)/viewerZoom));
				texts.get(selectedText).setPosY((int) (d + (e.getY() - b)/viewerZoom));
			}
		}

		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {}

		@Override
		public void mouseClicked(java.awt.event.MouseEvent e) {}

		@Override
		public void mouseEntered(java.awt.event.MouseEvent e) {}

		@Override
		public void mouseExited(java.awt.event.MouseEvent e) {}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			a = e.getX();
			b = e.getY();
			if (selectedItemId == 1) {
				c = getSelectedImageItem().getPosX();
				d = getSelectedImageItem().getPosY();
			} else if (selectedItemId == 2) {
				c = getSelectedTextItem().getPosX();
				d = getSelectedTextItem().getPosY();
			}
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			
		}
		
	}
	
	private class Redrawer extends Thread {
		boolean redraw = true;
		int waiting = 25;
		
		public void run () {
			do {
				if (redraw) {
					repaint();
					timeline.repaint();
				}
				try {
					sleep(waiting);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (true);
		}
		
		public void secureStop () {
			redraw = false;
			waiting = 1000;
		}
		
		public void slow() {
			waiting = 200;
		}
		
		public void secureRestart () {
			redraw = true;
			waiting = 25;
		}
	}

}
