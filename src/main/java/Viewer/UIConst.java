package Viewer;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

public class UIConst {
	
	static final int WIDTH = 540;
	static final int HEIGHT = 960;
	
	static final int BUTTON_LOGIN = 0x66;
	static final int BUTTON_SIGNUP = 0x65;
	static final int BUTTON_EXIT = 0x67;
	static final int BUTTON_UPLOAD = 0x68;
	
	static JButton BUTTON(String txt, int type) {
		JButton button = new JButton (txt);
		
		Color bg = null;
		Color fg = null;
		Color hover = null;
		
		switch(type) {
		case BUTTON_LOGIN:
			bg = new Color (30, 150, 25);
			fg = new Color (255, 255, 255);
			hover = new Color (196, 196, 196);
			break;
			
		case BUTTON_SIGNUP:
			bg = new Color (200, 225, 200);
			fg = new Color (100, 145, 100);
			hover = new Color (196, 196, 196);
			break;
			
		case BUTTON_EXIT:
			bg = new Color (125, 60, 55);
			fg = new Color (255, 240, 240);
			hover = new Color (196, 196, 196);
			break;
		case BUTTON_UPLOAD:
			bg = new Color (200, 225, 200);
			fg = new Color (100, 145, 100);
			hover = new Color (196, 196, 196);
			break;
		}
		
		
		button.setForeground (fg);
		button.setBackground (bg);
		MaterialUIMovement.add (button, hover, 5, 1000 / 30);
		
		return button;
	}
	
	static JLabel LABEL(String txt) {
		JLabel label = new JLabel(txt, SwingConstants.LEFT);
		label.setFont(new Font("Verdana", Font.PLAIN, 16));
		return label;
	}
}
