package API;

import javax.swing.JMenuItem;

public class SlveMenuItem extends JMenuItem {

	private static final long serialVersionUID = 1L;
	String path[];
	
	public SlveMenuItem (String name, String path[]) {
		super(name);
		this.path = path;
	}
	
	public String[] getPath () {
		return path;
	}

}
