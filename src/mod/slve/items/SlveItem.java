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
package mod.slve.items;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import miscelanious.SlveDefaultLayout;
import API.Item;
import miscelanious.MaxWidthPanel;
import miscelanious.MaxWidthSlider;

public class SlveItem extends Item {
	private boolean options[] = {false};
	//options id-> 1 = rotation

	public SlveItem (String str) {
		super(str, "slve");
		movable = true;
	}
	
	@Override
	public JPanel getOption(int w, int h) {
		MaxWidthPanel cont = new MaxWidthPanel();
		cont.setLayout(new SlveDefaultLayout());
		System.out.println("w:"+w+"h:"+h);
		cont.setPreferredSize(new Dimension (w,h));
		
		if (options[0]) {
			JTextField field = new JTextField();
			field.setText("r="+getRotationFormula());
			field.setPreferredSize(new Dimension(-1,30));
			field.addFocusListener(new MyFocusListener(this) {
				
				@Override
				public void focusLost(FocusEvent e) {
					JTextField field = (JTextField) e.getSource();
					if (field.getText().startsWith("r="))
						item.setRotationFormula(field.getText().substring(2));
					else 
						item.setRotationFormula(field.getText());
					boolean b = item.cache();
					if (b) {
						field.setText("r=" + field.getText());
						field.setBackground(Color.WHITE);
					} else
						field.setBackground(Color.red);
					field.setCaretPosition(0);
					field.revalidate();
				}

				@Override
				public void focusGained(FocusEvent e) {
					JTextField field = (JTextField) e.getSource();
					field.setText(item.getRotationFormula());
				}
			});
			cont.add(field);
		} else {
			MaxWidthSlider slider = new MaxWidthSlider();
			slider.setPreferredSize(new Dimension(w - 10, 30));
			slider.setMaximum(360);
			slider.setMinimum(-360);
			slider.setValue(0);
			slider.addChangeListener(new MyChangeListener(this) {
				
				@Override
				public void stateChanged(ChangeEvent e) {
					item.setRotationFormula(((JSlider) e.getSource()).getValue() + "");
				}
			});
			cont.add(slider);
		}
		
		JTextField field = new JTextField();
		field.setText("x="+getPosXFormula());
		field.setPreferredSize(new Dimension(130,30));
		field.addFocusListener(new MyFocusListener(this) {
			
			@Override
			public void focusLost(FocusEvent e) {
				JTextField field = (JTextField) e.getSource();
				if (field.getText().startsWith("x="))
					item.setPosXFormula(field.getText().substring(2));
				else 
					item.setPosXFormula(field.getText());
				boolean b = item.cache();
				if (b) {
					field.setText("x=" + field.getText());
					field.setBackground(Color.WHITE);
				} else
					field.setBackground(Color.red);
				field.setCaretPosition(0);
				field.revalidate();
			}

			@Override
			public void focusGained(FocusEvent e) {
				JTextField field = (JTextField) e.getSource();
				field.setText(item.getPosXFormula());
			}
		});
		cont.add(field);
		
		field = new JTextField();
		field.setText("y=" + getPosYFormula());
		field.setPreferredSize(new Dimension(130,30));
		field.addFocusListener(new MyFocusListener(this) {
			
			@Override
			public void focusLost(FocusEvent e) {
				JTextField field = (JTextField) e.getSource();
				if (field.getText().startsWith("y="))
					item.setPosYFormula(field.getText().substring(2));
				else 
					item.setPosYFormula(field.getText());
				boolean b = item.cache();
				if (b) {
					field.setText("y=" + field.getText());
					field.setBackground(Color.white);
				} else
					field.setBackground(Color.red);
				field.revalidate();
			}

			@Override
			public void focusGained(FocusEvent e) {
				JTextField field = (JTextField) e.getSource();
				field.setText(item.getPosYFormula());
			}
		});
		cont.add(field);
		
		field = new JTextField();
		field.setText("w=" + getPosYFormula());
		field.setPreferredSize(new Dimension(130,30));
		field.addFocusListener(new MyFocusListener(this) {
			
			@Override
			public void focusLost(FocusEvent e) {
				JTextField field = (JTextField) e.getSource();
				if (field.getText().startsWith("w="))
					item.setWidthFormula(field.getText().substring(2));
				else 
					item.setWidthFormula(field.getText());
				boolean b = item.cache();
				if (b) {
					field.setText("w=" + field.getText());
					field.setBackground(Color.white);
				} else
					field.setBackground(Color.red);
				field.revalidate();
			}

			@Override
			public void focusGained(FocusEvent e) {
				JTextField field = (JTextField) e.getSource();
				field.setText(item.getWidthFormula());
			}
		});
		cont.add(field);
		
		field = new JTextField();
		field.setText("h=" + getPosYFormula());
		field.setPreferredSize(new Dimension(130,30));
		field.addFocusListener(new MyFocusListener(this) {
			
			@Override
			public void focusLost(FocusEvent e) {
				JTextField field = (JTextField) e.getSource();
				if (field.getText().startsWith("h="))
					item.setHeightFormula(field.getText().substring(2));
				else 
					item.setHeightFormula(field.getText());
				boolean b = item.cache();
				if (b) {
					field.setText("h=" + field.getText());
					field.setBackground(Color.white);
				} else
					field.setBackground(Color.red);
				field.revalidate();
			}

			@Override
			public void focusGained(FocusEvent e) {
				JTextField field = (JTextField) e.getSource();
				field.setText(item.getHeightFormula());
			}
		});
		cont.add(field);
                
		return cont;
	}
	
	private abstract class MyChangeListener implements ChangeListener {
		
		Item item;
		
		public MyChangeListener(Item item) {
			this.item = item;
		}
		
		@Override
		public abstract void stateChanged(ChangeEvent e) ;
	}
	
	private abstract class MyFocusListener implements FocusListener {
		
		Item item;
		
		public MyFocusListener (Item item) {
			this.item = item;
		}

		@Override
		public abstract void focusGained(FocusEvent e) ;

		@Override
		public abstract void focusLost(FocusEvent e) ;
		
	}
	
	/*
	 * 1=image
	 * 2=text
	 * 3=video
	 * 4=shape
	 * |-> 401 rectangle shape
	 * |-> 402 oval shape
	 * 5=empty
	 */

}
