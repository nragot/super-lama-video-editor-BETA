package inittools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainWindow extends JFrame implements WindowListener{
	JCheckBox showConsole;
	JTextField renderDefOutput;
	JTextField imgSelPath;
	JCheckBox pause;
	JButton okAndClose;
	JButton help;
	RandomAccessFile file ;
	
	boolean done;
	
	public MainWindow () {
		setBounds (0,0,850,300);
		setTitle ("init script");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		done = true;
		
		try {
			A:do {
				File test = new File ("./slve.init");
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
		renderDefOutput.setPreferredSize(new Dimension(getWidth() - 50, 20));
		add(renderDefOutput);
		
		imgSelPath = new JTextField("open path for the image selector");
		imgSelPath.setToolTipText("<html>where the file selector should start when you open it ? <br/>put \"~\" if you want to use your \"users\" file (depends of your OS)</html>");
		imgSelPath.setPreferredSize(new Dimension(getWidth() - 50, 20));
		add(imgSelPath);
		
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
		
		help = new JButton("help me ! I don't now what all this mean :(");
		help.setPreferredSize(new Dimension(getWidth() - 50, 50));
		help.setMnemonic('h');
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {new HelpWindow();}
		});
		add(help);
		
		init();
		setVisible(true);
	}
	
	public boolean writeInit () {
		done = false;
		
		System.out.println("removing initme");
		File initmaker = new File ("initme");
		if (initmaker.exists()) initmaker.delete();
		System.out.println("write init");
		//checking if everything went right about image selector's and render's default path
		if (!( new File(imgSelPath.getText()).isDirectory()) && !(imgSelPath.getText().equals("~"))) {
			imgSelPath.setBackground(Color.red);
			return false;
		} else {
			imgSelPath.setBackground(new JTextField().getBackground());
			System.out.println("img sel path is ok");
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
			new File ("./slve.init").delete();
			file = new RandomAccessFile (new File ("./slve.init"), "rw");
		} catch (IOException e) {
			e.printStackTrace();
		}
		write ("#this script fire right before super lama video even open a window, its goal is to give to slve");
		write ("#every piece of information he needs and your personal settings");
		write ("#**********thank you and have a nice day :-)    ~Serge the lama");
		//setup cmd
		write ("command.prompt set.size 400 400\ncommand.prompt set.position 0 0\ncommand.prompt set.title \"hello, we are loadig\"");
		if (showConsole.isSelected()) {
			write ("command.Prompt visible");
		}
		if (imgSelPath.getText().equals ("~")) {
			write ("image.selector default.path \"" + System.getProperty("user.home") + "/\"");
		} else {
			write ("image.selector default.path \"" + imgSelPath.getText()+"\"");
		}
		if (renderDefOutput.getText().equals("~")) {
			write ("render default.output \"" + System.getProperty("user.home")+"/\"");
		} else {
			write ("render default.output \"" + renderDefOutput.getText()+"\"");
		}
		write ("main set.title \"super lama video editor\"\nmain set.size 900 600\nmain set.position 0 100");
		write ("outline set.title \"outline\"\noutline set.size 400 200\noutline set.position 900 100");
		write ("timeline set.title \"doctor whoooooooo\"\ntimeline set.size 1300 100\ntimeline set.position 0 0");
		write ("item.options set.title \":D\"\nitem.options set.size 400 200\nitem.options set.position 900 300");
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
	
	public class HelpWindow extends JFrame{

		private static final long serialVersionUID = 1L;
		
		ImageIcon console_Help = new ImageIcon (getClass().getResource("/console_help.png")),
				head_help = new ImageIcon(getClass().getResource("/help_head.png"));
		
		public HelpWindow () {
			setBounds(50, 50, 650, 800);
			setVisible(true);
			setContentPane(new MyPanel());
		}
		
		public class MyPanel extends JPanel{
			private static final long serialVersionUID = 1L;

			public void paintComponent (Graphics g) {
				g.drawImage(head_help.getImage(), 0, 0, this);
				g.drawImage(console_Help.getImage(), 5, 420, this);
				g.setFont(new Font("Dialog", Font.PLAIN, 20));
				g.drawString("the \"console\" (or command prompt) is a window that, as long ", 5, 850);
				g.drawString("you check the checkbox \" show console\" that will describe" , 5, 880);
				g.drawString("what is going on. if you don't know what it is or you don't", 5, 910);
				g.drawString("care let it uncheck", 5 , 940);
				System.out.println(" repaint i:" );
			}
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
