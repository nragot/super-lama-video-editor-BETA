package tools;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
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

import start.MainWindow;

public class ItemOption extends JFrame implements ComponentListener{
	ArrayList<JButton> allButtons = new ArrayList<JButton>();
	ArrayList<JSlider> allSliders = new ArrayList<JSlider>();
	ArrayList<JTextField> allTextFields = new ArrayList<JTextField>();
	ArrayList<JCheckBox> allCheckbox = new ArrayList<JCheckBox>();
	JSlider js = new JSlider(); //adding rotation slider
	JCheckBox jcb = new JCheckBox();
	
	public ItemOption () {
		setBounds(1200,600,800,400);
		setTitle("options");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addComponentListener(this);
		setVisible(true);
	}
	
	public void loadOptions () {
		getContentPane().removeAll();
		allButtons.clear();
		allSliders.clear();
		allTextFields.clear();
		allCheckbox.clear();
		try {
			if (MainWindow.getSelectedItemId() == 1) {
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


				jcb.setText("respect ratio");
				jcb.setToolTipText("if the width is change, the height is also change to fit the new size");
				add(jcb);
				allCheckbox.add(jcb);

				JTextField jtf = new JTextField();
				jtf.setToolTipText("set witdh");
				jtf.setText(MainWindow.getSelectedImageItem().getWidth()+"");
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,15));
				jtf.getDocument().addDocumentListener(new TextFieldWidthDocument());
				add(jtf);
				allTextFields.add(jtf);

				JTextField jtf2 = new JTextField();
				jtf2.setToolTipText("set height");
				jtf2.setPreferredSize(new Dimension(getWidth()/2 - 100,15));
				jtf2.setText(MainWindow.getSelectedImageItem().getHeight()+"");
				jtf2.getDocument().addDocumentListener(new TextFieldHeightDocument());
				add(jtf2);
				allTextFields.add(jtf2);
				
				JButton jb = new JButton("add a translation keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedImageItem().addKeyFrameTranslate(MainWindow.getTimeLine().getTime(), MainWindow.getSelectedImageItem().getPosX(), MainWindow.getSelectedImageItem().getPosY());
					}
				});
				add(jb);
				allButtons.add(jb);
				
				jb = new JButton("delete translation Keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedImageItem().deleteKeyFrameTranslationAt(MainWindow.getTimeLine().getTime());
					}
				});
				add(jb);
				allButtons.add(jb);
				
				jb = new JButton("add a rotation keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedImageItem().addKeyFrameRotation(MainWindow.getTimeLine().getTime(), MainWindow.getSelectedImageItem().getRotation());
					}
				});
				add(jb);
				allButtons.add(jb);
				
				jb = new JButton("delete rotation Keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedImageItem().deleteKeyFrameRotationAt(MainWindow.getTimeLine().getTime());
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
						if (MainWindow.getSelectedImageNumber() > 0) {
							MainWindow.setSelectedImageItem(MainWindow.getSelectedImageNumber() - 1);
							MainWindow.getListSprites().remove(MainWindow.getSelectedImageNumber() + 1);
						} else {
							MainWindow.setSelectedItemId(0);
							MainWindow.getListSprites().remove(MainWindow.getSelectedImageNumber());
						}
						MainWindow.getOutline().refresh();

						loadOptions();
					}
				});
				add(jb);
				allButtons.add(jb);
			} else if (MainWindow.getSelectedItemId() == 2) {
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
				
				JTextField jtf = new JTextField();
				jtf.setToolTipText("set text");
				jtf.setText(MainWindow.getSelectedTextItem().getText()+"");
				jtf.getDocument().addDocumentListener(new TextFieldTextDocument());
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,15));
				add(jtf);
				allTextFields.add(jtf);
				
				jtf = new JTextField();
				jtf.setToolTipText("set font size");
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,15));
				jtf.setText(MainWindow.getSelectedTextItem().getFontSize()+"");
				jtf.getDocument().addDocumentListener(new TextFieldTextSizeDocument());
				add(jtf);
				allTextFields.add(jtf);
				
				jtf = new JTextField();
				jtf.setToolTipText("set width");
				jtf.setText(MainWindow.getSelectedTextItem().getWidth()+"");
				jtf.getDocument().addDocumentListener(new TextFieldWidthDocument());
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,15));
				add(jtf);
				allTextFields.add(jtf);
				
				jtf = new JTextField();
				jtf.setToolTipText("set font size");
				jtf.setPreferredSize(new Dimension(getWidth()/2 - 100,15));
				jtf.setText(MainWindow.getSelectedTextItem().getHeight()+"");
				jtf.getDocument().addDocumentListener(new TextFieldHeightDocument());
				add(jtf);
				allTextFields.add(jtf);
				
				JButton jb = new JButton("add a translation keyframe");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						MainWindow.getSelectedTextItem().addKeyFrameTranslate(MainWindow.getTimeLine().getTime(), MainWindow.getSelectedTextItem().getPosX(), MainWindow.getSelectedTextItem().getPosY());
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
						MainWindow.getSelectedTextItem().addKeyFrameRotation(MainWindow.getTimeLine().getTime(), MainWindow.getSelectedTextItem().getRotation());
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
							MainWindow.getListTextItem().remove(MainWindow.getSelectedTextItemNumber() + 1);
						} else {
							MainWindow.setSelectedItemId(0);
							MainWindow.getListTextItem().remove(MainWindow.getSelectedTextItemNumber());
						}
						MainWindow.getOutline().refresh();

						loadOptions();
					}
				});
				add(jb);
				allButtons.add(jb);
				revalidate ();
			}
		} catch (java.lang.IndexOutOfBoundsException e) {
			System.err.print("Index out of bounds exception ");
			StringWriter stringWriter = new StringWriter();
			e.printStackTrace(new PrintWriter(stringWriter));
			System.out.println(stringWriter.toString());

		}
		revalidate();
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
	
	private class TextFieldWidthDocument implements DocumentListener {

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateStat();
		}
		
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateStat();
		}
		
		public void updateStat () {
			try {
				if (MainWindow.getSelectedItemId() == 1) {
					MainWindow.getSelectedImageItem().setWidth(Integer.parseInt(allTextFields.get(0).getText()));
					allTextFields.get(0).setBackground(Color.white);
					if (jcb.isSelected()) {
						MainWindow.getSelectedImageItem().setHeight((int) Math.round(Integer.parseInt(allTextFields.get(0).getText()) / MainWindow.getSelectedImageItem().getRatio()));
						ItemOption.this.revalidate();
					}
				}else if (MainWindow.getSelectedItemId() ==2) {
					MainWindow.getSelectedTextItem().setWidth(Integer.parseInt(allTextFields.get(2).getText()));
					allTextFields.get(2).setBackground(Color.white);
				}
			} catch (NumberFormatException e) {
				if (MainWindow.getSelectedItemId() == 1)       allTextFields.get(0).setBackground(Color.red);
				else if (MainWindow.getSelectedItemId() == 2)  allTextFields.get(2).setBackground(Color.red);
			}
		}
		
		@Override
		public void changedUpdate(DocumentEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class TextFieldHeightDocument implements DocumentListener {

		@Override
		public void removeUpdate(DocumentEvent arg0) {
			updateStat();
		}
		
		@Override
		public void insertUpdate(DocumentEvent arg0) {
			updateStat();
		}
		
		public void updateStat () {
			try {
				if (MainWindow.getSelectedItemId() == 1) {
					MainWindow.getSelectedImageItem().setHeight(Integer.parseInt(allTextFields.get(1).getText()));
					allTextFields.get(1).setBackground(Color.white);
					if (jcb.isSelected()) {
						MainWindow.getSelectedImageItem().setWidth((int) Math.round(Integer.parseInt(allTextFields.get(1).getText()) * MainWindow.getSelectedImageItem().getRatio()));
						ItemOption.this.revalidate();
					}
				} else if (MainWindow.getSelectedItemId() ==2) {
					MainWindow.getSelectedTextItem().setHeight(Integer.parseInt(allTextFields.get(3).getText()));
					allTextFields.get(3).setBackground(Color.white);
				}
			} catch (NumberFormatException e) {
				if (MainWindow.getSelectedItemId() == 1)       allTextFields.get(1).setBackground(Color.red);
				else if (MainWindow.getSelectedItemId() == 2)  allTextFields.get(3).setBackground(Color.red);
			}
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	private class TextFieldTextSizeDocument implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			if (!allTextFields.get(1).getText().equals("")) MainWindow.getSelectedTextItem().setFontSize(Integer.parseInt(allTextFields.get(1).getText()));
			MainWindow.getSelectedTextItem().reload();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			if (!allTextFields.get(1).getText().equals("")) MainWindow.getSelectedTextItem().setFontSize(Integer.parseInt(allTextFields.get(1).getText()));
			MainWindow.getSelectedTextItem().reload();
		}
		
	}
	
	private class TextFieldTextDocument implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			MainWindow.getSelectedTextItem().setText(allTextFields.get(0).getText());
			MainWindow.getSelectedTextItem().reload();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

}
