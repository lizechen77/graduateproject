package com.xtu.graduate.project.dao.impl;

import com.xtu.graduate.project.dao.UserDao;
import com.xtu.graduate.project.domains.ActivityInfo;
import com.xtu.graduate.project.domains.Role;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.domains.User;
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
    public List<Map<String,Object>> findActivityInfoByActivityName(String activityName, int pageNumber) {
        String sql = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        List<Map<String, Object>> list;
        try {
            list =  jdbcTemplate.queryForList(sql, new Object[]{activityName, (pageNumber-1)*pageSize, (pageNumber)*pageSize});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过locale查找activityInfo
    @Override
    public List<Map<String,Object>> findActivityInfoByLocale(String locale, int pageNumber) {
        String sql = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale =?)" +
                "inner join user on activityInfo.departmentID = user.userID LIMIT ?,?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{locale,  (pageNumber-1)*pageSize, (pageNumber)*pageSize});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过beginTime查找activityInfo
    @Override
    public List<Map<String,Object>> findActivityInfoByBeginTime(Date beginTime1, Date beginTime2, int pageNumber) {
        String sql = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{beginTime1, beginTime2, (pageNumber-1)*pageSize, (pageNumber)*pageSize});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过activityName和locale查找activityInfo
    @Override
    public List<Map<String, Object>> findActivityInfoByActivityNameAndLocale(String activityName, String locale, int pageNumber) {
        String sql = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where siteInfo.locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{activityName, locale, (pageNumber-1)*pageSize, (pageNumber)*pageSize});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过activityName和beginTime查找activityInfo
    @Override
    public List<Map<String, Object>> findActivityInfoByActivityNameAndBeginTime(String activityName,
                                                                                 Date beginTime1, Date beginTime2, int pageNumber) {
        String sql = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=? " +
                "and beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{activityName, beginTime1, beginTime2, (pageNumber-1)*pageSize, (pageNumber)*pageSize});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过locale和beginTime查找activityInfo
    @Override
    public List<Map<String, Object>> findActivityInfoByLocaleAndBeginTime(String locale, Date beginTime1,Date beginTime2, int pageNumber) {
        String sql = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{beginTime1, beginTime2, locale, (pageNumber-1)*pageSize, (pageNumber)*pageSize});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过activityName和locale和beginTime查找activityInfo
    @Override
    public List<Map<String, Object>> findActivityInfoByActivityNameAndLocaleAndBeginTime(String activityName, String locale, Date beginTime1, Date beginTime2, int pageNumber) {
        String sql = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where siteApplication.activityName=? " +
                "and beginTime between ? and ?)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID where locale=?)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        List<Map<String, Object>> list;
        try {
            list = this.jdbcTemplate.queryForList(sql, new Object[]{activityName, beginTime1, beginTime2, locale, (pageNumber-1)*pageSize, (pageNumber)*pageSize});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findAllActivityInfo(int pageNumber) {
        String sql = "select activityInfo.activityID, activityInfo.applicationID, activityInfo.departmentID, " +
                "activityInfo.siteID, siteApplication.activityName, siteApplication.details, siteApplication.beginTime, " +
                "user.userName, siteInfo.siteName from ((activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID)" +
                "inner join siteInfo on activityInfo.siteID = siteInfo.siteID)inner join user on activityInfo.departmentID = " +
                "user.userID LIMIT ?,?";
        List<Map<String, Object>> list;
        try {
            list = this.jdbcTemplate.queryForList(sql, new Object[]{(pageNumber-1)*pageSize, (pageNumber)*pageSize});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }


}
