package mod.test.start;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import tools.CommandFrame;
import API.Item;
import API.Mod;

public class start extends Mod {

	public start() {
		super("test");
	}

	@Override
	public void render(Item item, Graphics2D g, int x, int y, int w, int h,
			int cw, int ch, double z) {
	}

	@Override
	public int FireCommand(ArrayList<String> args, CommandFrame cmdFr) {
		return 0;
	}

	@Override
	public String[] getModInitParameters() {
		return null;
	}

	@Override
	public String[] getModInitParametersAfterJob() {
		return null;
	}
	
	JCheckBox doShowTerminal = new JCheckBox("show terminal");
	JCheckBox activate = new JCheckBox("activate");
	JTextField defaultRenderOutputPath = new JTextField();
	JCheckBox doPauseWhenDone = new JCheckBox("wait for user to press space");

	@Override
	public JPanel getModInitOptions(int w, int h) {
		System.out.println("w:"+w+" h:"+h);
		JPanel cont = new JPanel();
		cont.setLayout(new FlowLayout());
		cont.add(activate);
		cont.add(doShowTerminal);
		cont.add(doPauseWhenDone);
		JSeparator sep = new JSeparator();
		sep.setPreferredSize(new Dimension(w,4));
		cont.add(sep);
		cont.add(new JLabel("where to render by default"));
		cont.add(defaultRenderOutputPath);
		return cont;
	}

	@Override
	public boolean checkBeforeWritingInit() {
		return true;
	}

}
