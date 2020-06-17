package Team;

import Board.BoardRepository;
import Common.DBManager;
import Common.Result;
import Config.Transactional;
import User.User;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

import java.sql.*;
import java.util.*;

public class TeamRepository {

    public List<Team> getTeams(Result result, CMInfo cmInfo) {

        String query =
                "select u.user_id, u.user_name, u.user_email, r.role, t.team_id, t.team_name, t.file_name, t.original_file_name " +
                "from user u, team t, role r " +
                "where u.team_id = t.team_id and u.role_id = r.role_id;";
        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        HashMap<Long, Team> teams = new HashMap<>();

        try {
            while(resultSet.next()) {

                Team team;
                Long teamId = resultSet.getLong("team_id");
                String fileName = resultSet.getString("file_name");
                String originalFileName = resultSet.getString("original_file_name");

                if(teams.get(teamId) == null) {
                    String teamName = resultSet.getString("team_name");
                    team = new Team.Builder()
                            .id(teamId)
                            .name(teamName)
                            .fileName(fileName)
                            .originalFileName(originalFileName)
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

            for(Map.Entry<Long, Team> team : teams.entrySet()) {
                query = "select tr.team_id, tr.role, tr.role_limit " +
                        "from team_role tr, team t " +
                        "where tr.team_id = t.team_id; ;";

                resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
                while(resultSet.next()) {
                    Long teamId = resultSet.getLong("team_id");
                    System.out.println(teamId);
                    if (teams.get(teamId) == null) continue;
                    Role role = Role.valueOf(resultSet.getString("role"));
                    int limit = resultSet.getInt("role_limit");
                    Map<Role, Integer> roleLimits = teams.get(teamId).getRoleLimits();
                    roleLimits.put(role, limit);
                }
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

    public Team getTeamByName(String query, Result result, CMInfo cmInfo) {

        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        Team team = new Team();
        List<User> members = team.getUsers();
        Map<Role, Integer> roles = team.getCurrentRoles();
        User teamLeader = new User();

        try {
            while(resultSet.next()) {

                Long id = resultSet.getLong("user_id");
                Long teamLeader_id = resultSet.getLong("team_leader");
                String fileName = resultSet.getString("file_name");
                String originalFileName = resultSet.getString("original_file_name");
                String email = resultSet.getString("user_email");
                String userName = resultSet.getString("user_name");

                Role role = Role.valueOf(resultSet.getString("role"));
                if(roles.get(role) == null) roles.put(role, 1);
                else roles.put(role, roles.get(role) + 1);

                teamLeader.setId(teamLeader_id);

                User user = new User.Builder()
                        .id(id)
                        .email(email)
                        .name(userName)
                        .role(role)
                        .build();

                members.add(user);

                Long teamId = resultSet.getLong("team_id");
                String teamName1 = resultSet.getString("team_name");
                team.setFileName(fileName);
                team.setOriginalFileName(originalFileName);
                team.setName(teamName1);
                team.setId(teamId);
            }

            query = "select tr.role, tr.role_limit " +
                    "from team_role tr, team t " +
                    "where tr.team_id = t.team_id " +
                    "and tr.team_id = '" + team.getId() + "';";
            resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
            Map<Role, Integer> roleLimits = team.getRoleLimits();

            while(resultSet.next()) {
                Role role = Role.valueOf(resultSet.getString("role"));
                int limit = resultSet.getInt("role_limit");
                roleLimits.put(role, limit);
            }
            team.setRoleLimits(roleLimits);

        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }
        /*
            teamLeader 가져오는 쿼리
         */
        query = "select * from user where user_id = '" + teamLeader.getId() + "';";
        resultSet = CMDBManager.sendSelectQuery(query, cmInfo);

        try {
            while(resultSet.next()) {
                String userName = resultSet.getString("user_name");
                String email = resultSet.getString("user_email");
                teamLeader.setName(userName);
                teamLeader.setEmail(email);
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
        team.setTeamLeader(teamLeader);
        return team;
    }

    public String getTeamQueryTeamName(String teamName) {
        String query = "select u.user_id, u.user_name, u.user_email, r.role, sq.file_name, sq.original_file_name, sq.team_id, sq.team_name , sq.team_leader " +
                "from user u, role r, (" +
                "select team.team_id, team.team_name, team.team_leader, team.file_name, team.original_file_name " +
                "from team " +
                "where team.team_name = '" + teamName + "' " +
                ") sq " +
                "where sq.team_id = u.team_id and u.role_id = r.role_id;";
        System.out.println(query);
        return query;
    }

    public String getTeamQueryTeamId(Long id) {
        String query = "select u.user_id, u.user_name, u.user_email, r.role, sq.file_name, sq.original_file_name, sq.team_id, sq.team_name , sq.team_leader " +
                "from user u, role r, (" +
                "select team.team_id, team.team_name, team.team_leader, team.file_name, team.original_file_name " +
                "from team " +
                "where team.team_id = '" + id + "' " +
                ") sq " +
                "where sq.team_id = u.team_id and u.role_id = r.role_id;";
        return query;
    }

    @Transactional
    public long saveTeam(Team team, Result result, CMInfo cmInfo) {

        Connection connection = null;
        Statement statement = null;

        try {

            DBManager dbManager = DBManager.getConnection(cmInfo);
            connection = dbManager.getConnection();
            statement = dbManager.getStatement();
            connection.setAutoCommit(false);

            String query =
                    "insert into team(team_name, file_name, original_file_name, team_leader) values (" +
                            " '" + team.getName() + "'," +
                            " '" + team.getFileName() + "'," +
                            " '" + team.getOriginalFileName() + "'," +
                            " '" + team.getTeamLeader().getId() + "');";

            /*
                에러가 나면 에러처리
             */
            int ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();

            String getQuery = "select * from team where team_leader = '" + team.getTeamLeader().getId() + "';";
            ResultSet resultSet = CMDBManager.sendSelectQuery(getQuery, cmInfo);
            Long id = -9999l;
            while(resultSet.next()) {
                id = resultSet.getLong("team_id");
                team.setId(id);
            }

            /*
                팀 리더의 유저의 팀도 업데이트 해줘야됨
             */
            query = "update user set team_id = '" + id + "' where user_id = '" + team.getTeamLeader().getId() + "';";
            ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();

            /*
                팀 롤별 제한 정보 업데이트
             */
            Map<Role, Integer> teamLimits = team.getRoleLimits();
            for(Map.Entry<Role, Integer> entry : teamLimits.entrySet()) {

                query = "insert into team_role(team_id, role, role_limit) values (" +
                        " '" + team.getId()  + "'," +
                        " '" + entry.getKey()  + "'," +
                        " '" + entry.getValue() + "');";
                ret = statement.executeUpdate(query);
                if(ret != 1) throw new SQLException();
            }

            team.setId(id);
            connection.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("이미 존재하는 팀명입니다");
            result.setSuccess(false);
            return -1l;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return -1l;
        }

        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return team.getId();
    }

    public List<Application> getApplicationsByTeamId(Long userId, Result result, CMInfo cmInfo) {

        String query =
                "select u.user_name, u.user_email, r.role, a.user_id, a.team_id, a.didRead, a.application_id " +
                        "from user u, application a, role r, ( " +
                        "select t.team_id " +
                        "    from user u, team t " +
                        "    where u.team_id = t.team_id " +
                        "    and u.user_id = '" + userId + "' " +
                        ") t " +
                        "where t.team_id = a.team_id " +
                        "and u.user_id = a.user_id " +
                        "and r.role_id = u.role_id " +
                        "and a.didRead = 0;";
        System.out.println(query);
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

    @Transactional
    public Long applyTeam(Result result, Long userId, Long teamId, CMInfo cmInfo) {

        Connection connection = null;
        Statement statement = null;
        Long applicationId = null;

        try {

            DBManager dbManager = DBManager.getConnection(cmInfo);
            connection = dbManager.getConnection();
            statement = dbManager.getStatement();
            connection.setAutoCommit(false);

            String query = "insert into application (user_id, team_id) values ('"
                    + userId + "', '" + teamId + "');";
            int ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();

            query = "select * from application where user_id = '" + userId + "' and team_id = '" + teamId + "';";
            ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);

            while(resultSet.next()) {
                applicationId = resultSet.getLong("application_id");
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }
        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return applicationId;
    }


    @Transactional
    public void updateApplicationandUser(Application app, Result result, Integer yesTeam, CMInfo cmInfo) {

        Connection connection = null;
        Statement statement = null;

        try {

            DBManager dbManager = DBManager.getConnection(cmInfo);
            connection = dbManager.getConnection();
            statement = dbManager.getStatement();
            connection.setAutoCommit(false);

            Long id = app.getId();
            Long userId = app.getUser().getId();
            Long teamId = app.getTeam().getId();

            String query = "update application set didRead = 1 where " +
                            "application_id = '" + id + "';";

            int ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();

            if(yesTeam == 1) {
                query = "update user set team_id = '" + teamId + "' where " +
                        "user_id = '" + userId + "';";

                ret = statement.executeUpdate(query);
                if(ret != 1) throw new SQLException();
            }

            connection.commit();

        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setSuccess(false);
            result.setMsg("실패하였습니다");
        }

    }
}














