package Board;

import Common.Result;
import User.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

public class BoardRepository extends CMDBManager {
    public List<Board> getAllBoards(Long teamId, Result result, CMInfo cmInfo) {
    	String query = 
    			"select b.board_id, b.title, b.content, u.user_id, u.user_name" +
    			"from user u, board b" +
    			"where b.team_id = '" + teamId + "' and b.valid = 1 and b.author_id = u.user_id" +
    			"order by last_modified desc";
    	
        ResultSet resultSet = CMDBManager.sendSelectQuery(query, cmInfo);
        List<Board> boards = new ArrayList<Board>();
        try {
            while(resultSet.next()) {
                Long board_id = resultSet.getLong("board_id");
                Long user_id = resultSet.getLong("user_id");
                String user_name = resultSet.getString("user_name");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");
                User user = new User.Builder()
                        .id(user_id)
                        .name(user_name)
                        .build();
            	Board board = new Board.Builder()
            			.id(board_id)
            			.user(user)
            			.title(title)
            			.content(content)
            			.build();
            	boards.add(board);
            }
        } catch (SQLException e) {
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return null;
        }
        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return boards;
    }

    public Board save(Board board, CMInfo cmInfo) {
        return null;
    }
}
