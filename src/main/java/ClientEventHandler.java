import Team.*;
import User.*;
import Viewer.MyTeamView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMFileEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSNSEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.io.IOException;
import java.util.List;

public class ClientEventHandler implements CMAppEventHandler {

    public CMClientStub clientStub;
    private ObjectMapper objectMapper;
    private Client client;
    //private Manager mafsaf;
    
    public ClientEventHandler(CMClientStub clientStub, Client client) {
        this.clientStub = clientStub;
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void processEvent(CMEvent cmEvent)  {

        switch (cmEvent.getType()) {

            case CMInfo.CM_SNS_EVENT:
                CMSNSEvent se = (CMSNSEvent) cmEvent;
                System.out.println("======================");
                System.out.println(se.getContentID());
                System.out.println(se.getDate());
                System.out.println(se.getUserName() );
                System.out.println(se);
                break;

            case CMInfo.CM_FILE_EVENT:
                CMFileEvent fe = (CMFileEvent) cmEvent;
                System.out.println("===========================");
                System.out.println(fe);
                break;
            case CMInfo.CM_USER_EVENT:
                CMUserEvent ue = (CMUserEvent) cmEvent;

                if(ue.getStringID().equals("SIGN-UP-REPLY")) {
                    int success = Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "success"));
                    if(success == 1) {
                    }
                }

                if(ue.getStringID().equals("SIGN-IN-REPLY")) {
                    int success = Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "success"));
                    String token = ue.getEventField(CMInfo.CM_STR, "token");
                    if(success == 1) {
                        client.setToken(token);
                    }
                }


                if(ue.getStringID().equals("CREATE-TEAM")) {
                	// GETTER 호출 
                	//List<Application> applications = 지우님class.getApplications();
                }

                if(ue.getStringID().equals("GET-APPLICATIONS-REPLY")) {

                    int success = Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "success"));
                    if(success == 1) {
                        try {
                            String ret = ue.getEventField(CMInfo.CM_STR, "applications");
                            List<Application> applications =
                                    objectMapper.readValue(ret, objectMapper.getTypeFactory().constructCollectionType(List.class, Application.class));

                            for(Application application : applications) {
                                User user = application.getUser();
                                System.out.println(user);
                            }

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                    }

                }

                if(ue.getStringID().equals("GET-TEAM-REPLY")) {

                    Team team = null;
                    try {
                        String ret = ue.getEventField(CMInfo.CM_STR, "team");
                        team = objectMapper.readValue(ret, Team.class);
                        client.my_team = team;
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    String fileName = team.getFileName();
                    clientStub.requestFile("/kongee/ss.jpg", "SERVER");
                    //clientStub.requestFile(fileName, "SERVER");
                }
                if(ue.getStringID().equals("GET-TEAMS-REPLY")) {
                    try {
                        String ret = ue.getEventField(CMInfo.CM_STR, "team");
                        List<Team> teams = objectMapper.readValue(ret, objectMapper.getTypeFactory().constructCollectionType(List.class, Team.class));
                        for(Team team : teams)
                            System.out.println(team.getTeamLeader() == null);

                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                
                if(ue.getStringID().equals("GET-APPLICATIONS-REPLY")) {
                	  try {
                	      String ret = ue.getEventField(CMInfo.CM_STR, "applications");
                	      List<Application> applications = 
                					objectMapper.readValue(ret, objectMapper.getTypeFactory().constructCollectionType(List.class, Application.class));
                	  } catch (JsonProcessingException e) {
                	      e.printStackTrace();
                	  }
                }

                if(ue.getStringID().equals("GET-PROFILE-REPLY")) {

                    int success = Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "success"));
                    if(success == 1) {
                        Profile profile = null;
                        try {
                            String ret = ue.getEventField(CMInfo.CM_STR, "profile");
                            profile =
                                    objectMapper.readValue(ret, Profile.class);
                        } catch (JsonMappingException e) {
                            e.printStackTrace();
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        System.out.println(profile);
                        String fileName = profile.getFileName();
                        clientStub.requestFile("kongee/ss.jpg", "SERVER");
                    }
                    String msg = ue.getEventField(CMInfo.CM_STR, "msg");
                    System.out.println();

                }


                break;
            default:
                return;
        }
    }
}
