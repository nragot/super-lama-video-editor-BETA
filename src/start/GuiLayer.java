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
package start;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import API.Layer;
import exceptions.NoItemFoundException;

public class GuiLayer extends Layer {
	
	static Image image;
	
	public GuiLayer () {
		doRenderInside = true;
		doRenderOutSide = false;
		name = "graphical user's interface";
	}

	@Override
	public void render(BufferedImage canvas, int x, int y, int cw, int ch, double z) {
		//TODO: make something that fit a bit more than a empty methode
	}

	@Override
	public void render(Graphics2D g, int x, int y,int w, int h, int cw, int ch, double z) {
		g.setColor(Color.black);
		g.fillRect(0, h - 20, w, 20);
		int cw2 = (int) (cw*z), ch2 = (int) (ch*z);
		g.drawRect(x, y, cw2, ch2);
		g.setColor(new Color (50,50,50,50));
		g.fillRect(0, 0, w, y);
		g.fillRect(0, ch2 + y, w, h - ch2 - y);
		g.fillRect(0, y, x, ch2);
		g.fillRect(x + cw2, y, w - cw2 - x, ch2);
		g.setColor(Color.white);
		try {
			g.drawString(Start.getMainWindow().getSelectedItem().getInfo(), 10, h - 5);
		} catch (IndexOutOfBoundsException | NoItemFoundException e) {
			g.drawString("no item selected", 10, h - 5);
		}
	}

	@Override
	public Image getIcon() {
		return image;
	}
	
	public static void setIcon (Image img) {
		image = img;
	}

}
