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

                if(ue.getStringID().equals("login-reply")) {
                    ue.setEventField(CMInfo.CM_STR, "team_name", "하이루~");
                    ue.setStringID("CREATE-TEAM");
                    clientStub.send(ue, "SERVER");
                }

                if(ue.getStringID().equals("team-make-reply")) {
                    System.out.println("받");
                    System.out.println(ue.getEventField(CMInfo.CM_INT, "success"));
                    System.out.println(ue.getEventField(CMInfo.CM_STR, "msg"));
                }

                break;
            default:
                return;
        }
    }
}
