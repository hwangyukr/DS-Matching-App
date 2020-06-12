package User;

import Common.Result;
import Config.TokenProvider;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;

public class ProfileService {
    private CMInfo cmInfo;
    private ProfileRepository profileRepository;
    
    public ProfileService(CMInfo cmInfo) {
        this.cmInfo = cmInfo;
        this.profileRepository = new ProfileRepository();
    }
	
    public Profile getProfile(Long userId, Result result) {
    	return profileRepository.getProfileByUserId(userId, result, cmInfo);
    }
    
}
