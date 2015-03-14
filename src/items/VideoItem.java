package items;

import java.awt.Image;

import start.MainWindow;

import com.googlecode.javacv.FFmpegFrameGrabber;
import com.googlecode.javacv.FrameGrabber.Exception;

public class VideoItem extends Item {
	FFmpegFrameGrabber g;
	Image img;
	String path;
	int videoStart = 0;
	
	public VideoItem (String videoPath, String name) {
		path = videoPath;
		m_name = name;
		g = new FFmpegFrameGrabber(path);
		try {
			g.start();
			img = g.grab().getBufferedImage();
			m_width = img.getWidth(null)+"";
			m_height = img.getWidth(null)+"";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m_id = 3;
	}
	
	public Image getImage () {
		return img;
	}
	
	public void nextImage () {
		try {
			img = g.grab().getBufferedImage();
		} catch (Exception e) {
			System.err.println("CAN'T GRAB NEXT FRAME");
			e.printStackTrace();
		}
	}
	
	public void setFrame (int i) {
		try {
			g.setFrameNumber(i);
			img = g.grab().getBufferedImage();
			System.out.println("i:"+i);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("can't move to frame : " + i + " in " + m_name);
		}
	}
	
	public void CalculeAndSetProperFrame () {
		setFrame(MainWindow.getTimeLine().getTime() - videoStart);
	}
	
	public void stopStream () {
		try {
			g.stop();
		} catch (Exception e) {
			System.err.println("STREAM CAN'T BE STOP IN PATH :"+path + " ::: name" + m_name);
		}
	}

}
