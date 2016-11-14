/* 
 * Copyright 2016 nathan.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package miscelanious;

import API.MaxWidthItem;
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
                        
                        if (p instanceof MaxWidthItem) {
                            p.setPreferredSize(new Dimension((int) c.getWidth() - 5, (int) p.getPreferredSize().getHeight()));
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
		try {
			c.setPreferredSize(new Dimension((int) c.getPreferredSize().getWidth(), preferredHeight));
		} catch (NullPointerException exc) {
			System.err.println("NullPointerException handled in LayoutManager, you need to have a default prefferedSize set");
		}
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
