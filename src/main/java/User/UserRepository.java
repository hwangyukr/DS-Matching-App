package User;

import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

public class UserRepository extends CMDBManager {

    public User saveUser(User user, CMInfo cmInfo) {
        return null;
    }

    public User findByEmail(String email, CMInfo cmInfo) {
        return null;
    }

    public Profile getProfileByUserId(Long id, CMInfo cmInfo) {
        return null;
    }

    public User findById(Long userId, CMInfo cmInfo) {
        return null;
    }
}
