package inittools;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import miscelanious.SlveDefaultLayout;

public class ModBox extends JPanel {

	private static final long serialVersionUID = 1L;

	public ModBox (String str) {
		setLayout(new SlveDefaultLayout());
		setPreferredSize(new Dimension(1,1));
		setBorder(BorderFactory.createTitledBorder(str));
	}
	
	@Override
	@Deprecated
	public void setPreferredSize (Dimension d) {
		super.setPreferredSize(d);
	}
}