package tools;

import items.ImageItem;
import items.TextItem;

import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import start.MainWindow;
import start.Start;
import tools.SourceWindow.SourceActions;
import exceptions.NoItemFoundException;

public class ItemOption extends JFrame implements ComponentListener{
	ArrayList<JButton> allButtons = new ArrayList<JButton>();
	ArrayList<JSlider> allSliders = new ArrayList<JSlider>();
	ArrayList<JTextField> allTextFields = new ArrayList<JTextField>();
	ArrayList<JCheckBox> allCheckbox = new ArrayList<JCheckBox>();
	JSlider js = new JSlider(); //adding rotation slider
	JCheckBox jcb = new JCheckBox();
	boolean b[][] = {{false, false}};
	int ROTATION_FIELD = -1, POSX_FIELD = -1, POSY_FIELD = -1, WIDTH_FIELD = -1, HEIGHT_FIELD = -1, STRINGTEXT_FIELD = -1, FONTSIZE_FIELD = -1;
	int numberOfField;
	
	public void itemOptions () {
		setTitle("options");
	}
	
	public void GO () {
		setLayout(new FlowLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addComponentListener(this);
		setVisible(true);
	}
	
	@Override
	public void setLocation (int x, int y) {
		super.setLocation(x, y);
		System.out.println(x + " " + y);
	}
	
	public void addTextField (JTextField comp) {
		super.add(comp);
		allTextFields.add(comp);
		numberOfField ++;
	}
	
	public void loadOptions () {
		getContentPane().removeAll();
		allButtons.clear();
		allSliders.clear();
		allTextFields.clear();
		allCheckbox.clear();
		
		numberOfField = 0;
		ROTATION_FIELD = -1;POSX_FIELD = -1; POSY_FIELD = -1; WIDTH_FIELD = -1; HEIGHT_FIELD = -1; STRINGTEXT_FIELD = -1; FONTSIZE_FIELD = -1;
		
		try {
			TextItem text = (TextItem) MainWindow.getSelectedItem();
			if (MainWindow.getSelectedItem().getId() == 2) {
				JTextField jtf = new JTextField();
				jtf.setText(text.getText());
				jtf.setPreferredSize(new Dimension(100, 20));
				jtf.getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void changedUpdate(DocumentEvent arg0) {}
					@Override
					public void insertUpdate(DocumentEvent arg0) {update();}
					@Override
					public void removeUpdate(DocumentEvent arg0) {update();}
					
					public void update () {
						TextItem item;
						try {
							item = (TextItem) MainWindow.getSelectedItem();
							item.setText(allTextFields.get(STRINGTEXT_FIELD).getText());
							item.reload();
						} catch (ArrayIndexOutOfBoundsException | NoItemFoundException e) {
							e.printStackTrace();
						}
					}
					
				});
				STRINGTEXT_FIELD = numberOfField;
				addTextField(jtf);
				
				jtf = new JTextField();
				jtf.setText(text.getFontSizeFormula());
				jtf.setPreferredSize(new Dimension(100, 20));
				jtf.getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void changedUpdate(DocumentEvent arg0) {}
					@Override
					public void insertUpdate(DocumentEvent arg0) {update ();}
					@Override
					public void removeUpdate(DocumentEvent arg0) {update ();}
					
					public void update () {
						TextItem item;
						try {
							item = (TextItem) MainWindow.getSelectedItem();
							item.setFontSizeFormula(allTextFields.get(FONTSIZE_FIELD).getText());
							item.reload();
						} catch (ArrayIndexOutOfBoundsException | NoItemFoundException e) {
							e.printStackTrace();
						}
					}
					
				});
				FONTSIZE_FIELD = numberOfField;
				addTextField(jtf);
			}
		} catch (NoItemFoundException | ArrayIndexOutOfBoundsException | ClassCastException exc) {}
		
		
		if (b[0][0]) {
			try {
				JTextField jtf = new JTextField();
				jtf.setToolTipText("set rotation");
				jtf.setText(MainWindow.getSelectedItem().getRotationFormula());
				jtf.setPreferredSize(new Dimension(getWidth() - 20, 20));
				jtf.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void removeUpdate(DocumentEvent arg0) {
						updateStat();
					}
					@Override
					public void insertUpdate(DocumentEvent arg0) {
						updateStat();
					}
					@Override
					public void changedUpdate(DocumentEvent arg0) {}

					public void updateStat () {
						try {
							if (MainWindow.getSelectedItem().setRotationFormula(allTextFields.get(ROTATION_FIELD).getText())) {
								allTextFields.get(ROTATION_FIELD).setBackground(Color.white);
							} else {
								allTextFields.get(ROTATION_FIELD).setBackground(Color.red);
							}
						} catch (NoItemFoundException e) {

						}
					}
				});
				ROTATION_FIELD = numberOfField;
				addTextField(jtf); //don't worry, everything's fine with this function, look above
			} catch (NoItemFoundException | ArrayIndexOutOfBoundsException e) {

			}
		} else {
			JSlider js = new JSlider ();
			js.setMinimum(-360);
			js.setMaximum(360);
			js.setValue(0);
			js.setPreferredSize(new Dimension(getWidth() - 10, 20));
			js.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					try {
						MainWindow.getSelectedItem().setRotation(((JSlider) e.getSource()).getValue());
					} catch (NoItemFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			add(js);
			allSliders.add(js);
		}
		
		if (b[0][1]) {
			try {
				JTextField jtf = new JTextField();
				jtf.setToolTipText("set position on X axis");
				jtf.setText(MainWindow.getSelectedItem().getPosXFormula());
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100, 20));
				jtf.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void removeUpdate(DocumentEvent arg0) {
						updateStat();
					}
					@Override
					public void insertUpdate(DocumentEvent arg0) {
						updateStat();
					}
					@Override
					public void changedUpdate(DocumentEvent arg0) {}

					public void updateStat () {
						try {
							if (MainWindow.getSelectedItem().setPosXFormula(allTextFields.get(POSX_FIELD).getText())) {
								allTextFields.get(POSX_FIELD).setBackground(Color.white);
							} else {
								allTextFields.get(POSX_FIELD).setBackground(Color.red);
							}
						} catch (NoItemFoundException e) {

						}
					}
				});
				POSX_FIELD = numberOfField;
				addTextField(jtf); //don't worry, everything's fine with this function, look above
			} catch (NoItemFoundException | ArrayIndexOutOfBoundsException e) {

			}
			
			try {
				JTextField jtf = new JTextField();
				jtf.setToolTipText("set position on Y axis");
				jtf.setText(MainWindow.getSelectedItem().getPosYFormula());
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100, 20));
				jtf.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void removeUpdate(DocumentEvent arg0) {
						updateStat();
					}
					@Override
					public void insertUpdate(DocumentEvent arg0) {
						updateStat();
					}
					@Override
					public void changedUpdate(DocumentEvent arg0) {}

					public void updateStat () {
						try {
							if (MainWindow.getSelectedItem().setPosYFormula(allTextFields.get(POSY_FIELD).getText())) {
								allTextFields.get(POSY_FIELD).setBackground(Color.white);
							} else {
								allTextFields.get(POSY_FIELD).setBackground(Color.red);
							}
						} catch (NoItemFoundException e) {

						}
					}
				});
				POSY_FIELD = numberOfField;
				addTextField(jtf); //don't worry, everything's fine with this function, look above
			} catch (NoItemFoundException | ArrayIndexOutOfBoundsException e) {

			}
		}
		
		try {
			JTextField jtf = new JTextField();
			jtf.setToolTipText("set width");
			jtf.setText(MainWindow.getSelectedItem().getWidthFormula());
			jtf.setPreferredSize(new Dimension(getWidth()/2 - 100, 20));
			jtf.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					updateStat();
				}
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					updateStat();
				}
				@Override
				public void changedUpdate(DocumentEvent arg0) {}
				
				public void updateStat () {
					try {
						if (MainWindow.getSelectedItem().setWidthFormula(allTextFields.get(WIDTH_FIELD).getText())) {
							allTextFields.get(WIDTH_FIELD).setBackground(Color.white);
						} else {
							allTextFields.get(WIDTH_FIELD).setBackground(Color.red);
						}
					} catch (NoItemFoundException  e) {

					}
				}
			});
			WIDTH_FIELD = numberOfField;
			addTextField(jtf); //don't worry, everything's fine with this function, look above
		} catch (NoItemFoundException | ArrayIndexOutOfBoundsException e) {
			
		}
		
		try {
			JTextField jtf = new JTextField();
			jtf.setToolTipText("set height");
			jtf.setText(MainWindow.getSelectedItem().getHeightFormula());
			jtf.setPreferredSize(new Dimension(getWidth()/2 - 100, 20));
			jtf.getDocument().addDocumentListener(new DocumentListener() {
				@Override
				public void removeUpdate(DocumentEvent arg0) {
					updateStat();
				}
				@Override
				public void insertUpdate(DocumentEvent arg0) {
					updateStat();
				}
				@Override
				public void changedUpdate(DocumentEvent arg0) {}
				
				public void updateStat () {
					try {
						if (MainWindow.getSelectedItem().setHeightFormula(allTextFields.get(HEIGHT_FIELD).getText())) {
							allTextFields.get(HEIGHT_FIELD).setBackground(Color.white);
						} else {
							allTextFields.get(HEIGHT_FIELD).setBackground(Color.red);
						}
					} catch (NoItemFoundException | ArrayIndexOutOfBoundsException e) {
						
					}
				}
			});
			HEIGHT_FIELD = numberOfField;
			addTextField(jtf);
		} catch (NoItemFoundException | ArrayIndexOutOfBoundsException e) {
			
		}
		
		
		try {
			if (MainWindow.getSelectedItem().getId() == 1) {
				JButton jb = new JButton("change image");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Start.getSourceWindow().active(new SourceActions() {
							
							@Override
							public void userChooseImage(SourceWindow source, JFrame jf) {
								try {
									((ImageItem) MainWindow.getSelectedItem()).setImage(source.getSelectedItemAsImg().preview());
								} catch (ArrayIndexOutOfBoundsException
										| NoItemFoundException e) {
									e.printStackTrace();
								}
								jf.dispose();
							}
							
							@Override
							public void userChooseFolder(SourceWindow source, JFrame jf) {
								source.getSelectedItemAsFolder().toggleOpen();
							}
						});
					}
				});
				add(jb);
			}
		} catch (ArrayIndexOutOfBoundsException | NoItemFoundException e1) {
			e1.printStackTrace();
			System.err.println("error catched no problemo :)");
		}
		
		JButton jb = new JButton ("delete");
		jb.setPreferredSize(new Dimension(getWidth() - 10, 20));
		jb.setBackground(Color.red);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainWindow.removeItemByName(MainWindow.getSelectedItem().getName());
					System.out.println("index size:" + MainWindow.getIndex().size());
				} catch (NoItemFoundException | ArrayIndexOutOfBoundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		add(jb);
		allButtons.add(jb);
		
		jb = new JButton("manage options");
		jb.setBackground(Color.blue);
		jb.setPreferredSize(new Dimension(getWidth()-10,20));
		jb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ChooseOptionsType();
			}
		});
		add(jb);
		allButtons.add(jb);
		
		 revalidate();
	}

	public boolean getOptionType (int dim1, int dim2) {
		return b[dim1][dim2];
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void componentResized(ComponentEvent e) {
		loadOptions();
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//-------------------------
	//inner classes
	//-------------------------
	private class ChooseOptionsType extends JFrame {
		public ChooseOptionsType () {
			setBounds(0, 0, 400, 600);
			setTitle("tools type");
			setVisible(true);
			setLayout(new FlowLayout());
			Checkbox cb = new Checkbox("enable text rotation");
			cb.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					b[0][0] = e.getStateChange() == ItemEvent.SELECTED;
				}
			});
			add (cb);
			cb = new Checkbox("enable text position");
			cb.addItemListener(new ItemListener() {

				@Override
				public void itemStateChanged(ItemEvent e) {
					b[0][1] = e.getStateChange() == ItemEvent.SELECTED;
				}
			});
			add (cb);
			JButton jb = new JButton("reload");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					loadOptions();
				}
			});
			add(jb);
		}
	}
	

	/*
	private class TextFieldTextSizeDocument implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			updateStat();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			updateStat();
		}
		
		public void updateStat () {
			TextItem txti = MainWindow.getSelectedItem();
			String str = txti.calculeVariable(allTextFields.get(FONTSIZE_FIELD).getText());
			int res;
			if (str.equals("!") || str.equals("0")) {
				allTextFields.get(FONTSIZE_FIELD).setBackground(Color.red);
			} else {
				res = (int) Double.parseDouble(str);
				System.out.println("[debug] res"+res +"str" + str);
				allTextFields.get(FONTSIZE_FIELD).setBackground(Color.white);
				txti.setFontSizeFormulaNoCache(allTextFields.get(FONTSIZE_FIELD).getText());
				txti.setFontSize(res);
				System.out.println("[DEBUG] txti :"+ txti.getText() + " str " + str + " FONTSIZE_FIELD " + FONTSIZE_FIELD);
				MainWindow.getSelectedItem().reload();
			}
		}
		
	}
	
	private class TextFieldTextDocument implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			MainWindow.getSelectedItem().setText(allTextFields.get(STRINGTEXT_FIELD).getText());
			MainWindow.getSelectedItem().reload();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}*/

}