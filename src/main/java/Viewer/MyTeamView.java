package Viewer;


import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Team.Team;
import User.User;
import Team.Role;
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
		JTabbedPane tp = new JTabbedPane();
		tp.setBounds(30,150,UIConst.WIDTH-60, UIConst.HEIGHT-700);
		JPanel pn_member = new JPanel ();
		JPanel pn_posts = new JPanel ();
		pn_member.setLayout(new BoxLayout(pn_member, BoxLayout.Y_AXIS));
		pn_posts.setLayout(new BoxLayout(pn_posts, BoxLayout.Y_AXIS));
		pn_member.setSize(new Dimension(UIConst.WIDTH-60, 200));
		pn_posts.setSize(new Dimension(UIConst.WIDTH-60, 200));
		JScrollPane spn_member = new JScrollPane(pn_member);
		JScrollPane spn_posts  = new JScrollPane(pn_posts);
		pn_member.setAutoscrolls(true);
		pn_posts.setAutoscrolls(true);

		tp.addTab ("Member", spn_member);
		tp.addTab ("Posts", spn_posts);
		this.add(tp);

		JLabel title = new JLabel("<html><div style='color: #336644;'> Team </div></html>", SwingConstants.CENTER);
		Font font = new Font("돋움", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 50, UIConst.WIDTH, 40);
		this.add(title);

		int mem_num = team.getUsers().size() + 1;
		
		for(int i=0; i<mem_num; i++) {
			User member = null;
			if(i==0) member = team.getTeamLeader();
			else member = team.getUsers().get(i-1);
			JPanel row = new JPanel();
			row.setLayout(new GridLayout(1, 5));

			//row.setBounds(0,50*i,400,50);

			String id = String.valueOf(member.getId());
			String name = member.getName();
			String email = member.getEmail();
			//String role = member.getRole().name();
			String role = "temp";

			JLabel lbl_id = new JLabel(id); lbl_id.setSize(50,50);
			JLabel lbl_name = new JLabel(name); lbl_name.setSize(100,50);
			JLabel lbl_email = new JLabel(email); lbl_email.setSize(150,50);
			JLabel lbl_role = new JLabel(id); lbl_role.setSize(100,50);
			row.add(lbl_id);
			row.add(lbl_name);
			row.add(lbl_email);
			row.add(lbl_role);
			pn_member.add(row);
		}
		/*
		JList<String> member_list = new JList<String>(members);
		member_list.setVisibleRowCount(25);
		member_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		member_list.addListSelectionListener(this);
		//member_list.setBounds(30, 150, 200, 300);
		member_list.setBackground(new Color(224,224,224));
		pn_member.add(member_list);
		*/

		String temp[] = new String[1]; temp[0] = "test";
		JList<String> post_list = new JList<String>();
		post_list.setVisibleRowCount(25);
		post_list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		post_list.addListSelectionListener(this);
		post_list.setBounds(300, 150, 200, 300);
		post_list.setBackground(new Color(224,224,224));
		//this.add(post_list);


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