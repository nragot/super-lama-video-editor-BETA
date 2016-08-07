package tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import start.Start;

public class PropertiesWindow extends JFrame {
	static String[] screenSizeStr = {"custom","htdv - 1080i(1920*1080) | 16:9","HD - 720i(1280*720) | 16:9","WVGA - 480i(854*480) | 16:9","WQXGA - 1600(2560*1600) | 16:10","WUXGA - 1050(1680*1050) | 16:10","CGA - 200(320*200) | 16:10"};
	static JComboBox<String> ScreenSizesCombo = new JComboBox<String>(screenSizeStr);
	static int customW = 854, customH = 480, lastChoice = 3, endVideo = 250;
	static JTextField jtfW = new JTextField(), jtfH = new JTextField(), jtfeb = new JTextField();
	static JButton okScreenSize = new JButton("Load values");
	
	public PropertiesWindow () {
		setBounds(100,100,400,600);
		setContentPane(new MyPanel());
		setTitle("properties");
		setResizable(false);

		ScreenSizesCombo.addActionListener(new ItemState());
		ScreenSizesCombo.setSelectedIndex(lastChoice);
		add(ScreenSizesCombo);
		
		if (ScreenSizesCombo.getSelectedIndex() != 0) {
			jtfW.setEditable(false);
			jtfH.setEditable(false);
		}
		jtfW.setText(Start.getMainWindow().getCameraWidth() +"");
		jtfH.setText(Start.getMainWindow().getCameraHeight()+"");
		jtfW.setPreferredSize(new Dimension(137,20));
		jtfH.setPreferredSize(new Dimension(137,20));
		add(jtfW);
		add(jtfH);
		
		jtfeb.setText("" + endVideo);
		jtfeb.setPreferredSize(new Dimension(360,20));
		jtfeb.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				try {
					endVideo = Integer.parseInt(jtfeb.getText());
				} catch (java.lang.NumberFormatException e) {
					
				}
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				try {
					endVideo = Integer.parseInt(jtfeb.getText());
				} catch (java.lang.NumberFormatException e) {
					
				}
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		add(jtfeb);
		
		okScreenSize.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Start.getMainWindow().setCameraWidth(Integer.parseInt(jtfW.getText()));
				Start.getMainWindow().setCameraHeight(Integer.parseInt(jtfH.getText()));
			}
		});
		add(okScreenSize);
		
		
		setVisible(true);
	}
	
	private class MyPanel extends JPanel {
		public void paintComponent (Graphics g) {
			g.setColor(Color.DARK_GRAY);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
	
	private class ItemState implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("-->" + ScreenSizesCombo.getSelectedItem());
			
			if (ScreenSizesCombo.getSelectedIndex() == 1) {
				Start.getMainWindow().setCameraWidth(1920);
				Start.getMainWindow().setCameraHeight(1080);
			} else if (ScreenSizesCombo.getSelectedIndex() == 2) {
				Start.getMainWindow().setCameraWidth(1280);
				Start.getMainWindow().setCameraHeight(720);
			} else if (ScreenSizesCombo.getSelectedIndex() == 3) {
				Start.getMainWindow().setCameraWidth(854);
				Start.getMainWindow().setCameraHeight(480);
			} else if (ScreenSizesCombo.getSelectedIndex() == 4) {
				Start.getMainWindow().setCameraWidth(2560);
				Start.getMainWindow().setCameraHeight(1600);
			} else if (ScreenSizesCombo.getSelectedIndex() == 5) {
				Start.getMainWindow().setCameraWidth(1680);
				Start.getMainWindow().setCameraHeight(1050);
			} else if (ScreenSizesCombo.getSelectedIndex() == 6) {
				Start.getMainWindow().setCameraWidth(320);
				Start.getMainWindow().setCameraHeight(200);
			}
			boolean b = false;
			if (ScreenSizesCombo.getSelectedIndex() == 0) {		
				Start.getMainWindow().setCameraWidth(customW);
				Start.getMainWindow().setCameraWidth(customH);
				b = true;
			}
			jtfW.setEditable(b);
			jtfH.setEditable(b);
			revalidate();
			
			lastChoice = ScreenSizesCombo.getSelectedIndex();
		}
		
	}
	
	public static int getEndVideo () {
		return endVideo;
	}

}
