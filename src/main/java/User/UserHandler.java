package User;

import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

// handler에서는 파라미터 값 확인 & 클라이언트에게 이벤트 리턴

public class UserHandler {

    private CMServerStub cmServerStub;
    private UserService userService;

    public UserHandler(CMServerStub cmServerStub) {
        this.cmServerStub = cmServerStub;
        this.userService = new UserService(cmServerStub.getCMInfo());
    }

    public void signUp(CMUserEvent ue) {

        // TODO : 에러 처리
        String email = ue.getEventField(CMInfo.CM_STR, "email");
        String password = ue.getEventField(CMInfo.CM_STR, "password");
        String name = ue.getEventField(CMInfo.CM_STR, "name");

        User user = new User.Builder()
                .email(email)
                .password(password)
                .name(name)
                .build();

        user = userService.createUser(user);

        if(user == null) {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", "실패하였습니");
            ue.setEventField(CMInfo.CM_STR, "user_id", null);
        }
        else {
            ue.setEventField(CMInfo.CM_INT,"success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", "성공하였습니다");
            ue.setEventField(CMInfo.CM_STR, "user_id", String.valueOf(user.getId()));
        }
        System.out.println(ue.getSender());
        cmServerStub.send(ue, ue.getSender());
    }

    public void login(CMUserEvent ue) {

        String email = ue.getEventField(CMInfo.CM_STR, "email");
        String password = ue.getEventField(CMInfo.CM_STR, "password");
        UserDTO.LoginReq dto = new UserDTO.LoginReq(email, password);

        String token = userService.login(dto);

        if(token == null) {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", "실패하였습니다");
            ue.setEventField(CMInfo.CM_STR, "token", null);
        }
        else {
            ue.setEventField(CMInfo.CM_INT,"success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", "성공하였습니다");
            ue.setEventField(CMInfo.CM_STR, "token", token);
        }
        cmServerStub.send(ue, ue.getSender());
    }

    public void getProfile(CMUserEvent ue) {
        Profile profile = userService.findProfile(ue);
    }
}
