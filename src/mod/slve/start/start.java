package mod.slve.start;

import inittools.ModBox;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import mod.slve.items.ImageItem;
import mod.slve.items.ItemThatReturnAnImage;
import mod.slve.items.ShapeOval;
import mod.slve.items.ShapeRect;
import mod.slve.items.TextItem;
import mod.slve.items.VideoItem;
import start.AppProperties;
import start.BasicLayer;
import start.GuiLayer;
import start.MainWindow;
import start.Start;
import tools.CommandFrame;
import tools.PropertiesWindow;
import tools.RendererTool;
import tools.ScriptReader;
import tools.SourceWindow;
import tools.SourceWindow.SourceActions;
import API.Item;
import API.Mod;
import API.SlveFrame;
import API.SlveMenuItem;
import browser.BrowserActions;
import browser.FileBrowser;
import exceptions.NoItemFoundException;

public class start extends Mod{

	public start() {
		super("slve");
		
		SlveMenuItem renderProp = new SlveMenuItem("properties", new String[]{"render"});
		SlveMenuItem renderShot = new SlveMenuItem("shot", new String[]{"render"});
		SlveMenuItem renderFilm = new SlveMenuItem("video", new String[]{"render"});
		SlveMenuItem addSrcImage = new SlveMenuItem("image from source", new String[]{"add"});
		SlveMenuItem addImage = new SlveMenuItem("image", new String[]{"add"});
		SlveMenuItem addText = new SlveMenuItem("text", new String[]{"add"});
		SlveMenuItem addVideo = new SlveMenuItem("video", new String[]{"add"});
		SlveMenuItem addRect = new SlveMenuItem("rectangle", new String[]{"add", "shape"});
		SlveMenuItem addOval = new SlveMenuItem("oval", new String[]{"add", "shape"});
		SlveMenuItem addEmpty = new SlveMenuItem("empty", new String[]{"add"});
		SlveMenuItem addLayer = new SlveMenuItem("layer", new String[0]);
		renderProp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new PropertiesWindow();
			}
		});
		renderShot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RendererTool.renderShot();
			}
		});
		renderFilm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RendererTool.renderVideo();
			}
		});
		addSrcImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Start.getSourceWindow().active(new SourceActions() {
					
					@Override
					public void userChooseImage(SourceWindow source, SlveFrame window) {
						ImageItem img = new ImageItem(source.getSelectedItem().preview(), JOptionPane.showInputDialog(null,"give the name of the object you want to create","item #"), 10, 10);
						addItemInSelectedLayer(img);
						window.dispose();
					}
					
					@Override
					public void userChooseFolder(SourceWindow source, SlveFrame window) {
						source.getSelectedItemAsFolder().toggleOpen();
					}
				});
				/*Start.getSourceWindow().active(new SourceActions() {
					
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
				});*/
			}
		});
		addImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FileBrowser(new BrowserActions() {
					
					@Override
					public void done(String path) {
						try {
							Image img = new ImageIcon(path).getImage();
							String str = JOptionPane.showInputDialog("how are we calling that ?");
							addItemInSelectedLayer(new ImageItem(img, str, img.getWidth(null), img.getHeight(null)));
						} catch (Exception e) {
							e.printStackTrace();
							new SlveFrame().warnUser("something went wrong", "couldn't load the image for some reason");
						}
					}
					
					@Override
					public void close(String path) {
					}
				}).go(false, "that image", false);
			}
		});
		addRect.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addItemInSelectedLayer(new ShapeRect());
			}
		});
		addOval.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addItemInSelectedLayer(new ShapeOval());
			}
		});
		
		addLayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Start.getMainWindow().getLayers().add(new BasicLayer(JOptionPane.showInputDialog(null,"give the name of the layer you want to create")));
			}
		});
		Start.addMenuBarItem(renderShot);
		Start.addMenuBarItem(renderFilm);
		Start.addMenuBarItem(renderProp);
		Start.addMenuBarItem(addSrcImage);
		Start.addMenuBarItem(addImage);
		Start.addMenuBarItem(addText);
		Start.addMenuBarItem(addVideo);
		Start.addMenuBarItem(addRect);
		Start.addMenuBarItem(addOval);
		Start.addMenuBarItem(addEmpty);
		Start.addMenuBarItem(addLayer);
		
		BasicLayer.setIcon(new ImageIcon(getClass().getResource("/basic layer.png")).getImage());
		GuiLayer.setIcon(new ImageIcon(getClass().getResource("/GUI layer.png")).getImage());
	}
	
	@Override
	public void render(Item item, Graphics2D g, int x, int y, int w, int h, int cw, int ch, double z) {
		g.rotate(Math.toRadians(item.getRotation()), x+item.getPosX(), y+item.getPosY());
		if (item.getId() == 401) {
			ShapeRect rect = (ShapeRect) item;
			g.fillRoundRect(rect.getPosX() - rect.getWidth()/2 + x, rect.getPosY() - rect.getHeight()/2 + y, rect.getWidth(), rect.getHeight(), rect.getRoundBoundX(), rect.getRoundBoundY());
		}else if (item.getId() == 402) {
			ShapeOval ovl = (ShapeOval) item;
			g.fillOval(ovl.getPosX() - ovl.getWidth()/2 + x, ovl.getPosY() - ovl.getHeight()/2 + y, ovl.getWidth(), ovl.getHeight());
		} else {
			ItemThatReturnAnImage img = (ItemThatReturnAnImage) item;
			g.drawImage(img.getImage(), img.getPosX() - img.getWidth()/2 + x, img.getPosY() - img.getHeight()/2 + y, (int) (img.getWidth()*z), (int) (img.getHeight()*z), null);
		}
		g.rotate(Math.toRadians(-item.getRotation()), x+item.getPosX(), y+item.getPosY());
	}
	
	public void addItemInSelectedLayer (Item item) {
		try {
			((BasicLayer)Start.getMainWindow().getSelectedLayer()).addItem(item);
			Start.getOutline().refresh();
		} catch (ClassCastException e) {
			Start.getMainWindow().scoldUser("wrong layer", "this item can't go in that layer !");
		}
	}

	@Override
	public int FireCommand(ArrayList<String> args, CommandFrame cmds) {
		switch (args.get(0)) {
		case "help" :
			try {
				if (args.size() != 2) {
					cmds.print("[help]you are about the help for the usage of slve command prompt");
					Thread.sleep(4000);
					cmds.print("[help] everything comming out of the c.p shall start with [something] and appear in green");
					Thread.sleep(4000);
					cmds.print("[help] Your own writing should appear in blue");
					cmds.print("[help] If you ever see something you didn't write in blue, no worries");
					cmds.print("the developpers must have forgotten to had a bracket in the beggining of the sentence");
					Thread.sleep(10000);
					cmds.print("[help] if an error occur, a text in red should appear starting with [serge]");
					Thread.sleep(2000);
					cmds.print("[serge] like this !");
					Thread.sleep(4000);
					cmds.print("[help] command should be written like this (blue on purpose):");
					cmds.print("command [arg1] [arg2]");
					cmds.print("[help] everything between \" \" is considered as one argument");
					Thread.sleep(12000);
					cmds.print("[help] here's a tip; if you write :help a");
					cmds.print("[help] you will skip the tutorial and only the command list !");
					Thread.sleep(6000);
				}
				cmds.print("[help] *************************list of cmds");
				cmds.print("[help] echo, list, clear, pause, script");
				cmds.print("[help] command.prompt, outline, timeline, item.options, main");
				cmds.print("[help] render, exit");
				break;
			} catch (InterruptedException e) {

			}
		case "echo" :
			for (int i = 1; i < args.size(); i++) {
				cmds.print("[echo]"+args.get(i));
			}
			break;
		case "list" :
			ArrayList<String> ItemNames = new ArrayList<String> ();
			ArrayList<String> CommSpef  = new ArrayList<String> ();
			
			for (int i = 1; i < args.size(); i++) {
				if (args.get(i).startsWith("name:")) {
					ItemNames.add(args.get(i).substring(5));
				} else if (args.get(i).startsWith("n:")) {
					ItemNames.add(args.get(i).substring(2));
				} else {
					CommSpef.add(args.get(i));
				}
			}
			if (MainWindow.getIndex().size() == 0) {
				cmds.print ("[serge]there is no item to list or scan in the index");
			}else {
				int i = 0, y = 0, a = 0;
				try {
					if (ItemNames.size() > 0) {
						for (y = 0; y < ItemNames.size(); y++) {
							for (a = 0; a < CommSpef.size(); a++) {
								switch (CommSpef.get(a)) {
								case "keyframe" :
								case "k" :
									Item item = MainWindow.getItemByName(ItemNames.get(y));
									cmds.print ("[list] -" + ItemNames.get(y));
									if (item.getId() == 1 || item.getId() == 2 || item.getId() == 3 || item.getId() == 4) {
										for (int i2 = 0; i2 < item.getAllKeyFramesTranslation().length ; i2 ++) {
											cmds.print ("[list] -- " + item.getKeyFrameTranslate(i2));
										}
										for (int i2 = 0; i2 < item.getAllKeyFramesRotation().length ; i2 ++) {
											cmds.print ("[list] -- " + item.getKeyFrameRotation(i2));
										}
										for (int i2 = 0; i2 < item.getAllKeyframeActiv().size(); i2 ++) {
											cmds.print ("[list] -- " + item.getKeyframeActiv(i2));
										}
									}
									break;
								case "is.on" :
								case "i.o" :

								default :
									cmds.print("[serge]the argument \"" + CommSpef.get(a) +"\" is invalid, please see : list help for more informations");
								}
							}
						}
					} else {
						for (a = 0; a < CommSpef.size(); a++) {
							switch (CommSpef.get(a)) {
								case "i" :
								case "index" :
									for (int d = 0; d < MainWindow.getIndex().size();d++) {
										cmds.print ("[list] index("+d+")= a:" + MainWindow.getIndex().get(d).getA() + " b:" + MainWindow.getIndex().get(d).getB() + "name:" /*+ MainWindow.getItem(MainWindow.getIndex().get(d)).getName()*/);
									}
							}
						}
					}
				} catch (NoItemFoundException e) {
					cmds.print ("[serge]no item has the name of : \"" + ItemNames.get(y) +"\"");
				}

				for (int d = 0; d < ItemNames.size(); d++) {
					cmds.print ("[debug]item name :" + ItemNames.get(d));
				}
				for (int d = 0; d < CommSpef.size(); d ++) {
					cmds.print ("[debug]spef :" + CommSpef.get(d));
				}
				cmds.print("[debug]mainwindow.getindex.Size = " + MainWindow.getIndex().size() + " i:" + i +" y:" + y + " a:" + a);
			}
			cmds.print("[list]*done*");
			break;
		case "clear" :

			for (int i = 0 ; i < cmds.getLines().length; i++) {
				cmds.getLines()[cmds.getLines().length - 1 - i] = " ";
			}
			break;
		case "pause" :
			if (args.size() == 2) {
				JOptionPane.showMessageDialog(cmds, args.get(1));
			} else {
				cmds.print("[serge]pause has to be used has : \"pause [message as argument]\"");
				cmds.print("[help]exemple -> pause \"now waiting\"; exemple -> pause hello");
			}
			break;
		case "script" :
			if (args.get(1).equals("read")) {
				try {
					ScriptReader.read(args.get(2));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			break;
		case "command.prompt" :
			if (args.get(1).startsWith("set.title")) cmds.setTitle (args.get(2));
			else if (args.get(1).equals("visible")) cmds.setVisible(true);
			else if (args.get(1).equals("unvisible")) cmds.setVisible(false);
			else if (args.get(1).equals("set.size")) cmds.setSize(new Dimension(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3))));
			else if (args.get(1).equals("set.position")) cmds.setLocation(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)));
			else return 2;
			break;
		case "outline" :
			if (args.get(1).startsWith("set.title")) {
				Start.getOutline().setTitle (args.get(2)); 
				return 0;
			}	else if (args.size() > 3 && args.get(1).equals("add")) {
				switch (args.get(2)) {
				case "image":
				case "img" :
				case "i" :
				case "1" :
					try {
						((BasicLayer) Start.getMainWindow().getSelectedLayer()).addItem(new ImageItem(
								ImageIO.read(new File(args.get(3))), args.get(4), 0, 0));
						Start.getOutline().refresh();
					} catch (IOException e) {
						cmds.print("[serge] oops, can't load image");
						e.printStackTrace();
					}
					return 0;
				case "text" :
				case "txt" :
				case "t" :
				case "2" :
					MainWindow.addTextItem(new TextItem(args.get(3)));
					break;
				case "video" :
				case "vid" :
				case "v" :
				case "3" :
					MainWindow.addVideoItem(new VideoItem(args.get(3), args.get(4)));
					break;
					// TODO shape
				}
				//MainWindow.getOutline().refresh();
			}
			else if (args.get(1).equals("remove") || args.get(1).equals("rm")) {
				/*try {
					MainWindow.removeItemByName(args.get(2));
				} catch (NoItemFoundException e) {
					cmds.print ("[serge] no existing item with the name :" + args.get(2));
				}*/
				cmds.print ("[debug] command removed for maintenance");
			}
			else if (args.get(1).equals("visible")) Start.getOutline().setVisible(true);
			else if (args.get(1).equals("unvisible")) Start.getOutline().setVisible(false);
			else if (args.get(1).equals("reload")) {
				Start.getOutline().refresh();
			}
			else if (args.get(1).equals("set.size")) Start.getOutline().setSize(new Dimension(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3))));
			else if (args.get(1).equals("set.position")) Start.getOutline().setLocation(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)));
			break;
		case "timeline" :
			if (args.get(1).startsWith("set.title")) Start.getTimeLine().setTitle (args.get(2));
			else if (args.get(1).equals("visible")) Start.getTimeLine().setVisible(true);
			else if (args.get(1).equals("unvisible")) Start.getTimeLine().setVisible(false);
			else if (args.get(1).equals("set.size")) Start.getTimeLine().setSize(new Dimension(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3))));
			else if (args.get(1).equals("set.position")) Start.getTimeLine().setLocation(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)));
			break;
		case "item.options" :
			if (args.get(1).startsWith("set.title")) Start.getItemOption().setTitle (args.get(2));
			else if (args.get(1).equals("visible")) Start.getItemOption().setVisible(true);
			else if (args.get(1).equals("unvisible")) Start.getItemOption().setVisible(false);
			else if (args.get(1).equals("set.size")) Start.getItemOption().setSize(new Dimension(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3))));
			else if (args.get(1).equals("set.position")) Start.getItemOption().setLocation(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)));
			break;
		case "main" :
			if (args.get(1).startsWith("set.title")) Start.getMainWindow().setTitle (args.get(2));
			else if (args.get(1).equals("visible")) Start.getMainWindow().setVisible(true);
			else if (args.get(1).equals("unvisible")) Start.getMainWindow().setVisible(false);
			else if (args.get(1).equals("set.size")) Start.getMainWindow().setSize(new Dimension(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3))));
			else if (args.get(1).equals("set.position")) Start.getMainWindow().setLocation(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)));
			else if (args.get(1).equals("new.layer")) Start.getMainWindow().getLayers().add(new BasicLayer(args.get(2)));
			else if (args.get(1).equals("new.guilayer")) Start.getMainWindow().getLayers().add(new GuiLayer());
			else if (args.get(1).equals("set.selected.layer")) Start.getMainWindow().setSelectedLayer(Integer.parseInt(args.get(2)));
			break;
		case "image.selector" :
			if (args.get(1).equals("default.path")) {
				AppProperties.setImageSelectorDefaultPath(args.get(2));
			}
			break;
		case "render" :
			if (args.get(1).equals("default.output")) {
				AppProperties.setRenderOutputPath(args.get(2));
			}
			break;
		case "exit" :
			if (args.get(1).equals("slve")) System.exit(1);
			else if (args.get(1).equals ("cmd")) cmds.dispose();
		case "label" :
			return 0;
		case "goto" :
		case "GOTO" :
			return 0;
		default :
			cmds.print("[serge] I don't know that command, i'm sorry. Are you sure you spelled it right ?");
			return 1;
	}
		return 0;
	}

	JCheckBox doShowTerminal = new JCheckBox("show terminal");
	JCheckBox activate = new JCheckBox("activate");
	JTextField defaultRenderOutputPath = new JTextField();
	JCheckBox doPauseWhenDone = new JCheckBox("wait for user to press space");
	
	@Override
	public String[] getModInitParameters() {
		String x1 = "command.prompt visible";
		if (!doShowTerminal.isSelected()) {
			x1 = "#" + x1;
		}
		
		return new String[]{
				"command.prompt set.size 400 400\ncommand.prompt set.position 0 0\ncommand.prompt set.title \"hello, we are loading\"",
				x1,
				"render default.output \"" + defaultRenderOutputPath.getText() +"\"",
				"main set.title \"super lama video editor\"\nmain set.size 900 600\nmain set.position 0 100",
				"outline set.title \"outline\"\noutline set.size 400 200\noutline set.position 900 130",
				"timeline set.title \"timeline\"\ntimeline set.size 1300 100\ntimeline set.position 0 0",
				"item.options set.title \"item option\"\nitem.options set.size 400 200\nitem.options set.position 900 360",
				"main new.layer background",
				"main new.guilayer",
				"main set.selected.layer 0"};
	}
	


	@Override
	public void getModInitOptions(ModBox box) {
		defaultRenderOutputPath.setToolTipText("exemple : C/user/nathan/Desktop");
		defaultRenderOutputPath.setPreferredSize(new Dimension(100, 30));
		activate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setActivated(((JCheckBox)e.getSource()).isEnabled());
			}
		});
		
		box.add(activate);
		box.add(doShowTerminal);
		box.add(doPauseWhenDone);
		box.add(new JSeparator());
		box.add(new JLabel("where to render by default"));
		box.add(defaultRenderOutputPath);
	}

	@Override
	public String[] getModInitParametersAfterJob() {
		String x1 = "pause \"ready to start\"";
		
		if (!doPauseWhenDone.isSelected())
			x1 = "#" + x1;
		
		return new String[] {
		x1,
		"loadbar"
		};
	}

	@Override
	public boolean checkBeforeWritingInit() {
		boolean b = true;
		if (new File(defaultRenderOutputPath.getText()).exists()) {
			defaultRenderOutputPath.setBackground(Color.white);
		} else {
			defaultRenderOutputPath.setBackground(Color.red);
			b = false;
		}
		return b;
	}


}
