package Viewer;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import mdlaf.MaterialUIMovement;

public class UIConst {
	static final int BUTTON_LOGIN = 0x66;
	static final int BUTTON_SIGNUP = 0x65;
	static final int BUTTON_EXIT = 0x67;
	
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
		}
		
		button.setForeground (fg);
		button.setBackground (bg);
		MaterialUIMovement animateButton = new MaterialUIMovement (hover, 5, 1000 / 30);
		animateButton.add (button);
		return button;
	}
}
