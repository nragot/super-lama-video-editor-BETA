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
package API;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import start.Start;

public class SlveFrame extends JFrame implements KeyListener{

	private static final long serialVersionUID = 1L;
	
	public final static int FORGOTTEN = 0;
	public final static int UNDER = 1;
	public final static int USUAL = 2;
	public final static int OVER = 3;
	public final static int TOP = 4;
	
	int priority;
	
	public SlveFrame (int priority) {
		this.priority = priority;
		addKeyListener(this);
		//addWindowListener(this);
	}
	
	public SlveFrame () {
		priority = 2;
		addKeyListener(this);
		//addWindowListener(this);
	}

	public void setPriority (int i) {
		priority = i;
	}
	
	public int getPriority () {
		return priority;
	}
	
	boolean doAskToFront = true;
	Thread waiter;
	int boolint = 0;

	public void superToFront () {
		super.toFront();
	}
	
	@Override
	public void setVisible (boolean b) {
		if (b) {
			Start.getFrames().add(this);
		} else {
			Start.getFrames().remove(this);
		}
		super.setVisible (b);
	}
	
	@Override
	public void dispose () {
		Start.getFrames().remove(this);
		super.dispose();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_F3:
			System.out.println("about to call front every frame" + this.getName());
			Start.frontEveryFrame();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	
	public void messageUser (String title, String str) {
		JOptionPane.showMessageDialog(this,str,title, JOptionPane.DEFAULT_OPTION);
	}
	
	public void warnUser (String title, String str) {
		JOptionPane.showMessageDialog(this,str,title, JOptionPane.WARNING_MESSAGE);
	}
	
	public void scoldUser (String title, String str) {
		JOptionPane.showMessageDialog(this, str, title, JOptionPane.ERROR_MESSAGE);
	}
	

	/*
	@Override
	public void windowActivated(WindowEvent e) {
		if (e.getOppositeWindow() == null) {
			Start.frontEveryFrame(getTitle());
		} else {
			Start.setWaiterToFalse();
		}
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}*/
}
