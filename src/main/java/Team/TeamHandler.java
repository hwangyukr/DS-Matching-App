package Team;

import Common.Result;
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

        TokenProvider.TokenResult validResult = getUserInfo(ue);

        if(validResult.getSuccess() != "성공하였습니다") {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            cmServerStub.send(ue, ue.getSender());
        }

        Result result = new Result();
        Team team = teamService.getTeam(validResult, result);

        cmServerStub.send(ue, ue.getSender());
    }

    public void createTeam(CMUserEvent ue) {

        TokenProvider.TokenResult validResult = getUserInfo(ue);
        String teamName = ue.getEventField(CMInfo.CM_STR, "team_name");

        /*
            토큰 validation 실패시 바로 reply
         */
        if(validResult.getSuccess() != "성공하였습니다") {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            cmServerStub.send(ue, ue.getSender());
        }

        Result result = new Result();
        Team team = teamService.createTeam(validResult, result, teamName);

        /*
            teamService에 파라미터로 result를 넣어줘서 에러가 나면
            result의 success를 false로 만들어주고 그 사유를 msg에 담아줌
            그래서 그렇게 리턴하면
         */
        if(result.isSuccess() == false) {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
            cmServerStub.send(ue, ue.getSender());
        }
        /*
            성공시에는 좋게좋게 ㅇㅋ?
         */
        ue.setEventField(CMInfo.CM_INT,"success", "1");
        ue.setEventField(CMInfo.CM_STR, "msg", "성공하였습니다");
        ue.setEventField(CMInfo.CM_STR, "team_id", String.valueOf(team.getId()));
        cmServerStub.send(ue, ue.getSender());
    }

    public void getApplications(CMUserEvent ue) {
        List<Application> applications = teamService.getApplications(ue);
    }

    public void processApplications(CMUserEvent ue) {
        Application application = teamService.processApplications(ue);
    }
}
