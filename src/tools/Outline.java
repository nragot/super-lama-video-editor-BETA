package tools;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import start.MainWindow;

public class Outline extends JFrame implements ActionListener{
	ArrayList<JButton> AllButtons = new ArrayList<JButton>();
	
	public Outline () {
		setBounds(1200, 150, 500, 400);
		setTitle("Outline");
		setLayout(new FlowLayout());
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setVisible(true);
	}
	
	public void refresh () {
		for (int i =0; i < AllButtons.size(); i++) {
			remove(AllButtons.get(i));
		}
		
		for (int i = 0; i < MainWindow.getListSprites().size();i++) {
			JButton button = new JButton(MainWindow.getListSprites().get(i).getName());
			button.addActionListener(this);
			if (i == MainWindow.getSelectedImageNumber()) {
				button.setBackground(Color.orange);
			}
			add(button);
			AllButtons.add(button);
		}
		for (int i = 0; i < MainWindow.getListTextItem().size();i++) {
			JButton button = new JButton(MainWindow.getListTextItem().get(i).getName());
			button.addActionListener(this);
			if (i == MainWindow.getSelectedTextItemNumber()) {
				button.setBackground(Color.orange);
			}
			add(button);
			AllButtons.add(button);
		}
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton) e.getSource();
		String str = button.getText();
		
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
		
		refresh();
	}
	
}
