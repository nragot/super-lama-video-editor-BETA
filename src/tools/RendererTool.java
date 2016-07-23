package tools;

import items.ImageItem;
import items.TextItem;
import items.VideoItem;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.googlecode.javacv.FFmpegFrameRecorder;
import com.googlecode.javacv.FrameRecorder.Exception;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import start.AppProperties;
import start.MainWindow;


public class RendererTool extends JPanel{
	
	static ArrayList<ImageItem>        images = new ArrayList<ImageItem>()       ;
	static ArrayList<TextItem>         texts  = new ArrayList<TextItem>()        ;
	static ArrayList<VideoItem>        videos = new ArrayList<VideoItem>()       ;
	static ArrayList<ArrayListIndexer> index  = new ArrayList<ArrayListIndexer>();

	public void renderShot () {
		setSize(MainWindow.getCameraWidth(), MainWindow.getCameraHeight());
		images = MainWindow.getListSprites();
		texts = MainWindow.getListTextItem();
		videos = MainWindow.getListVideo();
		index = MainWindow.getIndex();
		repaint();
		BufferedImage render = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D d = render.createGraphics();
		paintComponent(d);
		try {
			ImageIO.write(render, "png", new File(AppProperties.getRenderOutputPath() + "frame-" + TimeLine.getTime() + ".png"));
		} catch (IOException e) {
			System.err.println("Wow ! what have you done ? the image can't be written :/");
		}
		MainWindow.getTimeLine();
		System.out.println("image shot ("+TimeLine.getTime()+")");
	}

	public void renderVideo () {
		setSize(MainWindow.getCameraWidth(), MainWindow.getCameraHeight());
		images = MainWindow.getListSprites();
		texts = MainWindow.getListTextItem();
		videos = MainWindow.getListVideo();
		index = MainWindow.getIndex();
		new Renderer().start();
	}
	
	class Renderer extends Thread {
		public void run () {
			CommandFrame printer = new CommandFrame();
			printer.activate();
			printer.print("rendering will be starting in a instant");
			
			System.out.println("trying to render in :" + AppProperties.getRenderOutputPath() + "film.mp4");
			FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(AppProperties.getRenderOutputPath() + "film.mp4", MainWindow.getCameraWidth(), MainWindow.getCameraHeight());
			MainWindow.secureRedrawerStop();
			
			try {
				recorder.setFrameRate(24);
				recorder.setPixelFormat(0);
				recorder.setVideoCodec(28);
				System.out.println("recording will start soon, info :" + MainWindow.getCameraWidth() + ":" + MainWindow.getCameraHeight());
				
				recorder.start();
				
				IplImage img = new IplImage();
				TimeLine.setTime(0);
				BufferedImage render;
				printer.print("rendering start, mainWindow is hibernating");
				printer.print("****************************");
				
				for (int i = 0; i<PropertiesWindow.getEndVideo();i++) {
					repaint();
					render = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_3BYTE_BGR);
					Graphics2D d = render.createGraphics();
					paintComponent(d);
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
			MainWindow.secureRedrawRestart();
		}
	}

	@Override
	public void paintComponent (Graphics g) {
		Graphics2D d = (Graphics2D) g.create();
		d.setColor(Color.WHITE);
		d.fillRect(0, 0, getWidth(), getHeight());
		for (int i = 0; i < index.size();i++) {
			int A = index.get(i).getA(), B = index.get(i).getB()-1;
			if (A == 1 && images.size() > 0) {
				d.rotate(Math.toRadians(images.get(B).getRotation()), images.get(B).getPosX(), images.get(B).getPosY());
				d.drawImage(images.get(B).getImage(), (int) (images.get(B).getPosX() -  (images.get(B).getWidth())/2), (int) (images.get(B).getPosY() - (images.get(B).getHeight())/2),(int) (images.get(B).getWidth()), (int) (images.get(B).getHeight()), null);
				d.rotate(-Math.toRadians(images.get(B).getRotation()), images.get(B).getPosX(), images.get(B).getPosY());
			} else if (A == 2 && texts.size() > 0) {
				d.rotate(Math.toRadians(texts.get(B).getRotation()), (texts.get(B).getWidth())/2 + texts.get(B).getPosX(), (texts.get(B).getHeight())/2 + texts.get(B).getPosY());
				d.drawImage(texts.get(B).getImage(), (int) (texts.get(B).getPosX()), (int) (texts.get(B).getPosY()),(int) (texts.get(B).getWidth()), (int) (texts.get(B).getHeight()), null);
				d.rotate(Math.toRadians(-texts.get(B).getRotation()), (texts.get(B).getWidth())/2 + texts.get(B).getPosX(), (texts.get(B).getHeight())/2 + texts.get(B).getPosY());
			} else if (A == 3 && videos.size() > 0) {
				d.rotate(Math.toRadians(videos.get(B).getRotation()), (videos.get(B).getWidth())/2 + videos.get(B).getPosX(), (videos.get(B).getHeight())/2 + videos.get(B).getPosY());
				d.drawImage(videos.get(B).getImage(), (int) (videos.get(B).getPosX()), (int) (videos.get(B).getPosY()),(int) (videos.get(B).getWidth()), (int) (videos.get(B).getHeight()), null);
				d.rotate(Math.toRadians(-videos.get(B).getRotation()), (videos.get(B).getWidth())/2 + videos.get(B).getPosX(), (videos.get(B).getHeight())/2 + videos.get(B).getPosY());
			}
		}
		
	}
}