package start;

import tools.ItemOption;
import tools.Outline;
import tools.TimeLine;

public class Start {

	static MainWindow mainWindow;
	static TimeLine timeline;
	static Outline outline;
	static ItemOption itemoptions;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world");
		mainWindow = new MainWindow();
		timeline = new TimeLine();
		outline = new Outline();
		itemoptions = new ItemOption();
		if (AppProperties.loadProperties()) mainWindow.GO(outline, itemoptions, timeline);
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

}
