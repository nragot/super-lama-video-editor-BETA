package start;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import tools.CommandFrame;

public class AppProperties {
	static String DefaultImageSelectorPath, DefaultRenderOutputPath;
	
	public static boolean loadProperties () {
		String path = "./slve.init";
		BufferedReader file;
		CommandFrame cmdf = new CommandFrame();
		cmdf.setVisible(true);
		cmdf.setBounds(0,0,1200,800);
		
		try {
			file = new BufferedReader(new FileReader(path));
			String line = "";
			line = file.readLine();
			int i = 0;
			
			while (line != null) {
				i++;
				if (line.startsWith("#"))  {
					line = file.readLine();
					continue;
				}
				int a = cmdf.command(line);
				
				if (a == 1) {
					cmdf.print("[serge] <!>  can't read line :"+i + " because it is an unexisting command");
					cmdf.print("[debug] the command was : " + line);
					file.close();
					return false;
				} else if (a != 0) {
					cmdf.print("[serge] <!> an error occure at line :" + i +" while loading slve.init");
				}
				
				line = file.readLine();
			}
			
			file.close();
			
			
			System.out.println("*********properties loaded \n image selector default path:"+DefaultImageSelectorPath);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			cmdf.print("[serge] flve.init not found, please create it and put personnal need");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			cmdf.print("[serge] an IO exception occur");
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
