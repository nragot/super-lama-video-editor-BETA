package browser;

public class TEST {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("before");
		new FileBrowser(true, true);
		System.out.println("after");
		System.out.println("after 2");
		System.out.println("after 3");
		System.exit(0);
	}

}
