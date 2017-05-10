package com.xtu.graduate.project.service;

import com.xtu.graduate.project.domains.SiteApplication;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1 0001.
 */
public interface DepartmentService {
    List<Map<String, Object>> findSiteApplicationInfo(String userID, String locale, Date beginTime1, Date beginTime2, int pageNumber);
    Integer createSiteApplication(SiteApplication siteApplication);
}
