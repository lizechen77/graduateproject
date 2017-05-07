package com.xtu.graduate.project.service.impl;

import com.xtu.graduate.project.dao.UserDao;
import com.xtu.graduate.project.domains.ActivityInfo;
import com.xtu.graduate.project.domains.Role;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
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


    @Override
    public List<Map<String, Object>> findActivityInfo(String activityName, String locale, Date begainTime1, Date begainTime2) {
        List<Map<String, Object>> list;
        //activityName不为空
        if (StringUtils.isNotBlank(activityName)) {
            if(StringUtils.isNotBlank(locale)) {
                list = this.userDao.findActivityInfoByActivityNameAndLocale(activityName, locale);
            }
            if (begainTime1 != null && begainTime2 != null) {
                list = this.userDao.findActivityInfoByActivityNameAndBegainTime(activityName, begainTime1, begainTime2);
            }
            if (StringUtils.isNotBlank(activityName) && begainTime1 != null && begainTime2 != null) {
                list = this.userDao.findActivityInfoByActivityNameAndLocaleAndBegainTime(activityName, locale, begainTime1, begainTime2);
            }
            list = this.userDao.findActivityInfoByActivityName(activityName);
        }
        //activityName为空，locale不为空
        if (StringUtils.isNotBlank(locale)) {
            if (begainTime1 != null && begainTime2 != null) {
                list = this.userDao.findActivityInfoByLocaleAndBegainTime(locale, begainTime1, begainTime2);
            }
            list = this.userDao.findActivityInfoByLocale(locale);
        }
        //activityName,locale为空，begainTime不为空
        if (begainTime1 != null && begainTime2 != null) {
            list = this.userDao.findActivityInfoByBegainTime(begainTime1, begainTime2);
        }
        //activityName,locale,begainTime都为空
        return null;
    }

}
