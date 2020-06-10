
import Board.BoardHandller;
import Team.TeamHandler;
import User.UserHandler;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMConfigurationInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class MyServerEventHandler implements CMAppEventHandler {

    private CMServerStub cmServerStub;
    private UserHandler userHandler;
    private TeamHandler teamHandler;
    private BoardHandller boardHandler;

    public MyServerEventHandler(CMServerStub cmServerStub) {
        this.cmServerStub = cmServerStub;
        this.userHandler = new UserHandler(cmServerStub);
        this.teamHandler = new TeamHandler(cmServerStub);
        this.boardHandler = new BoardHandller(cmServerStub);
    }

    public void processEvent(CMEvent cmEvent) {
        switch (cmEvent.getType()) {

            case CMInfo.CM_USER_EVENT:

                CMUserEvent ue = (CMUserEvent) cmEvent;

                switch (ue.getStringID()) {
                    case "SIGN-UP":
                        userHandler.signUp(ue);
                        break;
                    case "SIGN-IN":
                        userHandler.login(ue);
                        break;
                    case "GET-TEAMS":
                        teamHandler.getTeams(ue);
                        break;
                    case "APPLY-TEAM":
                        teamHandler.applyTeam(ue);
                        break;
                    case "GET-TEAM":
                        teamHandler.getTeam(ue);
                        break;
                    case "CREATE-TEAM":
                        teamHandler.createTeam(ue);
                        break;
                    case "GET-BOARDS":
                        boardHandler.getBoards(ue);
                        break;
                    case "MAKE-BOARD":
                        boardHandler.createBoard(ue);
                        break;
                    case "GET-APPLICATIONS":
                        teamHandler.getApplications(ue);
                        break;
                    case "PROCESS-APPLICATION":
                        teamHandler.processApplications(ue);
                        break;
                    case "GET-PROFILE":
                        userHandler.getProfile(ue);
                        break;
                    default:
                        return;

                }
            }
        }
    }



