import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMInteractionManager;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.util.Scanner;

public class Client {
    private CMClientStub clientStub;
    private ClientEventHandler clientEventHandler;

    public Client() {
        clientStub = new CMClientStub();
        clientEventHandler = new ClientEventHandler(clientStub);
    }

    public CMClientStub getClientStub() {
        return clientStub;
    }

    public ClientEventHandler getClientEventHandler() {
        return clientEventHandler;
    }

    public static void main(String[] args) {
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
            System.out.print("입력 : ");

            int input = sc.nextInt();
            String email;
            String password;
            String name;

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
                case 0:
                    System.exit(0);
                default:
                    break;
            }
        }

    }

}
