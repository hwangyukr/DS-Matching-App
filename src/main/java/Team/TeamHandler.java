package Team;

import Config.TokenProvider;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import java.util.List;

public class TeamHandler {
    private CMServerStub cmServerStub;
    private TeamService teamService;

    public TeamHandler(CMServerStub cmServerStub) {
        this.cmServerStub = cmServerStub;
        this.teamService = new TeamService(cmServerStub.getCMInfo());
    }

    public void getTeams(CMUserEvent ue) {

    }

    public void applyTeam(CMUserEvent ue) {
        Application application = teamService.applyTeam(ue);
    }

    public TokenProvider.TokenResult getUserInfo(CMUserEvent ue) {
        String token = ue.getEventField(CMInfo.CM_STR, "token");
        return TokenProvider.validateToken(token);
    }

    public void getTeam(CMUserEvent ue) {

        TokenProvider.TokenResult result
                = getUserInfo(ue);

        if(result.getSuccess() != "성공하였습니다") {
            ue.setEventField(CMInfo.CM_INT, "success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", result.getSuccess());
        }
        else {
            Team team = teamService.getTeam(result);
        }
        cmServerStub.send(ue, ue.getSender());
    }

    public void createTeam(CMUserEvent ue) {
        TokenProvider.TokenResult result = getUserInfo(ue);
        String teamName = ue.getEventField(CMInfo.CM_STR, "team_name");
        Team team = teamService.createTeam(result, teamName);
    }

    public void getApplications(CMUserEvent ue) {
        List<Application> applications = teamService.getApplications(ue);
    }

    public void processApplications(CMUserEvent ue) {
        Application application = teamService.processApplications(ue);
    }
}
