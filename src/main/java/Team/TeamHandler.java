package Team;

import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
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

        List<Team> teams = teamService.getTeams(ue);

    }

    public void applyTeam(CMUserEvent ue) {
        Application application = teamService.applyTeam(ue);
    }

    public void getTeam(CMUserEvent ue) {
        Team team = teamService.getTeam(ue);
    }

    public void createTeam(CMUserEvent ue) {
        Team team = teamService.createTeam(ue);
    }

    public void getApplications(CMUserEvent ue) {
        List<Application> applications = teamService.getApplications(ue);
    }

    public void processApplications(CMUserEvent ue) {
        Application application = teamService.processApplications(ue);
    }
}
