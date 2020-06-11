import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Button;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SignIn extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignIn frame = new SignIn();
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
	public SignIn() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(248, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("TEAM MATCH");
		lblNewJgoodiesTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewJgoodiesTitle.setFont(new Font("Impact", Font.ITALIC, 30));
		lblNewJgoodiesTitle.setBounds(70, 91, 244, 53);
		contentPane.add(lblNewJgoodiesTitle);
		
		JLabel lb_userID = new JLabel("ID");
		lb_userID.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_userID.setFont(new Font("Calibri", Font.PLAIN, 18));
		lb_userID.setBounds(57, 193, 57, 15);
		contentPane.add(lb_userID);
		
		textField = new JTextField();
		textField.setBounds(138, 190, 156, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lb_userPW = new JLabel("PW");
		lb_userPW.setHorizontalAlignment(SwingConstants.RIGHT);
		lb_userPW.setFont(new Font("Calibri", Font.PLAIN, 18));
		lb_userPW.setBounds(57, 247, 57, 15);
		contentPane.add(lb_userPW);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(138, 244, 156, 21);
		contentPane.add(passwordField);
		
		Button btn_login = new Button("Login");
		btn_login.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		btn_login.setBackground(new Color(240, 230, 140));
		btn_login.setBounds(154, 352, 76, 23);
		contentPane.add(btn_login);
		
		Button btn_join = new Button("Join");
		btn_join.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		btn_join.setBackground(new Color(244, 164, 96));
		btn_join.setBounds(57, 352, 76, 23);
		contentPane.add(btn_join);
		
		Button btn_quit = new Button("Quit");
		btn_quit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		btn_quit.setBackground(new Color(220, 220, 220));
		btn_quit.setBounds(251, 352, 76, 23);
		contentPane.add(btn_quit);
		setSize(400, 600);
	}
}
