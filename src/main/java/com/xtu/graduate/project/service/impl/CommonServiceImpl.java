package com.xtu.graduate.project.service.impl;

import com.xtu.graduate.project.dao.CommonDao;
import com.xtu.graduate.project.domains.CurrentPage;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.domains.User;
import com.xtu.graduate.project.service.CommonService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
@Service
public class CommonServiceImpl implements CommonService{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CommonServiceImpl.class);

    @Autowired
    CommonDao commonDao;

    @Override
    public User findUserByUserID(String userID) {
        User user = this.commonDao.findUserByUserID(userID);
        return user;
    }

    @Override
    public SiteInfo findSiteInfoBySiteID(String siteID) {
        SiteInfo siteInfo = this.commonDao.findSiteInfoBySiteID(siteID);
        return siteInfo;
    }

    @Override
    public Integer changePassword(String userID, String oldPassword, String newPassword) {
        LOGGER.info("{Changing password userID = {}, oldPassword = {}, newPassword = {} ",userID, oldPassword, newPassword);
        int rows = this.commonDao.changePassword(userID, oldPassword, newPassword);
        return rows;
    }

    @Override
    public SiteInfo findSiteInfoBySiteName(String siteName) {
        SiteInfo siteInfo = this.commonDao.findSiteInfoBySiteName(siteName);
        return siteInfo;
    }

}
