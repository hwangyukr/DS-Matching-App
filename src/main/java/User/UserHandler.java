package User;

import Common.Result;
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

        String email = ue.getEventField(CMInfo.CM_STR, "email");
        String password = ue.getEventField(CMInfo.CM_STR, "password");
        String name = ue.getEventField(CMInfo.CM_STR, "name");

        if(email == null || password == null || name == null) {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", "입력값을 확인하세요");
            ue.setEventField(CMInfo.CM_STR, "user_id", null);
            cmServerStub.send(ue, ue.getSender());
            return;
        }
        ue.setStringID("SIGN-UP-REPLY");
        Result result = new Result();
        User user = new User.Builder()
                .email(email)
                .password(password)
                .name(name)
                .build();

        user = userService.createUser(user, result);

        if(!result.isSuccess()) {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
            ue.setEventField(CMInfo.CM_STR, "user_id", null);
        }
        else {
            ue.setEventField(CMInfo.CM_INT,"success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
            ue.setEventField(CMInfo.CM_STR, "user_id", String.valueOf(user.getId()));
        }
        cmServerStub.send(ue, ue.getSender());
    }

    public void login(CMUserEvent ue) {

        String email = ue.getEventField(CMInfo.CM_STR, "email");
        String password = ue.getEventField(CMInfo.CM_STR, "password");
        if(email == null || password == null) {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", "입력값을 확인하세요");
            ue.setEventField(CMInfo.CM_STR, "user_id", null);
            cmServerStub.send(ue, ue.getSender());
            return;
        }

        UserDTO.LoginReq dto = new UserDTO.LoginReq(email, password);
        Result result = new Result();
        String token = userService.login(dto,result);
        ue.setStringID("SIGN-IN-REPLY");

        if(!result.isSuccess()) {
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
            ue.setEventField(CMInfo.CM_STR, "token", null);
        }
        else {
            ue.setEventField(CMInfo.CM_INT,"success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
            ue.setEventField(CMInfo.CM_STR, "token", token);
        }
        cmServerStub.send(ue, ue.getSender());
    }

    public void getProfile(CMUserEvent ue) {
        Profile profile = userService.findProfile(ue);
    }
}
