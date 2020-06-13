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
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import User.*;

public class ProfileView extends Viewer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User user;

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

		JLabel lblNewLabel = new JLabel("\uCE74\uD14C\uACE0\uB9AC");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(66, 116, 57, 15);
		contentPane.add(lblNewLabel);

		JButton btnNewButton_1 = new JButton("닫기");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				client.ChangeView(new MyTeamView(client, user.getTeam()));
			}
		});
		btnNewButton_1.setBounds(260, 500, 97, 23);
		contentPane.add(btnNewButton_1);

		TextArea textArea = new TextArea();
		textArea.setEnabled(false);
		textArea.setEditable(false);
		textArea.setBounds(34, 204, 313, 279);
		contentPane.add(textArea);

		JLabel lblNewLabel_1 = new JLabel("\uC138\uBD80 \uCE74\uD14C\uACE0\uB9AC");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(236, 116, 90, 15);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\uC790\uAE30\uC18C\uAC1C");
		lblNewLabel_2.setBounds(34, 173, 57, 15);
		contentPane.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("PROFILE");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Impact", Font.ITALIC, 28));
		lblNewLabel_3.setBounds(66, 50, 242, 44);
		contentPane.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("category");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(34, 137, 128, 20);
		contentPane.add(lblNewLabel_4);

		JLabel label = new JLabel("New label");
		label.setBounds(282, 137, -73, 15);
		contentPane.add(label);

		JLabel lblNewLabel_4_1 = new JLabel("detailed category");
		lblNewLabel_4_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4_1.setBounds(219, 137, 128, 20);
		contentPane.add(lblNewLabel_4_1);
		setSize(400, 600);
	}
}