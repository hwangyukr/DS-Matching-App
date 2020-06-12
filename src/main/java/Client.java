import Team.Role;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMInteractionManager;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private CMClientStub clientStub;
    private ClientEventHandler clientEventHandler;
    private String token;
    private ObjectMapper objectMapper;

    public Client() {
        clientStub = new CMClientStub();
        clientEventHandler = new ClientEventHandler(clientStub, this);
        this.token = "";
        this.objectMapper = new ObjectMapper();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CMClientStub getClientStub() {
        return clientStub;
    }

    public ClientEventHandler getClientEventHandler() {
        return clientEventHandler;
    }

    public void makeTeam(Scanner sc, CMUserEvent ue) {

        ue.setStringID("CREATE-TEAM");
        System.out.print("팀 이름 : ");

        String teamName = sc.next();

        ue.setEventField(CMInfo.CM_STR, "team_name", teamName);
        ue.setEventField(CMInfo.CM_STR, "token", token);
        clientStub.send(ue, "SERVER");

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        CMClientStub cmClientStub = client.getClientStub();
        cmClientStub.setAppEventHandler(client.getClientEventHandler());
        cmClientStub.startCM();

        CMUserEvent ue = new CMUserEvent();
        CMUser myself = cmClientStub.getCMInfo().getInteractionInfo().getMyself();
        ue.setSender(myself.getName());
        ue.setHandlerGroup(myself.getCurrentGroup());
        ue.setHandlerSession(myself.getCurrentSession());

        cmClientStub.loginCM("kongee", "0000");
        Scanner sc = new Scanner(System.in);

        while(true) {

            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 팀 생성");
            System.out.print("입력 : ");

            int input = sc.nextInt();
            String email;
            String password;
            String name;
            String token;
            String teamName;

            switch (input) {
                case 1:
                    System.out.print("이메일 : ");
                    email = sc.next();
                    System.out.print("비밀번호 : ");
                    password = sc.next();

                    ue.setStringID("SIGN-IN");
                    ue.setEventField(CMInfo.CM_STR, "email", email);
                    ue.setEventField(CMInfo.CM_STR, "password", password);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 2:
                    ue.setStringID("SIGN-UP");
                    System.out.print("이메일 : ");
                    email = sc.next();

                    System.out.print("비밀번호 : ");
                    password = sc.next();

                    System.out.print("이름 : ");
                    name = sc.next();

                    ue.setEventField(CMInfo.CM_STR, "email", email);
                    ue.setEventField(CMInfo.CM_STR, "password", password);
                    ue.setEventField(CMInfo.CM_STR, "name", name);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 3:
                    ue.setStringID("CREATE-TEAM");
                    System.out.print("팀 이름 : ");
                    teamName = sc.next();

                    Map<Role, Integer> rolelimits = new HashMap<>();

                    System.out.print("광고 디자인  : ");
                    rolelimits.put(Role.광고디자인, sc.nextInt());

                    System.out.print("앱 개발  : ");
                    rolelimits.put(Role.앱개발, sc.nextInt());

                    String json = client.objectMapper.writeValueAsString(rolelimits);

                    ue.setEventField(CMInfo.CM_STR, "team_name", teamName);
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    ue.setEventField(CMInfo.CM_STR, "teamlimit", json);

                    cmClientStub.send(ue, "SERVER");

                    break;
                case 4:
                    ue.setStringID("GET-TEAM");
                    teamName = sc.next();
                    ue.setEventField(CMInfo.CM_STR, "team_name", teamName);
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 5:
                    ue.setStringID("GET-TEAMS");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 6:
                    ue.setStringID("GET-APPLICATIONS");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 7:
                    ue.setStringID("APPLY-TEAM");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    System.out.print("팀 이름 : ");
                    ue.setEventField(CMInfo.CM_STR, "team_name", sc.next());
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 8:
                    ue.setStringID("PROCESS-APPLICATION");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    ue.setEventField(CMInfo.CM_LONG, "team_id", "26");
                    ue.setEventField(CMInfo.CM_LONG, "user_id", "3");
                    ue.setEventField(CMInfo.CM_INT, "yesTeam", "1");
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 9:
                    ue.setStringID("GET-USER");
                    ue.setEventField(CMInfo.CM_LONG, "user_id", "14");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 0:
                    System.exit(0);
                default:
                    break;
            }
        }

    }

}
