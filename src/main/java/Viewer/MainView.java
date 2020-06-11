package main_view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.AbstractListModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private Team team;
	private User user;
	private Profile profile;
	
	JButton btn_reg;		//팀 가입신청 버튼
	JList<Team> list;		//팀 리스트


	///db 가져오기
	public MainView(User user) {
		team = User.getTeam();		//아직 team이 없을 경우 null이라 가정
		profile = User.getProfile();		////////////////User에 profile 필드 없음
		
		ButtonActionListener listener = new ButtonActionListener()
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel_team = new JPanel();
		panel_team.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_team.setBounds(12, 10, 362, 176);
		contentPane.add(panel_team);
		panel_team.setLayout(null);
		
		
		//db
		/***속한 팀이름 또는 팀에 가입해달라는 라벨****/
		JLabel lb_team = new JLabel("(team name)");
		lb_team.setFont(new Font("굴림", Font.PLAIN, 16));
		lb_team.setBounds(125, 20, 212, 41);
		
		if(team!=null)
			lb_team.setText("팀 이름: "+team.getName());
		else
			lb_team.setText("팀에 가입해주세요");
		panel_team.add(lb_team);
		
		/***팀 입장 또는 팀 생성 버튼****/
		JButton btn_team = new JButton("팀 생성");
		btn_team.setFont(new Font("굴림", Font.PLAIN, 20));
		if(team!=null)
			btn_team.setText("팀 입장");
		btn_team.addActionListener(listener);
		btn_team.setBounds(186, 71, 151, 33);
		panel_team.add(btn_team);
		
		/***프로필 버튼****/
		JButton btn_profile = new JButton("프로필 보기");
		btn_profile.setFont(new Font("굴림", Font.PLAIN, 20));
		btn_profile.setBounds(186, 114, 151, 33);
		btn_profile.addActionListener(listener);
		panel_team.add(btn_profile);
		
		/***프로필 이미지****/
		JLabel lb_image = new JLabel("profile image");
		lb_image.setBackground(Color.WHITE);
		lb_image.setIcon(null);
		lb_image.setBounds(12, 20, 101, 133);
		panel_team.add(lb_image);
		
		/***추천받기 체크박스****/
		JCheckBox chckbx_recommend = new JCheckBox("\uCD94\uCC9C \uBC1B\uAE30");
		chckbx_recommend.setBounds(279, 198, 95, 23);
		chckbx_recommend.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(chckbx_recommend.isSelected()) {
					updateTeamList(true);		//팀 리스트 다시 받아오기
				}
			}
			
		});
		contentPane.add(chckbx_recommend);
		
		
		/***팀 목록*****/
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 227, 362, 244);
		contentPane.add(scrollPane);

		updateTeamList(false);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				List<TeamRole> teamRoles = list.getSelectedValue().getTeamRole();
				
				//팀 구성표 업데이트
				for(int i=0; i<3; i++) {
					table.setValueAt(teamRoles[i].getCurrent(), i+1, 1);	//teamRole[0]부터 차례대로 개발자, 기획, 디자인이라 생각
					table.setValueAt(teamRoles[i].getCurrent(), i+1, 2);	//([개발자/기획자/디자인], [현재 인원], [모집 인원])
				}
				
				if(team!=null)
					btn_reg.setVisible(true);
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lb_teamList = new JLabel("팀 목록");
		lb_teamList.setHorizontalAlignment(SwingConstants.CENTER);
		scrollPane.setColumnHeaderView(lb_teamList);
		lb_teamList.setFont(new Font("굴림", Font.PLAIN, 20));
		
		
		/****리스트에서 선택한 팀에 가입신청 버튼*****/
		btn_reg = new JButton("팀 가입");
		btn_reg.setFont(new Font("굴림", Font.PLAIN, 16));
		btn_reg.addActionListener(listener);
		btn_reg.setBounds(267, 494, 107, 37);
		btn_reg.setVisible(false);
		contentPane.add(btn_reg);
		
		/****리스트에서 선택한 팀의 구성 표****/
		setOrganizationTable();
		contentPane.add(table);
		
		
		
		setSize(400,600);
	}

	
	private void setOrganizationTable() {
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "\uD604\uC7AC \uC778\uC6D0", "\uBAA8\uC9D1 \uC778\uC6D0"},
				{"\uAC1C\uBC1C\uC790", null, null},
				{"\uAE30\uD68D\uC790", null, null},
				{"\uB514\uC790\uC778", null, null},
			},
			new String[] {
				" ", "current", "maximum"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, String.class, String.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(0).setPreferredWidth(45);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(1).setPreferredWidth(71);
		table.getColumnModel().getColumn(2).setResizable(false);
		table.setCellSelectionEnabled(true);
		table.setColumnSelectionAllowed(true);
		table.setBounds(22, 481, 238, 64);
		
	}
	
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			
			switch(b.getText()) {
			case "팀 생성":
				////////
				break;
			case "팀 입장":
				///////
				break;
			case "프로필 보기":
				///////
				break;
			case "팀 가입":
				///////
				break;
			}
		}
	}
	
	private void updateTeamList(boolean isChecked) {
		DefaultListModel<Team> listModel = new DefaultListModel<Team>();
		
		if(!isChecked) {		//추천 받기 체크 안되있으면
			for(int i=0; i<20; i++) {		//db 연동하기
				listModel.addElement(new Team(0, "team name"+i, "team info"));
			}
		}
		
		else {		//추천 받기 체크일때
			/////////////////////////
		}
		
		list.setModel(listModel);
	}
	


}


