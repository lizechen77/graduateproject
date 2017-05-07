package com.xtu.graduate.project.dao;

import com.xtu.graduate.project.domains.SiteApplication;
import com.xtu.graduate.project.domains.User;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
public interface SiteManagerDao {
    Integer createUser(User user);
    Integer deleteUser(String UserID);
    List<Map<String, Object>> findUnapprovedSiteApplication();
    Integer approve(String applicationID, String status);
}
