package start;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class AppProperties {
	static String DefaultImageSelectorPath, DefaultRenderOutputPath;
	
	public static void loadProperties () throws IOException {
		Properties mainProperties = new Properties();
		String path = "./slve.properties";
		FileInputStream file = new FileInputStream(path);
		mainProperties.load(file);
		file.close();
		
		DefaultImageSelectorPath = mainProperties.getProperty("ImageSelector.default");
		DefaultRenderOutputPath = mainProperties.getProperty("Render.output");
		System.out.println("*********properties loaded \n image selector default path:"+DefaultImageSelectorPath);
	}
	
	public static String getImageSelectorDefaultPath () {
		return DefaultImageSelectorPath;
	}
	
	public static String getRenderOutputPath () {
		return DefaultRenderOutputPath;
	}
}
