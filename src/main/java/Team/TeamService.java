package Team;

import Common.Result;
import Config.TokenProvider;
import User.User;
import User.UserRepository;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

import java.util.List;

public class TeamService {

    private CMInfo cmInfo;
    private TeamRepository teamRepository;
    private UserRepository userRepository;

    public TeamService(CMInfo cmInfo) {
        this.cmInfo = cmInfo;
        this.teamRepository = new TeamRepository();
        this.userRepository = new UserRepository();
    }

    public List<Team> getTeams(Result result) {
        return teamRepository.getTeams(result, cmInfo);
    }

    public Team getTeam(String teamName, Result result) {
        return teamRepository.getTeamByName(teamName, result, cmInfo);
    }

    public Team createTeam(TokenProvider.TokenResult validResult, Result result, String teamName) {

        User user = new User.Builder()
                .id(validResult.getId())
                .build();

        Team team = new Team.Builder()
                .name(teamName)
                .teamLeader(user)
                .teamRoles(null)
                .build();

        teamRepository.saveTeam(team, result, cmInfo);
        return team;
    }

    public List<Application> getApplications(TokenProvider.TokenResult validResult, Result result) {
        Long userId = validResult.getId();
        return teamRepository.getApplicationsByTeamId(userId, result, cmInfo);
    }

    public Application processApplications(CMUserEvent ue) {
        Long applicationId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "applicationId"));
        return teamRepository.processsApplication(applicationId, cmInfo);
    }

    public Long applyTeam(TokenProvider.TokenResult validResult, Result result, String teamName) {

        Long uesrId = validResult.getId();
        /*
            먼저 팀가져오기
         */
        Long teamId = teamRepository.getTeamIdByName(result, teamName, cmInfo);
        if(!result.isSuccess()) return null;

        Long applicationId = teamRepository.applyTeam(result, uesrId, teamId, cmInfo);
        return applicationId;

    }
}
