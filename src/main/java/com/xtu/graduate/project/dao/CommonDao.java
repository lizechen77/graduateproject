package com.xtu.graduate.project.dao;

import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.domains.User;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
public interface CommonDao {
    User findUserByUserID(String userID);
    SiteInfo findSiteInfoBySiteID(String SiteID);
}
