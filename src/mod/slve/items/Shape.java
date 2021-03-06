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
import java.awt.Image;
import java.awt.image.BufferedImage;

public class Shape extends SlveItem {
	Color color = new Color (0, 0, 0, 255);
	BufferedImage bi;
	int angle;
	
	public Shape() {
		super("not named");
		m_width = 100+"";
		m_height = 100+"";
		bi = new BufferedImage(100, 100 , BufferedImage.TYPE_INT_ARGB);
	}
	
	public Image getImage () {
		return bi;
	}

}
