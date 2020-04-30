package Board;

import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class BoardHandller {
    private CMServerStub cmServerStub;
    private BoardService boardService;

    public BoardHandller(CMServerStub cmServerStub) {
        this.cmServerStub = cmServerStub;
        this.boardService = new BoardService(cmServerStub.getCMInfo());
    }

    public void getBoards(CMUserEvent ue) {
        Board board = boardService.getBoards(ue);
    }

    public void createBoard(CMUserEvent ue) {
        Board board = boardService.createBoard(ue);
    }
}
