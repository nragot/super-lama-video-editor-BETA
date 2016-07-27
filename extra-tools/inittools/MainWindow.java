package inittools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import browser.BrowserActions;
import browser.FileBrowser;

public class MainWindow extends JFrame implements WindowListener{
	private static final long serialVersionUID = 1L;
	JCheckBox showConsole;
	JTextField renderDefOutput;
	JTextField imgSelPath;
	JCheckBox pause;
	JButton okAndClose;
	JButton help;
	JButton modsWindowUp;
	
	RandomAccessFile file ;
	String slvePath;
	ArrayList<String> modulesFound = new ArrayList<String>();
	ArrayList<JCheckBox> modulesFoundCheck = new ArrayList<JCheckBox>();
	int entryMod = 0;
	
	boolean done;
	
	public MainWindow (String path) {
		setBounds (0,0,850,300);
		setTitle ("init script");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		
		slvePath = path;
		
		done = true;
		
		try {
			new File (slvePath + "initme").createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		try {
			A:do {
				File test = new File (slvePath + "slve.init");
				if (!test.exists() || test.isDirectory()) {
					int i = JOptionPane.showConfirmDialog(this, "slve.init not found (which is normal if it is the first time you run super lama video editor).\n do you want to build it now ?");
					System.out.println(i);
					if (i == -1 || i == 2) {
						System.exit(0);
					} else if (i == 1) {
						String[] options = {"well then, i've changed my mind !", "yeah whatever, I'm going my rule !", "TL:DR"};
						int y = JOptionPane.showOptionDialog(this, "slve.init is a really small file although necessary for super lama video editor. \n if you press \"no\" in fear of not being able to erase it once you will not have any use of it anymore (and super lama video editor),\n you have to know that the file is (or will be) right next to super lama video editor's luncher, making it easy to erase.\n the file is not installed or copy in any part of your computer or your system", "a long warrning text that nobody read", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
						if (y == 0) {
							continue A;
						} else if (y == 1) {
							System.exit(0);
						} else if (y == 2) {
							JOptionPane.showOptionDialog(this, "Really ??? ya don't want to read ? you really wants to play that game with me?", "WHAT ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"yes","no","whatever"}, "yes");
							JOptionPane.showOptionDialog(this, "Uuuuugh, i quit ....", "you know what ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"i'll","just","restart :)"}, "yes");
							System.exit(255);
						}
					}
				}
				file = new RandomAccessFile(test, "rw");
				break;
			} while (true);
		} catch (FileNotFoundException e) {	e.printStackTrace();}
		
		setLayout(new FlowLayout());
		
		showConsole = new JCheckBox("show console while Super lama video editor is loading");
		showConsole.setMnemonic('c');
		add(showConsole);
		
		renderDefOutput = new JTextField("set render output path");
		renderDefOutput.setToolTipText("<html>where does super lama video editor when you want to save ?<br/>put \"~\" if you want to use your \"users\" file (depends of your OS)</html>");
		renderDefOutput.setPreferredSize(new Dimension(getWidth() - 200, 20));
		add(renderDefOutput);
		
		JButton browser1 = new JButton("...");
		browser1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FileBrowser(new BrowserActions() {
					
					@Override
					public void done(String path) {
						renderDefOutput.setText(path);
					}
					
					@Override
					public void close(String path) {}
				}).go(true, "I'm in the folder I like",false);
			}
		});
		add(browser1);
		
		imgSelPath = new JTextField("open path for the image selector");
		imgSelPath.setToolTipText("<html>where the file selector should start when you open it ? <br/>put \"~\" if you want to use your \"users\" file (depends of your OS)</html>");
		imgSelPath.setPreferredSize(new Dimension(getWidth() - 200, 20));
		add(imgSelPath);
		
		JButton browser2 = new JButton("...");
		browser2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new FileBrowser(new BrowserActions() {
					
					@Override
					public void done(String path) {
						imgSelPath.setText(path);
					}
					
					@Override
					public void close(String path) {}
				}).go(true, "I'm in the folder I like",false);
			}
		});
		add(browser2);
		
		pause = new JCheckBox("do a pause before super lama video editor end reading the init script, waiting for you to press OK or ENTER");
		pause.setMnemonic('p');
		add(pause);
		
		okAndClose = new JButton("OK, apply and close");
		okAndClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (writeInit()) {
					try {
						file.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.exit(0);
				}
			}
		});
		okAndClose.setPreferredSize(new Dimension(getWidth() - 50, 35));
		add(okAndClose);
		
		//loading modules for slve
		try {
			System.out.println("loading modules");
			URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
			URL jar = url;
			ZipInputStream zip = new ZipInputStream(jar.openStream());
			String lastModNameFound="";
			while(true) {
				ZipEntry e = zip.getNextEntry();
				if (e == null)
					break;
				String name = e.getName();
				if (name.startsWith("mod/") && name.length() > 4) {
					name = name.substring(4);
					name = name.substring(0, name.indexOf(File.separator));
					if (!lastModNameFound.equals(name)) {
						lastModNameFound = name;
						modulesFound.add(name);
						modulesFoundCheck.add(new JCheckBox(name));
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		modsWindowUp = new JButton("mods");
		modsWindowUp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new JFrame() {
					private static final long serialVersionUID = 1L;

					public void go () {
						setLayout(new FlowLayout());
						setBounds(0, 0, 800, 800);
						ButtonGroup group = new ButtonGroup();
						for (int i = 0; i < modulesFound.size();i++) {
							MyJRadioButton radio = new MyJRadioButton(i);
							radio.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									entryMod = ((MyJRadioButton)e.getSource()).i;
								}
							});
							group.add(radio);
							add(radio);
							add(modulesFoundCheck.get(i));
						}
						setVisible(true);
					}
				}.go();
			}
		});
		add(modsWindowUp);
		
		init();
		setVisible(true);
	}
	
	private class MyJRadioButton extends JRadioButton {
		private static final long serialVersionUID = 1L;
		public int i = 0;
		
		public MyJRadioButton (int i) {
			this.i = i;
		}
	}
	
	public boolean writeInit () {
		done = false;
		
		System.out.println("removing initme");
		File initmaker = new File (slvePath + "initme");
		if (initmaker.exists()) initmaker.delete();
		initmaker = new File (slvePath + "initme.txt");
		if (initmaker.exists()) initmaker.delete();
		System.out.println("write init");
		//checking if everything went right about image selector's and render's default path
		if (!( new File(imgSelPath.getText()).isDirectory()) && !(imgSelPath.getText().equals("~"))) {
			imgSelPath.setBackground(Color.red);
			return false;
		} else {
			imgSelPath.setBackground(new JTextField().getBackground());
		}
		if (!( new File (renderDefOutput.getText()).isDirectory()) && !(renderDefOutput.getText().equals("~"))) {
			renderDefOutput.setBackground(Color.red);
			return false;
		} else {
			renderDefOutput.setBackground(new JTextField().getBackground());
		}
		
		//writing the file
		try {
			file.close();
			new File (slvePath + "./slve.init").delete();
			file = new RandomAccessFile (new File (slvePath + "./slve.init"), "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
		write ("#this script fire right before super lama video even open a window, its goal is to give to slve");
		write ("#every pieces of information he needs and your personal settings");
		write ("#**********thank you and have a nice day :-)    ~Serge the lama");
		//setup cmd
		for (int i = 0; i < modulesFound.size();i++) {
			if (modulesFoundCheck.get(i).isSelected())
				write ("loadmod " + modulesFound.get(i));
		}
		write ("mod " + modulesFound.get(entryMod));
		write ("echo \"hello world !\"");
		write ("command.prompt set.size 400 400\ncommand.prompt set.position 0 0\ncommand.prompt set.title \"hello, we are loading\"");
		if (showConsole.isSelected()) {
			write ("command.prompt visible");
		} 
		if (imgSelPath.getText().equals ("~")) {
			write ("image.selector default.path \"" + System.getProperty("user.home") + "/\"");
		} else {
			write ("image.selector default.path \"" + imgSelPath.getText()+"\"");
		}
		write ("echo \"set up render output to " + renderDefOutput.getText() + "\"");
		if (renderDefOutput.getText().equals("~")) {
			write ("render default.output \"" + System.getProperty("user.home")+"/\"");
		} else {
			write ("render default.output \"" + renderDefOutput.getText()+"\"");
		}
		write ("main set.title \"super lama video editor\"\nmain set.size 900 600\nmain set.position 0 100");
		write ("echo \"main setup done\"");
		write ("outline set.title \"outline\"\noutline set.size 400 200\noutline set.position 900 130");
		write ("echo \"outline setup done\"");
		write ("timeline set.title \"timeline\"\ntimeline set.size 1300 100\ntimeline set.position 0 0");
		write ("echo \"timeline window setup done\"");
		write ("item.options set.title \"item option\"\nitem.options set.size 400 200\nitem.options set.position 900 360");
		write ("echo \"item option dialog setup done\"");
		write ("echo \"all done, ready to start\"");
		if (pause.isSelected()) {
			write ("pause \"ready to start\"");
		}
		try {
			file.write(new String ("#:)").getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		boolean done = true;
		return true;
	}
	
	public void write (String str) {
		str += "\n";
		try {
			file.write(str.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void init () {
		try {
			file.seek(0);
			String str = file.readLine();
			while (str != null) {
				if (str.equals("command.prompt visible")) {
					showConsole.setSelected(true);
				}
				else if (str.startsWith("image.selector default.path \"")) {
					imgSelPath.setText(str.substring(29, str.length()-1));
				}
				else if (str.startsWith("image.selector default.path ")) {
					renderDefOutput.setText(str.substring(28));
				}
				else if (str.startsWith("render default.output \"")) {
					renderDefOutput.setText(str.substring(23, str.length()-1));
				}
				else if (str.startsWith("render default.output ")) {
					renderDefOutput.setText(str.substring(22));
				}
				else if (str.startsWith("pause")) {
					pause.setSelected(true);
				}
				str = file.readLine();
			}
		} catch (IOException e) {	e.printStackTrace();
		}
	}
	

	@Override
	public void windowActivated(WindowEvent arg0) {}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		try {
			file.close();
			if (done) {
				new File ("slve.init").delete();
			}
			System.out.println("window closed");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("file.close failed");
		}
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {}

	@Override
	public void windowDeiconified(WindowEvent arg0) {}

	@Override
	public void windowIconified(WindowEvent arg0) {}

	@Override
	public void windowOpened(WindowEvent arg0) {}

}
