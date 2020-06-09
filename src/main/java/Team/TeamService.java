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

    public Application applyTeam(CMUserEvent ue) {
        return null;
//        Long userId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "userId"));
//        User user = userRepository.findById(userId, cmInfo);
//        Team team = getTeam(ue);
//
//        Application application = new Application.Builder()
//                .team(team)
//                .user(user)
//                .build();
//
//        return teamRepository.saveApplication(application, cmInfo);
    }

    public Team getTeam(TokenProvider.TokenResult validResult, Result result) {
        Long userId = validResult.getId();
        return teamRepository.getTeamById(userId, result, cmInfo);
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

    public List<Application> getApplications(CMUserEvent ue) {
        Long teamId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "teamId"));
        return teamRepository.getApplicationsByTeamId(teamId, cmInfo);
    }

    public Application processApplications(CMUserEvent ue) {
        Long applicationId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "applicationId"));
        return teamRepository.processsApplication(applicationId, cmInfo);
    }
}
