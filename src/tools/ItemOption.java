package tools;


import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import miscelanious.SlveDefaultLayout;

import start.BasicLayer;
import start.Start;
import API.SlveFrame;
import exceptions.NoItemFoundException;

public class ItemOption extends SlveFrame implements ComponentListener{

	private static final long serialVersionUID = 1L;
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
		
		Panel panel = new Panel();
		panel.setLayout(new SlveDefaultLayout());
		JButton remove = new JButton();
		remove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					((BasicLayer)(Start.getMainWindow().getSelectedLayer())).getItemList().remove(Start.getMainWindow().getSelectedItem());
				} catch (IndexOutOfBoundsException | NoItemFoundException e1) {
					e1.printStackTrace();
				}
			}
		});
		remove.setText("delete");
		remove.setBackground(Color.red);
		panel.add(remove);
		panel.add(new JSeparator());
		try {
			panel.add(Start.getMainWindow().getSelectedItem().getOption(getWidth(), getHeight()));
			setContentPane (panel);
		} catch ( IndexOutOfBoundsException | NoItemFoundException e) {
			e.printStackTrace();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				revalidate();
			}
		});
		
		/*
		getContentPane().removeAll();
		allButtons.clear();
		allSliders.clear();
		allTextFields.clear();
		allCheckbox.clear();
		
		numberOfField = 0;
		ROTATION_FIELD = -1;POSX_FIELD = -1; POSY_FIELD = -1; WIDTH_FIELD = -1; HEIGHT_FIELD = -1; STRINGTEXT_FIELD = -1; FONTSIZE_FIELD = -1;
		
		try {
			TextItem text = (TextItem) Start.getMainWindow().getSelectedItem();
			if (Start.getMainWindow().getSelectedItem().getId() == 2) {
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
							item = (TextItem) Start.getMainWindow().getSelectedItem();
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
							item = (TextItem) Start.getMainWindow().getSelectedItem();
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
		} catch (NoItemFoundException | IndexOutOfBoundsException | ClassCastException exc) {}
		
		
		if (b[0][0]) {
			try {
				JTextField jtf = new JTextField();
				jtf.setToolTipText("set rotation");
				jtf.setText(Start.getMainWindow().getSelectedItem().getRotationFormula());
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
							if (Start.getMainWindow().getSelectedItem().setRotationFormula(allTextFields.get(ROTATION_FIELD).getText())) {
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
						Start.getMainWindow().getSelectedItem().setRotation(((JSlider) e.getSource()).getValue());
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
				jtf.setText(Start.getMainWindow().getSelectedItem().getPosXFormula());
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
							if (Start.getMainWindow().getSelectedItem().setPosXFormula(allTextFields.get(POSX_FIELD).getText())) {
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
				jtf.setText(Start.getMainWindow().getSelectedItem().getPosYFormula());
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
							if (Start.getMainWindow().getSelectedItem().setPosYFormula(allTextFields.get(POSY_FIELD).getText())) {
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
			jtf.setText(Start.getMainWindow().getSelectedItem().getWidthFormula());
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
						if (Start.getMainWindow().getSelectedItem().setWidthFormula(allTextFields.get(WIDTH_FIELD).getText())) {
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
		} catch (NoItemFoundException | IndexOutOfBoundsException e) {
			
		}
		
		try {
			JTextField jtf = new JTextField();
			jtf.setToolTipText("set height");
			jtf.setText(Start.getMainWindow().getSelectedItem().getHeightFormula());
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
						if (Start.getMainWindow().getSelectedItem().setHeightFormula(allTextFields.get(HEIGHT_FIELD).getText())) {
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
		} catch (NoItemFoundException | IndexOutOfBoundsException e) {
			
		}
		
		
		try {
			if (Start.getMainWindow().getSelectedItem().getId() == 1) {
				JButton jb = new JButton("change image");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						Start.getSourceWindow().active(new SourceActions() {
							
							@Override
							public void userChooseImage(SourceWindow source, JFrame jf) {
								try {
									((ImageItem) Start.getMainWindow().getSelectedItem()).setImage(source.getSelectedItemAsImg().preview());
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
		} catch (IndexOutOfBoundsException | NoItemFoundException e1) {
		}
		
		JButton jb = new JButton ("delete");
		jb.setPreferredSize(new Dimension(getWidth() - 10, 20));
		jb.setBackground(Color.red);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//FIXME: replace me by WAY better methode
				//removed due to maintenance until better methode
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
		
		 revalidate();*/
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
	}

	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		
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
			TextItem txti = Start.getMainWindow().getSelectedItem();
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
				Start.getMainWindow().getSelectedItem().reload();
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
			Start.getMainWindow().getSelectedItem().setText(allTextFields.get(STRINGTEXT_FIELD).getText());
			Start.getMainWindow().getSelectedItem().reload();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}*/

}