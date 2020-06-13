package User;

import Common.Result;
import Common.DBManager;
import Config.Transactional;
import Team.Role;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

import java.sql.*;

public class ProfileRepository {
    public Profile getProfileByUserId(Long userId, Result result, CMInfo cmInfo) {
    	String query =
    			"select p.*, r.role " +
    			"from profile p, role r " +
    			"where p.user_id = '" + userId + "' " +
    			"and p.role_id = r.role_id;";
    	ResultSet res = CMDBManager.sendSelectQuery(query, cmInfo);
    	
    	Profile profile = null;
    	try {
			while(res.next()) {
				Long id = res.getLong("profile_id");
				User user = new User.Builder()
						.id(res.getLong("user_id"))
						.build();
				Role role = Role.valueOf(res.getString("role"));
				String content = res.getString("content");
				String photo = res.getString("photo");
				String portforlio = res.getString("portforlio");
				String fileName = res.getString("file_name");
				profile = new Profile.Builder()
						.id(id)
						.user(user)
						.role(role)
						.content(content)
						.photo(photo)
						.portforlio(portforlio)
						.fileName(fileName)
						.build();
			}
		} catch (SQLException e) {
            result.setSuccess(false);
            result.setMsg("실패하였습니다");
            return null;
		}
        result.setSuccess(true);
        result.setMsg("성공하였습니다");
        return profile;
    }
    
    @Transactional
    public Long postProfile(Profile profile, Result result, CMInfo cmInfo) {
        Connection connection = null;
        Statement statement = null;
        
        try {
            DBManager dbManager = DBManager.getConnection(cmInfo);
            connection = dbManager.getConnection();
            statement = dbManager.getStatement();
            connection.setAutoCommit(false);
            String query =
            		"insert into profile(user_id, role_id, content, photo, portforlio, file_name) values (" +
            				"'" + profile.getUser().getId() + "', " +
            				"'" + (profile.getRole().ordinal()+1) + "', " +
            				"'" + profile.getContent() + "', " +
            				"'" + profile.getPhoto() + "', " +
            				"'" + profile.getPortforlio() + "', " +
            				"'" + profile.getFileName() + "');";

            int ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();

            String getQuery = 
            		"select profile_id " + 
            		"from profile " +
            		"where user_id = '" + profile.getUser().getId() + "';";
            
            ResultSet res = CMDBManager.sendSelectQuery(getQuery, cmInfo);
            Long id = -9999l;
            while (res.next()) {
            	id = res.getLong("profile_id");
            }
            
            profile.setId(id);
            connection.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("해당 유저는 프로필이 이미 존재합니다.");
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
        return profile.getId();
    }
    
    @Transactional
    public Long putProfile(Profile profile, Result result, CMInfo cmInfo) {
        Connection connection = null;
        Statement statement = null;
        
        try {
            DBManager dbManager = DBManager.getConnection(cmInfo);
            connection = dbManager.getConnection();
            statement = dbManager.getStatement();
            connection.setAutoCommit(false);
            
            String query = 
            		"update profile set " +
            		"role_id = '" + (profile.getRole().ordinal()+1) + "', " + 
            		"content = '" + profile.getContent() + "', " +
            		"photo = '" + profile.getPhoto() + "', " +
            		"portforlio = '" + profile.getPortforlio() + "', " +
            		"file_name = '" + profile.getFileName() + "' " +
            		"where user_id = '" + profile.getUser().getId() + "';";

            int ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();
            
            connection.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("알 수 없는 오류");
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
        return profile.getId();
    }
    
    @Transactional
    public void deleteProfile(Long userId, Result result, CMInfo cmInfo) {
        Connection connection = null;
        Statement statement = null;
        
        try {
            DBManager dbManager = DBManager.getConnection(cmInfo);
            connection = dbManager.getConnection();
            statement = dbManager.getStatement();
            connection.setAutoCommit(false);
            
            String query = 
            		"update profile set user_id = null where user_id = '" + userId + "';";

            int ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();
            
            connection.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("알 수 없는 오류");
            result.setSuccess(false);
//            return -1l;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
//            return -1l;
        }

        result.setMsg("성공하였습니다");
        result.setSuccess(true);
//        return true;
    }
}
