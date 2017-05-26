package com.xtu.graduate.project.service.impl;

import com.xtu.graduate.project.dao.CommonDao;
import com.xtu.graduate.project.dao.SiteManagerDao;
import com.xtu.graduate.project.domains.CurrentPage;
import com.xtu.graduate.project.domains.SiteApplication;
import com.xtu.graduate.project.domains.User;
import com.xtu.graduate.project.service.SiteManagerService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1 0001.
 */
@Service
public class SiteManagerServiceImpl implements SiteManagerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Autowired
    SiteManagerDao siteManagerDao;

    @Autowired
    CommonDao commonDao;

    @Override
    public Integer createUser(User user) {
        LOGGER.info("Creating user.......uerID = {}, userName = {}", user.getUserID(), user.getUserName());
        User tempUser1 = this.commonDao.findUserByUserID(user.getUserID());
        User tempUser2 = this.commonDao.findUserByUserName(user.getUserName());
        if (tempUser1 != null || tempUser2 != null) {
            return 0;
        } else {
            return this.siteManagerDao.createUser(user);
        }
    }

    @Override
    public Integer deleteUserByUserID(String userID) {
        return this.siteManagerDao.deleteUser(userID);
    }

    @Override
    public CurrentPage findUnapproveSiteApplication(int pageNumber) {
        return this.siteManagerDao.findUnapprovedSiteApplication(pageNumber);
    }

    @Override
    public CurrentPage findApprovedSiteApplication(int pageNumber) {
        return this.siteManagerDao.findApprovedSiteApplication(pageNumber);
    }

    @Override
    public Integer approve(int applicationID, String status) {
        return this.siteManagerDao.approve(applicationID, status);
    }

    @Override
    public CurrentPage findAllUser(int pageNumber) {
        return this.siteManagerDao.findAllUser(pageNumber);
    }
}
