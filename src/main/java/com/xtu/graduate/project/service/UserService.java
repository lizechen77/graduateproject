package com.xtu.graduate.project.service;

import com.xtu.graduate.project.domains.ActivityInfo;
import com.xtu.graduate.project.domains.Role;
import com.xtu.graduate.project.domains.SiteInfo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1 0001.
 */
public interface UserService {
    //departmentID改为活动名称activityName
    List<Map<String, Object>> findActivityInfo(String activityName, String locale, Date begainTime1, Date begainTime2);

}
