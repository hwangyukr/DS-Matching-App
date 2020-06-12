package Viewer;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;

import Team.Application;
import User.User;

public class ApplicationView extends Viewer {
	private static final long serialVersionUID = 1L;
	private Button button_1;
	private List<Application> applications;
	
	public ApplicationView(ClientApp client, List<Application> applications) {
		super(client);
		this.applications = applications;
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button_1) {
			client.print("Come back to login view");
			client.ChangeView(new LoginView(client));
		}
		
	}

	@Override
	public void init() {
		int size = applications.size();
		if(size < 1) client.print("There is no Applcant");
		else {
			for(int i=0; i<size; i++) {
				User user = applications.get(i).getUser();
				JLabel lbl1 = new JLabel(user.getName());
				JLabel lbl2 = new JLabel(user.getEmail());
				JButton btn1 = new JButton("Show");
				JButton btn2 = new JButton("Confirm");
				btn1.setName(String.valueOf(user.getId()));
				btn2.setName(String.valueOf(user.getId()));
				btn1.addActionListener(this);
				btn2.addActionListener(this);
				int y = 150 + 40*i;
				lbl1.setBounds(30, y, 100, 40);
				lbl2.setBounds(130, y, 100, 40);
				btn1.setBounds(230, y, 100, 40);
				btn2.setBounds(330, y, 100, 40);
				this.add(lbl1);
				this.add(lbl2);
				this.add(btn1);
				this.add(btn2);
			}
		}
		
		button_1 = new Button("Cancel");
		button_1.setBackground(new Color(211, 211, 211));
		button_1.setBounds(155, 430, 76, 23);
		this.add(button_1);
		button_1.addActionListener(this);
	}
}
