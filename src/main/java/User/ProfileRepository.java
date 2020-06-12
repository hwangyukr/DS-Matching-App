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
    			"select p.*, r.role" +
    			"from profile p, role r" +
    			"where p.user_id = '" + userId + "' " +
    			"and p.role_id = r.role_id;";
    	ResultSet res = CMDBManager.sendSelectQuery(query, cmInfo);
    	
    	Profile profile = null;
    	try {
			if(res.first()) {
				Long id = res.getLong("profile_id");
				User user = new User.Builder()
						.id(res.getLong("user_id"))
						.build();
				Role role = Role.valueOf(res.getString("role"));
				String content = res.getString("content");
				String photo = res.getString("photo");
				String portforlio = res.getString("portforlio");
				profile = new Profile.Builder()
						.id(id)
						.user(user)
						.role(role)
						.content(content)
						.photo(photo)
						.portforlio(portforlio)
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
    	return null;
    }
    
    @Transactional
    public Long putProfile(Profile profile, Result result, CMInfo cmInfo) {
    	return null;
    }
    
    @Transactional
    public Long deleteProfile(Long profileId, Result result, CMInfo cmInfo) {
    	return null;
    }
}
