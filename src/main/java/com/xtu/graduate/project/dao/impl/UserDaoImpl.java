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
    @Autowired
    JdbcTemplate jdbcTemplate;


    //通过activityName查找activityInfo
    @Override
    public List<Map<String,Object>> findActivityInfoByActivityName(String activityName) {
        String sql = "select siteInfo.siteID,siteApplication.applicationID,activityInfo.departmentID," +
                "activityInfo.activityID from activityInfo inner join user on activityInfo.departmentID" +
                " = user.userID where user.activityName = ?";
        List<Map<String, Object>> list;
        try {
            list =  jdbcTemplate.queryForList(sql, new Object[]{activityName});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过locale查找activityInfo
    @Override
    public List<Map<String,Object>> findActivityInfoByLocale(String locale) {
        String sql = "select siteInfo.siteID,siteApplication.applicationID,activityInfo.departmentID," +
                "activityInfo.activityID from activityInfo inner join siteInfo on activityInfo.siteID = " +
                "siteInfo.siteID where siteInfo.locale = ?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{locale});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过begainTime查找activityInfo
    @Override
    public List<Map<String,Object>> findActivityInfoByBegainTime(Date begainTime1, Date begainTime2) {
        String sql = "select siteInfo.siteID,siteApplication.applicationID,activityInfo.departmentID," +
                "activityInfo.activityID from activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where " +
                "siteApplication.begainTime between ? and ?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{begainTime1, begainTime2 });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过activityName和locale查找activityInfo
    @Override
    public List<Map<String, Object>> findActivityInfoByActivityNameAndLocale(String activityName, String locale) {
        String sql = "select siteInfo.siteID,siteApplication.applicationID,activityInfo.departmentID," +
                "activityInfo.activityID from (activityInfo inner join user on activityInfo.departmentID" +
                " = user.userID where user.activityName = ?)  inner join siteInfo on " +
                "activityInfo.siteID = siteInfo.siteID where siteInfo.locale = ?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{activityName, locale});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过activityName和begainTime查找activityInfo
    @Override
    public List<Map<String, Object>> findActivityInfoByActivityNameAndBegainTime(String activityName,
                                                                                 Date begainTime1, Date begainTime2) {
        String sql = "select siteInfo.siteID,activityInfo.applicationID,activityInfo.departmentID," +
                "activityInfo.activityID from activityInfo inner join siteApplication on activityInfo.applicationID = " +
                "siteApplication.applicationID where activityName = ? and begainTime between ? and ?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{activityName, begainTime1, begainTime2});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过locale和begainTime查找activityInfo
    @Override
    public List<Map<String, Object>> findActivityInfoByLocaleAndBegainTime(String locale, Date begainTime1,Date begainTime2) {
        String sql = "select siteInfo.siteID,siteApplication.applicationID,activityInfo.departmentID," +
                "activityInfo.activityID from (activityInfo inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where " +
                "siteApplication.begainTime between ? and ?) inner join siteInfo on activityInfo.siteID = " +
                "siteInfo.siteID where siteInfo.locale = ?;";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{begainTime1, begainTime2, locale});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    //通过activityName和locale和begainTime查找activityInfo
    @Override
    public List<Map<String, Object>> findActivityInfoByActivityNameAndLocaleAndBegainTime(String activityName, String locale, Date begainTime1, Date begainTime2) {
        String sql = "select siteInfo.siteID,siteApplication.applicationID,activityInfo.departmentID," +
                "activityInfo.activityID from ( inner join siteApplication on " +
                "activityInfo.applicationID = siteApplication.applicationID where " +
                "activityName = ? and begainTime between ? and ?) inner join siteInfo on activityInfo.siteID = " +
                "siteInfo.siteID where siteInfo.locale = ?";
        List<Map<String, Object>> list;
        try {
            list = jdbcTemplate.queryForList(sql, new Object[]{activityName, begainTime1, begainTime2, locale});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }



}
