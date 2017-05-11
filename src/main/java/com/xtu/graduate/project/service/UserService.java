package com.xtu.graduate.project.service;

import com.xtu.graduate.project.domains.ActivityInfo;
import com.xtu.graduate.project.domains.CurrentPage;
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
    CurrentPage findActivityInfo(String activityName, String locale, Date beginTime1, Date beginTime2, int pageNumber);

}
