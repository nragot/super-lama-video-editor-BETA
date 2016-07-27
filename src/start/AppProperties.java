package start;

import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import tools.ScriptReader;
import API.Mod;

public class AppProperties {
	static String DefaultImageSelectorPath, DefaultRenderOutputPath;
	static ArrayList<Mod> mods = new ArrayList<Mod>();
	
	public static boolean loadProperties (String str) {
		try {
			return ScriptReader.read(str);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean loadMod (String str) {
		try {
			Class<?> clazz = Class.forName("mod."+str+".start.start");
			Constructor<?> ctor = clazz.getConstructor(String.class.getClasses());
			Mod mod = (Mod) ctor.newInstance();
			mods.add(mod);
		} catch (SecurityException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<Mod> getMods () {
		return mods;
	}
	
	public static String getImageSelectorDefaultPath () {
		return DefaultImageSelectorPath;
	}
	
	public static void setImageSelectorDefaultPath (String str) {
		DefaultImageSelectorPath = str;
	}
	
	public static String getRenderOutputPath () {
		return DefaultRenderOutputPath;
	}
	
	public static void setRenderOutputPath (String str) {
		DefaultRenderOutputPath = str;
	}
}
