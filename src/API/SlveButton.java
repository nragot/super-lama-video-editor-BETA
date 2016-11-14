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
package API;

import javax.swing.ImageIcon;

public abstract class SlveButton {
	
	protected ImageIcon icon;
	protected String name;
	private int w,h;
	
	public SlveButton (String str) {
		name = str;
		w = 40;
		h = 30;
	}
	
	public SlveButton (ImageIcon img) {
		icon = img;
		w = img.getIconWidth();
		h = img.getIconHeight();
	}
	
	public ImageIcon getIcon () {
		return icon;
	}
	
	public String getName () {
		return name;
	}
	
	public int getWidth () {
		return w;
	}
	
	public int getHeight () {
		return h;
	}
	
	public abstract void push ();

}
