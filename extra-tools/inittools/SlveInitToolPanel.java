package inittools;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class SlveInitToolPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final int EastButtonWidth = 100;
	private JPanel boxmodOptions = new JPanel();
	private JScrollPane boxModOptionsScroller;
	private JButton EastButton;

	public SlveInitToolPanel () {
		setLayout(new SlveLayout_InitTool());
		boxmodOptions.setLayout(new SlveLayout_BoxModOptions());
		boxmodOptions.setPreferredSize(new Dimension(1, 800));
		boxModOptionsScroller = new JScrollPane (boxmodOptions);
		EastButton = new JButton("done");
		super.add(boxModOptionsScroller);
		super.add(EastButton);
	}
	
	int h;
	@Override
	public Component add (Component c) {
		if (c instanceof ModBox) {
			boxmodOptions.add(c);
		}
		return null;
	}
	
	public JButton getEastButton () {
		return EastButton;
	}
	
	public class SlveLayout_BoxModOptions implements LayoutManager {

		@Override
		public void addLayoutComponent(String name, Component comp) {
		}

		@Override
		public void layoutContainer(Container parent) {
			int y = 0;
			for (Component c : parent.getComponents()) {
				c.setSize(parent.getWidth(), (int) c.getPreferredSize().getHeight());
				c.setLocation(0, y);
				y += c.getPreferredSize().getHeight();
			}
			boxmodOptions.setPreferredSize(new Dimension(1,y));
		}

		@Override
		public Dimension minimumLayoutSize(Container parent) {
			return null;
		}

		@Override
		public Dimension preferredLayoutSize(Container parent) {
			return null;
		}

		@Override
		public void removeLayoutComponent(Component comp) {
		}
		
	}

	public class SlveLayout_InitTool implements LayoutManager {

		@Override
		public void addLayoutComponent(String arg0, Component arg1) {
		}

		@Override
		public Dimension minimumLayoutSize(Container arg0) {
			return null;
		}

		@Override
		public Dimension preferredLayoutSize(Container arg0) {
			return null;
		}

		@Override
		public void removeLayoutComponent(Component arg0) {
		}

		@Override
		public void layoutContainer(Container parent) {
			boxModOptionsScroller.setSize(getWidth() - EastButtonWidth, parent.getHeight());
			boxModOptionsScroller.setLocation(0, 0);
			EastButton.setSize(EastButtonWidth, parent.getHeight());
			EastButton.setLocation(parent.getWidth() - EastButtonWidth, 0);
		}

	}
}
