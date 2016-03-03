package tools;

public class ArrayListIndexer {
	int a,b;
	public ArrayListIndexer (int arrayListId, int place) {
		a = arrayListId;
		b = place;
	}
	
	/**
	 * arrayListId
	 * @return 1 if image, 2 if text, 3 if video, 4 if shape
	 */
	public int getA () {
		return a;
	}
	
	/**
	 * place into the arrayList
	 * @return
	 */
	public int getB () {
		return b;
	}
	
	public void setA (int i) {
		a = i;
	}
	
	public void setB (int i) {
		b = i;
	}
}
