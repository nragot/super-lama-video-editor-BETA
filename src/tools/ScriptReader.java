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
package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class ScriptReader {
	
	public static boolean read (String path) throws IOException {
		RandomAccessFile file;
		CommandFrame cmdf = new CommandFrame();
		cmdf.setBounds(0,0,1200,800);
		try {
			file = new RandomAccessFile(new File (path), "r");
		} catch (FileNotFoundException e) {
			cmdf.print ("[serge] the file does not exist");
			cmdf.setVisible(true);
			return false;
		}
		
		String line = "";
		int i = 0;
		
		//looking for label

		ArrayList<Label> labels = new ArrayList<Label>();
		try {
			while (line != null) {
				i++;
				if (line.startsWith("label ")) {
					labels.add(new Label(line.substring(6), file.getFilePointer(), i));
				}
				line = file.readLine();
			}
		} catch (NullPointerException e) {

		}
		file.seek(0);

		i = 0;

		//reading the file
		try {
			line = new String (file.readLine().getBytes("ISO-8859-1"),"UTF-8");
			read:while (line != null) {
				i++;
				if (line.startsWith("#"))  {
					line = new String (file.readLine().getBytes("ISO-8859-1"),"UTF-8");
					continue;
				} else if (line.startsWith("GOTO") || line.startsWith("goto")) {
					String str = line.substring(5);
					for (int y = 0 ;  y < labels.size(); y ++) {
						if (str.equals(labels.get(y).getLabelName())) {
							file.seek(labels.get(y).getPosition());
							i = labels.get(y).getLine();
							line = new String (file.readLine().getBytes("ISO-8859-1"),"UTF-8");
							continue read;
						}
					}
					cmdf.print ("[serge] non existing label : \"" + str + "\"");
					file.close();
					return false;
				}
				int a;
				try {
					a = cmdf.command(line);
				} catch (Exception e) {
					cmdf.print("[serge] an exception (very bad error) occured at line :" + i);
					cmdf.print("[serge] the line was :" + line);
					cmdf.print("exception info :" + e.getClass() + "->" + e.getMessage());
					e.printStackTrace();
					file.close();
					return false;
				}

				if (a == 1) {
					cmdf.setVisible(true);
					cmdf.print("[serge] <!>  can't read line :"+i + " because it is an unexisting command");
					cmdf.print("[debug] the command was : " + line);
					file.close();
					return false;
				} else if (a != 0) {
					cmdf.print("[serge] <!> an error occure at line :" + i +" while loading script :" + path);
					file.close();
					return false;
				}

				line = new String (file.readLine().getBytes("ISO-8859-1"),"UTF-8");
			}
		} catch (NullPointerException e) {}
		file.close();
		cmdf.dispose();
		return true;
	}

}
