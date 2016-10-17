package miscelanious;

import inittools.TitleLabel;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;

import javax.swing.JSeparator;

public class SlveDefaultLayout implements LayoutManager{

	int preferredHeight;

	int x = 0,y = 0, yToBe = 0;
	ArrayList<Component> array = new ArrayList<Component>();

	@Override
	public void layoutContainer(Container c) {
		x = yToBe = 0;
		y = 25;
		array.clear();
		for (Component p : c.getComponents()) 
		{
			if (p instanceof TitleLabel)
			{
				GoNextLineAndSetupComponents(p, c);
				p.setSize(p.getPreferredSize());
				p.setLocation( c.getWidth()/2 - p.getWidth()/2, y);
				y += p.getSize().getHeight() + 10;
				x = 0;
				continue;
			}
			x+=p.getPreferredSize().getWidth();
			if (yToBe < p.getPreferredSize().getHeight()) 
			{
				yToBe = (int) p.getPreferredSize().getHeight();
			}
			if (p instanceof JSeparator) 
			{
				p.setPreferredSize(new Dimension(0, 10));
				GoNextLineAndSetupComponents(p, c);
				p.setSize(c.getWidth() - 40, 10);
				p.setLocation(20, y + 10);
				y += 20;
				continue;
			}
			if (x > c.getWidth()) 
			{
				GoNextLineAndSetupComponents(p, c);
			}
			if (yToBe < p.getPreferredSize().getHeight()) 
			{
				yToBe = (int) p.getPreferredSize().getHeight();
			}
			array.add(p);
		}
		x = (c.getWidth() - x) / 2;
		setupComponent(array, x, y);
		preferredHeight = y + yToBe + 20;
		c.setPreferredSize(new Dimension(0, preferredHeight));
	}

	public void GoNextLineAndSetupComponents (Component p, Container c) {
		x-=p.getPreferredSize().getWidth();
		x = (c.getWidth() - x) / 2;

		setupComponent(array, x, y);

		y += yToBe;
		x = (int) p.getPreferredSize().getWidth();
		yToBe = 0;
		array.clear();
	}

	public void setupComponent (ArrayList<Component> array, int x, int y) {
		for (Component o : array) 
		{
			o.setLocation(x, y);
			o.setSize(o.getPreferredSize());
			x+=o.getPreferredSize().getWidth();
		}
	}
	
	public int getPrefferedHeight () {
		return preferredHeight;
		
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return null;
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return null;
	}

	@Override
	public void removeLayoutComponent(Component comp) {
	}

}
