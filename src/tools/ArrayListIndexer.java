package tools;

public class ArrayListIndexer {
	int a,b;
	public ArrayListIndexer (int arrayListId, int place) {
		a = arrayListId;
		b = place;
	}
	
	public int getA () {
		return a;
	}
	
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
