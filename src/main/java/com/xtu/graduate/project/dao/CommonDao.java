package com.xtu.graduate.project.dao;

import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.domains.User;

import java.util.Date;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
public interface CommonDao {
    User findUserByUserID(String userID);
    SiteInfo findSiteInfoBySiteID(String SiteID);
    Integer changePassword(String userID, String oldPassword, String newPassword);
    SiteInfo findSiteInfoBySiteName(String siteName);
    Integer whetherHasApproved(String siteID, Date beginTime);
}
