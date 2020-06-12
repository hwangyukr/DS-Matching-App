package Viewer;


import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Team.Team;
import User.User;
import mdlaf.MaterialLookAndFeel;
import mdlaf.utils.MaterialColors;

public class MyTeamView extends Viewer implements ListSelectionListener {

	private static final long serialVersionUID = 1L;
	private JButton new_btn = null;
	private JButton confirm_btn = null;
	private JButton exit_btn = null;
	private JTextArea post_txt = null;
	private Team team = null;
	
	public MyTeamView(ClientApp client, Team team) {
		super(client);
		this.team = team;
		if(team == null) {
			client.print("team is null");
		}
		init();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == new_btn) {
			Long team_id = team.getId();
			client.reqeustNewPost(team_id, post_txt.getText());
		}
		
		if(e.getSource() == confirm_btn) {
			client.requestApplications();
		}
		
		if(e.getSource() == exit_btn) {
			client.exit();
			// client.ChangeView(new JoinView(client)); Main View
		}
	}
	

	@Override
	public void init() {
		System.out.println("Login View Init ...");
		this.setLayout(null);

		JLabel title = new JLabel("<html><div style='color: #336644;'> Team </div></html>", SwingConstants.CENTER);
		Font font = new Font("돋움", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 50, UIConst.WIDTH, 40);
		this.add(title);

		int mem_num = team.getUsers().size() + 1; 
		String[] members = new String[mem_num];
		
		for(int i=0; i<mem_num; i++) {
			if(i==0) members[i] = team.getTeamLeader().getEmail();
			else members[i] = team.getUsers().get(i-1).getEmail();
		}
		
		JList<String> member_list = new JList<String>(members);
		member_list.setVisibleRowCount(25);
		member_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		member_list.addListSelectionListener(this);
		member_list.setBounds(30, 150, 200, 300);
		member_list.setBackground(new Color(224,224,224));
		this.add(member_list);
		
		JList<String> post_list = new JList<String>(members);
		post_list.setVisibleRowCount(25);
		post_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		post_list.addListSelectionListener(this);
		post_list.setBounds(300, 150, 200, 300);
		post_list.setBackground(new Color(224,224,224));
		this.add(post_list);
        
		JLabel post_label = new JLabel("Your Post");
		post_label.setBounds(30, 450, 150, 40);
		this.add (post_label);
		
		post_txt = new JTextArea();
		post_txt.setBounds(30, 500, UIConst.WIDTH-60, 150);
		this.add (post_txt);
		
		new_btn = UIConst.BUTTON("New Post", UIConst.BUTTON_LOGIN);
		new_btn.setBounds(100, 695, 300, 50);
		new_btn.addActionListener(this);
		this.add (new_btn);
		
		confirm_btn = UIConst.BUTTON("Show Applier", UIConst.BUTTON_SIGNUP);
		confirm_btn.setBounds(100, 755, 300, 50);
		confirm_btn.addActionListener(this);
		this.add (confirm_btn);
		
		exit_btn = UIConst.BUTTON("Exit", UIConst.BUTTON_EXIT);
		exit_btn.setBounds(100, 815, 300, 50);
		exit_btn.addActionListener(this);
		this.add (exit_btn);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
}