package Viewer;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
	// CMSessionEvent requestResult = false;

	private JFileChooser jfc = new JFileChooser();
	private JButton photoUpload = null;
	private JButton portfolioUpload = null;
	private JButton done_btn = null;
	private JButton exit_btn = null;
	private JTextArea intro = null;
	JRadioButton role_radio[] = new JRadioButton[3];
	
	JPanel imagePanel = new JPanel();
	Choice subRole1 = new Choice();
	Choice subRole2 = new Choice();
	Choice subRole3 = new Choice();
	String photo_pathFileName = null;
	String photo_originalFileName = null;
	String portfolio_pathFileName = null;
	String portfolio_originalFileName = null;

	File targetFile;
	BufferedImage targetImg;

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

	
	public void setTarget(File photo_pathFileName2) {
		targetFile = photo_pathFileName2;
		try {
			targetImg = rescale(ImageIO.read(photo_pathFileName2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//imagePanel.setLayout(new BorderLayout(0, 0));
		//imagePanel.add(new JLabel(new ImageIcon(targetImg)));
		imagePanel.setLayout(null);
		ImageIcon icon = new ImageIcon(targetImg);
		JLabel lbl = new JLabel();
		lbl.setBounds(0,0,128,128);
		imagePanel.add(lbl);
		imagePanel.setVisible(true);
		setVisible(true);
	}

	private BufferedImage rescale(BufferedImage originalImage) {
		// TODO Auto-generated method stub
		BufferedImage resizedImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 128, 128, null);
		g.dispose();
		return resizedImage;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String role = null;

		if (e.getSource() == photoUpload) {
			if (jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
				photo_pathFileName = jfc.getSelectedFile().getAbsoluteFile().toString();
				photo_originalFileName = jfc.getSelectedFile().getName();
				setTarget(jfc.getSelectedFile().getAbsoluteFile());
			}
		}

		if (e.getSource() == portfolioUpload) {
			if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
				// showSaveDialog 저장 창을 열고 확인 버튼을 눌렀는지 확인
				portfolio_pathFileName = jfc.getSelectedFile().getAbsoluteFile().toString();
				portfolio_originalFileName = jfc.getSelectedFile().getName();
			}
		}

		// 디자이너
		if (role_radio[0].isSelected()) {
			for (int i = 0; i < designSub.length; i++) {
				subRole1.setVisible(true);
				subRole2.setVisible(false);
				subRole3.setVisible(false);
				// if (subRole1.) {
				role = subRole1.getItem(i);
				// }
			}
		} else if (role_radio[1].isSelected()) {
			for (int i = 0; i < developSub.length; i++) {
				subRole2.setVisible(true);
				subRole1.setVisible(false);
				subRole3.setVisible(false);
				// if (developer_radio[i].isSelected()) {
				role = subRole2.getItem(i);
				// }
			}
		} else if (role_radio[2].isSelected()) {
			for (int i = 0; i < planSub.length; i++) {
				subRole3.setVisible(true);
				subRole1.setVisible(false);
				subRole2.setVisible(false);
				// if (planner_radio[i].isSelected()) {
				role = subRole3.getItem(i);
				// }
			}

		}

		if (e.getSource() == done_btn) {
			String introduce = intro.getText();
			client.print("your Role : " + role);
			client.createProfileRequest(role, introduce, photo_pathFileName, photo_originalFileName, portfolio_pathFileName, portfolio_originalFileName);
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
		role_label.setBounds(40, 350, 200, 40);
		this.add(role_label);

		photoUpload = UIConst.BUTTON("Photo", UIConst.BUTTON_UPLOAD);
		photoUpload.setBounds(337, 137, 120, 37);
		photoUpload.addActionListener(this);
		this.add(photoUpload);
		
		imagePanel.setBounds(70, 107, 198, 191);
		imagePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
		this.add(imagePanel);

		portfolioUpload = UIConst.BUTTON("Portfolio", UIConst.BUTTON_UPLOAD);
		portfolioUpload.setBounds(337, 210, 120, 42);
		portfolioUpload.addActionListener(this);
		this.add(portfolioUpload);

		ButtonGroup group = new ButtonGroup();
		for (int i = 0; i < role_radio.length; i++) {
			role_radio[i] = new JRadioButton(role_name[i]);
			group.add(role_radio[i]);
			this.add(role_radio[i]);
			role_radio[i].addActionListener(this);
			role_radio[i].setBounds(60, 380 + 40 * i, 150, 40);
		}

		for (int i = 0; i < designSub.length; i++) {
			subRole1.add(designSub[i].toString());
			subRole1.setBounds(300, 400, 150, 40);
			add(subRole1);
			subRole1.setVisible(false);
		}
		for (int i = 0; i < developSub.length; i++) {
			subRole2.add(developSub[i].toString());
			subRole2.setBounds(300, 400, 150, 40);
			add(subRole2);
			subRole2.setVisible(false);
		}
		for (int i = 0; i < planSub.length; i++) {
			subRole3.add(planSub[i].toString());
			subRole3.setBounds(300, 400, 150, 40);
			add(subRole3);
			subRole3.setVisible(false);
		}


		JLabel intro_label = new JLabel("Introduce", SwingConstants.LEFT);
		intro_label.setBounds(70, 582, 111, 23);
		this.add(intro_label);

		JTextArea intro = new JTextArea("");
		intro.setBounds(60, 650, UIConst.WIDTH - 120, 100);
		this.add(intro);
		this.intro = intro;

		done_btn = UIConst.BUTTON("Done", UIConst.BUTTON_LOGIN);
		done_btn.setBounds(100, 800, 100, 50);
		done_btn.addActionListener(this);
		this.add(done_btn);

		exit_btn = UIConst.BUTTON("Cancel", UIConst.BUTTON_EXIT);
		exit_btn.setBounds(320, 800, 100, 50);
		exit_btn.addActionListener(this);
		this.add(exit_btn);
	}

}
