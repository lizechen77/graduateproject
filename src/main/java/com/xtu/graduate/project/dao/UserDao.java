package com.xtu.graduate.project.dao;

import com.xtu.graduate.project.domains.ActivityInfo;
import com.xtu.graduate.project.domains.Role;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.domains.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/16 0016.
 */
public interface UserDao {
    List<Map<String, Object>> findActivityInfoByActivityName(String activityName, int pageNumber);
    List<Map<String, Object>> findActivityInfoByLocale(String locale, int pageNumber);
    List<Map<String, Object>> findActivityInfoByBeginTime(Date beginTime1,Date beginTime2, int pageNumber);
    List<Map<String, Object>> findActivityInfoByActivityNameAndLocale(String activityName, String locale, int pageNumber);
    List<Map<String, Object>> findActivityInfoByActivityNameAndBeginTime(String activityName, Date beginTime1, Date beginTime2, int pageNumber);
    List<Map<String, Object>> findActivityInfoByLocaleAndBeginTime(String locale, Date beginTime1, Date beginTime2, int pageNumber);
    List<Map<String, Object>> findActivityInfoByActivityNameAndLocaleAndBeginTime(String activityName, String locale, Date beginTime, Date beginTime2, int pageNumber);
    List<Map<String, Object>> findAllActivityInfo(int pageNumber);
}
