  package tools;

import items.ImageItem;

import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.plaf.SliderUI;

import start.AppProperties;
import start.MainWindow;

public class ImageSelector extends JFrame implements ActionListener{
	String path = "";
	ArrayList<JButton> AllButtons = new ArrayList<JButton>();
	static int lastItemNumCreated = 0;
	
	public ImageSelector () {
		path = AppProperties.getImageSelectorDefaultPath();
		setBounds(100, 100, 700, 400);
		setTitle(path);
		setLayout(new FlowLayout());
		//setAlwaysOnTop(true);
		setVisible(true);
		
		JButton back = new JButton ("..");
		back.setBackground(Color.GREEN);
		back.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				path = path.substring(0,path.lastIndexOf("/",path.length()-2)+1);
				setTitle(path);
				for (int i = 0; i < AllButtons.size();i++) {
					remove(AllButtons.get(i));
				}
				AllButtons.clear();
				loadImages();
				setSize(getWidth(), getHeight()-1);
				revalidate();
				setSize(getWidth(), getHeight()+1);
			}
		});
		add(back);
		loadImages();
		revalidate();
	}
	
	public void loadImages () {
		File folder = new File(path);
		File[] listedFile = folder.listFiles();
		for (int i = 0; i < listedFile.length; i++) {
			if (listedFile[i].getName().endsWith(".png") || 
					listedFile[i].getName().endsWith(".jpeg") || listedFile[i].getName().endsWith(".ico") ||
					listedFile[i].getName().endsWith(".jpg")){
				JButton but = new JButton(listedFile[i].getName());
				but.setBackground(Color.red);
				but.addActionListener(this);
				AllButtons.add(but);
				add(but);
			} else if (listedFile[i].isDirectory()) {
				JButton but = new JButton(listedFile[i].getName());
				but.setBackground(Color.cyan);
				but.addActionListener(this);
				AllButtons.add(but);
				add(but);
			}
		}
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton but = (JButton) e.getSource();
		path += but.getText() + "/";
		System.out.println(path + " " + new File(path).isDirectory());
		if (new File(path).isDirectory()) {
			setTitle(path);
			for (int i = 0; i < AllButtons.size();i++) {
				remove(AllButtons.get(i));
			}
			AllButtons.clear();
			loadImages();
			setSize(getWidth(), getHeight()-1);
			revalidate();
			setSize(getWidth(), getHeight()+1);
		} else {
			String str = JOptionPane.showInputDialog(null,"give the name of the object you want to create","item #" + lastItemNumCreated);
			if (str != null) {
				lastItemNumCreated ++;
				MainWindow.addImageItem(new ImageItem(new ImageIcon(path).getImage(), str, 0, 0));
				MainWindow.getOutline().refresh();
				dispose();
			}
		}
	}
	

}
