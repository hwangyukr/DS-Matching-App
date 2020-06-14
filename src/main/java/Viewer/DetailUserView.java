package Viewer;

import User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DetailUserView extends Viewer {
    private static final long serialVersionUID = 1L;
    private Button button_1;
    private User user;

    public DetailUserView(ClientApp client, User user) {
        super(client);
        this.user = user;
        init();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button_1) {
            client.print("Come back to main view");
            client.ChangeView(new LoginView(client));
        }

    }

    @Override
    public void init() {
        this.setLayout(null);
        JLabel title = new JLabel("<html><div style='color: #336644;'> Profile View </div></html>", SwingConstants.CENTER);
        Font font = new Font("돋움", Font.PLAIN, 30);
        title.setFont(font);
        this.setSize(1000, 40);
        title.setBounds(0, 50, UIConst.WIDTH, 40);
        this.add(title);

        String id = String.valueOf(user.getId());
        String name = user.getName();
        String email = user.getName();
        String role = user.getName();

        JLabel t_id = new JLabel("id"); t_id.setBounds(30, 300, 100, 50);
        JLabel t_name = new JLabel("name"); t_name.setBounds(30, 350, 100, 50);
        JLabel t_email = new JLabel("email"); t_email.setBounds(30, 400, 100, 50);
        JLabel t_role = new JLabel("role"); t_role.setBounds(30, 450, 100, 50);

        JLabel lbl_id = new JLabel(id); lbl_id.setBounds(150, 300, 100, 50);
        JLabel lbl_name = new JLabel(id); lbl_id.setBounds(150, 350, 100, 50);
        JLabel lbl_email = new JLabel(id); lbl_id.setBounds(150, 400, 100, 50);
        JLabel lbl_role = new JLabel(id); lbl_id.setBounds(150, 450, 100, 50);

        this.add(t_id);
        this.add(t_name);
        this.add(t_email);
        this.add(t_role);

        this.add(lbl_id);
        this.add(lbl_name);
        this.add(lbl_email);
        this.add(lbl_role);

        button_1 = new Button("Cancel");
        button_1.setBackground(new Color(211, 211, 211));
        button_1.setBounds(155, 430, 76, 23);
        this.add(button_1);

        button_1.addActionListener(this);
    }
}
