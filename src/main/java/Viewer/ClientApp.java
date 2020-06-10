package Viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

public class ClientApp extends JFrame {
	private static final long serialVersionUID = 1L;

	private CMClientStub clientStub;
    private ClientEventHandler clientEventHandler;
	
	public ClientApp() {
		super("Team No3 - Matching System");
		setMinimumSize (new Dimension (UIConst.WIDTH, UIConst.HEIGHT));
		setResizable(false);
		setVisible (true);

		clientStub = new CMClientStub();
		clientEventHandler = new ClientEventHandler(clientStub);
		clientStub.setAppEventHandler(clientEventHandler);
		clientStub.startCM();
		
		ChangeView(new LoginView());
	}
	
	public void requestLogin() {
		
	}
	
	public void ChangeView(JPanel view) {
		add(view, BorderLayout.CENTER);
		pack ();
	}
	
	public static void main (String[] args) {
		try {
			UIManager.setLookAndFeel (new MaterialLookAndFeel ());
		}
		catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace ();
		}
		new ClientApp();
	}
}