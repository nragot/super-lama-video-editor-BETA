package API;

import java.awt.Graphics2D;
import java.util.ArrayList;

import tools.CommandFrame;

public abstract class Mod {
	
	private String modName;
	
	public Mod (String name) {
		modName = name;
	}
	
	public String getName () {
		return modName;
	}
	
	public abstract void render (Item item, Graphics2D g, int x, int y, int w, int h, int cw, int ch, double z);

	/**
	 * 
	 * @param args
	 * @param cmdFr
	 * @return you're free to do whatever you want with the answer, most people like to use 0 as fine and 1 as error
	 */
	public abstract int FireCommand(ArrayList<String> args, CommandFrame cmdFr) ;

}
