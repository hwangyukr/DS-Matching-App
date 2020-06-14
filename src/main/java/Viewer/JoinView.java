package Viewer;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JoinView extends Viewer {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JButton next_btn = null;
	private JButton cancel_btn = null;
	private JTextField name_fld = null;
	private JTextField id_fld = null;
	private JTextField pw_fld = null;
	
	public JoinView(ClientApp client) {
		super(client);
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == next_btn) {
			client.print("Write your profile");
			String name = name_fld.getText();
			String id = id_fld.getText();
			String pw = pw_fld.getText();

			client.requestSignUp(name, id, pw);
			client.ChangeView(new JoinProfileView(client, name, id, pw));
		}
		
		if(e.getSource() == cancel_btn) {
			client.print("Come back to login view");
			client.ChangeView(new LoginView(client));
		}
		
	}

	@Override
	public void init() {
		contentPane = this;
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("<html><div style='color: #336644;'> Join Member </div></html>", SwingConstants.CENTER);
		Font font = new Font("����", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 300, UIConst.WIDTH, 40);
		this.add(title);

		JLabel labelname = new JLabel("Name");
		labelname.setBounds(80, 365, 40, 30);
		this.add (labelname);
		
		JLabel labelid = new JLabel("ID");
		labelid.setBounds(90, 395, 30, 30);
		this.add (labelid);
		
		JLabel labelpw = new JLabel("PW");
		labelpw.setBounds(90, 425, 30, 30);
		this.add (labelpw);
		
		name_fld = new JTextField();
		name_fld.grabFocus();
		name_fld.setBounds(125, 365, 300, 30);
		this.add (name_fld);
		
		id_fld = new JPasswordField();
		id_fld.setBounds(125, 400, 300, 30);
		this.add (id_fld);
		
		pw_fld = new JPasswordField();
		pw_fld.setBounds(125, 435, 300, 30);
		this.add (pw_fld);
		
		next_btn = UIConst.BUTTON("Next", UIConst.BUTTON_SIGNUP);
		next_btn.setBounds(210, 465, 100, 50);
		next_btn.addActionListener(this);
		this.add (next_btn);
		
		cancel_btn = UIConst.BUTTON("Cancel", UIConst.BUTTON_EXIT);
		cancel_btn.setBounds(320, 465, 100, 50);
		cancel_btn.addActionListener(this);
		this.add (cancel_btn);
	}
}
