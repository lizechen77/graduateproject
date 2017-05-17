package com.xtu.graduate.project.service;

import com.xtu.graduate.project.domains.CurrentPage;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.domains.User;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
public interface CommonService {
    User findUserByUserID(String userID);
    SiteInfo findSiteInfoBySiteID(String siteID);
    Integer changePassword(String userID, String oldPassword, String newPassword);
    SiteInfo findSiteInfoBySiteName(String siteName);
}
