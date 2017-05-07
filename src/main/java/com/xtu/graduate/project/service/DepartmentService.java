package com.xtu.graduate.project.service;

import com.xtu.graduate.project.domains.SiteApplication;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1 0001.
 */
public interface DepartmentService {
    List<Map<String, Object>> findSiteApplicationInfo(String userID, String locale, Date begainTime1, Date begainTime2);
    Integer createSiteApplication(SiteApplication siteApplication);
}
