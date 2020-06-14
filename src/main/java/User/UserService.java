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
    /*
        회원가입 되있는 이메일인지 확인
     */
    private boolean isExistedEmail(String email, Result result) {
        return userRepository.findByEmail(email, result, cmInfo) != null;
    }

    public User createUser(Result result, String email, String password, String name) {

        /*
            회원가입 되어 있는 이메일이 에러
         */
        if(isExistedEmail(email, result)) {
            result.setSuccess(false);
            result.setMsg("이미 존재하는 이메일입니다");
            return null;
        }

        /*
            회원가입 DB 처리는 UserRepoitory에게 위임
         */
        User user = new User.Builder()
                .email(email)
                .name(name)
                .password(password)
                .build();

        userRepository.saveUser(user, result, cmInfo);
        return user;
    }

    public Profile findProfile(CMUserEvent ue) {

        Long id = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "userId"));
        Profile profile = userRepository.getProfileByUserId(id, cmInfo);
        if(profile == null) return null; // TODO : Exception 처리

        return profile;
    }

    public User login(UserDTO.LoginReq dto, Result result) {

        User user = userRepository.findByEmail(dto.getEmail(), result, cmInfo);

        /*
            에러가 나면 에러 메시지 표출
         */
        if(!result.isSuccess()) {
            result.setMsg("실패 했습니다");
            result.setSuccess(false);
            return null;
        }

        /*
            에러가 나지 않고 null이면 이메일이 없는거임
         */
        if(user == null) {
            result.setMsg("존재하지 않는 이메일입니다");
            result.setSuccess(false);
            return null;
        }

        /*
            비밀번호가 일치하지 않으면 에러 메시지
         */
        if(!dto.getPassword().equals(user.getPassword())) {
            result.setMsg("비밀번호가 일치 하지 않습니다");
            result.setSuccess(false);
            return null;
        }

        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return user;

    }

    public User getUser(Result result, Long userId) {
        User user = userRepository.getUser(result, userId, cmInfo);
        return user;
    }
}