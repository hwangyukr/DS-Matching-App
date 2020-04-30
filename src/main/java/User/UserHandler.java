package User;

import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
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
        User user = userService.createUser(ue);
    }

    public void login(CMUserEvent ue) {
        User user = userService.login(ue);
    }

    public void getProfile(CMUserEvent ue) {
        Profile profile = userService.findProfile(ue);
    }
}
