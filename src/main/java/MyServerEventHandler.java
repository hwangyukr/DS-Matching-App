
import Board.BoardHandller;
import Team.TeamHandler;
import User.ProfileHandler;
import User.UserHandler;
import kr.ac.konkuk.ccslab.cm.event.CMEvent;
import kr.ac.konkuk.ccslab.cm.event.CMFileEvent;
import kr.ac.konkuk.ccslab.cm.event.CMSessionEvent;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.event.handler.CMAppEventHandler;
import kr.ac.konkuk.ccslab.cm.info.CMConfigurationInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

import static kr.ac.konkuk.ccslab.cm.event.CMFileEvent.END_FILE_TRANSFER;

public class MyServerEventHandler implements CMAppEventHandler {

    private CMServerStub cmServerStub;
    private UserHandler userHandler;
    private TeamHandler teamHandler;
    private BoardHandller boardHandler;
    private ProfileHandler profileHandler;

    public MyServerEventHandler(CMServerStub cmServerStub) {
        this.cmServerStub = cmServerStub;
        this.userHandler = new UserHandler(cmServerStub);
        this.teamHandler = new TeamHandler(cmServerStub);
        this.boardHandler = new BoardHandller(cmServerStub);
        this.profileHandler = new ProfileHandler(cmServerStub);
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
                    case "GET-APPLICATIONS":
                        teamHandler.getApplications(ue);
                        break;
                    case "PROCESS-APPLICATION":
                        teamHandler.processApplications(ue);
                        break;
                    case "GET-PROFILE":
                        profileHandler.getProfile(ue);
                        break;
                    case "POST-PROFILE":
                    	profileHandler.postProfile(ue);
                        break;
                    case "PUT-PROFILE":
                    	profileHandler.putProfile(ue);
                    	break;
                    case "DELETE-PROFILE":
                    	profileHandler.deleteProfile(ue);
                    	break;
                    case "GET-USER":
                        userHandler.getUser(ue);
                        break;
                    case "GET-BOARD":
                    	boardHandler.getBoard(ue);
                        break;
                    case "GET-BOARDS":
                    	boardHandler.getBoards(ue);
                        break;
                    case "POST-BOARD":
                    	boardHandler.postBoard(ue);
                        break;
                    case "PUT-BOARD":
                    	boardHandler.putBoard(ue);
                    	break;
                    case "DELETE-BOARD":
                    	boardHandler.deleteBoard(ue);
                    	break;
                    default:
                        return;

                }
            }
        }
    }



