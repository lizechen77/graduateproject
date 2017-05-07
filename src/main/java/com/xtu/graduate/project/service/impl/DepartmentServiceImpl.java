package com.xtu.graduate.project.service.impl;


import com.xtu.graduate.project.dao.CommonDao;
import com.xtu.graduate.project.dao.DepartmentDao;
import com.xtu.graduate.project.dao.impl.UserDaoImpl;
import com.xtu.graduate.project.domains.SiteApplication;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.service.CommonService;
import com.xtu.graduate.project.service.DepartmentService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/1 0001.
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Autowired
    DepartmentDao departmentDao;

    @Autowired
    CommonDao commonDao;

    @Override
    public List<Map<String, Object>> findSiteApplicationInfo(String departmentID, String locale, Date begainTime1, Date begainTime2) {
        List<Map<String, Object>> list;
        if (StringUtils.isNotBlank(departmentID)) {
            LOGGER.info("Finding site application by departmentID......");
            list = this.departmentDao.findSiteApplicationByDepartmentID(departmentID);
            return list;
        }
        if (StringUtils.isNotBlank(locale)) {
            if(begainTime1 != null && begainTime2 != null) {
                LOGGER.info("Finding site application by locale and begainTime......");
                list = this.departmentDao.findSiteApplicationByLocaleAndBegainTime(locale, begainTime1, begainTime2);
                return list;
            }
            LOGGER.info("Finding site application by locale......");
            list = this.departmentDao.findSiteApplicationByLocale(locale);
            return list;
        }
        if (begainTime1 != null && begainTime2 != null) {
            LOGGER.info("Finding site application by begainTime......");
            list = this.departmentDao.findSiteApplicationByBegainTime(begainTime1, begainTime2);
            return list;
        }
        list = this.departmentDao.findAllSiteApplication();
        return list;
    }

    @Override
    public Integer createSiteApplication(SiteApplication siteApplication) {
        String siteID = siteApplication.getSiteID();
        SiteInfo siteInfo = this.commonDao.findSiteInfoBySiteID(siteID);
        String siteManagerID = siteInfo.getSiteManagerID();
        siteApplication.setSiteManagerID(siteManagerID);
        return this.departmentDao.createSiteApplication(siteApplication);
    }

}
