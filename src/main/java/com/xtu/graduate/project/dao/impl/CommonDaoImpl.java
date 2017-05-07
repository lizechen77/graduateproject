package com.xtu.graduate.project.dao.impl;

import com.xtu.graduate.project.dao.CommonDao;
import com.xtu.graduate.project.domains.SiteInfo;
import com.xtu.graduate.project.domains.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/5/3 0003.
 */
@Repository
public class CommonDaoImpl implements CommonDao{
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(CommonDaoImpl.class);
    @Autowired
    JdbcTemplate jdbcTemplate;


    @Override
    public User findUserByUserID(String userID) {
        String sql = "select * from user where userID=?";
        User user;
        try {
            user = (User)jdbcTemplate.queryForObject(sql, new Object[]{userID},
                    new RowMapper<User>() {
                        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                            User tempUser = new User();
                            tempUser.setUserID(rs.getString("userID"));
                            tempUser.setPassword(rs.getString("password"));
                            tempUser.setRoleID(rs.getString("roleID"));
                            return tempUser;
                        }
                    });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }

    @Override
    public SiteInfo findSiteInfoBySiteID(final String siteID) {
        String sql = "select * from siteInfo where siteID = ?";
        SiteInfo siteInfo;
        try {
            siteInfo = (SiteInfo)jdbcTemplate.queryForObject(sql, new Object[]{siteID},
                    new RowMapper<SiteInfo>() {
                        public SiteInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
                            SiteInfo tempSiteInfo = new SiteInfo();
                            tempSiteInfo.setSiteID(rs.getString("siteID"));
                            tempSiteInfo.setSiteName(rs.getString("siteName"));
                            tempSiteInfo.setLocale(rs.getString("locale"));
                            tempSiteInfo.setPrice(rs.getString("price"));
                            tempSiteInfo.setSiteManagerID(rs.getString("siteManagerID"));
                            return tempSiteInfo;
                        }
                    });
            } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return siteInfo;
    }
}
