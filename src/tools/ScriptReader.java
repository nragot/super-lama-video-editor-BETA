package tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
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
		while (line != null) {
			i++;
			if (line.startsWith("label ")) {
				labels.add(new Label(line.substring(6), file.getFilePointer(), i));
			}
			line = file.readLine();
		}
		file.seek(0);
		
		i = 0;
		line = file.readLine();
		
		//reading the file
		read:while (line != null) {
			i++;
			if (line.startsWith("#"))  {
				line = file.readLine();
				continue;
			} else if (line.startsWith("GOTO") || line.startsWith("goto")) {
				String str = line.substring(5);
				for (int y = 0 ;  y < labels.size(); y ++) {
					if (str.equals(labels.get(y).getLabelName())) {
						file.seek(labels.get(y).getPosition());
						i = labels.get(y).getLine();
						line = file.readLine();
						continue read;
					}
				}
				cmdf.print ("[serge] non existing label : \"" + str + "\"");
				file.close();
				return false;
			}
			int a = cmdf.command(line);
			
			if (a == 1) {
				cmdf.setVisible(true);
				cmdf.print("[serge] <!>  can't read line :"+i + " because it is an unexisting command");
				cmdf.print("[debug] the command was : " + line);
				file.close();
				return false;
			} else if (a != 0) {
				cmdf.print("[serge] <!> an error occure at line :" + i +" while loading script :" + path);
			}
			
			line = file.readLine();
		}
		file.close();
		cmdf.dispose();
		return true;
	}

}
