package start;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import mod.slve.items.ImageItem;
import mod.slve.items.Shape;
import mod.slve.items.ShapeOval;
import mod.slve.items.ShapeRect;
import mod.slve.items.TextItem;
import mod.slve.items.VideoItem;

import API.Item;
import API.Layer;
import API.SlveButton;

import tools.ArrayListIndexer;
import tools.CommandFrame;
import tools.ImageSelector;
import tools.ItemOption;
import tools.KeyframeTool;
import tools.Outline;
import tools.PropertiesWindow;
import tools.RendererTool;
import tools.SourceWindow;
import tools.SourceWindow.SourceActions;
import tools.SourceWindow.srcFolder;
import tools.TimeLine;
import tools.VideoSelector;
import exceptions.NoItemFoundException;

public class MainWindow extends JFrame implements FocusListener{
	
	private static final long serialVersionUID = 1L;
	
	static WorkingPanel panel;
	//menu bar
	/*
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
	JMenuItem jmi_video = new JMenuItem("video");*/
	

	//really usefull stuff
	static ArrayList<ImageItem>        images         = new ArrayList<ImageItem>()       ;
	static ArrayList<TextItem>         texts          = new ArrayList<TextItem>()        ;
	static ArrayList<VideoItem>        videos         = new ArrayList<VideoItem>()       ;
	static ArrayList<Shape>            shapes         = new ArrayList<Shape>()           ;
	static ArrayList<ArrayListIndexer> index          = new ArrayList<ArrayListIndexer>();
	static ArrayList<Integer>          itemSelection  = new ArrayList<Integer>()         ;
	static ArrayList<Layer>            layers         = new ArrayList<Layer>()           ;
	
	static Outline outline;
	static ItemOption itemOptions;
	static TimeLine timeline;
	static CommandFrame cmd = new CommandFrame();
	
	static int cameraWidth = 854, cameraHeight = 480,cameraPosX, cameraPosY;
	static double viewerZoom = 1;
	int viewerX = 0, viewerY = 0;
	int userClickedOnX = -1, userClickedOnY = -1; //-1:did not cliked anywhere
	int viewerXLayer = 0, viewerYLayer = 0;
	int panelStatus = 1; //0:drawing layer; 1:drawing layer manager 
	
	static Redrawer redrawer;
	
	public MainWindow () {
		setTitle("super lama video editor");
	}
	
	/**
	 *  startup the tool
	 */
	public void GO (Outline outline, ItemOption itemoptions, TimeLine timeline) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel = new WorkingPanel();
		setContentPane(panel);
		Mover mm = new Mover ();
		panel.addMouseListener(mm);
		panel.addMouseMotionListener(mm);
		addFocusListener((FocusListener) this);
		
		panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.CTRL_DOWN_MASK), "commandPromptReveal");
		panel.getActionMap().put("commandPromptReveal", new AbstractAction() {

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				openCommand();
		}});
		
		//FIXME:remove unnecessary comments
		//FIXME:remove unnecessary methode
		//TODO:check for optimization
		//TODO:git that shit
		
		//Menu bar settings
		/*Actions al = new Actions();
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
		setJMenuBar(jmb);*/

		//loading tools
		MainWindow.outline = outline;
		MainWindow.itemOptions = itemoptions;
		MainWindow.timeline = timeline;
		
		MainWindow.itemOptions.GO();
		MainWindow.outline.GO();
		MainWindow.timeline.GO();

		//prgm lunching
		redrawer = new Redrawer();
		redrawer.start();
		KeyListener km = new MyKeyListener();
		addKeyListener(km);
		setVisible(true);
	}
	
	/**
	 * arguments choseOver define how you select a new item.
	 * if choseOver = true then every selection will be erase to be replace with the new one. otherwise it will added from the already existing selection.
	 * @param choseOver
	 */
	public static void selectItem (int i,boolean choseOver) {
		if (!choseOver) {
			itemSelection.clear();
		} else {
		}
		itemSelection.add(i);
	}
	
	public static Item getItem (ArrayListIndexer al) throws NoItemFoundException {
		if (al.getA() == 1) {
			return images.get(al.getB()-1);
		} else if (al.getA() == 2) {
			return texts.get(al.getB()-1);
		} else if (al.getA() == 3) {
			return videos.get(al.getB()-1);
		} else if (al.getA() == 401) {
			return shapes.get(al.getB()-1);
		}
		throw new NoItemFoundException();
	}
	
	public ArrayList<Layer> getLayers () {
		return layers;
	}
	
	public void removeLayerByName (String str) {
		for (int i = 0; i < layers.size(); i++) {
			System.out.println(str + " " + layers.get(i).getName());
			if (layers.get(i).getName().equals(str)) {
				layers.remove(i);
			}
		}
	}
	
	public static Item getSelectedItem () throws NoItemFoundException, ArrayIndexOutOfBoundsException {
		return getItem(index.get(itemSelection.get(itemSelection.size()-1)));
	}
	
	public static Item getSelectedItem (int i) throws NoItemFoundException {
		return getItem(index.get(itemSelection.get(i)));
	}
	
	public static ArrayList<Integer> getItemSelection () {
		return itemSelection;
	}
	
	public static void addImageItem (ImageItem II) {
		images.add(II);
		index.add(new ArrayListIndexer(1, images.size()));
	}
	
	public static ArrayList<ImageItem> getListSprites () {
		return images;
	}
	
	public static void addTextItem (TextItem TI) {
		texts.add(TI);
		index.add(new ArrayListIndexer(2, texts.size()));
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
	
	public static ArrayList<ArrayListIndexer> getIndex () {
		return index;
	}
	public static void addShapeRect (ShapeRect sr) { //add shape
		shapes.add(sr);
		index.add(new ArrayListIndexer(401, shapes.size()));
	}
	
	public static ArrayList<Shape> getListShapes () {
		return shapes;
	}
	
	public static void addShapeOval (ShapeOval so) { //add shape
		shapes.add(so);
		index.add(new ArrayListIndexer(402, shapes.size()));
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
	
	public static int getNumberOfVideoItems () {
		return videos.size();
	}
	
	public static int getNumberOfShapes () {
		return shapes.size();
	}
	
	public static Item getItemByName (String str) throws NoItemFoundException {
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
		throw new NoItemFoundException();
	}
	
	public static int getItemByNameFromIndex (String str) throws NoItemFoundException {
		for (int i = 0; i < index.size(); i++) {
			int a = index.get(i).getA();
			
			if (a==1) {
				if (images.get( index.get(i).getB()-1 ).getName().equals(str)) {
					return i;
				}
			}
			else if (a==2) {
				if (texts.get( index.get(i).getB()-1 ).getName().equals(str)) {
					return i;
				}
			}
			else if (a==3) {
				if (videos.get( index.get(i).getB()-1 ).getName().equals(str)) {
					return i;
				}
			}
			else if (a==4) {
				if (shapes.get( index.get(i).getB()-1 ).getName().equals(str)) {
					return i;
				}
			}
		}
		throw new NoItemFoundException();
	}
	
	public static void removeItemByName (String str) throws NoItemFoundException{
		for (int index = 0; index < images.size();index++) {
			if (str.equals(images.get(index).getName())) {
				images.remove(index);
				ResolveIndexGap(1, index);
				outline.refresh();
				itemOptions.loadOptions();
				return;
			}
		}
		for (int index = 0; index < texts.size();index++) {
			if (str.equals(texts.get(index).getName())) {
				texts.remove(index);
				ResolveIndexGap(2, index);
				outline.refresh();
				itemOptions.loadOptions();
				return;
			}
		}
		for (int index = 0; index < videos.size();index++) {
			if (str.equals(videos.get(index).getName())) {
				videos.remove(index);
				ResolveIndexGap(3, index);
				outline.refresh();
				itemOptions.loadOptions();
				return;
			}
		}
		for (int index = 0; index < shapes.size();index++) {
			if (str.equals(shapes.get(index).getName())) {
				shapes.remove(index);
				ResolveIndexGap(401, index);
				outline.refresh();
				itemOptions.loadOptions();
				return;
			}
		}
		
		throw new NoItemFoundException();
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
	
	public static void openCommand () {
		cmd.setBounds(60, 60, 530, 800);
		cmd.setVisible(true);
	}
	
	public static void print (String str) {
		if (cmd.isVisible()) cmd.setVisible(true);
		cmd.print(str);
	}
	
	public static void ResolveIndexGap (int A, int B) {
		
		itemSelection.clear();
		int gapSolver = -1;
		for (int i = 0;i < index.size();i++) {
			if (A == index.get(i).getA() && index.get(i).getB() == B) {
				gapSolver = i;
			}
			else if (A == index.get(i).getA() && index.get(i).getB() > B) {
				index.get(i).setB(index.get(i).getB() - 1);
			}
		}
		if (gapSolver == -1) {
			for (int i = 0; i < index.size();i++) {
				if (A == index.get(i).getA() && B == index.get(i).getB()+1) {
					index.remove(i);
				}
			}
		} else {
			index.remove(gapSolver);
		}
	}
	
	 // windows event
	boolean focusCycle = false;
	@Override
	public void focusGained(FocusEvent e) {
		focusCycle = !focusCycle;
		if (focusCycle) {
			outline.toFront();
			itemOptions.toFront();
			timeline.toFront();
			toFront();
		} else {}
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 *  ----------------------------------------------------
	 *  |                 INNER CLASSES                    |
	 *  ----------------------------------------------------
	 */
	
	private class WorkingPanel extends JPanel{
		
		private static final long serialVersionUID = 1L;
		
		Font helpFont = new Font("Dialog", Font.PLAIN, 16);
		Font drawFont = new Font("Dialog", Font.PLAIN, 16);
		public WorkingPanel () {
			
		}
		
		@Override
		public void paintComponent (Graphics graphics) {
			Graphics2D g = (Graphics2D) graphics.create();
			if (panelStatus == 0) {
				for (Layer layer : layers) {
					if (layer.doRenderInside())
						layer.render(g);
				}
			} else if (panelStatus == 1) {
				g.setColor(Color.gray);
				g.fillRect(0, 0, getWidth(), getHeight());
				for (int i = 0; i < layers.size(); i++) {
					g.setColor(Color.WHITE);
					g.fillRect(getWidth()/2 - 50, 50+i*200, 100, 150); //TODO:affiche name, give choice, move item
					g.setColor(Color.BLACK);
					g.drawString(layers.get(i).getName(), getWidth()/2 + 100, 100+i*200);
					int x = 0;
					for (SlveButton button : layers.get(i).getLeftButtons()) {
						x += button.getWidth();
						if (button.getIcon() == null)
							g.drawString(button.getName(), getWidth()/2 - 50 - x, 85+i*200);
						else 
							g.drawImage(button.getIcon().getImage(), getWidth()/2 - 50 - x, 75+i*200, null);
						if (userClickedOnX != -1 
								&& userClickedOnX > getWidth()/2 - 50 - x && userClickedOnX < getWidth()/2 - 50 - x + button.getWidth()
								&& userClickedOnY > 75 + i*200 && userClickedOnY < 75 + i*200 + button.getHeight())
							{
								button.push();
								userClickedOnX = userClickedOnY = 0;
							}
					}
					x = 0;
					for (SlveButton button : layers.get(i).getRightButtons()) {
						if (button.getIcon() == null)
							g.drawString(button.getName(), getWidth()/2 + 50 + x, 190+i*200);
						else 
							g.drawImage(button.getIcon().getImage(), getWidth()/2 +100 + x, 180+i*200, null);
						if (userClickedOnX != -1 
								&& userClickedOnX > getWidth()/2 + 50 + x && userClickedOnX < getWidth()/2 + 50 + x + button.getWidth()
								&& userClickedOnY > 180 + i*200 && userClickedOnY < 180 + i*200 + button.getHeight())
							{
								button.push();
								userClickedOnX = userClickedOnY = 0;
							}
							;
						x += button.getWidth();
					}
				}
			}
			
			/*g.setColor(Color.white);
			g.fillRect(0, 0, 1920, 1080);
			Graphics2D d = (Graphics2D) g.create();
			d.translate((int) ((getWidth()-(cameraWidth*viewerZoom))/2), (int) ((getHeight()-(cameraHeight*viewerZoom))/2));
			AffineTransform old = d.getTransform();
			d.translate(viewerX, viewerY);
			
			g.setFont(helpFont);
			d.setFont(drawFont);
			g.setColor(Color.black);
			d.setColor(Color.black);
			for (int i = 0; i < index.size();i++) {
				int A = index.get(i).getA(), B = index.get(i).getB()-1;
				if (A == 1 && images.size() > 0 && images.get(B).isActiv()) {
					d.rotate(Math.toRadians(images.get(B).getRotation()), images.get(B).getPosX()*viewerZoom, images.get(B).getPosY()*viewerZoom);
					d.drawImage(images.get(B).getImage(), (int) (images.get(B).getPosX()*viewerZoom -  (images.get(B).getWidth()*viewerZoom)/2), (int) (images.get(B).getPosY()*viewerZoom - (images.get(B).getHeight()*viewerZoom)/2),(int) (images.get(B).getWidth() * viewerZoom), (int) (images.get(B).getHeight() * viewerZoom), null);
					d.rotate(-Math.toRadians(images.get(B).getRotation()), images.get(B).getPosX()*viewerZoom, images.get(B).getPosY()*viewerZoom);
				} else if (A == 2 && texts.size() > 0 && texts.get(B).isActiv()) {
					d.rotate(Math.toRadians(texts.get(B).getRotation()), (texts.get(B).getWidth()*viewerZoom)/2 + texts.get(B).getPosX()*viewerZoom, (texts.get(B).getHeight()*viewerZoom)/2 + texts.get(B).getPosY()*viewerZoom);
					d.drawImage(texts.get(B).getImage(), (int) (texts.get(B).getPosX()*viewerZoom), (int) (texts.get(B).getPosY()*viewerZoom),(int) (texts.get(B).getWidth() * viewerZoom), (int) (texts.get(B).getHeight() * viewerZoom), null);
					d.rotate(Math.toRadians(-texts.get(B).getRotation()), (texts.get(B).getWidth()*viewerZoom)/2 + texts.get(B).getPosX()*viewerZoom, (texts.get(B).getHeight()*viewerZoom)/2 + texts.get(B).getPosY()*viewerZoom);
				} else if (A == 3 && videos.size() > 0 && videos.get(B).isActiv()) {
					d.rotate(Math.toRadians(videos.get(B).getRotation()), (videos.get(B).getWidth()*viewerZoom)/2 + videos.get(B).getPosX()*viewerZoom, (videos.get(B).getHeight()*viewerZoom)/2 + videos.get(B).getPosY()*viewerZoom);
					d.drawImage(videos.get(B).getImage(), (int) (videos.get(B).getPosX()*viewerZoom), (int) (videos.get(B).getPosY()*viewerZoom),(int) (videos.get(B).getWidth() * viewerZoom), (int) (videos.get(B).getHeight() * viewerZoom), null);
					d.rotate(Math.toRadians(-videos.get(B).getRotation()), (videos.get(B).getWidth()*viewerZoom)/2 + videos.get(B).getPosX()*viewerZoom, (videos.get(B).getHeight()*viewerZoom)/2 + videos.get(B).getPosY()*viewerZoom);
				} else if ((A == 401 || A == 402) && shapes.size() > 0 && shapes.get(B).isActiv()) {
					d.rotate(Math.toRadians(shapes.get(B).getRotation()), (shapes.get(B).getWidth()*viewerZoom)/2 + shapes.get(B).getPosX()*viewerZoom, (shapes.get(B).getHeight()*viewerZoom)/2 + shapes.get(B).getPosY()*viewerZoom);
					d.drawImage(shapes.get(B).getImage(), (int) (shapes.get(B).getPosX()*viewerZoom), (int) (shapes.get(B).getPosY()*viewerZoom),(int) (shapes.get(B).getWidth() * viewerZoom), (int) (shapes.get(B).getHeight() * viewerZoom), null);
					d.rotate(Math.toRadians(-shapes.get(B).getRotation()), (shapes.get(B).getWidth()*viewerZoom)/2 + shapes.get(B).getPosX()*viewerZoom, (shapes.get(B).getHeight()*viewerZoom)/2 + shapes.get(B).getPosY()*viewerZoom);
				}
			}
			g.setColor(new Color(30,30,30,110));
			int left = (int) ((getWidth()-(cameraWidth*viewerZoom))/2) + viewerX,
					right = (int) ((getWidth()-(cameraWidth*viewerZoom))/2 + ((cameraWidth) * viewerZoom)) + viewerX,
					up = (int) ((getHeight()-(cameraHeight * viewerZoom))/2) + viewerY,
					down = (int) ((getHeight()-(cameraHeight*viewerZoom))/2 + cameraHeight*viewerZoom) + viewerY;
			g.fillRect(0, 0, left, 1080);//left
			g.fillRect(right, 0, getWidth() - right, getHeight());//right
			g.fillRect(left, 0, right - left, up); // up
			g.fillRect(left, down, right - left, getHeight() - down); //down
			
			d.setColor(Color.blue);
			for (int i = 0; i < itemSelection.size(); i++) {
				ArrayListIndexer ali = index.get(itemSelection.get(i));
				if (ali.getA() == 1) {
					d.rotate(Math.toRadians(images.get(ali.getB()-1).getRotation()), images.get(ali.getB()-1).getPosX()*viewerZoom, images.get(ali.getB()-1).getPosY()*viewerZoom);
					d.drawRect((int) (images.get(ali.getB()-1).getPosX()*viewerZoom - images.get(ali.getB()-1).getWidth()*viewerZoom/2), (int) (images.get(ali.getB()-1).getPosY()*viewerZoom - images.get(ali.getB()-1).getHeight()*viewerZoom/2),(int) (images.get(ali.getB()-1).getWidth() * viewerZoom), (int) (images.get(ali.getB()-1).getHeight() * viewerZoom));
					d.drawOval((int) (images.get(ali.getB()-1).getPosX()*viewerZoom - 3), (int) (images.get(ali.getB()-1).getPosY()*viewerZoom - 3), 6, 6);
				} else if (ali.getA() == 2) {
					d.rotate(Math.toRadians(texts.get(ali.getB()-1).getRotation()), (texts.get(ali.getB()-1).getWidth()*viewerZoom)/2 + texts.get(ali.getB()-1).getPosX()*viewerZoom, (texts.get(ali.getB()-1).getHeight()*viewerZoom)/2 + texts.get(ali.getB()-1).getPosY()*viewerZoom);
					d.drawRect((int) (texts.get(ali.getB()-1).getPosX()*viewerZoom), (int) (texts.get(ali.getB()-1).getPosY()*viewerZoom),(int) (texts.get(ali.getB()-1).getWidth() * viewerZoom), (int) (texts.get(ali.getB()-1).getHeight() * viewerZoom));
					d.drawOval((int) (texts.get(ali.getB()-1).getPosX()*viewerZoom + (texts.get(ali.getB()-1).getWidth()*viewerZoom)/2 - 3), (int) (texts.get(ali.getB()-1).getPosY()*viewerZoom + (texts.get(ali.getB()-1).getHeight()*viewerZoom)/2 - 3), 6, 6);
				} else if (ali.getA() == 3) {
					d.rotate(Math.toRadians(videos.get(ali.getB()-1).getRotation()), (videos.get(ali.getB()-1).getWidth()*viewerZoom)/2 + videos.get(ali.getB()-1).getPosX()*viewerZoom, (videos.get(ali.getB()-1).getHeight()*viewerZoom)/2 + videos.get(ali.getB()-1).getPosY()*viewerZoom);
					d.drawRect((int) (videos.get(ali.getB()-1).getPosX()*viewerZoom), (int) (videos.get(ali.getB()-1).getPosY()*viewerZoom),(int) (videos.get(ali.getB()-1).getWidth() * viewerZoom), (int) (videos.get(ali.getB()-1).getHeight() * viewerZoom));
					d.drawOval((int) (videos.get(ali.getB()-1).getPosX()*viewerZoom + (videos.get(ali.getB()-1).getWidth()*viewerZoom)/2 - 3), (int) (videos.get(ali.getB()-1).getPosY()*viewerZoom + (videos.get(ali.getB()-1).getHeight()*viewerZoom)/2 - 3), 6, 6);
				}
			}
			
			g.setColor(Color.BLACK);
			g.drawRect(left,up,right - left,down - up);
			g.setColor(new Color(0, 0, 120, 40));
			g.drawRect((int) ((getWidth()-(cameraWidth))/2),(int) ((getHeight()-(cameraHeight))/2),(int) (cameraWidth),(int) (cameraHeight));
			g.setColor(Color.black);
			g.fillRect(0, getHeight() - 20, getWidth(), 20);
			g.setColor(Color.white);
			try {
				g.drawString(getSelectedItem().getName() + "   X:" + getSelectedItem().getPosX() + " Y:" + getSelectedItem().getPosY() + " width:" + getSelectedItem().getWidth() + " height:" + getSelectedItem().getHeight() + " rotation:" + getSelectedItem().getRotation(), 20, getHeight() - 4);
			} catch (NoItemFoundException e) {
				g.drawString("for some reasons, the item was not found. that look like a bug to me", 20, getHeight() - 40);
			} catch (ArrayIndexOutOfBoundsException e) {
				g.drawString("here are the par of the last selected item", 20, getHeight() - 4);
			}
			g.drawString("Zoom :" + (viewerZoom+"").substring(0, 3), getWidth() - 120, getHeight() - 4);*/
		}	
	}
	
	private class Actions implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JMenuItem jmi= (JMenuItem)e.getSource();
			
			/*
			if (jmi == jmi_add_image) {
				Start.getSourceWindow().active(new SourceActions() {
					
					@Override
					public void userChooseImage(SourceWindow source, JFrame window) {
						addImageItem(new ImageItem(source.getSelectedItem().preview(), JOptionPane.showInputDialog(null,"give the name of the object you want to create","item #" + (index.size()+1))
								, cameraWidth/2, cameraHeight/2));
						selectItem(index.size()-1, true);
						window.dispose();
						outline.refresh();
					}
					
					@Override
					public void userChooseFolder(SourceWindow source, JFrame window) {
						source.getSelectedItemAsFolder().toggleOpen();
					}
				});
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
			}*/
		}
		
	}
	
	class MyKeyListener implements KeyListener {
		public MyKeyListener () {}
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (panelStatus == 0) {
				if (e.getModifiers() == KeyEvent.SHIFT_DOWN_MASK) {
					if (e.getKeyChar() == 'i') {
						try {
							getSelectedItem().deleteKeyFrameTranslationAt(TimeLine.getTime());
						} catch (NoItemFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
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
					} else if (e.getKeyChar() == 's') {
						Start.getSourceWindow().active(new SourceActions() {

							@Override
							public void userChooseImage(SourceWindow source, JFrame jf) {
							}

							@Override
							public void userChooseFolder(SourceWindow source, JFrame jf) {
								Start.getSourceWindow().getSelectedItemAsFolder().toggleOpen();
							}
						});
					}
				}
			} else if (panelStatus == 1) {
				
			}
			System.out.println("key pressed :" + e.getKeyChar() + " code :" + e.getKeyCode());
			
		}

		@Override
		public void keyReleased(KeyEvent e) {}
		@Override
		public void keyTyped(KeyEvent e) {}
		
	}
	
	private class Mover implements MouseMotionListener, MouseListener {
		int a,b;
		int[] c,d;

		@Override
		public void mouseDragged(java.awt.event.MouseEvent e) {
			if (panelStatus == 0) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					if (!itemOptions.getOptionType(0, 1)) {
						for (int i = 0; i < itemSelection.size(); i++) {
							try {
								getSelectedItem(i).setPosX((int) (c[i] + (e.getX() - a)/viewerZoom));
								getSelectedItem(i).setPosY((int) (d[i] + (e.getY() - b)/viewerZoom));
							} catch (NoItemFoundException exc) {

							}
						}
					}
				} else if (SwingUtilities.isMiddleMouseButton(e)) {
					viewerX += (e.getX() - a);
					viewerY += (e.getY() - b);
					a = e.getX();
					b = e.getY();
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
			if (panelStatus == 0) {
				a = e.getX();
				b = e.getY();
				c = new int[itemSelection.size()];
				d = new int[itemSelection.size()];
				for (int i = 0; i < itemSelection.size(); i++) {
					try {
						c[i] = getSelectedItem(i).getPosX();
						d[i] = getSelectedItem(i).getPosY();
					} catch (NoItemFoundException e1) {
						e1.printStackTrace();
					}
				}
			} else if (panelStatus == 1) {
				userClickedOnX = e.getX();
				userClickedOnY = e.getY();
				System.out.println("x:"+ userClickedOnX + " y:" + userClickedOnY);
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