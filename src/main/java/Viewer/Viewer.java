package Viewer;

import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public abstract class Viewer extends JFrame implements ActionListener {
	protected Viewer(String txt) {
		super(txt);
		this.setMinimumSize (new Dimension (540, 960));
		this.setResizable(false);
	}
	public abstract void init();
}
