package browser;

public class TEST {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("before");
		FileBrowser browser = new FileBrowser();
		String answer = browser.go(true, "TEST aGagAglouglou", true);
		System.out.println("answer :" + answer);
		System.out.println("after 2");
		System.out.println("after 3");
		System.exit(0);
	}

}
