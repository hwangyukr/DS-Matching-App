package Board;

import java.util.List;
import Common.Result;
import Config.TokenProvider;
import Team.Team;
import User.User;
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
    
    public Board getBoard(Long boardId, Result result) {
    	return boardRepository.getBoardById(boardId, result, cmInfo);
    }
    
   public Board postBoard(TokenProvider.TokenResult validResult, Result result, String title, String content, long teamId) {
       User user = new User.Builder()
               .id(validResult.getId())
               .build();

       Team team = new Team.Builder()
    		   .id(teamId)
               .build();

       Board board = new Board.Builder()
    		   .user(user)
    		   .team(team)
    		   .title(title)
    		   .content(content)
    		   .build();
       boardRepository.postBoard(board, result, cmInfo);
       return board;
   }
   
   public long putBoard(Board board, Result result, String title, String content) {
	   board.setTitle(title);
	   board.setContent(content);
	   return boardRepository.putBoard(board, result, cmInfo);
   }
}
