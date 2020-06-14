package Viewer;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Team.Role;

public class JoinProfileView extends Viewer {
	private static final long serialVersionUID = 1L;
	private String name, id, pw;
	//CMSessionEvent requestResult = false;

	private JFileChooser jfc = new JFileChooser();
    private JButton photoUpload = null;
    private JButton portfolioUpload = null;
	private JButton done_btn = null;
	private JButton exit_btn = null;
	private JTextArea intro = null;
	JRadioButton role_radio[] = new JRadioButton[3];
	JRadioButton designer_radio[] = new JRadioButton[3];
	JRadioButton developer_radio[] = new JRadioButton[4];
	JRadioButton planner_radio[] = new JRadioButton[2];
	JPanel panel1= new JPanel();
	JPanel panel2= new JPanel();
	JPanel panel3= new JPanel();
	String photo_pathFileName = null;
	String photo_originalFileName = null;
	String portfolio_pathFileName = null;
	String portfolio_originalFileName = null;
	
	JFileChooser fc;
	
	String[] role_name = { "Designer", "Developer", "Planner" };
	Role[] designSub = { Role.서비스UI, Role.광고디자인, Role.아이콘 };
	Role[] developSub = { Role.앱개발, Role.웹프론트, Role.웹백엔드, Role.QA };
	Role[] planSub = { Role.일정관리, Role.사용자분석 };

	public JoinProfileView(ClientApp client, String name, String id, String pw) {
		super(client);
		init();
		this.name = name;
		this.id = id;
		this.pw = pw;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Role role = null;
		
		if(e.getSource() == photoUpload){
            if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                photo_pathFileName = jfc.getSelectedFile().getAbsoluteFile().toString();
                photo_originalFileName = jfc.getSelectedFile().getName();
            }
        }
		
		if(e.getSource() == portfolioUpload){
            if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){
                // showSaveDialog 저장 창을 열고 확인 버튼을 눌렀는지 확인
            	portfolio_pathFileName = jfc.getSelectedFile().getAbsoluteFile().toString();
                portfolio_originalFileName = jfc.getSelectedFile().getName();
            }
        }
		
		// 디자이너
		if (role_radio[0].isSelected()) {
			panel1.setVisible(true);
			panel2.setVisible(false);
			panel3.setVisible(false);
			for (int i = 0; i < designer_radio.length; i++) {
				if (designer_radio[i].isSelected()) {
					role = designSub[i];
				}
			}
		} else if (role_radio[1].isSelected()) {
			panel2.setVisible(true);
			panel1.setVisible(false);
			panel3.setVisible(false);
			for (int i = 0; i < developer_radio.length; i++) {
				//developer_radio[i].setVisible(true);
				if (developer_radio[i].isSelected()) {
					role = developSub[i];
				}
			}
		} else if (role_radio[2].isSelected()) {
			panel1.setVisible(false);
			panel2.setVisible(false);
			panel3.setVisible(true);
			for (int i = 0; i < planner_radio.length; i++) {
				if (planner_radio[i].isSelected()) {
					role = planSub[i];
				}
			}
		}

		if (e.getSource() == done_btn) {
			String introduce = intro.getText();
			client.print("your Role : " + role);
			client.createProfileRequest(
					Role.앱개발, introduce, photo_pathFileName, photo_originalFileName, portfolio_pathFileName, portfolio_originalFileName);
		}

		if (e.getSource() == exit_btn) {
			client.ChangeView(new JoinView(client));
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		this.setLayout(null);

		JLabel title = new JLabel("<html><div style='color: #336644;'> Your Profile </div></html>",
				SwingConstants.CENTER);
		Font font = new Font("����", Font.PLAIN, 30);
		title.setFont(font);
		this.setSize(1000, 40);
		title.setBounds(0, 30, UIConst.WIDTH, 40);
		this.add(title);

		JLabel role_label = new JLabel("Role", SwingConstants.LEFT);
		role_label.setBounds(40, 260, 200, 40);
		this.add(role_label);
		
		photoUpload = UIConst.BUTTON("Photo", UIConst.BUTTON_UPLOAD);
		photoUpload.setBounds(90, 100, 100, 30);
		photoUpload.addActionListener(this);
		this.add(photoUpload);

		portfolioUpload = UIConst.BUTTON("Portfolio", UIConst.BUTTON_UPLOAD);
		portfolioUpload.setBounds(330, 100, 100, 30);
		portfolioUpload.addActionListener(this);
		this.add(portfolioUpload);

		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < role_radio.length; i++) {
			role_radio[i] = new JRadioButton(role_name[i]);
			group.add(role_radio[i]);
			this.add(role_radio[i]);
			role_radio[i].addActionListener(this);
			role_radio[i].setBounds(60, 300 + 40 * i, 300, 40);
		}

		ButtonGroup subgroup1 = new ButtonGroup();
		for (int i = 0; i < designer_radio.length; i++) {
			designer_radio[i] = new JRadioButton(designSub[i].toString());
			subgroup1.add(designer_radio[i]);
			this.add(designer_radio[i]);
			panel1.add(designer_radio[i]);
			designer_radio[i].addActionListener(this);
			designer_radio[i].setBounds(360, 300 + 40 * i, 300, 40);
		}

		ButtonGroup subgroup2 = new ButtonGroup();
		for (int i = 0; i < developer_radio.length; i++) {
			developer_radio[i] = new JRadioButton(developSub[i].toString());
			subgroup2.add(developer_radio[i]);
			this.add(developer_radio[i]);
			panel2.add(developer_radio[i]);
			developer_radio[i].addActionListener(this);
			developer_radio[i].setBounds(360, 300 + 40 * i, 300, 40);
		}
		
		ButtonGroup subgroup3 = new ButtonGroup();
		for (int i = 0; i < planner_radio.length; i++) {
			planner_radio[i] = new JRadioButton(planSub[i].toString());
			subgroup3.add(planner_radio[i]);
			this.add(planner_radio[i]);
			panel3.add(planner_radio[i]);
			planner_radio[i].setVisible(true);
			planner_radio[i].addActionListener(this);
			planner_radio[i].setBounds(360, 300 + 40 * i, 300, 40);
		}


		JLabel intro_label = new JLabel("Introduce", SwingConstants.LEFT);
		intro_label.setBounds(40, 510, 200, 40);
		this.add(intro_label);

		JTextArea intro = new JTextArea("");
		intro.setBounds(60, 550, UIConst.WIDTH - 120, 100);
		this.add(intro);
		this.intro = intro;

		done_btn = UIConst.BUTTON("Done", UIConst.BUTTON_LOGIN);
		done_btn.setBounds(100, 700, 100, 50);
		done_btn.addActionListener(this);
		this.add(done_btn);

		exit_btn = UIConst.BUTTON("Cancel", UIConst.BUTTON_EXIT);
		exit_btn.setBounds(320, 700, 100, 50);
		exit_btn.addActionListener(this);
		this.add(exit_btn);
	}

}
