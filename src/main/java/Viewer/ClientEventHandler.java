package Viewer;

import java.util.List;

import javax.swing.JOptionPane;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Team.Application;
import Team.Team;

import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEventField;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;


public class ClientEventHandler implements CMAppEventHandler {

	private ClientApp client;
    private CMClientStub clientStub;
    private ObjectMapper objectMapper;

    
    public ClientEventHandler(ClientApp client) {
    	this.client = client;
        this.clientStub = client.clientStub;
        this.objectMapper = new ObjectMapper();
   
    }

    private void processSessionEvent(CMSessionEvent event) {
    	int id = event.getID();
    	switch(id) {
		case CMSessionEvent.LOGIN_ACK:
			if(event.isValidUser() != 0) {
				client.print("Server Connected !");
				JOptionPane.showMessageDialog(null, "Server Connected Successfully");
				client.requestLogin();
			}
			else { // 0 is login fail
				client.print("Connection Refused");
				JOptionPane.showMessageDialog(null, "Connection Failed ..");
			}
			break;
		}
    }
    
    @Override
    public void processEvent(CMEvent cmEvent)  {

        switch (cmEvent.getType()) {
    
        case CMInfo.CM_SESSION_EVENT:
        	processSessionEvent((CMSessionEvent) cmEvent);
        	break;
        
        case CMInfo.CM_USER_EVENT:
            CMUserEvent ue = (CMUserEvent) cmEvent;

            if(ue.getStringID().equals("SIGN-IN-REPLY")) {
            	System.out.println("!SIGN-IN-REPLY !");
            	String success = ue.getEventField(CMInfo.CM_INT, "success");
            	
            	if(success.equals("1")) {
            		client.print("Login Success");
            		String token = ue.getEventField(CMInfo.CM_STR, "token");
            		client.token = token;
            		client.requestMyTeam(client.use.getName());
            	}
            	else {
            		client.print("Check your Email or Password !");
            	}
            	
                //ue.setEventField(CMInfo.CM_STR, "team_name", "�븯�씠猷�~");
                //ue.setStringID("GET-TEAMS");
                //clientStub.send(ue, "SERVER");
            }

            if(ue.getStringID().equals("team-make-reply")) {
                System.out.println("諛�");
                System.out.println(ue.getEventField(CMInfo.CM_INT, "success"));
                System.out.println(ue.getEventField(CMInfo.CM_STR, "msg"));
            }

            if(ue.getStringID().equals("GET-TEAM-REPLY")) {
                try {
                    String ret = ue.getEventField(CMInfo.CM_STR, "team");
                    Team team = null;
                    team = objectMapper.readValue(ret, Team.class);
                    client.ChangeView(new MyTeamView(client, team));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            if(ue.getStringID().equals("GET-TEAMS-REPLY")) {
                try {
                    String ret = ue.getEventField(CMInfo.CM_STR, "team");
                    List<Team> teams = objectMapper.readValue(ret, objectMapper.getTypeFactory().constructCollectionType(List.class, Team.class));
                    
                  //  for(Team team : teams)
                 //       System.out.println(team.getTeamLeader() == null);
				
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            if(ue.getStringID().equals("POST-BOARD-REPLY")) {
                try {
                    String success = ue.getEventField(CMInfo.CM_INT, "success");
                    client.print("New Post success : " + success);
                    String msg = ue.getEventField(CMInfo.CM_STR, "msg");
                    if(success.equals("1")) {
                        client.print("posted successfully");
                        client.reqeustMyTeam(client.my_team.getName());

                    }
                    else client.print("post failed" + msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if(ue.getStringID().equals("GET-APPLICATIONS-REPLY")) {
        	  try {
        		  String success = ue.getEventField(CMInfo.CM_INT, "success");
              	  client.print("PROCESS-APPLICATION-REPLY Succes : " + success);
        	      String ret = ue.getEventField(CMInfo.CM_STR, "applications");
        	      List<Application> applications = objectMapper.readValue(ret, objectMapper.getTypeFactory().constructCollectionType(List.class, Application.class));
        	      client.ChangeView(new ApplicationView(client, applications));
        	  } catch (JsonProcessingException e) {
        	      e.printStackTrace();
        	  }
        	}

            if(ue.getStringID().equals("PROCESS-APPLICATION-REPLY")) {
            	String success = ue.getEventField(CMInfo.CM_INT, "success");
            	client.print("PROCESS-APPLICATION-REPLY Succes : " + success);
            	if(success.equals("1")) {
            		client.print("Confirm Application Success");
            		client.requestApplications();
            	}
            	else {
            		client.print("Check you are team manager");
            	}
          	}
                ///리스트 업데이트하기
            

            break;
        default:
            return;
        }
    }
}