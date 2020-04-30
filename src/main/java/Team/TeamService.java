package Team;

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

        Long userId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "userId"));
        User user = userRepository.findById(userId, cmInfo);
        Team team = getTeam(ue);

        Application application = new Application.Builder()
                .team(team)
                .user(user)
                .build();

        return teamRepository.saveApplication(application, cmInfo);
    }

    public Team getTeam(CMUserEvent ue) {
        Long teamId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "teamId"));
        return teamRepository.getTeamById(teamId, cmInfo);
    }

    public Team createTeam(CMUserEvent ue) {

        String name = ue.getEventField(CMInfo.CM_STR, "name");
        Long userId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "userId"));

        // TODO 이미 있는놈인지 확인

        User teamLeader = userRepository.findById(userId, cmInfo);
        //private final List<TeamRole> teamRoles; //TODO 리스트형식의 데이터는 어떻게 주고 받을지 결

        Team team = new Team.Builder()
                .name(name)
                .teamLeader(teamLeader)
                .teamRoles(null)
                .build();

        return teamRepository.saveTeam(team, cmInfo);
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
