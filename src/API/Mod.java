package API;

import inittools.ModBox;

import java.awt.Graphics2D;
import java.util.ArrayList;

import tools.CommandFrame;

public abstract class Mod {
	
	private String modName;
	private String modLocation;
	private boolean activated = false;
	
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
	
	/**
	 * every line into the return array of string will be added in Slve.init.
	 * It is always called before getModInitParametersAfterJob.
	 * @return command prompt command or null
	 */
	public abstract String[] getModInitParameters ();
	
	/**
	 * every line into the return array of string will be added in Slve.init.
	 * It is always called after every mod added their own lines with getModInitParameters
	 * @return command prompt command or null
	 */
	public abstract String[] getModInitParametersAfterJob ();
	
	/**
	 * 
	 * @param initMakerTool window width (w)
	 * @param initMakerTool window height (h)
	 * @return a container that will appear in InitMakerTool
	 */
	public abstract void getModInitOptions (ModBox box);
	
	/**
	 * methode called before getModInitParameters and getModInitParametersAfterJob.
	 * Its job is to make sure that the user actually ask for something that make sens (for instance a folder path that goes nowhere)
	 * @return true if everything went alright and false if the user ask for something impossible
	 */
	public abstract boolean checkBeforeWritingInit ();
	
	public boolean isActivated () {
		return activated;
	}

	public void setActivated (boolean b) {
		activated = b;
	}
	
	public String getLocation () {
		return modLocation;
	}
	
	public void setLocation (String str) {
		modLocation = str;
	}
}
