package main.java.Viewer;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.MaterialLiteTheme;
import main.java.Team.*;
import main.java.Viewer.MainView;
import main.java.Viewer.TeamCreateView;


public class ClientApp extends JFrame {
	private static final long serialVersionUID = 1L;

	public CMClientStub clientStub;
    private ClientEventHandler clientEventHandler;
	public JLabel message = new JLabel("message will print here");
    public String token = null;
	
    private String email = null;
    private String pw = null;
    private MainView mainView;
    private TeamCreateView teamCreateView;
    
	public ClientApp() {
		super("Team No3 - Matching System");
		setMinimumSize (new Dimension (UIConst.WIDTH, UIConst.HEIGHT));
		setResizable(false);
		setVisible (true);

		clientStub = new CMClientStub();
		clientEventHandler = new ClientEventHandler(this);
		clientStub.setAppEventHandler(clientEventHandler);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        clientStub.terminateCM();
		        System.out.println("CM Terminated");
		    }
		});
		
		clientStub.startCM();
		print("message will print here");
		ChangeView(new LoginView(this));
		
		message.setBounds(30, 0, 500, 30);
	}
	
	public void print(String txt) {
		message.setText(txt);
	}
	
	public void exit() {
		clientStub.disconnectFromServer();
		System.exit(0);
	}
	
	public void requestConnection(String id, String pw) {
		clientStub.logoutCM();
		clientStub.loginCM(id, pw);
		System.out.println("requestConnection email : " + email);
		System.out.println("requestConnection pw : " + pw);
		this.email = id;
		this.pw = pw;
	}
	
	public void requestLogin() {
		CMUserEvent ue = new CMUserEvent();
		CMInteractionInfo info = clientStub.getCMInfo().getInteractionInfo();
		CMUser user = info.getMyself();
		ue.setStringID("SIGN-IN");
		
		System.out.println("requestLogin email : " + email);
		System.out.println("requestLogin pw : " + pw);
		ue.setEventField(CMInfo.CM_STR, "email", email);
		ue.setEventField(CMInfo.CM_STR, "password", pw);
		
		ue.setSender(user.getName());
		ue.setDistributionGroup(user.getCurrentGroup());
		ue.setDistributionSession(user.getCurrentSession());
		
		clientStub.send(ue, "SERVER");
		this.print("Request Login ...");
	}
	
	public void ChangeView(JPanel view) {
		this.getContentPane().removeAll();
		view.add(message);
		this.getContentPane().add(view, BorderLayout.CENTER);
		pack ();
	}
	
	public void requestSignUp(String name, String id, String pw, String role, String introduce) {
		
	}
	
	public void requestMyTeam(String team_name) {
		
	}
	
	public void requestTeamList() {
		CMUserEvent ue = new CMUserEvent();
		CMInteractionInfo info = clientStub.getCMInfo().getInteractionInfo();
		CMUser user = info.getMyself();
		
		ue.setStringID("GET-TEAMS");
		ue.setEventField(CMInfo.CM_STR, "token", token);
	}
	
	public void requestCreateTeam(Map<Role, Integer> limits) {
		CMUserEvent ue = new CMUserEvent();
		ue.setStringID("CREATE-TEAM");
		Map<Role, Integer> rolelimits = limits;
		String json = client.objectMapper.writeValueAsString(rolelimits);

		ue.setEventField(CMInfo.CM_STR, "team_name", "만나서 반가워요 ^^");
		ue.setEventField(CMInfo.CM_STR, "token", token);
		ue.setEventField(CMInfo.CM_STR, "teamlimit", json);
	}
	
	public void applyTeam() {
	      CMUserEvent ue = new CMUserEvent();
	      CMInteractionInfo info = clientStub.getCMInfo().getInteractionInfo();
	      CMUser user = info.getMyself();
	      ue.setStringID("APPLY-TEAM");
	      
	      ue.setSender(user.getName());
	      ue.setDistributionGroup(user.getCurrentGroup());
	      ue.setDistributionSession(user.getCurrentSession());
	      
	      clientStub.send(ue, "SERVER");
	      this.print("Successfully applied ...");
	   }
	
	public static void main (String[] args) {
		try {
			UIManager.setLookAndFeel (new MaterialLookAndFeel ());
		}
		catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace ();
		}
		new ClientApp();
	}


}