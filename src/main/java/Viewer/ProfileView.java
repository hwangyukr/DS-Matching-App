package Viewer;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.ButtonGroup;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.TextArea;
import java.awt.Choice;
import javax.swing.SwingConstants;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import User.*;
import Viewer.MyTeamView;
import Viewer.UIConst;

public class ProfileView extends Viewer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;
	private JTextField textField;
	private JTextField textField_1;

	public ProfileView(ClientApp client, User user) {
		super(client);
		this.user = user;
		this.client = client;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		contentPane = this;
		setBounds(100, 100, 450, 300);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JLabel role_label = UIConst.LABEL("Role");
	    role_label.setBounds(40, 260, 200, 40);
	    contentPane.add(role_label);
	      
	    JLabel role_label2 = UIConst.LABEL("Subrole");
	    role_label2.setBounds(300, 260, 200, 40);
		contentPane.add(role_label2);
	     

	    JButton exit_btn = UIConst.BUTTON("Cancle", UIConst.BUTTON_EXIT);
	    exit_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				client.requestMyTeam(String.valueOf(user.getTeam().getId()));
			}
		});
	    exit_btn.setBounds(320, 700, 100, 50);
		
		contentPane.add (exit_btn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 556, 460, 119);
		contentPane.add(scrollPane);

		JTextArea intro = new JTextArea("");
		intro.setEnabled(false);
		intro.setEditable(false);
		intro.setLineWrap(true);
		intro.setFont(new Font("Verdana", Font.PLAIN, 12));
		scrollPane.setViewportView(intro);
		
		
		JLabel intro_label = UIConst.LABEL("Introduction");
		intro_label.setBounds(40, 510, 200, 40);
		contentPane.add(intro_label);

		JLabel title = new JLabel("<html><div style='color: #336644;'> Your Profile </div></html>",
		SwingConstants.CENTER);
		Font font = new Font("    ", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 30, 540, 40);
		contentPane.add(title);

		JLabel label = new JLabel("New label");
		label.setBounds(282, 137, -73, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setAutoscrolls(false);
		textField.setFont(new Font("Verdana", Font.PLAIN, 16));
		textField.setBounds(40, 310, 170, 40);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setEditable(false);
		textField_1.setAutoscrolls(false);
		textField_1.setFont(new Font("Verdana", Font.PLAIN, 16));
		textField_1.setColumns(10);
		textField_1.setBounds(300, 310, 170, 40);
		contentPane.add(textField_1);
		
		
		//profile image
		JLabel img_profile = new JLabel("(No Profile Image)");
		img_profile.setHorizontalAlignment(SwingConstants.CENTER);
		img_profile.setBounds(40, 80, 170, 203);
		contentPane.add(img_profile);
		setSize(UIConst.WIDTH, UIConst.HEIGHT);
	}
}