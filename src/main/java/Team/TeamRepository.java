package Team;

import Common.Result;
import User.User;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class TeamRepository extends CMDBManager {

    public List<Team> getTeams(Boolean flag, CMInfo cmInfo) {
        return null;
    }

    public Application saveApplication(Application application, CMInfo cmInfo) {
        return null;

    }

    public Team getTeamById(Long userId, Result result, CMInfo cmInfo) {

        /*
        먼저 팀을 가져
         */
        String query = "select * from user " +
                " left outer join team on user.team_id = team.id and user.id = '"
                + userId + "';";

        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        User user = null;
        Long teamId = 0l;

        try {
            while(resultSet.next()) {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                String userName = resultSet.getString("username");
                teamId = resultSet.getLong("team_id");

                user = new User.Builder()
                        .id(id)
                        .password(password)
                        .email(email)
                        .build();
            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }

        /*
       쿼리를 날렸는데 teamid가 안변한 건 팀이 없는거임 그래서 result를 false로 세팅 후 리턴
         */
        if(teamId == 0l) {
            result.setMsg("속한 팀이 없습니");
            result.setSuccess(false);
            return null;
        }

        Team team = new Team.Builder()
                .id(teamId)
                .build();
        List<User> teams = team.getUsers();

        query = "select * from user, team " +
                " where user.team_id = team.id and team.id = '"
                + teamId + "';";

        resultSet = CMDBManager.sendSelectQuery(query, cmInfo);

        try {
            while(resultSet.next()) {
                Long id = resultSet.getLong("user_id");
                String userName = resultSet.getString("user_name");
                String email = resultSet.getString("user_email");
                Long leader = resultSet.getLong("team_leader");
                User user1 = new User.Builder()
                        .email(email)
                        .id(id)
                        .email(email)
                        .name(userName)
                        .build();
                teams.add(user1);
            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }

        return team;
    }

    public long saveTeam(Team team, Result result, CMInfo cmInfo) {

        String query =
                "insert into team(team_name, team_leader) values (" +
                        " '" + team.getName() + "'," +
                        " '" + team.getTeamLeader().getId() + "');";

        int ret = CMDBManager.sendUpdateQuery(query, cmInfo);

        if(ret == -1) return -1l;

        String getQuery = "select * from team where team_leader = '" + team.getTeamLeader().getId() + "';";
        ResultSet resultSet = CMDBManager.sendSelectQuery(getQuery, cmInfo);
        Long id = -9999l;
        try {
            while(resultSet.next()) {
                id = resultSet.getLong("team_id");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            result.setMsg("이미 존재하는 팀명입니다");
            result.setSuccess(false);
            return -1l;
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return -1l;
        }
        team.setId(id);
        return id;
    }

    public List<Application> getApplicationsByTeamId(Long teamId, CMInfo cmInfo) {
        return null;

    }

    public Application processsApplication(Long applicationId, CMInfo cmInfo) {
        return null;

    }
}
