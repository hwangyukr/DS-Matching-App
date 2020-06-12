package Board;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Common.Result;
import Config.TokenProvider;
import Team.Team;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class BoardHandller {
    private CMServerStub cmServerStub;
    private BoardService boardService;
    private ObjectMapper objectMapper;

    public BoardHandller(CMServerStub cmServerStub) {
        this.cmServerStub = cmServerStub;
        this.boardService = new BoardService(cmServerStub.getCMInfo());
        this.objectMapper = new ObjectMapper();
    }

    /*
    	토큰 validation 확인 후 오류 시 에러 처리
	*/
	public TokenProvider.TokenResult getUserInfo(CMUserEvent ue) {
	    String token = ue.getEventField(CMInfo.CM_STR, "token");
	    if(token == null){
	        ue.setEventField(CMInfo.CM_INT, "success", "0");
	        ue.setEventField(CMInfo.CM_STR, "msg", "NOT AUTHORIZED");
	        cmServerStub.send(ue, ue.getSender());
	        return null;
	    }
	    return TokenProvider.validateToken(token);
	}
	
	public void handleError(Result result, CMUserEvent ue) {
	    ue.setEventField(CMInfo.CM_INT, "success", "0");
	    ue.setEventField(CMInfo.CM_STR, "msg", result.getMsg());
	    cmServerStub.send(ue, ue.getSender());
	}
	
	public void getBoards(CMUserEvent ue) {
		ue.setStringID("GET-BOARDS-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Long teamId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "team_id"));
        if(teamId == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }
        
        Result result = new Result();
        List<Board> boards = boardService.getBoards(teamId, result);

        /*
            result값이 false이면 그에 대한 에러 메시지 처리
         */
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        try {
            String ret = objectMapper.writeValueAsString(boards);
            ue.setEventField(CMInfo.CM_INT, "success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            ue.setEventField(CMInfo.CM_STR, "board", ret);
            cmServerStub.send(ue, ue.getSender());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

	public void getBoard(CMUserEvent ue) {
		ue.setStringID("GET-BOARD-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Long boardId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "board_id"));
        if(boardId == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }
        
        Result result = new Result();
        Board board = boardService.getBoard(boardId, result);

        /*
            result값이 false이면 그에 대한 에러 메시지 처리
         */
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        try {
            String ret = objectMapper.writeValueAsString(board);
            ue.setEventField(CMInfo.CM_INT, "success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            ue.setEventField(CMInfo.CM_STR, "board", ret);
            cmServerStub.send(ue, ue.getSender());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
	
    public void postBoard(CMUserEvent ue) {
        ue.setStringID("POST-BOARD-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        String title = ue.getEventField(CMInfo.CM_STR, "title");
        String content = ue.getEventField(CMInfo.CM_STR, "content");
        Long teamId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "team_id"));
        if(teamId == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }
        
        Result result = new Result();
        Board board = boardService.postBoard(validResult, result, title, content, teamId);

        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        ue.setEventField(CMInfo.CM_INT,"success", "1");
        ue.setEventField(CMInfo.CM_STR, "msg", "성공하였습니다");
        ue.setEventField(CMInfo.CM_STR, "board", String.valueOf(board.getId()));
        cmServerStub.send(ue, ue.getSender());
    }
    
    public void putBoard(CMUserEvent ue) {
        ue.setStringID("PUT-BOARD-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Long boardId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "board_id"));
        String title = ue.getEventField(CMInfo.CM_STR, "title");
        String content = ue.getEventField(CMInfo.CM_STR, "content");
        
        if(boardId == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }
        
        Result result = new Result();
        Board board = boardService.getBoard(boardId, result);

        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        if(title == null) {
        	title = board.getTitle();
        }
        if(content == null) {
        	content = board.getContent();
        }
        
        Long id = boardService.putBoard(board, result, title, content);

        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        ue.setEventField(CMInfo.CM_INT,"success", "1");
        ue.setEventField(CMInfo.CM_STR, "msg", "성공하였습니다");
        ue.setEventField(CMInfo.CM_STR, "board", String.valueOf(id));
        cmServerStub.send(ue, ue.getSender());
    }
    
    public void deleteBoard(CMUserEvent ue) {
        ue.setStringID("DELETE-BOARD-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;

        Long boardId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "board_id"));
        
        if(boardId == null) {
            handleError(new Result("입력값을 확인하세요", false), ue);
            return;
        }
        
        Result result = new Result();
        Board board = boardService.getBoard(boardId, result);
        
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }
        
        Long id = boardService.deleteBoard(board, result);
        
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }

        ue.setEventField(CMInfo.CM_INT,"success", "1");
        ue.setEventField(CMInfo.CM_STR, "msg", "성공하였습니다");
        ue.setEventField(CMInfo.CM_STR, "board", String.valueOf(id));
        cmServerStub.send(ue, ue.getSender());
    }
}
