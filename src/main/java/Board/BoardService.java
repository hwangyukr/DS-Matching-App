package Board;

import java.util.List;
import Common.Result;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

public class BoardService {
    private CMInfo cmInfo;
    private BoardRepository boardRepository;

    public BoardService(CMInfo cmInfo) {
        this.cmInfo = cmInfo;
        this.boardRepository = new BoardRepository();
    }

    public List<Board> getBoards(CMUserEvent ue, Result result) {
        Long teamId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "teamId"));
        return boardRepository.getBoards(teamId, result, cmInfo);
    }
}
