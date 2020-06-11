package Viewer;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

public abstract class Viewer extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	protected ClientApp client = null;
	public Viewer(ClientApp client) {
		this.client = client;
	}
	public abstract void init();
}
