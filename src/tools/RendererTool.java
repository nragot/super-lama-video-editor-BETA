package tools;

import items.ImageItem;
import items.TextItem;

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
	
	static ArrayList<ImageItem> images = new ArrayList<ImageItem>();
	static ArrayList<TextItem> texts = new ArrayList<TextItem>();

	public void renderShot () {
		setSize(MainWindow.getCameraWidth(), MainWindow.getCameraHeight());
		images = MainWindow.getListSprites();
		texts = MainWindow.getListTextItem();
		repaint();
		BufferedImage render = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
		Graphics2D d = render.createGraphics();
		paintComponent(d);
		try {
			ImageIO.write(render, "png", new File(AppProperties.getRenderOutputPath() + "frame-" + MainWindow.getTimeLine().getTime() + ".png"));
		} catch (IOException e) {
			System.err.println("Wow ! what have you done ? the image can't be written :/");
		}
		System.out.println("image shot ("+MainWindow.getTimeLine().getTime()+")");
	}

	public void renderVideo () {
		setSize(MainWindow.getCameraWidth(), MainWindow.getCameraHeight());
		images = MainWindow.getListSprites();
		texts = MainWindow.getListTextItem();
		new Renderer().start();
	}
	
	class Renderer extends Thread {
		public void run () {
			FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(AppProperties.getRenderOutputPath() + "film.mp4", MainWindow.getCameraWidth(), MainWindow.getCameraHeight());
			MainWindow.secureRedrawerStop();
			
			try {
				recorder.setFrameRate(24);
				recorder.setFormat("mp4");
				recorder.setPixelFormat(0);
				recorder.setVideoCodec(28);
				System.out.println("recording will start soon, info :" + MainWindow.getCameraWidth() + ":" + MainWindow.getCameraHeight());
				
				recorder.start();
				
				IplImage img = new IplImage();
				MainWindow.getTimeLine().setTime(0);
				
				for (int i = 0; i<PropertiesWindow.getEndVideo();i++) {
					repaint();
					BufferedImage render = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
					Graphics2D d = render.createGraphics();
					paintComponent(d);
					ImageIO.write(render, "png", new File(AppProperties.getRenderOutputPath() + "~.png"));
					img = IplImage.createFrom(ImageIO.read(new File (AppProperties.getRenderOutputPath() + "~.png")));
					recorder.record(img);
					System.out.println("recording images :"+i);
					MainWindow.getTimeLine().addTime(1);
					MainWindow.getTimeLine().calculateItemsState();
				}
				
				recorder.stop();
			} catch (Exception e) {
				System.err.println("exception catched" + e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.err.println("Io exception catched :" + e.getMessage() + ":" + e.getCause());
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
		for (int index = 0; index < images.size();index++) {
			d.rotate(Math.toRadians(images.get(index).getRotation()), (images.get(index).getWidth())/2 + images.get(index).getPosX(), (images.get(index).getHeight())/2 + images.get(index).getPosY());
			d.drawImage(images.get(index).getImage(), (int) (images.get(index).getPosX()), (int) (images.get(index).getPosY()),(int) (images.get(index).getWidth()), (int) (images.get(index).getHeight()), null);
			d.rotate(Math.toRadians(-images.get(index).getRotation()), (images.get(index).getWidth())/2 + images.get(index).getPosX(), (images.get(index).getHeight())/2 + images.get(index).getPosY());
		}
		for (int index = 0; index < texts.size();index++) {
			d.rotate(Math.toRadians(texts.get(index).getRotation()), texts.get(index).getWidth()/2 + texts.get(index).getPosX(), texts.get(index).getHeight()/2 + texts.get(index).getPosY());
			d.drawImage(texts.get(index).getImage(), (int) (texts.get(index).getPosX()), texts.get(index).getPosY(),texts.get(index).getWidth() ,texts.get(index).getHeight(), null);
			d.rotate(Math.toRadians(-texts.get(index).getRotation()), texts.get(index).getWidth()/2 + texts.get(index).getPosX(), texts.get(index).getHeight()/2 + texts.get(index).getPosY());
		}
		
	}
}
