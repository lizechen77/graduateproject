package com.xtu.graduate.project.dao;

import com.xtu.graduate.project.domains.SiteApplication;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
public interface DepartmentDao {
    List<Map<String, Object>> findAllSiteApplication();
    List<Map<String, Object>> findSiteApplicationByDepartmentID(String departmentID);
    List<Map<String, Object>> findSiteApplicationByLocale(String locale);
    List<Map<String, Object>> findSiteApplicationByBegainTime(Date begainTime1, Date begainTime2);
    List<Map<String, Object>> findSiteApplicationByLocaleAndBegainTime(String local, Date begainTime1, Date begainTime2);
    Integer createSiteApplication(SiteApplication siteApplication);
}
