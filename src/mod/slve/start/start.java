package mod.slve.start;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mod.slve.items.ImageItem;
import mod.slve.items.ItemThatReturnAnImage;
import mod.slve.items.ShapeOval;
import mod.slve.items.ShapeRect;
import mod.slve.items.SlveItem;
import mod.slve.items.TextItem;
import mod.slve.items.VideoItem;
import start.AppProperties;
import start.BasicLayer;
import start.MainWindow;
import start.Start;
import tools.CommandFrame;
import tools.ScriptReader;
import tools.SourceWindow;
import tools.SourceWindow.SourceActions;
import API.Item;
import API.Layer;
import API.Mod;
import API.SlveMenuItem;
import exceptions.NoItemFoundException;

public class start extends Mod{

	public start() {
		super("slve");
		
		SlveMenuItem addImage = new SlveMenuItem("image", new String[]{"add"});
		SlveMenuItem addText = new SlveMenuItem("text", new String[]{"add"});
		SlveMenuItem addVideo = new SlveMenuItem("video", new String[]{"add"});
		SlveMenuItem addRect = new SlveMenuItem("oval", new String[]{"add", "shape"});
		SlveMenuItem addOval = new SlveMenuItem("rectangle", new String[]{"add", "shape"});
		SlveMenuItem addEmpty = new SlveMenuItem("empty", new String[]{"add"});
		SlveMenuItem addLayer = new SlveMenuItem("layer", new String[0]);
		addImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Start.getSourceWindow().active(new SourceActions() {
					
					@Override
					public void userChooseImage(SourceWindow source, JFrame window) {
						System.out.println("bleep");
					}
					
					@Override
					public void userChooseFolder(SourceWindow source, JFrame window) {
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
		addLayer.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Start.getMainWindow().getLayers().add(new BasicLayer(JOptionPane.showInputDialog(null,"give the name of the object you want to create")));
			}
		});
		Start.addMenuBarItem(addImage);
		Start.addMenuBarItem(addText);
		Start.addMenuBarItem(addVideo);
		Start.addMenuBarItem(addRect);
		Start.addMenuBarItem(addOval);
		Start.addMenuBarItem(addEmpty);
		Start.addMenuBarItem(addLayer);
	}
	
	@Override
	public void render(Item item, Graphics2D g) {
		g.rotate(Math.toRadians(item.getRotation()));
		if (item.getId() == 401) {
			ShapeRect rect = (ShapeRect) item;
			g.fillRoundRect(rect.getX() - rect.getWidth()/2, rect.getY() - rect.getHeight()/2, rect.getWidth(), rect.getHeight(), rect.getRoundBoundX(), rect.getRoundBoundY());
		}else if (item.getId() == 402) {
			ShapeOval ovl = (ShapeOval) item;
			g.fillOval(ovl.getX() - ovl.getWidth()/2, ovl.getY() - ovl.getHeight()/2, ovl.getWidth(), ovl.getHeight());
		} else {
			ItemThatReturnAnImage img = (ItemThatReturnAnImage) item;
			g.drawImage(img.getImage(), img.getX() - img.getWidth()/2, img.getY() - img.getHeight()/2, img.getWidth(), img.getHeight(), null);
		}
		g.rotate(Math.toRadians(-item.getRotation()));
	}

	@Override
	public int FireCommand(ArrayList<String> args, CommandFrame cmds) {
		switch (args.get(0)) {
		case "help" :
			try {
				cmds.print ("[help] hello, welcome in the Command Prompt !");
				Thread.sleep(1000);
				cmds.print ("[help] here you will be able to enter commands to do a lot of stuff.");
				cmds.print ("[help] in wich case we (the commands) will, sometime, answer you. Our answers are in green.");
				cmds.print ("[help] your commands are in blue (look above).");
				cmds.print ("[help] if something goes wrong, serge himself will tell you that an error occur. here is an exemple :");
				cmds.print ("[serge] it is absolutly forbidden to use the help! ... let's say it is fine for now but i'm watching you.");
				cmds.print ("[help] a command can be written as this");
				cmds.print ("command argument1 \"argument 2\" ...");
				cmds.print ("[help] hopefully many commands don't need any arguments, but many more might need you much information");
				cmds.print ("[help] if you need to write an argument wich need spaces to be written, you will have to put ...");
				cmds.print ("[help] ... \" \" to make sure it is considere as only one argument");
				cmds.print ("[help] here is a great exemple of command you may use :");
				cmds.print ("outline add image \"C:/image 1\"");
				cmds.print("[help] commands available :");
				cmds.print("echo, list");
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
			if (args.get(1).startsWith("set.title")) Start.getOutline().setTitle (args.get(2));
			else if (args.size() > 3 && args.get(1).equals("add")) {
				switch (args.get(2)) {
					case "image":
					case "img" :
					case "i" :
					case "1" :
						cmds.print (args.get(3) + " " + args.get(4));
						MainWindow.addImageItem(new ImageItem(new ImageIcon(args.get(3)).getImage(), args.get(4), 0, 0));
						break;
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
				try {
					MainWindow.removeItemByName(args.get(2));
				} catch (NoItemFoundException e) {
					cmds.print ("[serge] no existing item with the name :" + args.get(2));
				}
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
		case "loadMod":
		case "ldmod" :
		case "insmod":
			AppProperties.loadMod(args.get(1));
			break;
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


}
