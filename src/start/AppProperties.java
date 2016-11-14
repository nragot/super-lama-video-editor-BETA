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

import java.io.IOException;
import java.util.ArrayList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import tools.ScriptReader;
import API.Mod;

public class AppProperties {
	static String DefaultImageSelectorPath, DefaultRenderOutputPath;
	static ArrayList<Mod> mods = new ArrayList<Mod>();
	
	public static boolean loadProperties (String str) {
		try {
			return ScriptReader.read(str);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public static boolean loadMod (String str) {
		try {
			Class<?> clazz = Class.forName("mod."+str+".start.start");
			Constructor<?> ctor = clazz.getConstructor(String.class.getClasses());
			Mod mod = (Mod) ctor.newInstance();
			mods.add(mod);
		} catch (SecurityException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public static ArrayList<Mod> getMods () {
		return mods;
	}
	
	public static String getImageSelectorDefaultPath () {
		return DefaultImageSelectorPath;
	}
	
	public static void setImageSelectorDefaultPath (String str) {
		DefaultImageSelectorPath = str;
	}
	
	public static String getRenderOutputPath () {
		return DefaultRenderOutputPath;
	}
	
	public static void setRenderOutputPath (String str) {
		DefaultRenderOutputPath = str;
	}
}
