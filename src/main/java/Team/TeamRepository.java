package Team;

import Common.Result;
import User.User;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

public class TeamRepository extends CMDBManager {

    public List<Team> getTeams(Result result, CMInfo cmInfo) {

        String query =
                "select u.user_id, u.user_name, u.user_email, r.role, t.team_id, t.team_name " +
                "from user u, team t, role r " +
                "where u.team_id = t.team_id and u.role_id = r.role_id;";
        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        HashMap<Long, Team> teams = new HashMap<>();

        try {
            while(resultSet.next()) {

                Team team;
                Long teamId = resultSet.getLong("team_id");
                if(teams.get(teamId) == null) {
                    String teamName = resultSet.getString("team_name");
                    team = new Team.Builder()
                            .id(teamId)
                            .name(teamName)
                            .build();
                }
                else team = teams.get(teamId);

                Map<Role, Integer> roles = team.getCurrentRoles();
                List<User> members = team.getUsers();

                Role role = Role.valueOf(resultSet.getString("role"));
                if(roles.get(role) == null) roles.put(role, 1);
                else roles.put(role, roles.get(role) + 1);

                Long id = resultSet.getLong("user_id");
                String email = resultSet.getString("user_email");
                String userName = resultSet.getString("user_name");
                User user = new User.Builder()
                        .id(id)
                        .email(email)
                        .name(userName)
                        .role(role)
                        .build();

                members.add(user);
                teams.put(teamId, team);

            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }

        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return new ArrayList<Team>(teams.values());
    }

    public Application saveApplication(Application application, CMInfo cmInfo) {

        return null;
    }


    public Team getTeamByName(String teamName, Result result, CMInfo cmInfo) {

        /*
        팀 가져오기
         */
        String query = "select u.user_id, u.user_name, u.user_email, r.role, sq.team_id, sq.team_name " +
                "from user u, role r, (" +
                "select team.team_id, team.team_name " +
                "from user, team " +
                "where user.team_id = team.team_id " +
                "and team.team_id = '" + teamName + "' " +
                ") sq " +
                "where sq.team_id = u.team_id and u.role_id = r.role_id;";

        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        Team team = new Team();
        List<User> members = team.getUsers();
        Map<Role, Integer> roles = team.getCurrentRoles();

        try {
            while(resultSet.next()) {

                Long id = resultSet.getLong("user_id");
                String email = resultSet.getString("user_email");
                String userName = resultSet.getString("user_name");

                Role role = Role.valueOf(resultSet.getString("role"));
                if(roles.get(role) == null) roles.put(role, 1);
                else roles.put(role, roles.get(role) + 1);

                User user = new User.Builder()
                        .id(id)
                        .email(email)
                        .name(userName)
                        .role(role)
                        .build();

                members.add(user);

                Long teamId = resultSet.getLong("team_id");
                String teamName1 = resultSet.getString("team_name");
                team.setName(teamName1);
                team.setId(teamId);
            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }

        /*
        Application들 가져오는 쿼리
         */
        query = "select * from user, application where " +
                "user.user_id = application.user_id and application.team_id = '" +
                team.getId() + "' and application.didRead = '0';";
        resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        List<Application> applications = team.getApplications();
        try {
            while(resultSet.next()) {
                int id = resultSet.getInt("application_id");
                Long uid = resultSet.getLong("user_id");
                String userName = resultSet.getString("user_name");
                String email = resultSet.getString("user_email");

                User user1 = new User.Builder()
                        .id(uid)
                        .name(userName)
                        .email(email)
                        .build();

                Application application = new Application.Builder()
                        .user(user1)
                        .team(team)
                        .id(Long.valueOf(id))
                        .build();
                applications.add(application);
            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }
        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return team;
    }

    public long saveTeam(Team team, Result result, CMInfo cmInfo) {

        String query =
                "insert into team(team_name, team_leader) values (" +
                        " '" + team.getName() + "'," +
                        " '" + team.getTeamLeader().getId() + "');";

        int ret = CMDBManager.sendUpdateQuery(query, cmInfo);

        if(ret == -1) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return -1l;
        }

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

        /*
            팀 리더의 유저의 팀도 업데이트 해줘야됨
         */
        query = "update user set team_id = '" + id + "' where user_id = '" + team.getTeamLeader().getId() + "';";
        ret = CMDBManager.sendUpdateQuery(query, cmInfo);

        if(ret == -1) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return -1l;
        }

        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        team.setId(id);
        return id;
    }
    public List<Application> getApplicationsByTeamId(Long userId, Result result, CMInfo cmInfo) {

        String query =
                "select u.user_name, u.user_email, r.role, a.user_id, a.team_id, a.didRead " +
                        "from user u, application a, role r, ( " +
                        "select t.team_id " +
                        "    from user u, team t " +
                        "    where u.team_id = t.team_id " +
                        "    and u.user_id = '" + userId + "' " +
                        ") t" +
                        "where t.team_id = a.team_id " +
                        "and u.user_id = a.user_id " +
                        "and r.role_id = u.role_id " +
                        "and a.didRead = 0;";

        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        Team team = new Team();
        List<Application> applications = team.getApplications();
        try {
            while(resultSet.next()) {

                Long teamId = resultSet.getLong("team_id");
                Long id = resultSet.getLong("user_id");
                String email = resultSet.getString("user_email");
                String userName = resultSet.getString("user_name");
                Role role = Role.valueOf(resultSet.getString("role"));
                Long applicationId = resultSet.getLong("application_id");

                team.setId(id);

                User user = new User.Builder()
                        .id(id)
                        .email(email)
                        .name(userName)
                        .role(role)
                        .build();

                Application application = new Application.Builder()
                        .user(user)
                        .team(team)
                        .id(applicationId)
                        .build();

                applications.add(application);

            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }
        result.setSuccess(true);
        result.setMsg("성공하였습니다");
        return applications;
    }

    public Application processsApplication(Long applicationId, CMInfo cmInfo) {
        return null;

    }

    public Long getTeamIdByName(Result result, String teamName, CMInfo cmInfo) {
        String query = "select * from team where team_name = '" + teamName + "';";
        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        Long teamId = null;
        try {
            while(resultSet.next()) {
                teamId = resultSet.getLong("team_id");
            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }
        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return teamId;
    }

    public Long applyTeam(Result result, Long userId, Long teamId, CMInfo cmInfo) {
        String query = "insert into application (user_id, team_id) values ('"
                + userId + "', '" + teamId + "');";
        int ret = CMDBManager.sendUpdateQuery(query, cmInfo);

        if(ret == -1) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }

        query = "select * from application where user_id = '" + userId + "' and team_id = '" + teamId + "';";
        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        Long applicationId = null;
        try {
            while(resultSet.next()) {
                 applicationId = resultSet.getLong("application_id");
            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }
        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return applicationId;
    }
}














