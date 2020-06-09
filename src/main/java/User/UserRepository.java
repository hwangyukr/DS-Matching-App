package User;

import Common.Result;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository extends CMDBManager {

    public int saveUser(User user, Result result, CMInfo cmInfo)  {

        String strQuery = "insert into user (user_name, password, user_email) values (" +
                " '"+ user.getName() + "'," +
                " '" + user.getPassword() + "'," +
                " '" + user.getEmail() + "');";

        int ret = CMDBManager.sendUpdateQuery(strQuery, cmInfo);

        if(ret == -1) {
            result.setSuccess(false);
            result.setMsg("실패하였습니");
            return ret;
        }

        String getQuery = "select * from user where user_email = '" + user.getEmail() + "';";
        ResultSet resultSet = CMDBManager.sendSelectQuery(getQuery, cmInfo);
        Long id = -9999l;
        try {
            while(resultSet.next()) {
                id = resultSet.getLong("user_id");
            }
        } catch (SQLException e) {
            result.setSuccess(false);
            result.setMsg("실패하였습니다");
            return ret;
        }
        result.setSuccess(true);
        result.setMsg("성공하였습니다");
        user.setId(id);
        return ret;
    }

    public User findByEmail(String email, CMInfo cmInfo) {
        String getQuery = "select * from user where user_email = '" + email + "';";
        ResultSet resultSet = CMDBManager.sendSelectQuery(getQuery, cmInfo);
        User user = null;
        try {
            while(resultSet.next()) {
                Long id = resultSet.getLong("user_id");
                String password = resultSet.getString("password");
                user = new User.Builder()
                        .id(id)
                        .password(password)
                        .email(email)
                        .build();
            }
        } catch (SQLException e) {
            return null;
        }
        return user;
    }

    public Profile getProfileByUserId(Long id, CMInfo cmInfo) {
        return null;
    }

    public User findById(Long userId, CMInfo cmInfo) {
        return null;
    }
}
