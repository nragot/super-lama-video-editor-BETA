package tools;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.JComponent;

import start.MainWindow;

public class Outline extends JFrame implements ActionListener{
	ArrayList<JButton> AllButtons = new ArrayList<JButton>();
	private JPanel myPanel = new JPanel();
	boolean[] key = {
			false /*shift*/
			};
	
	public Outline () {
		setBounds(1200, 150, 500, 400);
		setTitle("Outline");
		setLayout(new FlowLayout());
		bindKey();
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		add(myPanel);
		refresh();
		setVisible(true);
	}
	
	private void bindKey() {
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT,KeyEvent.SHIFT_DOWN_MASK), "outline_shiftPressed");
		myPanel.getActionMap().put("outline_shiftPressed", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				key[0] = true;
				setTitle("Outline (multi selection)");
		}});
		myPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SHIFT,0, true), "outline_shiftReleased");
		myPanel.getActionMap().put("outline_shiftReleased", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				key[0] = false;
				setTitle("Outline");
		}});
	}
	
	public void refresh () {
		for (int i =0; i < AllButtons.size(); i++) {
			remove(AllButtons.get(i));
		}
		AllButtons.clear();
		
		for (int i = 0; i < MainWindow.getListSprites().size();i++) {
			JButton button = new JButton(MainWindow.getListSprites().get(i).getName());
			button.addActionListener(this);
			add(button);
			AllButtons.add(button);
		}
		for (int i = 0; i < MainWindow.getListTextItem().size();i++) {
			JButton button = new JButton(MainWindow.getListTextItem().get(i).getName());
			button.addActionListener(this);
			add(button);
			AllButtons.add(button);
		}
		for (int i = 0;i < MainWindow.getListVideo().size();i++) {
			JButton button = new JButton(MainWindow.getListVideo().get(i).getName());
			button.addActionListener(this);
			add(button);
			AllButtons.add(button);
		}
		for (int i = 0; i < MainWindow.getListShapes().size();i++) {
			JButton button = new JButton(MainWindow.getListShapes().get(i).getName());
			button.addActionListener(this);
			add(button);
			AllButtons.add(button);
		}
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String str = button.getText();
		
		/*
		for (int index = 0; index < MainWindow.getListSprites().size();index ++) {
			if (str.equals( MainWindow.getListSprites().get(index).getName())) {
				MainWindow.setSelectedImageItem(index);
				MainWindow.setSelectedItemId(1);
				MainWindow.getItemOption().loadOptions();
			}
		}
		for (int index = 0; index < MainWindow.getListTextItem().size();index ++) {
			if (str.equals(MainWindow.getListTextItem().get(index).getName())) {
				MainWindow.setSelectedTextItem(index);
				MainWindow.setSelectedItemId(2);
				MainWindow.getItemOption().loadOptions();
			}
		}
		for (int index = 0; index < MainWindow.getListVideo().size();index++) {
			if (str.equals(MainWindow.getListVideo().get(index).getName())) {
				MainWindow.setSelectedVideoItem(index);
				MainWindow.setSelectedItemId(3);
				MainWindow.getItemOption().loadOptions();
			}
		}
		for (int index = 0; index < MainWindow.getListShapes().size();index++) {
			if (str.equals(MainWindow.getListShapes().get(index).getName())) {
				MainWindow.setSelectedShape(index);
				MainWindow.setSelectedItemId(MainWindow.getSelectedShape().getId());
				MainWindow.getItemOption().loadOptions();
			}
		}*/
		
		//IN DEV, HIGLY UNSTABLE
		MainWindow.selectItem(AllButtons.indexOf(button), key[0]);
		MainWindow.getItemOption().loadOptions();
		refresh();
	}
	
	private class MyKeyboardListener implements KeyListener{

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 16) {
				key[0] = true;
				setTitle(e.getKeyCode()+"");
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == 16) {
				key[0] = false;
				setTitle(e.getKeyCode()+"~");
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}
		
	}
	
}
