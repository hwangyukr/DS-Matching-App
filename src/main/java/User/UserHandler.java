package User;

import Common.Result;
import Config.TokenProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

// handler에서는 파라미터 값 확인 & 클라이언트에게 이벤트 리턴
public class UserHandler {

    private CMServerStub cmServerStub;
    private UserService userService;
    private ObjectMapper objectMapper;

    public UserHandler(CMServerStub cmServerStub) {
        this.cmServerStub = cmServerStub;
        this.userService = new UserService(cmServerStub.getCMInfo());
        this.objectMapper = new ObjectMapper();
    }

    public void handleError(Result result, CMUserEvent ue) {
        ue.setEventField(CMInfo.CM_INT, "success", "0");
        ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
        cmServerStub.send(ue, ue.getSender());
    }

    /*
        토큰 validation 확인 후 오류 시 에러 처리
     */
    public TokenProvider.TokenResult getUserInfo(CMUserEvent ue) {
        String token = ue.getEventField(CMInfo.CM_STR, "token");
        if(token == null || token.equals("")){
            ue.setEventField(CMInfo.CM_INT, "success", "0");
            ue.setEventField(CMInfo.CM_STR, "msg", "NOT AUTHORIZED");
            cmServerStub.send(ue, ue.getSender());
            return null;
        }
        return TokenProvider.validateToken(token);
    }

    /*
        회원가입 : SIGN-UP
     */
    public void signUp(CMUserEvent ue) {

        /*
            클라이언트에게 보낼 API 지정
         */
        ue.setStringID("SIGN-UP-REPLY");

        /*
            클라이언트가 보낸 파라미터 확인
            만약 보내지 않았다면 오류 처리
         */
        String email = ue.getEventField(CMInfo.CM_STR, "email");
        String password = ue.getEventField(CMInfo.CM_STR, "password");
        String name = ue.getEventField(CMInfo.CM_STR, "name");

        if(email == null || password == null || name == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }

        /*
            유저 생성은 UserService에게 위임함
         */
        Result result = new Result();
        User user = userService.createUser(result, email, password, name);

        /*
            유저 생성 도중 오류가 났다면, 오류 메시지와 함께 클라이언트에게 답변 전송
         */
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        /*
            유저 생성 성공시 클라이언트에게 성공 메시지 전송
         */
        ue.setEventField(CMInfo.CM_INT,"success", "1");
        ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
        ue.setEventField(CMInfo.CM_STR, "user_id", String.valueOf(user.getId()));
        cmServerStub.send(ue, ue.getSender());
    }

    /*
        로그인 : SIGN-IN
     */
    public void login(CMUserEvent ue) {

        /*
            클라이언트에게 보낼 API 지정
         */
        ue.setStringID("SIGN-IN-REPLY");

        /*
            클라이언트가 보낸 파라미터 확인
            만약 보내지 않았다면 오류 처리
         */
        String email = ue.getEventField(CMInfo.CM_STR, "email");
        String password = ue.getEventField(CMInfo.CM_STR, "password");
        if(email == null || password == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }

        UserDTO.LoginReq dto = new UserDTO.LoginReq(email, password);
        Result result = new Result();
        String token = userService.login(dto,result);

        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        ue.setEventField(CMInfo.CM_INT,"success", "1");
        ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
        ue.setEventField(CMInfo.CM_STR, "token", token);
        cmServerStub.send(ue, ue.getSender());
    }

    public void getProfile(CMUserEvent ue) {
        Profile profile = userService.findProfile(ue);
    }

    public void getUser(CMUserEvent ue) {

        ue.setStringID("GET-USER-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Long userId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "user_id"));
        if(userId == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }

        Result result = new Result();
        User user = userService.getUser(result, userId);

        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        try {
            String ret = objectMapper.writeValueAsString(user);
            ue.setEventField(CMInfo.CM_INT, "success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            ue.setEventField(CMInfo.CM_STR, "user", ret);
            cmServerStub.send(ue, ue.getSender());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
}
