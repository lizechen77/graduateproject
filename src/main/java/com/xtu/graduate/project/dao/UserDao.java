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
    List<Map<String, Object>> findActivityInfoByActivityName(String activityName);
    List<Map<String, Object>> findActivityInfoByLocale(String locale);
    List<Map<String, Object>> findActivityInfoByBegainTime(Date begainTime1,Date begainTime2);
    List<Map<String, Object>> findActivityInfoByActivityNameAndLocale(String activityName, String locale);
    List<Map<String, Object>> findActivityInfoByActivityNameAndBegainTime(String activityName, Date begainTime1, Date begainTime2);
    List<Map<String, Object>> findActivityInfoByLocaleAndBegainTime(String locale, Date begainTime1, Date begainTime2);
    List<Map<String, Object>> findActivityInfoByActivityNameAndLocaleAndBegainTime(String activityName, String locale, Date begainTime, Date begainTime2);

}
