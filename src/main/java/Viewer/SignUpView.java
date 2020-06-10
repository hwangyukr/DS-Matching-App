package Viewer;


import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class SignUpView extends Viewer {
	private static final long serialVersionUID = 1L;

	private ClientApp client = null;
	public SignUpView(ClientApp client) {
		super();
		this.client = client;
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void init() {
		this.setLayout(null);
		JLabel title = new JLabel("<html><div style='color: #336644;'> Sign Up </div></html>", SwingConstants.CENTER);
		Font font = new Font("µ¸¿ò", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 300, UIConst.WIDTH, 40);
		this.add(title);

	}
}
