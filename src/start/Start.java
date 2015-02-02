package start;

import java.io.IOException;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world");
		try {
			AppProperties.loadProperties();
		} catch (IOException e) {
			System.err.println("ERROR WHILE LOADING PROPERTIES");
		}
		new MainWindow();
	}

}
