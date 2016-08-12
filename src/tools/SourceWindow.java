package tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import API.SlveFrame;
import browser.BrowserActions;
import browser.FileBrowser;

public class SourceWindow {
	
	private static final long serialVersionUID = 1L;
	
	srcFolder root = new srcFolder("/");
	Image folderIcon = new ImageIcon(getClass().getResource("/icon folder.png")).getImage();
	int selectedNumber = 0; SourceItem selectedItem;
	
	public SourceWindow () {}
	
	public void active (SourceActions actions) {
		MyWindow window = new MyWindow(actions);
		window.setVisible(true);
	}
	
	public SourceItem getSelectedItem () {
		return selectedItem;
	}
	
	public srcImg getSelectedItemAsImg () {
		return (srcImg) selectedItem;
	}
	
	public srcFolder getSelectedItemAsFolder () {
		return (srcFolder) selectedItem;
	}
	
	private class MyWindow extends SlveFrame implements KeyListener{
		
		SourceActions actions;
		
		JMenuBar menuBar = new JMenuBar();
		JMenu add = new JMenu("add");
		JMenuItem mi_image = new JMenuItem("image");
		JMenuItem mi_mkdir = new JMenuItem("create folder");
		
		public MyWindow (SourceActions actions) {
			super (OVER);
			this.actions = actions;
			
			setBounds(20, 20, 800, 800);
			addKeyListener(this);
			setContentPane(new MyPanel());
			
			selectedItem = root;
			
			mi_image.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					FileBrowser browser = new FileBrowser(new BrowserActions() {
						@Override
						public void done(String path) {
							File file = new File(path);
							try {
								selectedItem.getParent().addItem(new srcImg(
										file.getName(), ImageIO.read(file)));
							} catch (IOException e) {
								e.printStackTrace();
							}
							repaint();
						}
						
						@Override
						public void close(String path) {
						}
					});
					browser.go(false, "yep, that the one !", false);
				} //ouai je sais, c'est long :/
			});
			mi_mkdir.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					String str = JOptionPane.showInputDialog(null,"please name the folder","Bond, James Bond");
					selectedItem.getParent().addItem(new srcFolder(str));
					repaint();
				}
			});
			add.add(mi_image);
			menuBar.add(add);
			menuBar.add(mi_mkdir);
			setJMenuBar(menuBar);
		}
		
		public void drawFolder (srcFolder s, Point p, Graphics g) {
			p.x += 20;
			for (SourceItem item : s.getSourceItem()) {
				g.drawImage(item.preview(), p.x, p.y + 30, 50, 50, null);
				if (item.id == 1) {
					if (((srcFolder) item).getOpen())
						g.drawString(item.getName() + " (open)", p.x + 80, p.y + 60);
					else 
						g.drawString(item.getName() + " (closed)", p.x + 80, p.y + 60);
				} else {
					g.drawString(item.getName(), p.x + 80, p.y + 60);
				}
				g.drawLine(p.x, p.y + 82 , getWidth() - 10, p.y + 82);
				if ( p.y / 55 == selectedNumber) {
					selectedItem = item;
					g.setColor(Color.red);
					g.drawRect(p.x, p.y+27, getWidth() - 10 - p.x, 55);
					g.setColor(Color.BLACK);
					setTitle(selectedItem.getParent().getName()+"");
				}
				if (item.id == 1 && ((srcFolder) item).getOpen()) {
					p.y+=55;
					drawFolder((srcFolder) item, p, g);
					p.x-=20;
					continue;
				}
				p.y+=55;
			}
		}
		
		private class MyPanel extends JPanel{
			public void paintComponent (Graphics g) {
				int x = 0, y = 0;
				drawFolder(root, new Point(0, 0), g);
			}
		}

		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case 38:
				selectedNumber--;
				break;
			case 40:
				selectedNumber++;
				break;
			case 10:
				if (selectedItem.id == 1) {
					actions.userChooseFolder(SourceWindow.this, this);
				} else if (selectedItem.id == 2) {
					actions.userChooseImage(SourceWindow.this, this);
				}
				break;
			}
			repaint();
		}

		@Override
		public void keyReleased(KeyEvent e) {
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
	}
	
	public abstract class SourceItem {
		
		String name;
		srcFolder parentFolder;
		int id;
		
		public SourceItem (String name) {
			this.name = name;
		}
		
		public abstract Image preview ();
		
		public String getName () {
			return name;
		}
		
		public void setParent (srcFolder parent) {
			parentFolder = parent;
		}
		
		public srcFolder getParent () {
			return parentFolder;
		}
	}
	
	public class srcFolder extends SourceItem {
		
		boolean open = false;
		
		public srcFolder(String name) {
			super(name);
			id = 1;
		}
		
		public void addItem (SourceItem item) {
			item.setParent(this);
			src.add(item);
		}

		ArrayList<SourceItem> src = new ArrayList<SourceItem>();

		@Override
		public Image preview() {
			return folderIcon;
		}
		
		public ArrayList<SourceItem> getSourceItem () {
			return src;
		}
		
		public void toggleOpen () {
			open = !open;
		}
		
		public boolean getOpen () {
			return open;
		}
		
		@Override
		public srcFolder getParent () {
			return this;
		}
		
	}
	
	public class srcImg extends SourceItem {

		Image image;
		
		public srcImg(String name, Image image) {
			super(name);
			this.image = image;
			id = 2;
		}
		
		@Override
		public Image preview() {
			return image;
		}
		
	}
	
	public interface SourceActions {
		public void userChooseImage (SourceWindow source, JFrame window);
		public void userChooseFolder (SourceWindow source, JFrame window);
	}

}
