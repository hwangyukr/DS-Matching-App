package Viewer;

import java.awt.Button;
import java.awt.Color;
import java.awt.event.ActionEvent;

public class ViewSample extends Viewer {
	private static final long serialVersionUID = 1L;
	private Button button_1;

	public ViewSample(ClientApp client) {
		super(client);
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
		button_1 = new Button("Cancel");
		button_1.setBackground(new Color(211, 211, 211));
		button_1.setBounds(155, 430, 76, 23);
		this.add(button_1);

		button_1.addActionListener(this);
	}
}
