package start;

import java.io.File;

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
		if ( ! new File ("slve.init").exists() || new File ("initme").exists() || new File("initme.txt").exists()) 
			new inittools.MainWindow();
		else {
			System.out.println("hello world");
			mainWindow = new MainWindow();
			timeline = new TimeLine();
			outline = new Outline();
			itemoptions = new ItemOption();
			srcWindow = new SourceWindow();
			if (AppProperties.loadProperties()) mainWindow.GO(outline, itemoptions, timeline);
		}
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
