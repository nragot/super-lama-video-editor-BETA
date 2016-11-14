/* 
 * Copyright 2016 nathan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package inittools;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import API.Mod;

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
	ArrayList<Mod> modulesFound = new ArrayList<>();
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
                                            switch (y) {
                                                case 0:
                                                    continue;
                                                case 1:
                                                    System.exit(0);
                                                case 2:
                                                    JOptionPane.showOptionDialog(this, "Really ??? ya don't want to read ? you really wants to play that game with me?", "WHAT ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"yes","no","whatever"}, "yes");
                                                    JOptionPane.showOptionDialog(this, "Uuuuugh, i quit ....", "you know what ?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, new String[]{"i'll","just","restart :)"}, "yes");
                                                    System.exit(255);
                                                default:
                                                    break;
                                            }
					}
				}
				file = new RandomAccessFile(test, "rw");
				break;
			} while (true);
		} catch (FileNotFoundException e) {	e.printStackTrace();}
		
		//load modules for slve
		try {
			System.out.println("loading modules");
			URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
			URL jar = url;
			ZipInputStream zip = new ZipInputStream(jar.openStream());
			String lastModNameFound="";
			
			SlveInitToolPanel pan = new SlveInitToolPanel();
			pan.getEastButton().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					writeInit();
				}
			});
			
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
						
						Mod mod;
						try {
							Class<?> clazz = Class.forName("mod."+name+".start.start");
							Constructor<?> ctor = clazz.getConstructor(String.class.getClasses());
							mod = (Mod) ctor.newInstance();
							mod.setLocation(name);
							
							ModBox modbox = new ModBox(mod.getName());
							mod.getModInitOptions(modbox);
							pan.add(modbox);
							modulesFound.add(mod);
						} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
			
			setContentPane(pan);
		} catch (IOException e) {
			e.printStackTrace();
		}
		setVisible(true);
	}
	
	public boolean writeInit () {
		done = false;
		
		//checking
		boolean failed = false;
		for (Mod mod : modulesFound) {
			if (!mod.checkBeforeWritingInit())
				failed = true;
		}
		if (failed) return false;
		
		System.out.println("removing initme");
		File initmaker = new File (slvePath + "initme");
		if (initmaker.exists()) initmaker.delete();
		initmaker = new File (slvePath + "initme.txt");
		if (initmaker.exists()) initmaker.delete();
		System.out.println("now writing init");
		
		//remove slve.init
		File byebye = new File (slvePath+"/slve.init");
		if (byebye.exists()) byebye.delete();
		try {
			file = new RandomAccessFile(slvePath + "/slve.init", "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		
		write ("#that file auto fire from slve to give every pieces of informations we would need from you");
		
		//first turn writing
		for (Mod mod : modulesFound) {
			System.out.println("now writing mod :" + mod.getName());
			if (!mod.isActivated()) continue; //skip mod if not activated
			String line = mod.getName();
			for (int i = 0; i < (32 - mod.getName().length())/2;i++) {
				line = " " + line + " ";
			}
			line = "|" + line + "|";
			write ("#+--------------------------------+");
			write ("#"+line);
			write ("#+--------------------------------+");
			write ("insmod " + mod.getLocation() + "\nmod " + mod.getName());
			
			for (String str : mod.getModInitParameters())
				write(str);
		}
		
		for (Mod mod : modulesFound) {
			write ("#after job : " + mod.getName());
			for (String str : mod.getModInitParametersAfterJob())
				write(str);
		}
		dispose();
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
