package User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Common.Result;
import Config.TokenProvider;
import kr.ac.konkuk.ccslab.cm.event.CMUserEvent;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.stub.CMServerStub;

public class ProfileHandler {
    private CMServerStub cmServerStub;
    private ProfileService profileService;
    private ObjectMapper objectMapper;
    
    public ProfileHandler(CMServerStub cmServerStub) {
    	this.cmServerStub = cmServerStub;
    	this.profileService = new ProfileService(cmServerStub.getCMInfo());
    	this.objectMapper = new ObjectMapper();
    }
    
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
	
	public void getProfile(CMUserEvent ue) {
		ue.setStringID("GET-PROFILE-REPLY");
        TokenProvider.TokenResult validResult = getUserInfo(ue);
        if(validResult == null) return;
        
        Long userId = Long.valueOf(ue.getEventField(CMInfo.CM_LONG, "user_id"));
        if(userId == null) {
        	userId = validResult.getId();
        }
        
        Result result = new Result();
        Profile profile = profileService.getProfile(userId, result);
        
        if(!result.isSuccess()) {
            handleError(result, ue);
            return;
        }
        
        try {
            String ret = objectMapper.writeValueAsString(profile);
            ue.setEventField(CMInfo.CM_INT, "success", "1");
            ue.setEventField(CMInfo.CM_STR, "msg", validResult.getSuccess());
            ue.setEventField(CMInfo.CM_STR, "profile", ret);
            cmServerStub.send(ue, ue.getSender());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
	}
}
