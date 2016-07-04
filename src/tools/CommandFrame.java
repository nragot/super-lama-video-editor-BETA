package tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import exceptions.NoItemFoundException;
import items.ImageItem;
import items.Item;
import items.TextItem;
import items.VideoItem;
import start.AppProperties;
import start.MainWindow;
import start.Start;

public class CommandFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JPanel myPanel = new MyPanel ();
	JTextField cmd = new JTextField();
	final int cmdsLenght = 40;
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

			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				print(cmd.getText());
				command(cmd.getText());
				cmd.setText("");
				repaint();
			}});
	}
	
	private class MyPanel extends JPanel {
		
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent (Graphics g) {
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
				try {
					print ("[help] hello, welcome in the Command Prompt !");
					Thread.sleep(1000);
					print ("[help] here you will be able to enter commands to do a lot of stuff.");
					print ("[help] in wich case we (the commands) will, sometime, answer you. Our answers are in green.");
					print ("[help] your commands are in blue (look above).");
					print ("[help] if something goes wrong, serge himself will tell you that an error occur. here is an exemple :");
					print ("[serge] it is absolutly forbidden to use the help! ... let's say it is fine for now but i'm watching you.");
					print ("[help] a command can be written as this");
					print ("command argument1 \"argument 2\" ...");
					print ("[help] hopefully many commands don't need any arguments, but many more might need you much information");
					print ("[help] if you need to write an argument wich need spaces to be written, you will have to put ...");
					print ("[help] ... \" \" to make sure it is considere as only one argument");
					print ("[help] here is a great exemple of command you may use :");
					print ("outline add image \"C:/image 1\"");
					print("[help] commands available :");
					print("echo, list");
					break;
				} catch (InterruptedException e) {

				}
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
				if (MainWindow.getIndex().size() == 0) {
					print ("[serge]there is no item to list or scan in the index");
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
										print ("[list] -" + ItemNames.get(y));
										if (item.getId() == 1 || item.getId() == 2 || item.getId() == 3 || item.getId() == 4) {
											for (int i2 = 0; i2 < item.getAllKeyFramesTranslation().length ; i2 ++) {
												print ("[list] -- " + item.getKeyFrameTranslate(i2));
											}
											for (int i2 = 0; i2 < item.getAllKeyFramesRotation().length ; i2 ++) {
												print ("[list] -- " + item.getKeyFrameRotation(i2));
											}
											for (int i2 = 0; i2 < item.getAllKeyframeActiv().size(); i2 ++) {
												print ("[list] -- " + item.getKeyframeActiv(i2));
											}
										}
										break;
									case "is.on" :
									case "i.o" :

									default :
										print("[serge]the argument \"" + CommSpef.get(a) +"\" is invalid, please see : list help for more informations");
									}
								}
							}
						} else {
							for (a = 0; a < CommSpef.size(); a++) {
								switch (CommSpef.get(a)) {
									case "i" :
									case "index" :
										for (int d = 0; d < MainWindow.getIndex().size();d++) {
											print ("[list] index("+d+")= a:" + MainWindow.getIndex().get(d).getA() + " b:" + MainWindow.getIndex().get(d).getB() + "name:" /*+ MainWindow.getItem(MainWindow.getIndex().get(d)).getName()*/);
										}
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
			case "pause" :
				JOptionPane.showMessageDialog(this, args.get(1));
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
				if (args.get(1).startsWith("set.title")) setTitle (args.get(2));
				else if (args.get(1).equals("visible")) setVisible(true);
				else if (args.get(1).equals("unvisible")) setVisible(false);
				else if (args.get(1).equals("set.size")) setSize(new Dimension(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3))));
				else if (args.get(1).equals("set.position")) setLocation(Integer.parseInt(args.get(2)),Integer.parseInt(args.get(3)));
				else return 1;
				break;
			case "outline" :
				if (args.get(1).startsWith("set.title")) Start.getOutline().setTitle (args.get(2));
				else if (args.size() > 3 && args.get(1).equals("add")) {
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
					//MainWindow.getOutline().refresh();
				}
				else if (args.get(1).equals("remove") || args.get(1).equals("rm")) {
					try {
						MainWindow.removeItemByName(args.get(2));
					} catch (NoItemFoundException e) {
						print ("[serge] no existing item with the name :" + args.get(2));
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
				else if (args.get(1).equals ("cmd")) dispose();
			case "label" :
				return 0;
			case "goto" :
			case "GOTO" :
				return 0;
			default :
				print("[serge] I don't know that command, i'm sorry. Are you sure you spelled it right ?");
				return 1;
		}
		return 0;
	}

}
