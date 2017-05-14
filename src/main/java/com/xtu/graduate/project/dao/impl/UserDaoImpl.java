package com.xtu.graduate.project.dao.impl;

import com.xtu.graduate.project.dao.UserDao;
import com.xtu.graduate.project.domains.*;
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
 * Created by Administrator on 2017/4/29 0029.
 */
@Repository
public class UserDaoImpl implements UserDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);
    public static final int pageSize = 10;
    @Autowired
    JdbcTemplate jdbcTemplate;


    //通过activityName查找activityInfo
    @Override
    public CurrentPage findActivityInfoByActivityName(String activityName, int pageNumber) {
        String sql1 = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        String sql2 = "select count(*) from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list =  jdbcTemplate.queryForList(sql1, new Object[]{activityName, (pageNumber-1)*pageSize, (pageNumber)*pageSize});
            tempPageCount = jdbcTemplate.queryForObject(sql1, new Object[]{activityName}, Integer.class);
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

    //通过locale查找activityInfo
    @Override
    public CurrentPage findActivityInfoByLocale(String locale, int pageNumber) {
        String sql1 = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale =?)" +
                "inner join user on activityInfo.departmentID = user.userID LIMIT ?,?";
        String sql2 = "select count(*) from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale =?)" +
                "inner join user on activityInfo.departmentID = user.userID";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{locale,  (pageNumber-1)*pageSize, pageSize});
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

    //通过beginTime查找activityInfo
    @Override
    public CurrentPage findActivityInfoByBeginTime(Date beginTime1, Date beginTime2, int pageNumber) {
        String sql1 = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteInfo on activityInfo.siteID = " +
                "siteInfo.siteID) inner join user on activityInfo.departmentID = user.userID) inner join siteApplication" +
                " on activityInfo.applicationID = siteApplication.applicationID where beginTime BETWEEN ? and ? " +
                "LIMIT ?,?";
        String sql2 = "select count(*) from ((activityInfo inner join siteInfo on activityInfo.siteID = " +
                "siteInfo.siteID) inner join user on activityInfo.departmentID = user.userID) inner join siteApplication" +
                " on activityInfo.applicationID = siteApplication.applicationID where beginTime BETWEEN ? and ?";
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

    //通过activityName和locale查找activityInfo
    @Override
    public CurrentPage findActivityInfoByActivityNameAndLocale(String activityName, String locale, int pageNumber) {
        String sql1 = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where siteInfo.locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        String sql2 = "select count(*) from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where siteInfo.locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{activityName, locale, (pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, new Object[]{activityName, locale}, Integer.class);
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

    //通过activityName和beginTime查找activityInfo
    @Override
    public CurrentPage findActivityInfoByActivityNameAndBeginTime(String activityName,
                                                                                 Date beginTime1, Date beginTime2, int pageNumber) {
        String sql1 = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=? " +
                "and beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        String sql2 = "select count(*) from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=? " +
                "and beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{activityName, beginTime1, beginTime2, (pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, new Object[]{activityName, beginTime1, beginTime2}, Integer.class);
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

    //通过locale和beginTime查找activityInfo
    @Override
    public CurrentPage findActivityInfoByLocaleAndBeginTime(String locale, Date beginTime1,Date beginTime2, int pageNumber) {
        String sql1 = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        String sql2 = "select count(*) from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = jdbcTemplate.queryForList(sql1, new Object[]{beginTime1, beginTime2, locale, (pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, new Object[]{beginTime1, beginTime2, locale}, Integer.class);
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

    //通过activityName和locale和beginTime查找activityInfo
    @Override
    public CurrentPage findActivityInfoByActivityNameAndLocaleAndBeginTime(String activityName, String locale, Date beginTime1, Date beginTime2, int pageNumber) {
        String sql1 = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=? " +
                "and beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";

        String sql2 = "select count(*) from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=? " +
                "and beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = this.jdbcTemplate.queryForList(sql1, new Object[]{activityName, beginTime1, beginTime2, locale, (pageNumber-1)*pageSize, pageSize});
            tempPageCount = this.jdbcTemplate.queryForObject(sql2, new Object[]{activityName, beginTime1, beginTime2, locale}, Integer.class);
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
    public CurrentPage findAllActivityInfo(int pageNumber) {
        String sql1 = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        String sql2 = "select count(*) from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID";
        List<Map<String, Object>> list;
        Integer tempPageCount;
        int pageCount;
        try {
            list = this.jdbcTemplate.queryForList(sql1, new Object[]{(pageNumber-1)*pageSize, pageSize});
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


}
