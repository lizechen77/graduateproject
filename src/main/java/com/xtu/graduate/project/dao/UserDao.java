package com.xtu.graduate.project.dao;

import com.xtu.graduate.project.domains.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public interface UserDao {
    CurrentPage findActivityInfoByActivityName(String activityName, int pageNumber);
    CurrentPage findActivityInfoByLocale(String locale, int pageNumber);
    CurrentPage findActivityInfoByBeginTime(Date beginTime1,Date beginTime2, int pageNumber);
    CurrentPage findActivityInfoByActivityNameAndLocale(String activityName, String locale, int pageNumber);
    CurrentPage findActivityInfoByActivityNameAndBeginTime(String activityName, Date beginTime1, Date beginTime2, int pageNumber);
    CurrentPage findActivityInfoByLocaleAndBeginTime(String locale, Date beginTime1, Date beginTime2, int pageNumber);
    CurrentPage findActivityInfoByActivityNameAndLocaleAndBeginTime(String activityName, String locale, Date beginTime, Date beginTime2, int pageNumber);
    CurrentPage findAllActivityInfo(int pageNumber);
}
