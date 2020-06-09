package User;

import Common.Result;
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

    public User createUser(User user, Result result) {
        if(isExistedEmail(user.getEmail())) {
            result.setSuccess(false);
            result.setMsg("이미 존재하는 이메일입니다");
            return null;
        }
        userRepository.saveUser(user, result, cmInfo);
        return user;
    }

    public Profile findProfile(CMUserEvent ue) {

        Long id = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "userId"));
        Profile profile = userRepository.getProfileByUserId(id, cmInfo);
        if(profile == null) return null; // TODO : Exception 처리

        return profile;
    }

    public String login(UserDTO.LoginReq dto, Result result) {

        User user = userRepository.findByEmail(dto.getEmail(), cmInfo);

        if(user == null) {
            result.setMsg("존재하지 않는 이메일입니다");
            result.setSuccess(false);
            return null;
        }
        if(!dto.getPassword().equals(user.getPassword())) {
            result.setMsg("비밀번호가 일치 하지 않습니다");
            result.setSuccess(false);
            return null;
        }

        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return TokenProvider.createToken(user.getEmail(), user.getId());

    }


}