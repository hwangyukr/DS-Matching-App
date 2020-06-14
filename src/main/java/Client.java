import Team.Role;
import Team.Team;
import Viewer.MyTeamView;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.ac.konkuk.ccslab.cm.entity.CMUser;
import kr.ac.konkuk.ccslab.cm.event.*;
import kr.ac.konkuk.ccslab.cm.info.CMFileTransferInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.info.CMInteractionInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMFileTransferManager;
import kr.ac.konkuk.ccslab.cm.manager.CMInteractionManager;
import kr.ac.konkuk.ccslab.cm.stub.CMClientStub;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Client {
    private CMClientStub clientStub;
    private ClientEventHandler clientEventHandler;
    private String token;
    private ObjectMapper objectMapper;
	public Team my_team;

    public Client() {
        clientStub = new CMClientStub();
        clientEventHandler = new ClientEventHandler(clientStub, this);
        this.token = "";
        this.objectMapper = new ObjectMapper();
    }

    public void setToken(String token) {
        this.token = token;
    }

    public CMClientStub getClientStub() {
        return clientStub;
    }

    public ClientEventHandler getClientEventHandler() {
        return clientEventHandler;
    }

    public void makeTeam(Scanner sc, CMUserEvent ue) {

        ue.setStringID("CREATE-TEAM");
        System.out.print("팀 이름 : ");

        String teamName = sc.next();

        ue.setEventField(CMInfo.CM_STR, "team_name", teamName);
        ue.setEventField(CMInfo.CM_STR, "token", token);
        clientStub.send(ue, "SERVER");

    }

    public static void main(String[] args) throws IOException {
        Client client = new Client();
        CMClientStub cmClientStub = client.getClientStub();
        cmClientStub.setAppEventHandler(client.getClientEventHandler());
        cmClientStub.startCM();

        CMUserEvent ue = new CMUserEvent();
        CMUser myself = cmClientStub.getCMInfo().getInteractionInfo().getMyself();
        ue.setSender(myself.getName());
        ue.setHandlerGroup(myself.getCurrentGroup());
        ue.setHandlerSession(myself.getCurrentSession());

        String cmId = "kongee";
        cmClientStub.loginCM(cmId, "0000");
        Scanner sc = new Scanner(System.in);

        while(true) {

            System.out.println("1. 로그인");
            System.out.println("2. 회원가입");
            System.out.println("3. 팀 생성");
            System.out.print("입력 : ");

            int input = sc.nextInt();
            String email;
            String password;
            String name;
            String token;
            String teamName;

            switch (input) {

                case 10:
                    cmClientStub.requestFile("/"+ cmId + "/ss.jpg", "SERVER");
                    cmClientStub.requestSNSContent( myself.getName(), 10);
                    break;

                case 11:
                    String strMessage = "안";
                    ArrayList<String> filePathList = new ArrayList<>();
                    int nReplyOf = 0;
                    int nLevelOfDisclosure = 0;
                    filePathList.add("/Users/jonghyun/Desktop/ss.jpg");
                    cmClientStub.requestSNSContentUpload(myself.getName(), strMessage, 1,
                            nReplyOf, nLevelOfDisclosure, filePathList);

                    //CMFileTransferManager.pushFile("/Users/jonghyun/Desktop/ss.jpg", "SERVER", cmClientStub.getCMInfo());
                    break;

                case 1:
                    System.out.print("이메일 : ");
                    email = sc.next();
                    System.out.print("비밀번호 : ");
                    password = sc.next();

                    ue.setStringID("SIGN-IN");
                    ue.setEventField(CMInfo.CM_STR, "email", email);
                    ue.setEventField(CMInfo.CM_STR, "password", password);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 2:
                    ue.setStringID("SIGN-UP");
                    System.out.print("이메일 : ");
                    email = sc.next();

                    System.out.print("비밀번호 : ");
                    password = sc.next();

                    System.out.print("이름 : ");
                    name = sc.next();

                    ue.setEventField(CMInfo.CM_STR, "email", email);
                    ue.setEventField(CMInfo.CM_STR, "password", password);
                    ue.setEventField(CMInfo.CM_STR, "name", name);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 3:
                    ue.setStringID("CREATE-TEAM");
                    System.out.print("팀 이름 : ");
                    teamName = sc.next();

                    Map<Role, Integer> rolelimits = new HashMap<>();

                    System.out.print("광고 디자인  : ");
                    rolelimits.put(Role.광고디자인, sc.nextInt());

                    System.out.print("앱 개발  : ");
                    rolelimits.put(Role.앱개발, sc.nextInt());

                    String json = client.objectMapper.writeValueAsString(rolelimits);
                    String fileNameWithOutLocation = "ss.jpg";
                    String fileNameThatWillBeInServer = "/" + myself.getName() + "/ss.jpg";
                    String fileNameInClientLocal = "/Users/jonghyun/Desktop/ss.jpg";

                    ue.setEventField(CMInfo.CM_STR, "team_name", teamName);
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    ue.setEventField(CMInfo.CM_STR, "teamlimit", json);
                    ue.setEventField(CMInfo.CM_STR, "file_name", fileNameThatWillBeInServer);
                    ue.setEventField(CMInfo.CM_STR, "original_file_name",fileNameWithOutLocation);

                    cmClientStub.send(ue, "SERVER");

                    /*
                        파일 전송은 팀생성이 성공하였으면
                        CREATE-TEAM-REPLY에서 success를 확인후 보내주는게 좋을듯!

                     */
                    CMFileTransferManager.pushFile(fileNameInClientLocal, "SERVER", cmClientStub.getCMInfo());

                    break;
                case 4:
                    ue.setStringID("GET-TEAM");
                    teamName = sc.next();
                    ue.setEventField(CMInfo.CM_LONG, "team_id", teamName);
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    cmClientStub.send(ue, "SERVER");

                    break;
                case 5:
                    ue.setStringID("GET-TEAMS");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 6:
                    ue.setStringID("GET-APPLICATIONS");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 7:
                    ue.setStringID("APPLY-TEAM");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    System.out.print("팀 이름 : ");
                    ue.setEventField(CMInfo.CM_STR, "team_name", sc.next());
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 8:
                    ue.setStringID("PROCESS-APPLICATION");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    ue.setEventField(CMInfo.CM_LONG, "team_id", "26");
                    ue.setEventField(CMInfo.CM_LONG, "user_id", "3");
                    ue.setEventField(CMInfo.CM_INT, "yesTeam", "1");
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 9:
                    ue.setStringID("GET-USER");
                    ue.setEventField(CMInfo.CM_LONG, "user_id", "14");
                    ue.setEventField(CMInfo.CM_STR, "token", client.token);
                    cmClientStub.send(ue, "SERVER");
                    break;
                case 0:
                    System.exit(0);
                default:
                    break;
            }
        }

    }


}
