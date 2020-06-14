package Viewer;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Team.Role;
import User.User;


public class TeamCreateView extends Viewer{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private JPanel contentPane;
	private JTextField edit_teamName;
	private JLabel lb_subject;
	private JPanel panel;
	private JTable table;
	private JLabel lb_design;
	private JLabel lb_developer;
	private JLabel lb_planner;
	private JButton btn_cancle;
	private JButton btn_create;
	private User user;
	private Map<Role, Integer> rolelimits;
	JLabel lb_teamName;

	public TeamCreateView(ClientApp client, User user) {
		super(client);
		rolelimits = new HashMap<Role, Integer>();
		this.user = user;
		init();
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		setBounds(100, 100, 450, 300);
		this.setBorder(new EmptyBorder(5, 5, 5, 5));
		setSize(400,600);
		this.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(12, 20, 362, 130);
		this.add(panel);
		panel.setLayout(null);
		
		JLabel img_profile = new JLabel("profile image");
		img_profile.setBounds(0, 0, 100, 130);
		panel.add(img_profile);
		
		edit_teamName = new JTextField();
		edit_teamName.setBounds(196, 4, 166, 24);
		panel.add(edit_teamName);
		edit_teamName.setFont(new Font("굴림", Font.PLAIN, 14));
		edit_teamName.setColumns(10);
		
		lb_teamName = new JLabel("\uD300 \uC774\uB984");
		lb_teamName.setBounds(134, 9, 64, 19);
		panel.add(lb_teamName);
		lb_teamName.setFont(new Font("굴림", Font.PLAIN, 16));
		
		lb_subject = new JLabel("\uC8FC\uC81C");
		lb_subject.setBounds(134, 38, 64, 19);
		panel.add(lb_subject);
		lb_subject.setFont(new Font("굴림", Font.PLAIN, 16));
		
		JTextArea edit_subject = new JTextArea();
		edit_subject.setRows(100);
		edit_subject.setBounds(196, 36, 166, 94);
		edit_subject.setLineWrap( true );
		panel.add(edit_subject);
		
		
		table = new JTable();
		table.setFont(new Font("굴림", Font.PLAIN, 16));
		table.setRowHeight(35);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{"서비스UI", 0},
				{"광고디자인", 0},
				{"아이콘", 0},
				{"앱개발", 0},
				{"웹프론트", 0},
				{"웹백엔드", 0},
				{"QA", 0},
				{"일정관리", 0},
				{"사용자분석", 0},
			},
			new String[] {
				"role", "number"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Integer.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
			boolean[] columnEditables = new boolean[] {
				false, true
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.setBounds(137, 179, 237, 316);
		contentPane.add(table);
		
		lb_design = new JLabel("\uB514\uC790\uC778");
		lb_design.setFont(new Font("굴림", Font.PLAIN, 24));
		lb_design.setHorizontalAlignment(SwingConstants.CENTER);
		lb_design.setHorizontalTextPosition(SwingConstants.CENTER);
		lb_design.setBorder(new LineBorder(new Color(0, 0, 0)));
		lb_design.setBackground(Color.WHITE);
		lb_design.setBounds(12, 179, 125, 105);
		contentPane.add(lb_design);
		
		lb_developer = new JLabel("\uAC1C\uBC1C");
		lb_developer.setHorizontalTextPosition(SwingConstants.CENTER);
		lb_developer.setHorizontalAlignment(SwingConstants.CENTER);
		lb_developer.setFont(new Font("굴림", Font.PLAIN, 24));
		lb_developer.setBorder(new LineBorder(new Color(0, 0, 0)));
		lb_developer.setBackground(Color.WHITE);
		lb_developer.setBounds(12, 283, 125, 141);
		contentPane.add(lb_developer);
		
		lb_planner = new JLabel("\uAE30\uD68D");
		lb_planner.setHorizontalTextPosition(SwingConstants.CENTER);
		lb_planner.setHorizontalAlignment(SwingConstants.CENTER);
		lb_planner.setFont(new Font("굴림", Font.PLAIN, 24));
		lb_planner.setBorder(new LineBorder(new Color(0, 0, 0)));
		lb_planner.setBackground(Color.WHITE);
		lb_planner.setBounds(12, 424, 125, 70);
		contentPane.add(lb_planner);
		
		
		ButtonActionListener listener = new ButtonActionListener();
		btn_cancle = new JButton("취소");
		btn_cancle.setBounds(283, 505, 91, 48);
		btn_cancle.addActionListener(listener);
		contentPane.add(btn_cancle);
		
		btn_create = new JButton("생성");
		btn_create.setBounds(167, 505, 91, 48);
		btn_create.addActionListener(listener);
		contentPane.add(btn_create);
	}
	
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton) e.getSource();
			
			switch(b.getText()) {
			case "생성":
				String teamName = lb_teamName.getText();
				int[] limits = new int[9];
				for(int i=0; i<9; i++)
					limits[i] = (int) table.getValueAt(i, 1);
				Role[] roles = Role.values();
				for(int i=0; i<9; i++)
					rolelimits.put(roles[i], limits[i]);
				client.print("팀이 생성되었습니다.");
				client.requestCreateTeam(rolelimits, teamName);
				
			case "취소":
				client.requestGetTeams();		//TeamsView로 이동
				break;
			}
		}
	}
}
