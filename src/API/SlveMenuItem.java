package API;

import javax.swing.JMenuItem;

public class SlveMenuItem extends JMenuItem {
	
	String path[];
	
	public SlveMenuItem (String name, String path[]) {
		super(name);
		this.path = path;
	}
	
	public String[] getPath () {
		return path;
	}

}
