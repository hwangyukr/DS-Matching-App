package User;

import Common.Result;
import Common.DBManager;
import Config.Transactional;
import Team.Role;
import Team.Team;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;
import lombok.Builder;

import java.sql.*;
public class UserRepository {

    /*
        @Transactional은 트랜잭션이 필요하는 마킹 애노테이션
     */
    @Transactional
    public void saveUser(User user, Result result, CMInfo cmInfo)  {

        Connection connection = null;
        Statement statement = null;

        try {

            /*
                CM에게 트랜잭션을 위임하기 보다는 직접 처리하기 위해 CMDBInfo에서
                Connection과 Statement를 가져오는 직접 작성한 DBManager 인스턴스 생
             */
            DBManager dbManager = DBManager.getConnection(cmInfo);
            connection = dbManager.getConnection();
            statement = dbManager.getStatement();
            connection.setAutoCommit(false);

            String query = "insert into user (user_name, password, user_email) values (" +
                    " '"+ user.getName() + "'," +
                    " '" + user.getPassword() + "'," +
                    " '" + user.getEmail() + "');";

            /*
                에러가 나면 에러처리
             */
            int ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();

            query = "select * from user where user_email = '" + user.getEmail() + "';";
            ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);

            while(resultSet.next()) {
                user.setId(resultSet.getLong("user_id"));
            }
            connection.commit();

        } catch (SQLException e) {
            try {
                /*
                   에러나면 트랜잭션 롤백
                 */
                connection.rollback();
                result.setSuccess(false);
                result.setMsg("실패하였습니다");
            } catch (SQLException ex) {
                return;
            }
        }

        result.setSuccess(true);
        result.setMsg("성공하였습니다");
    }

    public User findByEmail(String email, Result result, CMInfo cmInfo) {
        String getQuery = "select * from user where user_email = '" + email + "';";
        ResultSet resultSet = CMDBManager.sendSelectQuery(getQuery, cmInfo);
        User user = null;
        Team team = null;

        try {
            while(resultSet.next()) {
                Long id = resultSet.getLong("user_id");
                Long teamId = resultSet.getLong("team_id");
                String password = resultSet.getString("password");
                team = new Team.Builder()
                        .id(teamId)
                        .build();

                user = new User.Builder()
                        .id(id)
                        .team(team)
                        .password(password)
                        .email(email)
                        .build();
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMsg("실패하였습니다");
            return null;
        }
        result.setSuccess(true);
        result.setMsg("성공하였습니다");
        return user;
    }

    public Profile getProfileByUserId(Long id, CMInfo cmInfo) {
        return null;
    }

    public User findById(Long userId, CMInfo cmInfo) {
        return null;
    }

    public User getUser(Result result, Long userId, CMInfo cmInfo) {
        String getQuery = "select * from user u , role r where u.role_id = r.role_id and u.user_id = '" + userId + "';";
        ResultSet resultSet = CMDBManager.sendSelectQuery(getQuery, cmInfo);
        User user = null;
        try {
            while(resultSet.next()) {

                Long id = resultSet.getLong("user_id");
                String email = resultSet.getString("user_email");
                String name = resultSet.getString("user_name");
                Long team_id = resultSet.getLong("team_id");
                Role role = Role.valueOf(resultSet.getString("role"));

                user = new User.Builder()
                        .id(id)
                        .email(email)
                        .name(name)
                        .role(role)
                        .team(new Team.Builder().id(team_id).build())
                        .build();
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMsg("실패하였습니다");
            return null;
        }
        result.setSuccess(true);
        result.setMsg("성공하였습니다");
        return user;
    }
}
