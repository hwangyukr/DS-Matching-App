package Board;

import Team.Team;
import Team.TeamRepository;
import User.User;
import User.UserRepository;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

public class BoardService {

    private CMInfo cmInfo;
    private BoardRepository boardRepository;
    private UserRepository userRepository;
    private TeamRepository teamRepository;

    public BoardService(CMInfo cmInfo) {
        this.cmInfo = cmInfo;
        this.boardRepository = new BoardRepository();
    }

    public Board getBoards(CMUserEvent ue) {
        Long teamId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "teamId"));
        return boardRepository.getAllBoards(teamId, cmInfo);
    }

    public Board createBoard(CMUserEvent ue) {

        Long teamId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "teamId"));
        Long userId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "userId"));
        String title = ue.getEventField(CMInfo.CM_STR, "title");
        String content = ue.getEventField(CMInfo.CM_STR, "content");

        User user = userRepository.findById(userId, cmInfo);
        Team team = teamRepository.getTeamById(teamId, cmInfo);

        Board board = new Board.Builder()
                .team(team)
                .user(user)
                .title(title)
                .content(content)
                .build();

        return boardRepository.save(board, cmInfo);
    }
}
