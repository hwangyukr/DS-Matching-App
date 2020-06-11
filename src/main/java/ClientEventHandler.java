import Team.Team;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
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

    public ClientEventHandler(CMClientStub clientStub, Client client) {
        this.clientStub = clientStub;
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void processEvent(CMEvent cmEvent)  {

        switch (cmEvent.getType()) {
            case CMInfo.CM_USER_EVENT:
                CMUserEvent ue = (CMUserEvent) cmEvent;

                if(ue.getStringID().equals("SIGN-IN-REPLY")) {
                    System.out.println("=======================");
                    int success = Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "success"));
                    String token = ue.getEventField(CMInfo.CM_STR, "token");

                    if(success == 1) {
                        client.setToken(token);
                    }
                }

                if(ue.getStringID().equals("CREATE-TEAM")) {

                }

                if(ue.getStringID().equals("GET-TEAM-REPLY")) {
                    try {
                        String ret = ue.getEventField(CMInfo.CM_STR, "team");
                        Team team = null;
                        team = objectMapper.readValue(ret, Team.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
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

                break;
            default:
                return;
        }
    }
}
