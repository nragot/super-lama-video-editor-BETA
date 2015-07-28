package tools;

import items.ImageItem;
import items.Item;
import items.TextItem;
import items.VideoItem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import exceptions.NoItemFoundException;

import start.AppProperties;
import start.MainWindow;

public class CommandFrame extends JFrame {
	JPanel myPanel = new MyPanel ();
	JTextField cmd = new JTextField();
	final int cmdsLenght = 10;
	String cmds[] = new String[cmdsLenght];
	
	public CommandFrame () {
		setContentPane(myPanel);
		setLayout(new BorderLayout());
		add(cmd,BorderLayout.SOUTH);
		setTitle ("command prompt");
		for (int i = 0; i < cmdsLenght; i++) {
			cmds[i] = " ";
		}
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER,0), "enterCmd");
		myPanel.getActionMap().put("enterCmd", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				print(cmd.getText());
				command(cmd.getText());
				cmd.setText("");
				repaint();
		}});
	}
	
	private class MyPanel extends JPanel {
		
		@Override
		public void paintComponent (Graphics g) {
			System.out.println("repaint");
			g.fillRect(0, 0, getWidth(), getHeight());
			for (int i = 0 ; i < cmdsLenght; i++) {
				if (cmds[cmdsLenght - 1 -i].startsWith("[serge]")) g.setColor(Color.RED);
				else if (cmds[cmdsLenght - 1 -i].startsWith("[debug]")) g.setColor(Color.MAGENTA);
				else if (cmds[cmdsLenght - 1 -i].charAt(0) == '[') g.setColor(Color.green);
				else g.setColor (Color.cyan);
				g.drawString(cmds[cmdsLenght - 1 -i], 10, getHeight() - cmdsLenght * 20 + i * 20 - 5);
			}
		}
	}
	
	public void print (String str) {
		for (int i = 0; i < cmdsLenght - 1 ; i++) {
			cmds[cmdsLenght - 1 -i] = cmds[cmdsLenght - 2 -i];
		}
		cmds[0] = str;
	}
	
	public int command (String str) {
		ArrayList<String> args = new ArrayList<String>();
		str = str + " ";
		String arg = "";
		int index = 0;
		boolean space = true;
		
		a:do {
			arg = "";
			b:do {
				if (str.charAt(index) == '"' && index > 0 && str.charAt(index - 1) != '\\' ) {
					space = !space ;
					index ++;
				}
				if (str.charAt(index) != ' ' ) {
					arg = arg + str.charAt(index);
				} else if (space){
					break b;
				} else {
					arg = arg + ' ';
				}
				index++;
				//System.out.println("[debug]" + index + " - " + space);
			} while (true);
			index++;
			args.add(arg);
			if (index >= str.length() ) {
				break a;
			}
		}while (true);
		
		switch (args.get(0)) {
			case "help" :
				print("[help] commands available :");
				print("echo, list");
				break;
			case "echo" :
				for (int i = 1; i < args.size(); i++) {
					print("[echo]"+args.get(i));
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
				if (ItemNames.size() == 0 || CommSpef.size() == 0) {
					print ("[serge] missing argument(s)");
				} else if (MainWindow.getIndex().size() == 0) {
					print ("[serge]there is no item to list or scan in the index");
				}else {
					int i = 0, y = 0, a = 0;
					try {
						for (y = 0; y < ItemNames.size(); y++) {
							Item item = MainWindow.getItemByName(ItemNames.get(y));
							for (a = 0; a < CommSpef.size(); a++) {
								switch (CommSpef.get(a)) {
								case "keyframe" :
								case "k" :
									print ("[list] -" + ItemNames.get(y));
									if (item.getId() == 1 || item.getId() == 2 || item.getId() == 3 || item.getId() == 4) {
										for (int i2 = 0; i2 < item.getAllKeyFramesTranslation().length ; i2 ++) {
											print ("[list] -- " + item.getKeyFrameTranslate(i2));
										}
										for (int i2 = 0; i2 < item.getAllKeyFramesRotation().length ; i2 ++) {
											print ("[list] -- " + item.getKeyFrameRotation(i2));
										}
									}
									break;
								default :
									print("[serge]the argument \"" + CommSpef.get(a) +"\" is invalid, please see : list help for more informations");
								}
							}
						}
					} catch (NoItemFoundException e) {
						print ("[serge]no item has the name of : \"" + ItemNames.get(y) +"\"");
					}

					for (int d = 0; d < ItemNames.size(); d++) {
						print ("[debug]item name :" + ItemNames.get(d));
					}
					for (int d = 0; d < CommSpef.size(); d ++) {
						print ("[debug]spef :" + CommSpef.get(d));
					}
					print("[debug]mainwindow.getindex.Size = " + MainWindow.getIndex().size() + " i:" + i +" y:" + y + " a:" + a);
				}
				print("[list]*done*");
				break;
			case "clear" :

				for (int i = 0 ; i < cmdsLenght; i++) {
					cmds[cmdsLenght - 1 - i] = " ";
				}
				break;
			case "Command.Prompt" :
				if (args.get(1).startsWith("setTitle:")) setTitle (args.get(1).substring(9));
				break;
			case "outline" :
				if (args.get(1).startsWith("setTitle:")) MainWindow.getOutline().setTitle (args.get(1).substring(9));
				if (args.size() > 3 && args.get(1).equals("add")) {
					switch (args.get(2)) {
						case "image":
						case "img" :
						case "i" :
						case "1" :
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
				}
				if (args.get(1).equals("reload")) {
					MainWindow.getOutline().refresh();
				}
				break;
			case "timeline" :
				if (args.get(1).startsWith("setTitle:")) MainWindow.getTimeLine().setTitle (args.get(1).substring(9));
				break;
			case "item.options" :
				if (args.get(1).startsWith("setTitle:")) MainWindow.getItemOption().setTitle (args.get(1).substring(9));
				break;
			case "main" :
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
				System.exit(1);
			default :
				print("[serge] I don't know that command, i'm sorry. Are you sure you spelled it right ?");
				return 1;
		}
		return 0;
	}

}
