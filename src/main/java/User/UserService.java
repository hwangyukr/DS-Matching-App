package User;

import Config.TokenProvider;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

public class UserService {

    private CMInfo cmInfo;
    private UserRepository userRepository;

    public UserService(CMInfo cmInfo) {
        this.cmInfo = cmInfo;
        this.userRepository = new UserRepository();
    }

    private boolean isExistedEmail(String email) {
        return userRepository.findByEmail(email, cmInfo) != null;
    }

    public User createUser(User user) {
        if(isExistedEmail(user.getEmail()))
            return null;
        int ret = userRepository.saveUser(user, cmInfo);
        if(ret == -1) return null;
        return user;
    }

    public Profile findProfile(CMUserEvent ue) {

        Long id = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "userId"));
        Profile profile = userRepository.getProfileByUserId(id, cmInfo);
        if(profile == null) return null; // TODO : Exception 처리

        return profile;
    }

    public String login(UserDTO.LoginReq dto) {

        User user = userRepository.findByEmail(dto.getEmail(), cmInfo);

        if(user == null) return null;
        if(!dto.getPassword().equals(user.getPassword())) return null;

        return TokenProvider.createToken(user.getEmail(), user.getId());

    }


}