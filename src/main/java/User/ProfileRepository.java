package User;

import Common.Result;
import Common.DBManager;
import Config.Transactional;
import kr.ac.konkuk.ccslab.cm.info.CMInfo;
import kr.ac.konkuk.ccslab.cm.manager.CMDBManager;

import java.sql.*;

import Board.Board;

public class ProfileRepository {
    public Profile getProfileByUserId(Long userId, CMInfo cmInfo) {
        return null;
    }
    
    @Transactional
    public Long postProfile(Profile profile, Result result, CMInfo cmInfo) {
    	return null;
    }
    
    @Transactional
    public Long putProfile(Profile profile, Result result, CMInfo cmInfo) {
    	return null;
    }
    
    @Transactional
    public Long deleteProfile(Long profileId, Result result, CMInfo cmInfo) {
    	return null;
    }
}