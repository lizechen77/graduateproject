package com.xtu.graduate.project.service.impl;

import com.xtu.graduate.project.dao.UserDao;
import com.xtu.graduate.project.domains.ActivityInfo;
import com.xtu.graduate.project.domains.CurrentPage;
import com.xtu.graduate.project.domains.Role;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.DocFlavor;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1 0001.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public CurrentPage findActivityInfo(String activityName, String locale, Date beginTime1, Date beginTime2, int pageNumber) {
        CurrentPage page;
        //activityName不为空
        if (StringUtils.isNotBlank(activityName)) {
            if(StringUtils.isNotBlank(locale)) {
                LOGGER.info("Find activityInfo by activityName and locale");
                page = this.userDao.findActivityInfoByActivityNameAndLocale(activityName, locale, pageNumber);
                return page;
            }
            if (beginTime1 != null && beginTime2 != null) {
                LOGGER.info("Find activityInfo by activityName and beginTime");
                page = this.userDao.findActivityInfoByActivityNameAndBeginTime(activityName, beginTime1, beginTime2, pageNumber);
                return page;
            }
            if (StringUtils.isNotBlank(activityName) && beginTime1 != null && beginTime2 != null) {
                LOGGER.info("Find activityInfo by activityName and beginTime and locale");
                page = this.userDao.findActivityInfoByActivityNameAndLocaleAndBeginTime(activityName, locale, beginTime1, beginTime2, pageNumber);
                return page;
            }
            LOGGER.info("Find activityInfo by activityName");
            page = this.userDao.findActivityInfoByActivityName(activityName, pageNumber);
            return page;
        }
        //activityName为空，locale不为空
        if (StringUtils.isNotBlank(locale)) {
            if (beginTime1 != null && beginTime2 != null) {
                LOGGER.info("Find activityInfo by locale and beginTime");
                page = this.userDao.findActivityInfoByLocaleAndBeginTime(locale, beginTime1, beginTime2, pageNumber);
                return page;
            }
            LOGGER.info("Find activityInfo by locale");
            page = this.userDao.findActivityInfoByLocale(locale, pageNumber);
            return page;
        }
        //activityName,locale为空，beginTime不为空
        if (beginTime1 != null && beginTime2 != null) {
            LOGGER.info("Find activityInfo by beginTime");
            page = this.userDao.findActivityInfoByBeginTime(beginTime1, beginTime2, pageNumber);
            return page;
        }
        //activityName,locale,beginTime都为空
        LOGGER.info("Find all activityInfo");
        page = this.userDao.findAllActivityInfo(pageNumber);
        return page;
    }

}
