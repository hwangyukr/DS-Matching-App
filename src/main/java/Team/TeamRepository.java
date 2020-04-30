package Team;

import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

import java.util.List;

public class TeamRepository extends CMDBManager {

    public List<Team> getTeams(Boolean flag, CMInfo cmInfo) {
        return null;
    }

    public Application saveApplication(Application application, CMInfo cmInfo) {
        return null;

    }

    public Team getTeamById(Long teamId, CMInfo cmInfo) {
        return null;

    }

    public Team saveTeam(Team team, CMInfo cmInfo) {
        return null;

    }

    public List<Application> getApplicationsByTeamId(Long teamId, CMInfo cmInfo) {
        return null;

    }

    public Application processsApplication(Long applicationId, CMInfo cmInfo) {
        return null;

    }
}
