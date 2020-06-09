import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.CMDummyEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMInteractionManager;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

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

        cmClientStub.loginCM("jonghyun", "0000");
        ue.setStringID("SIGN-IN");
        ue.setEventField(CMInfo.CM_STR, "email", "jongjong1994@gmail.com");
        ue.setEventField(CMInfo.CM_STR, "password", "0000");
        ue.setEventField(CMInfo.CM_STR, "name", "jonghyun");
        ue.setSender(myself.getName());
        ue.setHandlerGroup(myself.getCurrentGroup());
        ue.setHandlerSession(myself.getCurrentSession());

        cmClientStub.send(ue, "SERVER");
    }

}
