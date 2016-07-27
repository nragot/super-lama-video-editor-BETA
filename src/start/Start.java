package start;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Properties;

import tools.ItemOption;
import tools.Outline;
import tools.SourceWindow;
import tools.TimeLine;

public class Start {

	static MainWindow mainWindow;
	static TimeLine timeline;
	static Outline outline;
	static ItemOption itemoptions;
	static SourceWindow srcWindow;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String slvePath = new File (new File (new MainWindow().getClass().getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent()).getAbsolutePath() + File.separator;
			if ( ! new File (slvePath + "slve.init").exists() 			//
					|| new File (slvePath + "initme").exists() 			//
					|| new File (slvePath + "initme.txt").exists()) 	//
				new inittools.MainWindow(slvePath);
			else {
				System.out.println("hello world");
				mainWindow = new MainWindow();
				timeline = new TimeLine();
				outline = new Outline();
				itemoptions = new ItemOption();
				srcWindow = new SourceWindow();
				if (AppProperties.loadProperties(slvePath + "slve.init")) mainWindow.GO(outline, itemoptions, timeline);
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		};
	}
	
	public static MainWindow getMainWindow () {
		return mainWindow;
	}
	
	public static TimeLine getTimeLine () {
		return timeline;
	}
	
	public static Outline getOutline () {
		return outline;
	}
	
	public static ItemOption getItemOption() {
		return itemoptions;
	}
	
	public static SourceWindow getSourceWindow () {
		return srcWindow;
	}

}
