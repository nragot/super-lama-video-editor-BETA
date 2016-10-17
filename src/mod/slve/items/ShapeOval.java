package mod.slve.items;


public class ShapeOval extends Shape{
	static int nbOfShape = 0;
	
	public ShapeOval () {
		m_id = 402;
		nbOfShape++;
		m_name = "Oval n#" + nbOfShape;
	}
}
