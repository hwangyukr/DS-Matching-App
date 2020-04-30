import kr.ac.konkuk.ccslab.cm.event.CMEvent;
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
                System.out.println("Woohah~");
                break;
            default:
                return;
        }
    }
}
