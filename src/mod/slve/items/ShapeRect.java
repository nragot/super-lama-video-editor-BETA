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
package mod.slve.items;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ShapeRect extends Shape {
	static int nbofshape = 0;
	int roundBoundX = 30, roundBoundY = 30;
	
	public ShapeRect() {
		m_id = 401;
		nbofshape++;
		m_name = "rect n#"+nbofshape;
		setWidth(300);
		setHeight(300);
		reload();
	}
	
	public void reload () {
		bi = new BufferedImage(cacheWidth, cacheHeight , BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = bi.createGraphics();
		g2d.setColor(Color.black);
		g2d.fillRoundRect(0, 0, Integer.parseInt(m_width)-1, Integer.parseInt(m_height)-1, roundBoundX, roundBoundY);
	}
	
	public void setRoundBoundX (int i) {
		roundBoundX = i;
	}
	
	public int getRoundBoundX () {
		return roundBoundX;
	}
	
	public void setRoundBoundY (int i) {
		roundBoundY = i;
	}
	
	public int getRoundBoundY () {
		return roundBoundY;
	}

}
