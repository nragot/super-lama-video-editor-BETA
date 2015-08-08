package tools;

import items.Item;
import items.ShapeRect;
import items.TextItem;
import items.VideoItem;

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
import java.io.PrintWriter;
import java.io.StringWriter;
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

import exceptions.NoItemFoundException;

import start.MainWindow;

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
	
	public ItemOption () {
		setBounds(1200,600,800,400);
		setTitle("options");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addComponentListener(this);
		setVisible(true);
	}
	
	public void addTextField (JTextField comp) {
		super.add(comp);
		allTextFields.add(comp);
		numberOfField ++;
	}
	/*
	public void loadOptions () {
		getContentPane().removeAll();
		allButtons.clear();
		allSliders.clear();
		allTextFields.clear();
		allCheckbox.clear();
		
		JTextField jtf;
		numberOfField = -1;
		ROTATION_FIELD = -1;POSX_FIELD = -1; POSY_FIELD = -1; WIDTH_FIELD = -1; HEIGHT_FIELD = -1; STRINGTEXT_FIELD = -1; FONTSIZE_FIELD = -1;
		try {
			if (MainWindow.getSelectedItem().getId() == 1) {
				if (!b[0][0]) {
					js.setMaximum(359);
					js.setMinimum(-359);
					js.setToolTipText("Change rotation of the selected Object");
					js.setPreferredSize(new Dimension(getWidth() - 10, 20));
					js.setValue(0);
					js.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							MainWindow.getSelectedImageItem().setRotation(js.getValue());
						}
					});
					allSliders.add(js);
					add(js);
				} else {
					jtf = new JTextField();
					jtf.setToolTipText("set rotation");
					jtf.setText(MainWindow.getSelectedImageItem().getRotationFormula());
					jtf.setPreferredSize(new Dimension(getWidth() - 10, 20));
					jtf.setHorizontalAlignment(JTextField.CENTER);
					jtf.getDocument().addDocumentListener(new DocumentListener() {
						
						@Override
						public void removeUpdate(DocumentEvent arg0) {updateStat();}
						@Override
						public void insertUpdate(DocumentEvent arg0) {updateStat();}
						@Override
						public void changedUpdate(DocumentEvent arg0) {}
						public void updateStat () {
							String str = "";
							try {
								Item item = new Item();
								str = item.calculeVariable(allTextFields.get(ROTATION_FIELD).getText());
								MainWindow.getSelectedImageItem().setRotationFormula(allTextFields.get(ROTATION_FIELD).getText());
							} catch (NumberFormatException e){
								System.err.println("rotate " + str);
							}
						}
					});
					addTextField(jtf);
					ROTATION_FIELD = numberOfField;
					allTextFields.add(jtf);
				}


				jcb.setText("respect ratio");
				jcb.setToolTipText("if the width is change, the height is also change to fit the new size");
				add(jcb);
				allCheckbox.add(jcb);
				
				if (b[0][1]) {
					jtf = new JTextField();
					jtf.setToolTipText("set posX");
					jtf.setText(MainWindow.getSelectedImageItem().getPosXFormula());
					jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
					jtf.getDocument().addDocumentListener(new DocumentListener() {
						@Override
						public void removeUpdate(DocumentEvent e) { updateStat();}
						@Override
						public void insertUpdate(DocumentEvent e) { updateStat();}
						@Override
						public void changedUpdate(DocumentEvent e) {}
						public void updateStat () {
							try {
								MainWindow.getSelectedImageItem().setPosXFormula(allTextFields.get(POSX_FIELD).getText());
							} catch (NumberFormatException e) {
								
							}
						}
					});
					addTextField(jtf);
					POSX_FIELD = numberOfField;
					allTextFields.add(jtf);
					
					jtf = new JTextField();
					jtf.setToolTipText("set posY");
					jtf.setText(MainWindow.getSelectedImageItem().getPosYFormula());
					jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
					jtf.getDocument().addDocumentListener(new DocumentListener() {
						@Override
						public void removeUpdate(DocumentEvent e) { updateStat();}
						@Override
						public void insertUpdate(DocumentEvent e) { updateStat();}
						@Override
						public void changedUpdate(DocumentEvent e) {}
						public void updateStat () {
							try {
								MainWindow.getSelectedImageItem().setPosYFormula(allTextFields.get(POSY_FIELD).getText());
							} catch (NumberFormatException e) {
								
							}
						}
					});
					addTextField(jtf);
					POSY_FIELD = numberOfField;
					allTextFields.add(jtf);
				}

				jtf = new JTextField();
				jtf.setToolTipText("set witdh");
				jtf.setText(MainWindow.getSelectedImageItem().getWidthFormula());
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
				jtf.getDocument().addDocumentListener(new TextFieldWidthDocument());
				addTextField(jtf);
				WIDTH_FIELD = numberOfField;
				allTextFields.add(jtf);

				JTextField jtf2 = new JTextField();
				jtf2.setToolTipText("set height");
				jtf2.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
				jtf2.setText(MainWindow.getSelectedImageItem().getHeightFormula());
				jtf2.getDocument().addDocumentListener(new TextFieldHeightDocument());
				addTextField(jtf2);
				HEIGHT_FIELD = numberOfField;
				allTextFields.add(jtf2);

				JButton jb;
				jb = new JButton("delete");
				jb.setBackground(Color.red);
				jb.setPreferredSize(new Dimension(getWidth()-10,20));
				jb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						for (int i = 0; i < MainWindow.getItemSelection().size (); i++) {
							try {
								MainWindow.removeItemByName(MainWindow.getItem(MainWindow.getIndex().get(MainWindow.getItemSelection().get(i))).getName());
							} catch (NoItemFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						MainWindow.getOutline().refresh();

						loadOptions();
					}
				});
				add(jb);
				allButtons.add(jb);
				
				jb = new JButton("change item type");
				jb.setBackground(Color.blue);
				jb.setPreferredSize(new Dimension(getWidth()-10,20));
				jb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						new ChooseOptionsType(1);
					}
				});
				add(jb);
				allButtons.add(jb);
			} else if (MainWindow.getSelectedItem().getId() == 2) {
				js = new JSlider();
				js.setMaximum(359);
				js.setMinimum(-359);
				js.setToolTipText("Change rotation of the selected Object");
				js.setPreferredSize(new Dimension(getWidth() - 10, 20));
				js.setValue(0);
				js.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						MainWindow.getSelectedTextItem().setRotation(js.getValue());
					}
				});
				allSliders.add(js);
				add(js);

				jtf = new JTextField();
				jtf.setToolTipText("set text");
				jtf.setText(MainWindow.getSelectedTextItem().getText()+"");
				jtf.getDocument().addDocumentListener(new TextFieldTextDocument());
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
				addTextField(jtf);
				STRINGTEXT_FIELD = numberOfField;
				allTextFields.add(jtf);
				
				jtf = new JTextField();
				jtf.setToolTipText("set font size");
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
				jtf.setText(MainWindow.getSelectedTextItem().getFontSize()+"");
				jtf.getDocument().addDocumentListener(new TextFieldTextSizeDocument());
				addTextField(jtf);
				FONTSIZE_FIELD = numberOfField;
				allTextFields.add(jtf);
				
				jtf = new JTextField();
				jtf.setToolTipText("set width");
				jtf.setText(MainWindow.getSelectedTextItem().getWidth()+"");
				jtf.getDocument().addDocumentListener(new TextFieldWidthDocument());
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
				addTextField(jtf);
				WIDTH_FIELD = numberOfField;
				allTextFields.add(jtf);
				
				jtf = new JTextField();
				jtf.setToolTipText("set height");
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
				jtf.setText(MainWindow.getSelectedTextItem().getHeight()+"");
				jtf.getDocument().addDocumentListener(new TextFieldHeightDocument());
				addTextField(jtf);
				HEIGHT_FIELD = numberOfField;
				allTextFields.add(jtf);
				
				JButton jb = new JButton("add a translation keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedTextItem().addKeyFrameTranslate(MainWindow.getTimeLine().getTime(), MainWindow.getSelectedTextItem().getPosXFormula()+"", MainWindow.getSelectedTextItem().getPosYFormula()+"",1);
					}
				});
				add(jb);
				allButtons.add(jb);
				
				jb = new JButton("delete translation Keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedTextItem().deleteKeyFrameTranslationAt(MainWindow.getTimeLine().getTime());
					}
				});
				add(jb);
				allButtons.add(jb);
				
				jb = new JButton("add a rotation keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedTextItem().addKeyFrameRotation(MainWindow.getTimeLine().getTime(), MainWindow.getSelectedTextItem().getRotationFormula());
					}
				});
				add(jb);
				allButtons.add(jb);
				
				jb = new JButton("delete rotation Keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedTextItem().deleteKeyFrameRotationAt(MainWindow.getTimeLine().getTime());
					}
				});
				add(jb);
				allButtons.add(jb);
				
				jb = new JButton("delete");
				jb.setBackground(Color.red);
				jb.setPreferredSize(new Dimension(getWidth()-10,20));
				jb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (MainWindow.getSelectedTextItemNumber() > 0) {
							MainWindow.setSelectedTextItem(MainWindow.getSelectedTextItemNumber() - 1);
							MainWindow.ResolveIndexGap(1, MainWindow.getSelectedImageNumber() + 1);
							MainWindow.getListTextItem().remove(MainWindow.getSelectedTextItemNumber() + 1);
						} else {
							MainWindow.setSelectedItemId(0);
							MainWindow.getListTextItem().remove(MainWindow.getSelectedTextItemNumber());
							MainWindow.getItemSelection().clear();
						}
						MainWindow.getOutline().refresh();

						loadOptions();
					}
				});
				add(jb);
				allButtons.add(jb);
				revalidate ();
			} else if (MainWindow.getSelectedItem().getId() == 3) {
				js = new JSlider();
				js.setMaximum(359);
				js.setMinimum(-359);
				js.setToolTipText("Change rotation of the selected Object");
				js.setPreferredSize(new Dimension(getWidth() - 10, 20));
				js.setValue(0);
				js.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent e) {
						MainWindow.getSelectedVideoItem().setRotation(js.getValue());
					}
				});
				allSliders.add(js);
				add(js);

				jtf = new JTextField();
				jtf.setToolTipText("set witdh");
				jtf.setText(MainWindow.getSelectedVideoItem().getWidth()+"");
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
				jtf.getDocument().addDocumentListener(new DocumentListener() {
					
					@Override
					public void removeUpdate(DocumentEvent arg0) {
						MainWindow.getSelectedVideoItem().setWidth(Integer.parseInt(allTextFields.get(WIDTH_FIELD).getText()));
					}
					@Override
					public void insertUpdate(DocumentEvent arg0) {
						MainWindow.getSelectedVideoItem().setWidth(Integer.parseInt(allTextFields.get(WIDTH_FIELD).getText()));
					}
					@Override
					public void changedUpdate(DocumentEvent arg0) {}
				});
				addTextField(jtf);
				WIDTH_FIELD = numberOfField;
				allTextFields.add(jtf);
				
				JTextField jtf2 = new JTextField();
				jtf2.setToolTipText("set height");
				jtf2.setPreferredSize(new Dimension(getWidth()/2 - 100,20));
				jtf2.setText(MainWindow.getSelectedVideoItem().getHeight()+"");
				jtf2.getDocument().addDocumentListener(new DocumentListener() {
					@Override
					public void removeUpdate(DocumentEvent e) {
						MainWindow.getSelectedVideoItem().setHeight(Integer.parseInt(allTextFields.get(HEIGHT_FIELD).getText()));
					}
					@Override
					public void insertUpdate(DocumentEvent e) {
						MainWindow.getSelectedVideoItem().setHeight(Integer.parseInt(allTextFields.get(HEIGHT_FIELD).getText()));
					}
					
					@Override
					public void changedUpdate(DocumentEvent e) {}
				});
				addTextField(jtf2);
				HEIGHT_FIELD = numberOfField;
				allTextFields.add(jtf2);
				
				JButton jb;
				jb = new JButton("cut");
				jb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						VideoItem vd = MainWindow.getSelectedVideoItem();
						vd.setStop(TimeLine.getTime());
						MainWindow.addVideoItem(new VideoItem(vd.getPath(), vd.getName() + "~", TimeLine.getTime(), TimeLine.getTime() - vd.getBorn()));
						MainWindow.getOutline().refresh();
						System.out.println("hello world");
					}
				});
				allButtons.add(jb);
				add(jb);
				
				jb = new JButton("delete");
				jb.setBackground(Color.red);
				jb.setPreferredSize(new Dimension(getWidth()-10,20));
				jb.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedVideoItem().stopStream();
						if (MainWindow.getSelectedVideoItemNumber() > 0) {
							MainWindow.setSelectedVideoItem(MainWindow.getSelectedVideoItemNumber() - 1);
							MainWindow.ResolveIndexGap(1, MainWindow.getSelectedVideoItemNumber() + 1);
							MainWindow.getListVideo().remove(MainWindow.getSelectedVideoItemNumber() + 1);
						} else {
							MainWindow.setSelectedItemId(0);
							MainWindow.getListVideo().remove(MainWindow.getSelectedVideoItemNumber());
							MainWindow.getItemSelection().clear();
						}
						MainWindow.getOutline().refresh();

						loadOptions();
					}
				});
				allButtons.add(jb);
				add(jb);
				
				revalidate();
			} else if (MainWindow.getSelectedItem().getId() == 401) {
				if (!b[0][0]) {
					js.setMaximum(359);
					js.setMinimum(-359);
					js.setToolTipText("Change rotation of the selected Object");
					js.setPreferredSize(new Dimension(getWidth() - 10, 20));
					js.setValue(0);
					js.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							MainWindow.getSelectedShape().setRotation(js.getValue());
							System.out.println("[debug] rotation" + MainWindow.getSelectedShape().getRotation() + ":"+js.getMaximum());
						}
					});
					allSliders.add(js);
					add(js);
				} else {
					jtf = new JTextField();
					jtf.setToolTipText("set rotation");
					jtf.setText(MainWindow.getSelectedShape().getRotationFormula());
					jtf.setPreferredSize(new Dimension(getWidth() - 10, 20));
					jtf.setHorizontalAlignment(JTextField.CENTER);
					jtf.getDocument().addDocumentListener(new DocumentListener() {
						
						@Override
						public void removeUpdate(DocumentEvent arg0) {updateStat();}
						@Override
						public void insertUpdate(DocumentEvent arg0) {updateStat();}
						@Override
						public void changedUpdate(DocumentEvent arg0) {}
						public void updateStat () {
							String str = "";
							try {
								Item item = new Item();
								str = item.calculeVariable(allTextFields.get(ROTATION_FIELD).getText());
								MainWindow.getSelectedImageItem().setRotationFormula(allTextFields.get(ROTATION_FIELD).getText());
							} catch (NumberFormatException e){
								System.err.println("rotate " + str);
							}
						}
					});
					addTextField(jtf);
					ROTATION_FIELD = numberOfField;
					allTextFields.add(jtf);
				}
				
				{
					final JSlider js2 = new JSlider();
					js2.setMaximum(MainWindow.getSelectedShape().getWidth());
					js2.setMinimum(0);
					js2.setToolTipText("change bound");
					js2.setPreferredSize(new Dimension(getWidth() - 30, 20));
					js2.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							ShapeRect sp = (ShapeRect) MainWindow.getSelectedShape();
							sp.setRoundBoundX(js2.getValue());
							sp.reload(); 
							System.out.println("[debug] bound" + js2.getValue());
						}
					});
					allSliders.add(js2);
					add(js2);
				}
				{
					final JSlider js2 = new JSlider();
					js2.setMaximum(MainWindow.getSelectedShape().getHeight());
					js2.setMinimum(0);
					js2.setToolTipText("change bound");
					js2.setPreferredSize(new Dimension(getWidth() - 30, 20));
					js2.addChangeListener(new ChangeListener() {

						@Override
						public void stateChanged(ChangeEvent e) {
							ShapeRect sp = (ShapeRect) MainWindow.getSelectedShape();
							sp.setRoundBoundY(js2.getValue());
							sp.reload(); 
							System.out.println("[debug] bound" + js2.getValue());
						}
					});
					allSliders.add(js2);
					add(js2);
				}
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			System.err.print("Index out of bounds exception ");
			StringWriter stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			System.out.println(stringWriter.toString());
		}
		revalidate();
	}*/
	
	public void loadOptions () {
		getContentPane().removeAll();
		allButtons.clear();
		allSliders.clear();
		allTextFields.clear();
		allCheckbox.clear();
		
		numberOfField = 0;
		ROTATION_FIELD = -1;POSX_FIELD = -1; POSY_FIELD = -1; WIDTH_FIELD = -1; HEIGHT_FIELD = -1; STRINGTEXT_FIELD = -1; FONTSIZE_FIELD = -1;
		
		if (b[0][0]) {
			try {
				JTextField jtf = new JTextField();
				jtf.setToolTipText("set rotation");
				jtf.setText(MainWindow.getSelectedItem().getRotationFormula());
				jtf.setPreferredSize(new Dimension(getWidth() - 10, 20));
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
		
		JButton jb = new JButton ("delete");
		jb.setPreferredSize(new Dimension(getWidth() - 10, 20));
		jb.setBackground(Color.red);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainWindow.removeItemByName(MainWindow.getSelectedItem().getName());
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