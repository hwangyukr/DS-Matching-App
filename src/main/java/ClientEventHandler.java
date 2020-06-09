import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

public class ClientEventHandler implements CMAppEventHandler {

    private CMClientStub clientStub;

    public ClientEventHandler(CMClientStub clientStub) {
        this.clientStub = clientStub;
    }

    @Override
    public void processEvent(CMEvent cmEvent) {

        switch (cmEvent.getType()) {
            case CMInfo.CM_USER_EVENT:
                CMUserEvent ue = (CMUserEvent) cmEvent;
                clientStub.send(ue, "SERVER");
                System.out.println("Woohah~");
                System.out.println(ue.getEventField(CMInfo.CM_STR, "token"));
                ue.setEventField(CMInfo.CM_STR, "team_name", "종현이");
                ue.setStringID("CREATE-TEAM");
                clientStub.send(ue, "SERVER");
                break;
            default:
                return;
        }
    }
}
