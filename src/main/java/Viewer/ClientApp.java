package Viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;

public class ClientApp extends JFrame {
	private static final long serialVersionUID = 1L;

	public CMClientStub clientStub;
    private ClientEventHandler clientEventHandler;
	public JLabel message = new JLabel("message will print here");
    
	public ClientApp() {
		super("Team No3 - Matching System");
		setMinimumSize (new Dimension (UIConst.WIDTH, UIConst.HEIGHT));
		setResizable(false);
		setVisible (true);

		clientStub = new CMClientStub();
		clientEventHandler = new ClientEventHandler(this);
		clientStub.setAppEventHandler(clientEventHandler);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        clientStub.terminateCM();
		        System.out.println("CM Terminated");
		    }
		});
		
		clientStub.startCM();
		ChangeView(new LoginView(this));
		
		message.setBounds(30, 0, 500, 30);
	}
	
	public void print(String txt) {
		message.setText(txt);
	}
	
	public void exit() {
		clientStub.disconnectFromServer();
		System.exit(0);
	}
	
	public void requestLogin(String id, String pw) {
		clientStub.loginCM(id, pw);
	}
	
	public void ChangeView(JPanel view) {
		this.getContentPane().removeAll();
		view.add(message);
		this.getContentPane().add(view, BorderLayout.CENTER);
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