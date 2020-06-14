package Viewer;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class JoinProfileView extends Viewer {
	private static final long serialVersionUID = 1L;
	private String name, id, pw;
	
	private JButton done_btn = null;
	private JButton exit_btn = null;
	private JTextArea intro = null;
	JRadioButton role_radio[] = new JRadioButton[4];
	String[] role_name = {"Develop", "Design", "Plannar", "Marketer"};
	
	public JoinProfileView(ClientApp client, String name,String id,String pw) {
		super(client);
		init();
		this.name = name;
		this.id = id;
		this.pw = pw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == done_btn) {
			String role = "default";
			String introduce = intro.getText();
			for(int i=0; i<role_radio.length; i++) {
				if(role_radio[i].isSelected()) {
					role = role_name[i];
				}
			}
			client.print("yout Role : " + role);
			client.requestSignUp(name, id, pw, role, introduce);
			
		}
		if(e.getSource() == exit_btn) {
			client.ChangeView(new JoinView(client));
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.setLayout(null);

		JLabel title = new JLabel("<html><div style='color: #336644;'> Your Profile </div></html>", SwingConstants.CENTER);
		Font font = new Font("����", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 30, UIConst.WIDTH, 40);
		this.add(title);

		JLabel role_label = new JLabel("Role", SwingConstants.LEFT);
		role_label.setBounds(40, 260, 200, 40);
		this.add(role_label);
		
		
		ButtonGroup group = new ButtonGroup();
		for(int i=0; i<role_radio.length; i++){
			role_radio[i] = new JRadioButton(role_name[i]);
            group.add(role_radio[i]);
            this.add(role_radio[i]);
            role_radio[i].addActionListener(this);
            role_radio[i].setBounds(60, 300 + 40*i, 300, 40);
        }
		
		
		JLabel intro_label = new JLabel("Introduce", SwingConstants.LEFT);
		intro_label.setBounds(40, 510, 200, 40);
		this.add(intro_label);
		
		JTextArea intro = new JTextArea("");
		intro.setBounds(60, 550, UIConst.WIDTH-120, 100);
		this.add(intro);
		
		done_btn = UIConst.BUTTON("Done", UIConst.BUTTON_LOGIN);
		done_btn.setBounds(100, 700, 100, 50);
		done_btn.addActionListener(this);
		this.add (done_btn);
		
		exit_btn = UIConst.BUTTON("Cancle", UIConst.BUTTON_EXIT);
		exit_btn.setBounds(320, 700, 100, 50);
		exit_btn.addActionListener(this);
		this.add (exit_btn);
	}
	
}
