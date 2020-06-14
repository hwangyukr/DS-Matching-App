package Viewer;


import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class JoinView extends Viewer {
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private Button button;
	private Button button_1;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	public JoinView(ClientApp client) {
		super(client);
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == button) {

			client.print("Write your profile");
			String name = textField.getText();
			String id = textField_1.getText();
			String pw = textField_2.getText();
			System.out.println(name + " " + id + " " + pw);
			client.state = 2;
			client.requestSignUp(name, id, pw);
			client.ChangeView(new JoinProfileView(client, name, id, pw));
		}
		
		if(e.getSource() == button_1) {
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
		
		JLabel lblNewJgoodiesTitle = new JLabel("Join Member");
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesTitle.setFont(new Font("Impact", Font.PLAIN, 30));
		lblNewJgoodiesTitle.setBounds(70, 91, 244, 53);
		contentPane.add(lblNewJgoodiesTitle);
		
		button = new Button("Next");
		button.setBackground(new Color(255, 160, 122));
		button.setBounds(155, 375, 76, 23);
		contentPane.add(button);
		
		button_1 = new Button("Cancel");
		button_1.setBackground(new Color(211, 211, 211));
		button_1.setBounds(155, 430, 76, 23);
		contentPane.add(button_1);
		
		JLabel lb_userName = new JLabel("Name");
		lb_userName.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_userName.setBounds(74, 190, 57, 15);
		contentPane.add(lb_userName);
		
		textField = new JTextField();
		textField.setBounds(175, 187, 116, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lb_userID = new JLabel("ID");
		lb_userID.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_userID.setBounds(74, 235, 57, 15);
		contentPane.add(lb_userID);
		
		textField_1 = new JTextField();
		textField_1.setBounds(175, 232, 116, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lb_userPW = new JLabel("PW");
		lb_userPW.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_userPW.setBounds(74, 283, 57, 15);
		contentPane.add(lb_userPW);
		
		textField_2 = new JTextField();
		textField_2.setBounds(175, 280, 116, 21);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		setSize(UIConst.WIDTH, UIConst.HEIGHT);
		
		button.addActionListener(this);
		button_1.addActionListener(this);
	}
}
