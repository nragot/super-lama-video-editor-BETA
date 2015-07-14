package tools;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class CommandFrame extends JFrame {
	JPanel myPanel = new MyPanel ();
	JTextField cmd = new JTextField();
	String cmds[] = new String[20];
	
	public CommandFrame () {
		setContentPane(myPanel);
		setLayout(new BorderLayout());
		add(cmd,BorderLayout.SOUTH);
		setTitle ("command prompt");
		for (int i = 0; i < 20 ; i++) {
			cmds[i] = "";
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
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.green);
			for (int i = 0 ; i < 20 ; i++) {
				g.drawString(cmds[19-i], 10, getHeight() - 410 + i * 20);
			}
		}
	}
	
	public void print (String str) {
		for (int i = 0; i < 19 ; i++) {
			cmds[19-i] = cmds[18-i];
		}
		cmds[0] = str;
	}
	
	public void command (String str) {
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
				System.out.println("[debug]" + index + " - " + space);
			} while (true);
			index++;
			args.add(arg);
			if (index >= str.length() ) {
				break a;
			}
		}while (true);
		
		switch (args.get(0)) {
			case "help" :
				print("[help] hello there, can't help for now, i'm terribly sorry :), Nathan");
				break;
			case "echo" :
				for (int i = 1; i < args.size(); i++) {
					print(args.get(i));
				}
				break;
			default :
				print("[serge] I don't know that command, i'm sorry. Are you sure you spelled it right ?");
		}
	}

}
