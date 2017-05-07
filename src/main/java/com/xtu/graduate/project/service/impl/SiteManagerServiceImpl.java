package com.xtu.graduate.project.service.impl;

import com.xtu.graduate.project.dao.SiteManagerDao;
import com.xtu.graduate.project.domains.SiteApplication;
import com.xtu.graduate.project.domains.User;
import com.xtu.graduate.project.service.SiteManagerService;
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
    @Override
    public Integer createUser(User user) {
        LOGGER.info("Creating user.......uerID = {}", user.getUserID());
        return this.siteManagerDao.createUser(user);
    }

    @Override
    public Integer deleteUserByUserID(String userID) {
        return this.siteManagerDao.deleteUser(userID);
    }

    @Override
    public List<Map<String, Object>> findUnapproveSiteApplication() {
        return this.siteManagerDao.findUnapprovedSiteApplication();
    }

    @Override
    public Integer approve(String applicationID, String advice) {
        if (advice.equals("approve")) {
            return this.siteManagerDao.approve(applicationID, "已审批通过");
        }
        return this.siteManagerDao.approve(applicationID, "审批未通过");
    }
}
