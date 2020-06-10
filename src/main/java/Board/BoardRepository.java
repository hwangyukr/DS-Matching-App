package Board;

import Common.DBManager;
import Common.Result;
import Config.Transactional;
import Team.Team;
import User.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

public class BoardRepository {
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

    public Board getBoardById(Long boardId, Result result, CMInfo cmInfo) {
    	String query =
    			"select b.title, b.content, u.user_id, u.user_name, t.team_id, t.team_name" +
    			"from board b, user u, team t" +
    			"where b.board_id = '" + boardId + "'and b.valid = 1 and b.author_id = u.user_id and b.team_id = t.team_id";
    	ResultSet res = CMDBManager.sendSelectQuery(query, cmInfo);
    	Board board = null;
		try {
			if (res.first()) {
				Long user_id = res.getLong("user_id");
				Long team_id = res.getLong("team_id");
				String user_name = res.getString("user_name");
				String team_name = res.getString("team_name");
				String title = res.getString("title");
				String content = res.getString("content");
				User user = new User.Builder()
						.id(user_id)
						.name(user_name)
						.build();
				Team team = new Team.Builder()
						.id(team_id)
						.name(team_name)
						.build();
				board = new Board.Builder()
						.id(boardId)
						.user(user)
						.team(team)
						.title(title)
						.content(content)
						.build();
			}
		} catch (SQLException e) {
			result.setMsg("실패하였습니다");
			result.setSuccess(false);
		}
        result.setMsg("성공하였습니다");
        result.setSuccess(true);
    	return board;
    }
    
    @Transactional
    public long postBoard(Board board, Result result, CMInfo cmInfo) {
        Connection connection = null;
        Statement statement = null;
        
        try {
            DBManager dbManager = DBManager.getConnection(cmInfo);
            connection = dbManager.getConnection();
            statement = dbManager.getStatement();
            connection.setAutoCommit(false);

            String query =
            		"insert into board(author_id, team_id, title, content) values (" +
            				"'" + board.getUser().getId() + "', " +
            				"'" + board.getTeam().getId() + "', " +
            				"'" + board.getTitle() + "', " +
            				"'" + board.getContent() + "');";

            int ret = statement.executeUpdate(query);
            if(ret != 1) throw new SQLException();

            String getQuery = 
            		"select board_id" + 
            		"from board" +
            		"where author_id = '" + board.getUser().getId() + "'" +
            			"and team_id = '" + board.getTeam().getId() + "'" +
            		"order by last_modified desc";
            
            ResultSet res = CMDBManager.sendSelectQuery(getQuery, cmInfo);
            Long id = -9999l;
            if (res.first()) {
            	id = res.getLong("board_id");
            }
            
            board.setId(id);
            connection.commit();

        } catch (SQLIntegrityConstraintViolationException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("알 수 없는 오류");
            result.setSuccess(false);
            return -1l;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            result.setMsg("실패하였습니다");
            result.setSuccess(false);
            return -1l;
        }

        result.setMsg("성공하였습니다");
        result.setSuccess(true);
        return board.getId();
    }
}
