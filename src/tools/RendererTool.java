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


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import start.AppProperties;
import start.Start;
import API.Layer;

import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.FrameRecorder.Exception;
import com.googlecode.javacv.cpp.opencv_core.IplImage;


public class RendererTool{
	
	public static void renderShot () {
		int w = Start.getMainWindow().getCameraWidth();
		int h = Start.getMainWindow().getCameraHeight();
		BufferedImage render = new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
		for (Layer layer : Start.getMainWindow().getLayers())
			if (layer.doRenderOutside())
				layer.render(render, 0, 0, w, h, 1);
		try {
			ImageIO.write(render, "png", new File(AppProperties.getRenderOutputPath() + "frame-" + TimeLine.getTime() + ".png"));
		} catch (IOException e) {
			System.err.println("Wow ! what have you done ? the image can't be written :/");
		}
		System.out.println("image shot ("+TimeLine.getTime()+")" + " at path :" + AppProperties.getRenderOutputPath());
	}

	public static void renderVideo () {
		new RendererTool().new  Renderer().start();
	}
	
	class Renderer extends Thread {
		public void run () {
			int w = Start.getMainWindow().getCameraWidth();
			int h = Start.getMainWindow().getCameraHeight();
			
			CommandFrame printer = new CommandFrame();
			printer.activate();
			printer.print("rendering will be starting in a instant");
			
			System.out.println("trying to render in :" + AppProperties.getRenderOutputPath() + "film.mp4");
			FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(AppProperties.getRenderOutputPath() + "film.mp4", Start.getMainWindow().getCameraWidth(), Start.getMainWindow().getCameraHeight());
			Start.getMainWindow().secureRedrawerStop();
			
			try {
				recorder.setFrameRate(24);
				recorder.setPixelFormat(0);
				recorder.setVideoCodec(28);
				System.out.println("recording will start soon, info :" + Start.getMainWindow().getCameraWidth() + ":" + Start.getMainWindow().getCameraHeight());
				
				recorder.start();
				
				IplImage img = new IplImage();
				TimeLine.setTime(0);
				BufferedImage render;
				printer.print("rendering start, Start.getMainWindow() is hibernating");
				printer.print("****************************");
				
				for (int i = 0; i<PropertiesWindow.getEndVideo();i++) {
					render = new BufferedImage(w,h,BufferedImage.TYPE_3BYTE_BGR);
					for (Layer layer : Start.getMainWindow().getLayers())
						if (layer.doRenderOutside())
							layer.render(render, 0, 0, w, h, 1);
					img = IplImage.createFrom(render);
					recorder.record(img);
					System.out.println("recording images :"+i);
					TimeLine.addTime(1);
					TimeLine.calculateItemsState();
					printer.print("frame " + TimeLine.getTime() + "/" + PropertiesWindow.getEndVideo());
				}
				printer.print("****************************");
				recorder.stop();
				printer.print("rendering stoped");
				printer.print("done :)");
			} catch (Exception e) {
				System.err.println("exception catched" + e.getMessage());
			}
			System.out.println("video done");
			Start.getMainWindow().secureRedrawRestart();
		}
	}
}