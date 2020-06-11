import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Color;

public class Join extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Join frame = new Join();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Join() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Join Member");
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesTitle.setFont(new Font("Impact", Font.PLAIN, 30));
		lblNewJgoodiesTitle.setBounds(70, 91, 244, 53);
		contentPane.add(lblNewJgoodiesTitle);
		
		Button button = new Button("Next");
		button.setBackground(new Color(255, 160, 122));
		button.setBounds(155, 375, 76, 23);
		contentPane.add(button);
		
		Button button_1 = new Button("Cancel");
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
		setSize(400, 600);
	}
}
