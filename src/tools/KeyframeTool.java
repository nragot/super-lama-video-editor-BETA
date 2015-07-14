package tools;

import items.ImageItem;
import items.TextItem;
import items.VideoItem;

import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import start.MainWindow;

public class KeyframeTool extends JFrame{
	public KeyframeTool () {
		Point p = MouseInfo.getPointerInfo().getLocation();
		setBounds((int)(p.getX() - 50),(int) (p.getY() - 20), 500, 230);
		setLayout(new FlowLayout());
		setTitle("keyframe manager");
		
		if (MainWindow.getSelectedItemId() == 1 || MainWindow.getSelectedItemId() == 2 || MainWindow.getSelectedItemId() == 3) {
			JButton jb = new JButton("add translation keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (MainWindow.getSelectedItemId() == 1) {
						ImageItem img = MainWindow.getSelectedImageItem();
						img.addKeyFrameTranslate(MainWindow.getTimeLine().getTime(), img.calculeVariable(img.getPosXFormula()), img.calculeVariable(img.getPosYFormula()), 1);
					} else if (MainWindow.getSelectedItemId() == 2) {
						TextItem txt = MainWindow.getSelectedTextItem();
						txt.addKeyFrameTranslate(MainWindow.getTimeLine().getTime(), txt.calculeVariable(txt.getPosXFormula()), txt.calculeVariable(txt.getPosYFormula()), 1);
					}
				}
			});
			add(jb);

			jb = new JButton("remove translation keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (MainWindow.getSelectedItemId() == 1) {
						ImageItem img = MainWindow.getSelectedImageItem();
						img.deleteKeyFrameTranslationAt(MainWindow.getTimeLine().getTime());
					} else if (MainWindow.getSelectedItemId() == 2) {
						TextItem txt = MainWindow.getSelectedTextItem();
						txt.deleteKeyFrameTranslationAt(MainWindow.getTimeLine().getTime());
					}
				}
			});
			add(jb);

			jb = new JButton("add rotation keyframe");
			jb.setToolTipText("rotate from past keyframe to now keyframe");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (MainWindow.getSelectedItemId() == 1) {
						ImageItem img = MainWindow.getSelectedImageItem();
						img.addKeyFrameRotation(MainWindow.getTimeLine().getTime(), img.calculeVariable(img.getRotationFormula()));
					} else if (MainWindow.getSelectedItemId() == 2) {
						TextItem txt = MainWindow.getSelectedTextItem();
						txt.addKeyFrameRotation(MainWindow.getTimeLine().getTime(), txt.calculeVariable( (txt.getRotationFormula())));
					}
				}
			});
			add(jb);

			jb = new JButton("remove translation keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if (MainWindow.getSelectedItemId() == 1) {
						ImageItem img = MainWindow.getSelectedImageItem();
						img.deleteKeyFrameTranslationAt(MainWindow.getTimeLine().getTime());
					} else if (MainWindow.getSelectedItemId() == 2) {
						TextItem txt = MainWindow.getSelectedTextItem();
						txt.deleteKeyFrameTranslationAt(MainWindow.getTimeLine().getTime());
					}
				}
				
			});
			add(jb);

			jb = new JButton("add solid translation keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (MainWindow.getSelectedItemId() == 1) {
						ImageItem img = MainWindow.getSelectedImageItem();
						img.addKeyFrameTranslate(MainWindow.getTimeLine().getTime(), img.getPosXFormula(), img.getPosYFormula(), 2);
					} else if (MainWindow.getSelectedItemId() == 2) {
						TextItem txt = MainWindow.getSelectedTextItem();
						txt.addKeyFrameTranslate(MainWindow.getTimeLine().getTime(), txt.getPosXFormula(), txt.getPosYFormula(), 2);
					}
				}
			});
			add(jb);

			jb = new JButton("add solid rotation keyframe");
			jb.setToolTipText("rotate from past keyframe to now keyframe");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (MainWindow.getSelectedItemId() == 1) {
						ImageItem img = MainWindow.getSelectedImageItem();
						img.addKeyFrameRotation(TimeLine.getTime(), img.getRotationFormula());
					} else if (MainWindow.getSelectedItemId() == 2) {
						TextItem txt = MainWindow.getSelectedTextItem();
						txt.addKeyFrameRotation(TimeLine.getTime(), txt.getRotationFormula());
					}
				}
			});
			add(jb);
			
			jb = new JButton("add activation keyframe");
			jb.setToolTipText("add activation keyframe");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (MainWindow.getSelectedItemId() == 1) {
						ImageItem img = MainWindow.getSelectedImageItem();
						img.addKeyFrameActiv(TimeLine.getTime());
					} else if (MainWindow.getSelectedItemId() == 2) {
						TextItem txt = MainWindow.getSelectedTextItem();
						txt.addKeyFrameActiv(TimeLine.getTime());
					} else if (MainWindow.getSelectedItemId() == 3) {
						VideoItem vdi = MainWindow.getSelectedVideoItem();
						vdi.addKeyFrameActiv(TimeLine.getTime());
					}
				}
			});
			add(jb);
		}
		if (MainWindow.getSelectedItemId() == 2) {
			JButton jb = new JButton();
			jb = new JButton("add solid text keyframe");
			jb.setToolTipText("change the text at this point of the video");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (MainWindow.getSelectedItemId() == 2) {
						TextItem tx = MainWindow.getSelectedTextItem();
						tx.addKeyFrameText(TimeLine.getTime(), tx.getText());
					}
				}
			});
			add(jb);
			
			jb = new JButton("remove Text keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					MainWindow.getSelectedTextItem().deleteKeyFrameTextAt(TimeLine.getTime());
				}
			});
			add(jb);
		}
		setVisible(true);
	}
}
