import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class MyServer {
    private CMServerStub cmServerStub;
    private MyServerEventHandler myServerEventHandler;

    public MyServer() {
        cmServerStub = new CMServerStub();
        myServerEventHandler = new MyServerEventHandler(cmServerStub);
    }

    public CMServerStub getCmServerStub() {
        return cmServerStub;
    }

    public MyServerEventHandler getCmServerEventHandler() {
        return myServerEventHandler;
    }

    public static void main(String[] args) {

        MyServer myServer = new MyServer();
        CMServerStub cmServerStub = myServer.getCmServerStub();
        cmServerStub.setAppEventHandler(myServer.getCmServerEventHandler());

        CMInteractionInfo interInfo = cmServerStub.getCMInfo().getInteractionInfo();

        cmServerStub.startCM();

    }
}
