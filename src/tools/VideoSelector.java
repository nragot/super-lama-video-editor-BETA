package tools;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import mod.slve.items.ImageItem;
import mod.slve.items.VideoItem;

import start.AppProperties;
import start.MainWindow;

public class VideoSelector extends JFrame implements ActionListener{
	String path = "";
	ArrayList<JButton> AllButtons = new ArrayList<JButton>();
	static int lastItemNumCreated = 0;
	
	public VideoSelector () {
		path = AppProperties.getImageSelectorDefaultPath();
		setBounds(100, 100, 700, 400);
		setTitle(path);
		setLayout(new FlowLayout());
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
			if (listedFile[i].getName().endsWith(".avi") || listedFile[i].getName().endsWith(".mp4") || listedFile[i].getName().endsWith(".webm")){
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
				MainWindow.addVideoItem(new VideoItem(path.substring(0, path.length() - 1),str, TimeLine.getTime()));
				MainWindow.getOutline().refresh();
				dispose();
			}
		}
	}
}
