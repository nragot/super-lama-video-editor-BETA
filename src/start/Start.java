package start;

import java.io.IOException;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("hello world");
		if (AppProperties.loadProperties()) new MainWindow();
	}

}
