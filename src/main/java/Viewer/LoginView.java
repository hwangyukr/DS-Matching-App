package Viewer;


import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.sun.tools.javac.launcher.Main;

import mdlaf.MaterialLookAndFeel;
import mdlaf.utils.MaterialColors;

public class LoginView extends Viewer {

	private static final long serialVersionUID = 1L;

	private JTextField idtxt = null;
	private JTextField pwtxt = null;
	private JButton login_btn = null;
	private JButton join_btn = null;
	private JButton exit_btn = null;
	
	
	public LoginView(ClientApp client) {
		super(client);
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == login_btn) {
			String id = idtxt.getText();
			String pw = pwtxt.getText();
			System.out.println(id);
			client.requestConnection(id, pw);
			client.print("Trying Login ...");
		}
		
		if(e.getSource() == join_btn) {
			client.ChangeView(new JoinView(client));
		}
		
		if(e.getSource() == exit_btn) {
			client.exit();
		}
	}
	
	@Override
	public void init() {
		System.out.println("Login View Init ...");
		this.setLayout(null);

		JLabel title = new JLabel("<html><div style='color: #336644;'> Team Matching App </div></html>", SwingConstants.CENTER);
		Font font = new Font("����", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 300, UIConst.WIDTH, 40);
		this.add(title);

		JLabel labelid = new JLabel("ID");
		labelid.setBounds(90, 365, 30, 30);
		this.add (labelid);
		
		JLabel labelpw = new JLabel("PW");
		labelpw.setBounds(90, 395, 30, 30);
		this.add (labelpw);
		
		idtxt = new JTextField();
		idtxt.setText("email");
		idtxt.grabFocus();
		idtxt.setBounds(125, 365, 300, 30);
		this.add (idtxt);
		
		pwtxt = new JPasswordField("0000");
		pwtxt.setBounds(125, 400, 300, 30);
		this.add (pwtxt);
		
		login_btn = UIConst.BUTTON("Sign In", UIConst.BUTTON_LOGIN);
		login_btn.setBounds(100, 465, 100, 50);
		login_btn.addActionListener(this);
		
		this.add (login_btn);
		
		join_btn = UIConst.BUTTON("Sign Up", UIConst.BUTTON_SIGNUP);
		join_btn.setBounds(210, 465, 100, 50);
		join_btn.addActionListener(this);
		this.add (join_btn);
		
		exit_btn = UIConst.BUTTON("Exit", UIConst.BUTTON_EXIT);
		exit_btn.setBounds(320, 465, 100, 50);
		exit_btn.addActionListener(this);
		this.add (exit_btn);
	}
}
