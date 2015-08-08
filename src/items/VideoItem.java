package items;

import java.awt.Image;

import start.MainWindow;
import tools.TimeLine;

import com.googlecode.javacv.FFmpegFrameGrabber;
import com.googlecode.javacv.FrameGrabber.Exception;

public class VideoItem extends Item {
	FFmpegFrameGrabber g;
	Image img;
	String path;
	int videoBorn = 0, videoStart = 0, videoStop = 0;
	
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
	
	public VideoItem (String videoPath, String name, int VideoBorn) {
		path = videoPath;
		m_name = name;
		g = new FFmpegFrameGrabber(path);
		try {
			g.start();
			img = g.grab().getBufferedImage();
			m_width = img.getWidth(null)+"";
			m_height = img.getHeight(null)+"";
			videoBorn = VideoBorn;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m_id = 3;
	}
	
	public VideoItem (String videoPath, String name, int VideoBorn, int VideoStart) {
		path = videoPath;
		m_name = name;
		g = new FFmpegFrameGrabber(path);
		try {
			g.start();
			img = g.grab().getBufferedImage();
			m_width = img.getWidth(null)+"";
			m_height = img.getHeight(null)+"";
			videoBorn = VideoBorn;
			videoStart = VideoStart;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m_id = 3;
	}
	
	public void setBorn (int i) {
		videoBorn = i;
	}
	
	public int getBorn () {
		return videoBorn;
	}
	
	public void setStart (int i) {
		videoStart = i;
	}
	
	public int getStart () {
		return videoStart;
	}
	
	public void setStop (int i) {
		videoStop = i;
	}
	
	public int getStop () {
		return videoStop;
	}
	
	public String getPath () {
		return path;
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
			if (videoBorn > MainWindow.getTimeLine().getTime()) {
				g.setFrameNumber(0);
			} else {
				g.setFrameNumber(i);
				img = g.grab().getBufferedImage();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("can't move to frame : " + i + " in " + m_name);
		}
	}
	
	
	public void CalculeAndSetProperFrame () {
		MainWindow.getTimeLine();
		setFrame(TimeLine.getTime() - videoBorn + videoStart);
	}
	
	public void stopStream () {
		try {
			g.stop();
		} catch (Exception e) {
			System.err.println("STREAM CAN'T BE STOP IN PATH :"+path + " ::: name" + m_name);
		}
	}

}
