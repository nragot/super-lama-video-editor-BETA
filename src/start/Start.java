package start;

import java.io.IOException;

public class Start {

	static MainWindow mainWindow;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world");
		if (AppProperties.loadProperties()) mainWindow = new MainWindow();
	}
	
	public static MainWindow getMainWindow () {
		return mainWindow;
	}

}
