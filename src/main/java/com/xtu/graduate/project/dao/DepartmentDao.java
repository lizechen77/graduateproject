package com.xtu.graduate.project.dao;

import com.xtu.graduate.project.domains.CurrentPage;
import com.xtu.graduate.project.domains.SiteApplication;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
public interface DepartmentDao {
    CurrentPage findAllSiteApplication(int pageNumber);
    CurrentPage findSiteApplicationByDepartmentID(String departmentID, int pageNumber);
    CurrentPage findSiteApplicationByLocale(String locale, int pageNumber);
    CurrentPage findSiteApplicationByBeginTime(Date beginTime1, Date beginTime2, int pageNumber);
    CurrentPage findSiteApplicationByLocaleAndBeginTime(String local, Date beginTime1, Date beginTime2, int pageNumber);
    Integer createSiteApplication(SiteApplication siteApplication);
}
