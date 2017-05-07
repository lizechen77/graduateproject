package com.xtu.graduate.project.dao.impl;

import com.xtu.graduate.project.dao.DepartmentDao;
import com.xtu.graduate.project.domains.ActivityInfo;
import com.xtu.graduate.project.domains.SiteApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
@Repository
public class DepartmentDaoImpl implements DepartmentDao{
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> findAllSiteApplication() {
        String sql = "select * from siteApplication";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findSiteApplicationByLocale(String locale) {
        String sql = "select ApplicationID, status, siteApplication.siteID, details, begainTime, " +
                "endTime, departmentID, siteApplication.siteManagerID, activityName from siteapplication inner join siteInfo on " +
                "siteapplication.siteID = siteInfo.siteID where siteInfo.locale = ? and status = '审批未通过'";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{locale});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findSiteApplicationByBegainTime(Date begainTime1, Date begainTime2) {
        String sql = "select * from siteApplication where begainTime between ? and ? and status = '审批未通过'";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{begainTime1, begainTime2});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findSiteApplicationByLocaleAndBegainTime(String locale, Date begainTime1, Date begainTime2) {
        String sql = "select ApplicationID, status, siteApplication.siteID, details, begainTime, " +
                "endTime, departmentID, siteManagerID, activityName from siteapplication inner join siteInfo on " +
                "siteapplication.siteID = siteInfo.siteID where siteInfo.locale = ? and begainTime between ? and ?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{locale,begainTime1, begainTime2});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findSiteApplicationByDepartmentID(String departmentID) {
        String sql = "select ApplicationID, status, siteID, details, begainTime, " +
                "endTime, departmentID, siteManagerID, activityName from siteApplication where departmentID = ?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{departmentID});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    @Override
    public Integer createSiteApplication(SiteApplication siteApplication) {
        String siteID = siteApplication.getSiteID();
        String details = siteApplication.getDetails();
        Date begainTime = siteApplication.getBegainTime();
        Date    endTime = siteApplication.getEndTime();
        String departmentID = siteApplication.getDepartmentID();
        String siteManagerID = siteApplication.getSiteManagerID();
        String activityName = siteApplication.getActivityName();
        String sql = "insert into siteApplication(status, siteID, details, begainTime, endTime, " +
                "departmentID, siteManagerID, activityName) values('审批未通过', ?, ?, ?, ?, ? ,?, ?)";
        int rows = jdbcTemplate.update(sql, new Object[] {siteID, details, begainTime, endTime,
        departmentID, siteManagerID, activityName});
        return rows;
    }
}
