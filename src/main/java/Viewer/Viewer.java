package Viewer;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class Viewer extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;

	public abstract void init();
}
