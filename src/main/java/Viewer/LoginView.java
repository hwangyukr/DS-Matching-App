package Viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import mdlaf.MaterialLookAndFeel;

public class LoginView extends Viewer {

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	public LoginView() {
		super("Team No.3  Matching App");

		init();
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		try {
			UIManager.setLookAndFeel (new MaterialLookAndFeel ());
		}
		catch (UnsupportedLookAndFeelException e) {
			System.out.println("Theme Failed");
			e.printStackTrace ();
		}
		
		JPanel row1 = new JPanel ();
		row1.setLayout(null);
		
		JLabel labelid = new JLabel("ID");
		labelid.setBounds(90, 365, 300, 30);
		row1.add (labelid);
		
		JLabel labelpw = new JLabel("PW");
		labelpw.setBounds(90, 395, 300, 30);
		row1.add (labelpw);
		
		JTextArea idtxt = new JTextArea();
		idtxt.setBounds(125, 365, 300, 30);
		row1.add (idtxt);
		
		JTextArea pwtxt = new JTextArea();
		pwtxt.setBounds(125, 400, 300, 30);
		row1.add (pwtxt);
		
		JButton login_btn = UIConst.BUTTON("Sign In", UIConst.BUTTON_LOGIN);
		login_btn.setBounds(100, 465, 100, 50);
		row1.add (login_btn);
		
		JButton join_btn = UIConst.BUTTON("Sign Up", UIConst.BUTTON_SIGNUP);
		join_btn.setBounds(210, 465, 100, 50);
		row1.add (join_btn);
		
		JButton exit_btn = UIConst.BUTTON("Exit", UIConst.BUTTON_EXIT);
		exit_btn.setBounds(320, 465, 100, 50);
		row1.add (exit_btn);
		
		this.add (row1, BorderLayout.CENTER);
		this.pack ();
		this.setVisible (true);
	}

}
