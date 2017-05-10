package com.xtu.graduate.project.dao.impl;

import com.xtu.graduate.project.dao.SiteManagerDao;
import com.xtu.graduate.project.domains.SiteApplication;
import com.xtu.graduate.project.domains.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/2 0002.
 */
@Repository
public class SiteManagerDaoImpl implements SiteManagerDao{
    @Autowired
    JdbcTemplate jdbcTemplate;
    public static final int pageSize = 10;
    @Override
    public Integer createUser(User user) {
        String userID = user.getUserID();
        String password = user.getPassword();
        String userName = user.getUserName();
        String roleID = user.getRoleID();
        String sql = "insert into user(userID, password, roleID, userName) values(?, ?, ?, ?)";
        int rows = this.jdbcTemplate.update(sql, new Object[] {userID, password, roleID, userName});
        return rows;
    }

    @Override
    public Integer deleteUser(String userID) {
        String sql = "delete from user where userID = ?";
        int rows = this.jdbcTemplate.update(sql, new Object[]{userID});
        return rows;
    }

    @Override
    public List<Map<String, Object>> findUnapprovedSiteApplication(int pageNumber) {
        String sql = "select * from siteApplication where status = '待审批' LIMIT ?,?";
        List<Map<String, Object>> list;
        try{
            list = this.jdbcTemplate.queryForList(sql, new Object[]{(pageNumber-1)*pageSize, (pageNumber)*pageSize});
        }   catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> findApprovedSiteApplication(int pageNumber) {
        String sql = "select * from siteApplication where status <> '待审批' LIMIT ?,?";
        List<Map<String, Object>> list;
        try{
            list = this.jdbcTemplate.queryForList(sql, new Object[]{(pageNumber-1)*pageSize, (pageNumber)*pageSize});
        }   catch (EmptyResultDataAccessException e) {
            return null;
        }
        return list;
    }

    @Override
    public Integer approve(String applicationID, String status) {
        String sql = "update siteApplication set status = ? where applicationID = ? LIMIT ?,?";
        int rows = this.jdbcTemplate.update(sql, new Object[]{status, applicationID});
        return rows;
    }
}
