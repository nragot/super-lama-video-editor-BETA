package tools;

public class Label {
	private String label;
	private long pos;
	private int line;
	
	public Label (String labelName, long position, int line) {
		label = labelName;
		pos = position;
		this.line = line;
	}
	
	public String getLabelName () {
		return label;
	}
	
	public long getPosition () {
		return pos;
	}
	
	public int getLine () {
		return line;
	}

}
