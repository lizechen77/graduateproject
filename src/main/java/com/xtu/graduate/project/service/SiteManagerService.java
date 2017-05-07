package com.xtu.graduate.project.service;

import com.xtu.graduate.project.domains.SiteApplication;
import com.xtu.graduate.project.domains.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1 0001.
 */
public interface SiteManagerService {
    Integer createUser(User user);
    Integer deleteUserByUserID(String userID);
    List<Map<String, Object>> findUnapproveSiteApplication();
    Integer approve(String applicationID, String advice);
}
