package com.xtu.graduate.project.dao;

import com.xtu.graduate.project.domains.SiteApplication;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
public interface DepartmentDao {
    List<Map<String, Object>> findAllSiteApplication(int pageNumber);
    List<Map<String, Object>> findSiteApplicationByDepartmentID(String departmentID, int pageNumber);
    List<Map<String, Object>> findSiteApplicationByLocale(String locale, int pageNumber);
    List<Map<String, Object>> findSiteApplicationByBeginTime(Date beginTime1, Date beginTime2, int pageNumber);
    List<Map<String, Object>> findSiteApplicationByLocaleAndBeginTime(String local, Date beginTime1, Date beginTime2, int pageNumber);
    Integer createSiteApplication(SiteApplication siteApplication);
}
