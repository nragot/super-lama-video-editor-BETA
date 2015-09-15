package start;

import java.io.IOException;

import tools.ScriptReader;

public class AppProperties {
	static String DefaultImageSelectorPath, DefaultRenderOutputPath;
	
	public static boolean loadProperties () {
		try {
			return ScriptReader.read("./slve.init");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
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
