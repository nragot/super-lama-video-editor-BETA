package start;

import java.util.ArrayList;

public class ArrayListWithName<E> extends ArrayList<E> {
	private static final long serialVersionUID = 1L;
	String name;
	
	public ArrayListWithName (String name) {
		this.name = name;
	}
	
	public String getName () {
		return name;
	}

}
