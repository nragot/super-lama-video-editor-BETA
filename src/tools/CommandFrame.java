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

import mod.slve.items.ImageItem;
import mod.slve.items.TextItem;
import mod.slve.items.VideoItem;

import API.Item;
import API.Mod;

import exceptions.NoItemFoundException;
import start.AppProperties;
import start.MainWindow;
import start.Start;

public class CommandFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	JPanel myPanel = new MyPanel ();
	JTextField cmd = new JTextField();
	final int cmdsLenght = 40;
	String cmds[] = new String[cmdsLenght];
	Mod currentmod;
	
	public CommandFrame () {
		setContentPane(myPanel);
		setLayout(new BorderLayout());
		add(cmd,BorderLayout.SOUTH);
		setTitle ("command prompt");
		setBounds(0, 0, 530, 800);
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
			}});
	}
	
	public String[] getLines () {
		return cmds;
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
	
	public void activate () {
		setVisible(true);
	}
	
	public void print (String str) {
		for (int i = 0; i < cmdsLenght - 1 ; i++) {
			cmds[cmdsLenght - 1 -i] = cmds[cmdsLenght - 2 -i];
		}
		cmds[0] = str;
		repaint();
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
		case "mod":
			for (Mod mod : AppProperties.getMods()) {
				if (mod.getName().equals(args.get(1))) {
					currentmod = mod;
					return 0;
				}
			}
			print ("[serge] didn't find your mod");
			return 2;
		case "insmod":
		case "loadmod":
		case "ldmod":
			boolean b = AppProperties.loadMod(args.get(1));
			if (b) {
				print ("mod succesfully loaded : " + AppProperties.getMods().get(AppProperties.getMods().size()-1).getName());
				return 0;
			} else {
				print ("mod not find");
				return 2;
			}
		default:
			if (args.get(0).equals("*")) {
				for (Mod mod : AppProperties.getMods()) {
					if (mod.getName().equals(args.get(1))) {
						args.remove(0);args.remove(0);
						mod.FireCommand(args, this);
						return 0;
					}
				}
			} else {
				if (currentmod==null) {
					if (!isVisible()) {
						print("[serge]a command has been fired, yet no module has been loaded");
						print("[serge]console set to \"visible\" automatically");
						setTitle("aouch");
						setBounds(0, 0, 1000, 800);
						setVisible(true);
					}
					print ("[serge]no module set, error will occure");
					return 2;
				}
			}
			currentmod.FireCommand(args, this);
			return 0;
		}
	}

}
