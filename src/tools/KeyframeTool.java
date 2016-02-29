package tools;

import items.TextItem;

import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import exceptions.NoItemFoundException;

import start.MainWindow;

public class KeyframeTool extends JFrame{

	private static final long serialVersionUID = 1L;

	public KeyframeTool () {
		Point p = MouseInfo.getPointerInfo().getLocation();
		setBounds((int)(p.getX() - 50),(int) (p.getY() - 20), 500, 230);
		setLayout(new FlowLayout());
		setTitle("keyframe manager");
		
		try {
		if (MainWindow.getSelectedItem().getId() == 1 || MainWindow.getSelectedItem().getId() == 2 || MainWindow.getSelectedItem().getId() == 3) {
			JButton jb = new JButton("add translation keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						MainWindow.getTimeLine();
						MainWindow.getSelectedItem().addKeyFrameTranslate(TimeLine.getTime(), MainWindow.getSelectedItem().calculeVariable(MainWindow.getSelectedItem().getPosXFormula()), MainWindow.getSelectedItem().calculeVariable(MainWindow.getSelectedItem().getPosYFormula()), 1);
					} catch (NoItemFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			add(jb);

			jb = new JButton("remove translation keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						MainWindow.getTimeLine();
						MainWindow.getSelectedItem().deleteKeyFrameTranslationAt(TimeLine.getTime());
					} catch (NoItemFoundException e1) {
						e1.printStackTrace();
					}
				}
			});
			add(jb);

			jb = new JButton("add rotation keyframe");
			jb.setToolTipText("rotate from past keyframe to now keyframe");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						MainWindow.getTimeLine();
						MainWindow.getSelectedItem().addKeyFrameRotation(TimeLine.getTime(), MainWindow.getSelectedItem().calculeVariable(MainWindow.getSelectedItem().getRotationFormula()) , 1);
					} catch (NoItemFoundException e1) {
						
					}
				}
			});
			add(jb);

			jb = new JButton("remove rotation keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						MainWindow.getTimeLine();
						MainWindow.getSelectedItem().deleteKeyFrameRotationAt(TimeLine.getTime());
					} catch (NoItemFoundException e1) {
						e1.printStackTrace();
					}
				}
				
			});
			add(jb);

			jb = new JButton("add solid translation keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						MainWindow.getTimeLine();
						MainWindow.getSelectedItem().addKeyFrameTranslate(TimeLine.getTime(), MainWindow.getSelectedItem().getPosXFormula(), MainWindow.getSelectedItem().getPosYFormula(), 2);
					} catch (NoItemFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			add(jb);

			jb = new JButton("add solid rotation keyframe");
			jb.setToolTipText("rotate from past keyframe to now keyframe");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
						try {
							MainWindow.getSelectedItem().addKeyFrameRotation(TimeLine.getTime(), MainWindow.getSelectedItem().getRotationFormula(), 2);
						} catch (NoItemFoundException e1) {
							e1.printStackTrace();
						}
				}
			});
			add(jb);
			
			jb = new JButton("add activation keyframe");
			jb.setToolTipText("add activation keyframe");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						MainWindow.getSelectedItem().addKeyFrameActiv(TimeLine.getTime());
					} catch (NoItemFoundException e1) {
						e1.printStackTrace();
					}
				}
			});
			add(jb);
			
			jb = new JButton("remove activation keyframe");
			jb.setToolTipText("remove activation keyframe");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						MainWindow.getSelectedItem().deleteKeyFrameActiv(TimeLine.getTime());
					} catch (NoItemFoundException e1) {
						e1.printStackTrace();
					}
				}
			});
			add(jb);
		}// those keyframe will only spawn with TextItem
		if (MainWindow.getSelectedItem().getId() == 2) {
			JButton jb = new JButton();
			jb = new JButton("add solid text keyframe");
			jb.setToolTipText("change the text at this point of the video");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						if (MainWindow.getSelectedItem().getId() == 2) {
							TextItem tx = (TextItem) MainWindow.getSelectedItem();
							tx.addKeyFrameText(TimeLine.getTime(), tx.getText());
						}
					} catch (NoItemFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			add(jb);
			
			jb = new JButton("remove Text keyframe");
			jb.setToolTipText("move to point a to point b");
			jb.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					TextItem tx;
					try {
						tx = (TextItem) MainWindow.getSelectedItem();
						tx.deleteKeyFrameTextAt(TimeLine.getTime());
					} catch (NoItemFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			});
			add(jb);
		}
		setVisible(true);
		} catch (NoItemFoundException | IndexOutOfBoundsException e){
			
		}
	}
}
