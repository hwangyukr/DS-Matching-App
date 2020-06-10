package Team;

import Common.Result;
import Config.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEventField;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import java.util.List;
import java.util.Vector;

public class TeamHandler<T> {
    private CMServerStub cmServerStub;
    private TeamService teamService;
    private ObjectMapper objectMapper;

    public TeamHandler(CMServerStub cmServerStub) {
        this.cmServerStub = cmServerStub;
        this.teamService = new TeamService(cmServerStub.getCMInfo());
        this.objectMapper = new ObjectMapper();
    }

    /*
        토큰 validation 확인 후 오류 시 에러 처리
     */
    public TokenProvider.TokenResult getUserInfo(CMUserEvent ue) {
        String token = ue.getEventField(CMInfo.CM_STR, "token");
        if(token == null){
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", "NOT AUTHORIZED");
            cmServerStub.send(ue, ue.getSender());
            return null;
        }
        return TokenProvider.validateToken(token);
    }

    public void handleError(Result result, CMUserEvent ue) {
        ue.setEventField(CMInfo.CM_INT, "success", "0");
        ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
        cmServerStub.send(ue, ue.getSender());
    }

    public void getTeams(CMUserEvent ue) {

        ue.setStringID("GET-TEAMS-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Result result = new Result();
        List<Team> teams = teamService.getTeams(result);

        /*
            result값이 false이면 그에 대한 에러 메시지 처리
         */
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        try {
            String ret = objectMapper.writeValueAsString(teams);
            ue.setEventField(CMInfo.CM_INT, "success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            ue.setEventField(CMInfo.CM_STR, "team", ret);
            cmServerStub.send(ue, ue.getSender());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void applyTeam(CMUserEvent ue) {

        ue.setStringID("APPLY-TEAM-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Result result = new Result();
        String teamName = ue.getEventField(CMInfo.CM_STR, "team_name");
        if(teamName == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }
        Long id = teamService.applyTeam(validResult, result, teamName);

        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }
        ue.setEventField(CMInfo.CM_INT, "success", "1");
        ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
        ue.setEventField(CMInfo.CM_LONG, "application_id", String.valueOf(id));
        cmServerStub.send(ue, ue.getSender());
    }

    public void getTeam(CMUserEvent ue) {

        ue.setStringID("GET-TEAM-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        String teamName = ue.getEventField(CMInfo.CM_STR, "team_name");
        if(teamName == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }

        Result result = new Result();
        Team team = teamService.getTeam(teamName, result);

        /*
            result값이 false이면 그에 대한 에러 메시지 처리
         */
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        /*
            올바른 값인 경우에 제대로 처리해줌
         */
        try {
            String ret = objectMapper.writeValueAsString(team);
            ue.setEventField(CMInfo.CM_INT, "success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            ue.setEventField(CMInfo.CM_STR, "team", ret);
            cmServerStub.send(ue, ue.getSender());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void createTeam(CMUserEvent ue) {

        ue.setStringID("CREATE-TEAM-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        String teamName = ue.getEventField(CMInfo.CM_STR, "team_name");
        if(teamName == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }
        Result result = new Result();
        Team team = teamService.createTeam(validResult, result, teamName);

        /*
            teamService에 파라미터로 result를 넣어줘서 에러가 나면
            result의 success를 false로 만들어주고 그 사유를 msg에 담아줌
            그래서 그렇게 리턴하면
         */
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
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

        ue.setStringID("GET-APPLICATIONS-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Result result = new Result();
        List<Application> applications = teamService.getApplications(validResult,result);

        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        try {
            String ret = objectMapper.writeValueAsString(applications);
            ue.setEventField(CMInfo.CM_INT, "success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            ue.setEventField(CMInfo.CM_STR, "applications", ret);
            cmServerStub.send(ue, ue.getSender());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public void processApplications(CMUserEvent ue) {
        ue.setStringID("PROCESS-APPLICATION-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Long userId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "user_id"));
        Long teamId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "team_id"));
        Integer yesTeam = Integer.valueOf(ue.getEventField(CMInfo.CM_INT, "yesTeam"));

        if(userId == null || teamId == null || yesTeam == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }
        Result result = new Result();
        teamService.processApplications(validResult, result, userId, teamId, yesTeam);

        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }
        ue.setEventField(CMInfo.CM_INT, "success", "1");
        ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
        cmServerStub.send(ue, ue.getSender());
    }
}





















