package Team;

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

    public List<Team> getTeams(CMUserEvent ue) {
        Boolean flag = Boolean.valueOf(ue.getEventField(CMInfo.CM_INT, "flag"));
        return teamRepository.getTeams(flag, cmInfo);
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

    public Team getTeam(TokenProvider.TokenResult result) {
        Long userId = result.getId();
        return teamRepository.getTeamById(userId, cmInfo);
    }

    public Team createTeam(TokenProvider.TokenResult result, String teamName) {

        User user = new User.Builder()
                .id(result.getId())
                .build();

        Team team = new Team.Builder()
                .name(teamName)
                .teamLeader(user)
                .teamRoles(null)
                .build();

        int ret = teamRepository.saveTeam(team, cmInfo);

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
