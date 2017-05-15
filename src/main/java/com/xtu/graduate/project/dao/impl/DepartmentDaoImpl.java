package com.xtu.graduate.project.dao.impl;

import com.xtu.graduate.project.dao.DepartmentDao;
import com.xtu.graduate.project.domains.ActivityInfo;
import com.xtu.graduate.project.domains.CurrentPage;
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
    public static final int pageSize = 10;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public CurrentPage findAllSiteApplication(int pageNumber) {
        String sql1 = "select * from siteApplication where status = '待审批' LIMIT ?,?";
        String sql2 = "select count(*) from siteApplication where status = '待审批'";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{(pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        if (tempPageCount % 10 == 0) {
            pageCount = tempPageCount.intValue()/10;
        } else {
            pageCount = tempPageCount.intValue()/10 + 1;
        }
        CurrentPage page = new CurrentPage();
        page.setList(list);
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public CurrentPage findSiteApplicationByLocale(String locale, int pageNumber) {
        String sql1 = "select ApplicationID, status, siteApplication.siteID, details, beginTime, " +
                "endTime, departmentID, siteApplication.siteManagerID, activityName from siteapplication inner join siteInfo on " +
                "siteapplication.siteID = siteInfo.siteID where siteInfo.locale = ? and status = '待审批' LIMIT ?,?";
        String sql2 = "select count(*) from siteapplication inner join siteInfo on " +
                "siteapplication.siteID = siteInfo.siteID where siteInfo.locale = ? and status = '待审批'";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{locale, (pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, new Object[]{locale}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        if (tempPageCount % 10 == 0) {
            pageCount = tempPageCount.intValue()/10;
        } else {
            pageCount = tempPageCount.intValue()/10 + 1;
        }
        CurrentPage page = new CurrentPage();
        page.setList(list);
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public CurrentPage findSiteApplicationByBeginTime(Date beginTime1, Date beginTime2, int pageNumber) {
        String sql1 = "select * from siteApplication where beginTime between ? and ? and status = '待审批' LIMIT ?,?";
        String sql2 = "select count(*) from siteApplication where beginTime between ? and ? and status = '待审批'";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{beginTime1, beginTime2, (pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, new Object[]{beginTime1, beginTime2}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        if (tempPageCount % 10 == 0) {
            pageCount = tempPageCount.intValue()/10;
        } else {
            pageCount = tempPageCount.intValue()/10 + 1;
        }
        CurrentPage page = new CurrentPage();
        page.setList(list);
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public CurrentPage findSiteApplicationByLocaleAndBeginTime(String locale, Date beginTime1, Date beginTime2, int pageNumber) {
        String sql1 = "select ApplicationID, status, siteApplication.siteID, details, beginTime, " +
                "endTime, departmentID, siteApplication.siteManagerID, activityName from siteapplication inner join siteInfo on " +
                "siteapplication.siteID = siteInfo.siteID where siteInfo.locale = ? and beginTime between ? and ? " +
                "and status = '待审批' LIMIT ?,?";
        String sql2 = "select count(*) from siteapplication inner join siteInfo on " +
                "siteapplication.siteID = siteInfo.siteID where siteInfo.locale = ? and beginTime between ? and ? " +
                "and status = '待审批' ";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{locale,beginTime1, beginTime2, (pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, new Object[]{locale,beginTime1, beginTime2}, Integer.class);

        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        if (tempPageCount % 10 == 0) {
            pageCount = tempPageCount.intValue()/10;
        } else {
            pageCount = tempPageCount.intValue()/10 + 1;
        }
        CurrentPage page = new CurrentPage();
        page.setList(list);
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public CurrentPage findSiteApplicationByDepartmentID(String departmentID, int pageNumber) {
        String sql1 = "select ApplicationID, status, siteID, details, beginTime, " +
                "endTime, departmentID, siteManagerID, activityName from siteApplication " +
                "where departmentID = ? LIMIT ?,?";
        String sql2 = "select count(*) from siteApplication " +
                "where departmentID = ?";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{departmentID, (pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, new Object[]{departmentID}, Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        if (tempPageCount % 10 == 0) {
            pageCount = tempPageCount.intValue()/10;
        } else {
            pageCount = tempPageCount.intValue()/10 + 1;
        }
        CurrentPage page = new CurrentPage();
        page.setList(list);
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public Integer createSiteApplication(SiteApplication siteApplication) {
        String siteID = siteApplication.getSiteID();
        String details = siteApplication.getDetails();
        Date beginTime = siteApplication.getBeginTime();
        Date    endTime = siteApplication.getEndTime();
        String departmentID = siteApplication.getDepartmentID();
        String siteManagerID = siteApplication.getSiteManagerID();
        String activityName = siteApplication.getActivityName();
        String sql = "insert into siteApplication(status, siteID, details, beginTime, endTime, " +
                "departmentID, siteManagerID, activityName) values('待审批', ?, ?, ?, ?, ? ,?, ?)";
        int rows = jdbcTemplate.update(sql, new Object[] {siteID, details, beginTime, endTime,
        departmentID, siteManagerID, activityName});
        return rows;
    }
}
