package Team;

import User.User;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class TeamRepository extends CMDBManager {

    public List<Team> getTeams(Boolean flag, CMInfo cmInfo) {
        return null;
    }

    public Application saveApplication(Application application, CMInfo cmInfo) {
        return null;

    }

    public Team getTeamById(Long userId, CMInfo cmInfo) {
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
            return null;
        }

        return team;
    }

    public int saveTeam(Team team, CMInfo cmInfo) {

        String query =
                "insert into team(team_name, team_leader) values (" +
                        " '" + team.getName() + "'," +
                        " '" + team.getTeamLeader().getId() + "');";
        System.out.println("쿼리 이거임 !!!");
        System.out.println(query);
        int ret = CMDBManager.sendUpdateQuery(query, cmInfo);

        if(ret == -1) return ret;

        String getQuery = "select * from team where team_leader = '" + team.getTeamLeader().getId() + "';";
        ResultSet resultSet = CMDBManager.sendSelectQuery(getQuery, cmInfo);
        Long id = -9999l;
        try {
            while(resultSet.next()) {
                id = resultSet.getLong("team_id");
            }
        } catch (SQLException e) {
            return -1;
        }
        team.setId(id);
        return ret;



    }

    public List<Application> getApplicationsByTeamId(Long teamId, CMInfo cmInfo) {
        return null;

    }

    public Application processsApplication(Long applicationId, CMInfo cmInfo) {
        return null;

    }
}
