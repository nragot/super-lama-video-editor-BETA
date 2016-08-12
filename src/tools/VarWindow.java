package tools;

import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import API.SlveFrame;

public class VarWindow {
	
	ArrayList<String> vars = new ArrayList<String>();
	
	public VarWindow () {
		
	}
	
	private class MyWindow extends SlveFrame {
		
		public MyWindow () {
			super (OVER);
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
