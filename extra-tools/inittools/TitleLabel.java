package inittools;

import java.awt.Font;
import java.awt.Label;

public class TitleLabel extends Label {
	
	public TitleLabel(String string) {
		super (string);
		setFont(new Font("Dialog", Font.PLAIN, 40));
	}

	private static final long serialVersionUID = 1L;
}
