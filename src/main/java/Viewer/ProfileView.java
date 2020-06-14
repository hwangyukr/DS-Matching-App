package Viewer;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;

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
import javax.swing.JFileChooser;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JMenuItem;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import User.*;
import Viewer.MyTeamView;
import Viewer.UIConst;

public class ProfileView extends Viewer {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Profile profile;
	private JTextField txt_role;
	private JTextField txt_subrole;
	private JButton btn_pf;
	private JFileChooser jfc;
	File pf_src;
	
	public ProfileView(ClientApp client, Profile profile) {
		super(client);
		this.profile = profile;
		this.client = client;
		//download files from server
		String imgFileName = profile.getFileName();
		client.clientStub.requestFile(imgFileName, "SERVER");
		
		String pfFileName = profile.getPortforlio();
		client.clientStub.requestFile(pfFileName, "SERVER");
		
		pf_src = new File("./client-file-path/"+ profile.getOriginalPortfolio());	//server에서 구현필요

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btn_pf)) {
			jfc = new JFileChooser(pf_src);
			jfc.showSaveDialog(this);
		}

	}

	@SuppressWarnings("null")
	@Override
	public void init() {
		// TODO Auto-generated method stub
		contentPane = this;
		setBounds(100, 100, 450, 300);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);

		JLabel role_label = UIConst.LABEL("Role");
	    role_label.setBounds(40, 260, 200, 40);
	    contentPane.add(role_label);
	      
	    JLabel role_label2 = UIConst.LABEL("Subrole");
	    role_label2.setBounds(300, 260, 200, 40);
		contentPane.add(role_label2);
	     

	    JButton exit_btn = UIConst.BUTTON("Cancle", UIConst.BUTTON_EXIT);
	    exit_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				client.requestMyTeam(String.valueOf(profile.getUser().getTeam().getId()));
			}
		});
	    exit_btn.setBounds(320, 700, 100, 50);
		
		contentPane.add (exit_btn);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(40, 556, 460, 119);
		contentPane.add(scrollPane);

		JTextArea intro = new JTextArea("");
		intro.setEnabled(false);
		intro.setEditable(false);
		intro.setLineWrap(true);
		intro.setFont(new Font("Verdana", Font.PLAIN, 12));
		scrollPane.setViewportView(intro);
		intro.setText(profile.getContent());
		
		JLabel intro_label = UIConst.LABEL("Introduction");
		intro_label.setBounds(40, 510, 200, 40);
		contentPane.add(intro_label);

		JLabel title = new JLabel("<html><div style='color: #336644;'> Your Profile </div></html>",
		SwingConstants.CENTER);
		Font font = new Font("    ", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 30, 540, 40);
		contentPane.add(title);
		
		txt_role = new JTextField();
		txt_role.setEnabled(false);
		txt_role.setEditable(false);
		txt_role.setAutoscrolls(false);
		txt_role.setFont(new Font("Verdana", Font.PLAIN, 16));
		txt_role.setBounds(40, 310, 170, 40);
		txt_role.setColumns(10);
		
		txt_subrole = new JTextField();
		txt_subrole.setEnabled(false);
		txt_subrole.setEditable(false);
		txt_subrole.setAutoscrolls(false);
		txt_subrole.setFont(new Font("Verdana", Font.PLAIN, 16));
		txt_subrole.setColumns(10);
		txt_subrole.setBounds(300, 310, 170, 40);
		
		String subrole = String.valueOf(profile.getRole());
		if(subrole.equals("서비스UI") || subrole.equals("광고디자인") || subrole.equals("아이콘"))
			txt_role.setText("designer");
		else if(subrole.equals("일정관리") || subrole.equals("사용자분석"))
			txt_role.setText("planner");
		else
			txt_role.setText("developer");
		txt_subrole.setText(subrole);
		contentPane.add(txt_role);
		contentPane.add(txt_subrole);
		
		//profile image
		JLabel img_profile = new JLabel("(No Profile Image)");
		if(profile.getOriginalFileName()!=null) {
			img_profile.setIcon(client.getProfileImg(profile.getOriginalFileName()));
			img_profile.setText(null);
		}
		
		img_profile.setHorizontalAlignment(SwingConstants.CENTER);
		img_profile.setBounds(40, 80, 170, 203);
		contentPane.add(img_profile);
		
		btn_pf = UIConst.BUTTON("Portfolio", UIConst.BUTTON_LOGIN);
		btn_pf.setBounds(300, 80, 170, 40);
		btn_pf.setFont(new Font("Verdana", Font.PLAIN, 16));
		btn_pf.addActionListener(this);
		
		contentPane.add(btn_pf);
		setSize(UIConst.WIDTH, UIConst.HEIGHT);
	}
}