package Team;

import Board.Board;
import Board.BoardRepository;
import Common.Result;
import Config.TokenProvider;
import User.User;
import User.UserRepository;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

import java.util.List;
import java.util.Map;

public class TeamService {

    private CMInfo cmInfo;
    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private BoardRepository boardRepository;

    public TeamService(CMInfo cmInfo) {
        this.cmInfo = cmInfo;
        this.teamRepository = new TeamRepository();
        this.userRepository = new UserRepository();
        this.boardRepository = new BoardRepository();
    }

    public List<Team> getTeams(Result result) {
        return teamRepository.getTeams(result, cmInfo);
    }

    public Team getTeam(String teamName, Result result) {
        String query = teamRepository.getTeamQueryTeamName(teamName);
        Team team = teamRepository.getTeamByName(query, result, cmInfo);
        List<Board> boards = boardRepository.getBoards(team.getId(), result, cmInfo);
        team.setBoards(boards);
        return team;
    }

    public Team createTeam(TokenProvider.TokenResult validResult, Result result, String teamName, Map<Role, Integer> limit) {

        User user = new User.Builder()
                .id(validResult.getId())
                .build();

        Team team = new Team.Builder()
                .name(teamName)
                .teamLeader(user)
                .teamRoles(null)
                .roleLimits(limit)
                .build();

        teamRepository.saveTeam(team, result, cmInfo);
        return team;
    }

    public List<Application> getApplications(TokenProvider.TokenResult validResult, Result result) {
        Long userId = validResult.getId();
        return teamRepository.getApplicationsByTeamId(userId, result, cmInfo);
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

    public void processApplications(TokenProvider.TokenResult validResult, Result result, Long userId, Long teamId, Integer yesTeam) {

        String query = teamRepository.getTeamQueryTeamId(teamId);
        Team team = teamRepository.getTeamByName(query, result, cmInfo);

        if(!result.isSuccess()) {
            return;
        }
        if(team.getTeamLeader().getId() != validResult.getId()) {
            result.setMsg("지원서 수정 권한이 없습니다");
            result.setSuccess(false);
            return;
        }

        List<Application> applications = team.getApplications();
        Application app = null;

        for(Application application : applications) {
            if(application.getUser().getId() == userId) {
                application.setDidRead(true);
                app = application;
                break;
            }
        }

        teamRepository.updateApplicationandUser(app, result, yesTeam, cmInfo);

        if(!result.isSuccess()) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return;
        }
        result.setMsg("성공하였습니다");
        result.setSuccess(true);
    }
}
