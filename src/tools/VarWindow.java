package tools;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class VarWindow {
	
	ArrayList<String> vars = new ArrayList<String>();
	
	public VarWindow () {
		
	}
	
	private class MyWindow extends JFrame {
		
		public MyWindow () {
			setBounds(0, 0, 500, 500);
			setVisible(true);
		}
		
		private class MyPanel extends JPanel{
			@Override
			public void paintComponent (Graphics g) {
				
			}
		}
		
	}

}
