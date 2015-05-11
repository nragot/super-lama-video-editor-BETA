package start;

import items.ImageItem;
import items.Item;
import items.Shape;
import items.ShapeOval;
import items.ShapeRect;
import items.TextItem;
import items.VideoItem;

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

import tools.ArrayListIndexer;
import tools.ImageSelector;
import tools.ItemOption;
import tools.KeyframeTool;
import tools.Outline;
import tools.PropertiesWindow;
import tools.RendererTool;
import tools.TimeLine;
import tools.VideoSelector;

public class MainWindow extends JFrame{
	
	static WorkingPanel panel;
	//menu bar
	JMenuBar jmb = new JMenuBar();
	JMenu jm_add= new JMenu("add");
	JMenu jm_render = new JMenu("render");
	JMenuItem jmi_add_image = new JMenuItem("image");
	JMenuItem jmi_add_text = new JMenuItem("text");
	JMenuItem jmi_add_video = new JMenuItem("video");
	JMenuItem jmi_add_shape_rect = new JMenuItem("Rect");
	JMenuItem jmi_add_shape_oval = new JMenuItem("Oval");
	JMenu jm_add_shape = new JMenu("shape");
	JMenuItem jmi_property = new JMenuItem("properties");
	JMenuItem jmi_shot = new JMenuItem("shot");
	JMenuItem jmi_video = new JMenuItem("video");

	//really usefull stuff
	static ArrayList<ImageItem>        images = new ArrayList<ImageItem>()       ;
	static ArrayList<TextItem>         texts  = new ArrayList<TextItem>()        ;
	static ArrayList<VideoItem>        videos = new ArrayList<VideoItem>()       ;
	static ArrayList<Shape>            shapes = new ArrayList<Shape>()           ;
	static ArrayList<ArrayListIndexer> index  = new ArrayList<ArrayListIndexer>(); 
	static int selectedSprite = 0;
	static int selectedText = 0;
	static int selectedVideo = 0;
	static int selectedShape = 0;
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
		jmi_add_video.addActionListener(al);
		jmi_add_shape_rect.addActionListener(al);
		jmi_add_shape_oval.addActionListener(al);
		jmi_property.addActionListener(al);
		jmi_shot.addActionListener(al);
		jmi_video.addActionListener(al);
		
		jm_add_shape.add(jmi_add_shape_rect);
		jm_add_shape.add(jmi_add_shape_oval); //inner menu
		jm_add.add(jmi_add_image);
		jm_add.add(jmi_add_text);
		jm_add.add(jmi_add_video);
		jm_add.add(jm_add_shape);//menu
		jm_render.add(jmi_shot);
		jm_render.add(jmi_video);
		jm_render.add(jmi_property);
		jmb.add(jm_render);
		jmb.add(jm_add);
		
		jmb.setPreferredSize(new Dimension(jmb.getWidth(),25));
		setJMenuBar(jmb);

		//loading tools
		outline = new Outline();
		itemOptions = new ItemOption();
		timeline = new TimeLine();

		//prgm lunching
		redrawer = new Redrawer();
		redrawer.start();
		KeyListener km = new MyKeyListener();
		addKeyListener(km);
		setVisible(true);
	}
	
	public static void addImageItem (ImageItem II) {
		images.add(II);
		index.add(new ArrayListIndexer(1, images.size()));
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
		index.add(new ArrayListIndexer(2, texts.size()));
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
	
	public static void addVideoItem (VideoItem v) {
		videos.add(v);
		index.add(new ArrayListIndexer(3, videos.size()));
	}
	
	public static ArrayList<VideoItem> getListVideo () {
		return videos;
	}
	
	public static ArrayList<ArrayListIndexer> getListIndexer () {
		return index;
	}
	
	public static int getSelectedVideoItemNumber () {
		return selectedVideo;
	}
	
	public static VideoItem getSelectedVideoItem () {
		return videos.get(selectedVideo);
	}
	
	public static void setSelectedVideoItem (int i) {
		selectedVideo = i;
	}
	
	public static void addShapeRect (ShapeRect sr) { //add shape
		shapes.add(sr);
		System.out.print("2:" + shapes.size());
		index.add(new ArrayListIndexer(401, shapes.size()));
	}
	
	public static ArrayList<Shape> getListShapes () {
		return shapes;
	}
	
	public static int getSelectedShapeNumber () {
		return selectedShape;
	}
	
	public static Shape getSelectedShape () {
		return shapes.get(selectedShape);
	}
	
	public static void setSelectedShape (int i) {
		selectedShape = i;
	}
	
	public static void addShapeOval (ShapeOval so) { //add shape
		shapes.add(so);
		System.out.print("2:" + shapes.size());
		index.add(new ArrayListIndexer(401, shapes.size()));
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
	
	public static Item getItemByName (String str) {
		for (int index = 0; index < images.size();index++) {
			if (str.equals(images.get(index).getName())) {
				return images.get(index);
			}
		}
		for (int index = 0; index < texts.size();index++) {
			if (str.equals(texts.get(index).getName())) {
				return texts.get(index);
			}
		}
		for (int index = 0; index < videos.size();index++) {
			if (str.equals(videos.get(index).getName())) {
				return videos.get(index);
			}
		}
		for (int index = 0; index < shapes.size();index++) {
			if (str.equals(shapes.get(index).getName())) {
				return shapes.get(index);
			}
		}
		return new Item();
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
	
	public static void ResolveIndexGap (int A, int B) {
		int gapSolver = 0;
		for (int i = 0;i < index.size();i++) {
			if (A == index.get(i).getA() && index.get(i).getB() == B) {
				gapSolver = i;
			}
			else if (A == index.get(i).getA() && index.get(i).getB() > B) {
				index.get(i).setB(index.get(i).getB() - 1);
			}
		}
		index.remove(gapSolver);
		System.out.println("gapsolved at " + A + ";" + B);
	}
	
	public static void refreshItemStatFromFormula () {
		for (int index = 0; index < images.size(); index++) {
			/*
			ImageItem item = images.get(index);
			try {
				item.setPosX((int)Double.parseDouble(item.calculeVariable(item.getPosXFormula())));
				item.setRotation((int)Double.parseDouble(item.calculeVariable(item.getRotationFormula())));
				item.setWidth((int)Double.parseDouble(item.calculeVariable(item.getWidthFormula())));
				item.setHeight((int)Double.parseDouble(item.calculeVariable(item.getHeightFormula())));
			} catch (NumberFormatException e) {
				System.err.println("nmbfrmtexcpet in refreshItemStateFromFormula () :: \n" + e.getMessage());
			}
			*/
		}
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
			g.setFont(helpFont);
			d.setFont(drawFont);
			g.setColor(Color.black);
			d.setColor(Color.black);
			for (int i = 0; i < index.size();i++) {
				int A = index.get(i).getA(), B = index.get(i).getB()-1;
				if (A == 1 && images.size() > 0) {
					d.rotate(Math.toRadians(images.get(B).getRotation()), images.get(B).getPosX()*viewerZoom, images.get(B).getPosY()*viewerZoom);
					d.drawImage(images.get(B).getImage(), (int) (images.get(B).getPosX()*viewerZoom -  (images.get(B).getWidth()*viewerZoom)/2), (int) (images.get(B).getPosY()*viewerZoom - (images.get(B).getHeight()*viewerZoom)/2),(int) (images.get(B).getWidth() * viewerZoom), (int) (images.get(B).getHeight() * viewerZoom), null);
					d.rotate(-Math.toRadians(images.get(B).getRotation()), images.get(B).getPosX()*viewerZoom, images.get(B).getPosY()*viewerZoom);
				} else if (A == 2 && texts.size() > 0) {
					d.rotate(Math.toRadians(texts.get(B).getRotation()), (texts.get(B).getWidth()*viewerZoom)/2 + texts.get(B).getPosX()*viewerZoom, (texts.get(B).getHeight()*viewerZoom)/2 + texts.get(B).getPosY()*viewerZoom);
					d.drawImage(texts.get(B).getImage(), (int) (texts.get(B).getPosX()*viewerZoom), (int) (texts.get(B).getPosY()*viewerZoom),(int) (texts.get(B).getWidth() * viewerZoom), (int) (texts.get(B).getHeight() * viewerZoom), null);
					d.rotate(Math.toRadians(-texts.get(B).getRotation()), (texts.get(B).getWidth()*viewerZoom)/2 + texts.get(B).getPosX()*viewerZoom, (texts.get(B).getHeight()*viewerZoom)/2 + texts.get(B).getPosY()*viewerZoom);
				} else if (A == 3 && videos.size() > 0) {
					d.rotate(Math.toRadians(videos.get(B).getRotation()), (videos.get(B).getWidth()*viewerZoom)/2 + videos.get(B).getPosX()*viewerZoom, (videos.get(B).getHeight()*viewerZoom)/2 + videos.get(B).getPosY()*viewerZoom);
					d.drawImage(videos.get(B).getImage(), (int) (videos.get(B).getPosX()*viewerZoom), (int) (videos.get(B).getPosY()*viewerZoom),(int) (videos.get(B).getWidth() * viewerZoom), (int) (videos.get(B).getHeight() * viewerZoom), null);
					d.rotate(Math.toRadians(-videos.get(B).getRotation()), (videos.get(B).getWidth()*viewerZoom)/2 + videos.get(B).getPosX()*viewerZoom, (videos.get(B).getHeight()*viewerZoom)/2 + videos.get(B).getPosY()*viewerZoom);
				} else if ((A == 401 || A == 402) && shapes.size() > 0) {
					d.rotate(Math.toRadians(shapes.get(B).getRotation()), (shapes.get(B).getWidth()*viewerZoom)/2 + shapes.get(B).getPosX()*viewerZoom, (shapes.get(B).getHeight()*viewerZoom)/2 + shapes.get(B).getPosY()*viewerZoom);
					d.drawImage(shapes.get(B).getImage(), (int) (shapes.get(B).getPosX()*viewerZoom), (int) (shapes.get(B).getPosY()*viewerZoom),(int) (shapes.get(B).getWidth() * viewerZoom), (int) (shapes.get(B).getHeight() * viewerZoom), null);
					d.rotate(Math.toRadians(-shapes.get(B).getRotation()), (shapes.get(B).getWidth()*viewerZoom)/2 + shapes.get(B).getPosX()*viewerZoom, (shapes.get(B).getHeight()*viewerZoom)/2 + shapes.get(B).getPosY()*viewerZoom);
				}
			}
			
			g.setColor(new Color(30, 30, 30, 110));
			g.fillRect(0, 0, (int) ((getWidth()-(cameraWidth*viewerZoom))/2), 1080);//left
			g.fillRect((int) ((getWidth()-(cameraWidth*viewerZoom))/2 + ((cameraWidth) * viewerZoom)), 0, (int) ((getWidth() - cameraWidth * viewerZoom)/2 ) + 2, getHeight());//right
			double I = ((int) ((getWidth()-(cameraWidth*viewerZoom))/2 + ((cameraWidth) * viewerZoom) - (getWidth()-(cameraWidth*viewerZoom))/2));
			g.fillRect((int) ((getWidth()-(cameraWidth*viewerZoom))/2), 0,(int) Math.round(I), (int) ((getHeight()-(cameraHeight * viewerZoom))/2));//up
			g.fillRect((int) ((getWidth()-(cameraWidth*viewerZoom))/2), (int) ((getHeight()-(cameraHeight*viewerZoom))/2 + cameraHeight*viewerZoom), (int) Math.round(I), (int) ((getHeight()-(cameraHeight*viewerZoom))/2));//down
			d.setColor(Color.red);

			if (selectedItemId == 1) {
				d.rotate(Math.toRadians(images.get(selectedSprite).getRotation()), images.get(selectedSprite).getPosX()*viewerZoom, images.get(selectedSprite).getPosY()*viewerZoom);
				d.drawRect((int) (images.get(selectedSprite).getPosX()*viewerZoom - images.get(selectedSprite).getWidth()*viewerZoom/2), (int) (images.get(selectedSprite).getPosY()*viewerZoom - images.get(selectedSprite).getHeight()*viewerZoom/2),(int) (images.get(selectedSprite).getWidth() * viewerZoom), (int) (images.get(selectedSprite).getHeight() * viewerZoom));
				d.drawOval((int) (images.get(selectedSprite).getPosX()*viewerZoom - 3), (int) (images.get(selectedSprite).getPosY()*viewerZoom - 3), 6, 6);
			} else if (selectedItemId == 2) {
				d.rotate(Math.toRadians(texts.get(selectedText).getRotation()), (texts.get(selectedText).getWidth()*viewerZoom)/2 + texts.get(selectedText).getPosX()*viewerZoom, (texts.get(selectedText).getHeight()*viewerZoom)/2 + texts.get(selectedText).getPosY()*viewerZoom);
				d.drawRect((int) (texts.get(selectedText).getPosX()*viewerZoom), (int) (texts.get(selectedText).getPosY()*viewerZoom),(int) (texts.get(selectedText).getWidth() * viewerZoom), (int) (texts.get(selectedText).getHeight() * viewerZoom));
				d.drawOval((int) (texts.get(selectedText).getPosX()*viewerZoom + (texts.get(selectedText).getWidth()*viewerZoom)/2 - 3), (int) (texts.get(selectedText).getPosY()*viewerZoom + (texts.get(selectedText).getHeight()*viewerZoom)/2 - 3), 6, 6);
			} else if (selectedItemId == 3) {
				d.rotate(Math.toRadians(videos.get(selectedText).getRotation()), (videos.get(selectedText).getWidth()*viewerZoom)/2 + videos.get(selectedText).getPosX()*viewerZoom, (videos.get(selectedText).getHeight()*viewerZoom)/2 + videos.get(selectedText).getPosY()*viewerZoom);
				d.drawRect((int) (videos.get(selectedText).getPosX()*viewerZoom), (int) (videos.get(selectedText).getPosY()*viewerZoom),(int) (videos.get(selectedText).getWidth() * viewerZoom), (int) (videos.get(selectedText).getHeight() * viewerZoom));
				d.drawOval((int) (videos.get(selectedText).getPosX()*viewerZoom + (videos.get(selectedText).getWidth()*viewerZoom)/2 - 3), (int) (videos.get(selectedText).getPosY()*viewerZoom + (videos.get(selectedText).getHeight()*viewerZoom)/2 - 3), 6, 6);
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
			else if (jmi == jmi_add_video) {
				new VideoSelector();
			}
			else if (jmi == jmi_add_shape_rect) {
				addShapeRect(new ShapeRect());
				outline.refresh();
			}
			else if (jmi == jmi_add_shape_oval) {
				addShapeOval(new ShapeOval());
				outline.refresh();
			}
		}
		
	}
	
	class MyKeyListener implements KeyListener {
		public MyKeyListener () {}
		
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
						new KeyframeTool();
				} else if (e.getKeyChar() == '-') {
					viewerZoom -= 0.1;
				} else if (e.getKeyChar() == '+') {
					viewerZoom += 0.1;
				} else if (e.getKeyCode() == 37) {
					TimeLine.addTime(-1);
					setTitle("timeline ("+TimeLine.getTime()+")");
					TimeLine.calculateItemsState();
				} else if (e.getKeyCode() == 39) {
					TimeLine.addTime(1);
					setTitle("timeline ("+TimeLine.getTime()+")");
					TimeLine.calculateItemsState();
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
			if (!itemOptions.getOptionType(0, 1)) {
				if (selectedItemId == 1) {
					images.get(selectedSprite).setPosX((int) (c + (e.getX() - a)/viewerZoom));
					images.get(selectedSprite).setPosY((int) (d + (e.getY() - b)/viewerZoom));
				} else if (selectedItemId == 2) {
					texts.get(selectedText).setPosX((int) (c + (e.getX() - a)/viewerZoom));
					texts.get(selectedText).setPosY((int) (d + (e.getY() - b)/viewerZoom));
				} else if (selectedItemId == 3) {
					videos.get(selectedVideo).setPosX((int) (c + (e.getX() - a)/viewerZoom));
					videos.get(selectedVideo).setPosY((int) (d + (e.getY() - b)/viewerZoom));
				} else if (selectedItemId == 401 || selectedItemId == 402) {
					shapes.get(selectedShape).setPosX((int) (c + (e.getX() - a)/viewerZoom));
					shapes.get(selectedShape).setPosY((int) (d + (e.getY() - b)/viewerZoom));
				}
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
			} else if (selectedItemId == 3) {
				c = getSelectedVideoItem().getPosX();
				d = getSelectedVideoItem().getPosY();
			} else if (selectedItemId == 401 || selectedItemId == 402) {
				c = getSelectedShape().getPosX();
				d = getSelectedShape().getPosY();
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
